package ru.rvorozheikin.homework.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.rvorozheikin.homework.entity.Ad;
import ru.rvorozheikin.homework.entity.Comment;
import ru.rvorozheikin.homework.service.AdService;
import ru.rvorozheikin.homework.service.UserService;
import ru.rvorozheikin.homework.dto.Role;
import ru.rvorozheikin.homework.dto.comment.FullCommentDto;
import ru.rvorozheikin.homework.dto.comment.ResponseWrapperComment;
import ru.rvorozheikin.homework.entity.User;
import ru.rvorozheikin.homework.mapper.CommentMapper;
import ru.rvorozheikin.homework.service.CommentService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author rvorozheikin
 */
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @Spy
    private CommentMapper commentMapper;
    @Mock
    private AdService adService;
    @Mock
    private CommentService commentService;
    @Mock
    private UserService userService;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private CommentController commentController;

    @Test
    public void testGetAdCommentStatusOK() {
        User testAuthor = new User(
                "author", "firstName", "lastName", "phone", Role.USER, "image", "passw", null, null);
        Ad testAd = new Ad(
                10, testAuthor, "descr", "image", 1123, "title", null);
        Comment testComment = new Comment(
                new Ad(), new User(), 123L, "text");
        List<Comment> testListCom = List.of(testComment);
        Optional<Ad> adOpt = Optional.of(testAd);

        when(commentService.findAllByAdId(testAd.getPk())).thenReturn(testListCom);
        when(adService.findById(testAd.getPk())).thenReturn(adOpt);

        ResponseEntity<ResponseWrapperComment> response = commentController.getAdComment(testAd.getPk());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createCommentAdStatusOK() {
        User testAuthor = new User(
                "author", "firstName", "lastName", "phone", Role.USER, "image", "passw", null, null);
        Ad testAd = new Ad(
                10, testAuthor, "descr", "image", 1123, "title", null);
        Comment testComment = new Comment(
                testAd, testAuthor, 123L, "text");
        Optional<Ad> adOptionalTest = Optional.of(testAd);

        when(commentService.createComment(testAuthor.getEmail(), testAd.getPk(), null)).thenReturn(testComment);
        when(authentication.getName()).thenReturn(testAuthor.getEmail());
        when(adService.findById(testAd.getPk())).thenReturn(adOptionalTest);

        ResponseEntity<FullCommentDto> response = commentController.createCommentsAd(authentication, testAd.getPk(), null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}