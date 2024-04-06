package ru.rvorozheikin.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rvorozheikin.homework.dto.comment.CreateCommentDto;
import ru.rvorozheikin.homework.dto.comment.FullCommentDto;
import ru.rvorozheikin.homework.entity.Ad;
import ru.rvorozheikin.homework.entity.Comment;
import ru.rvorozheikin.homework.entity.User;
import ru.rvorozheikin.homework.mapper.CommentMapper;
import ru.rvorozheikin.homework.repository.CommentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentRepository repository;
    @Mock
    private UserService userService;
    @Mock
    private AdService adService;

    @Spy
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void findAllByAdIdShouldCallRepositoryFindAllByAdPkMethod() {
        when(repository.findAllByAdPk(anyInt())).thenReturn(any());

        commentService.findAllByAdId(0);

        verify(repository, only()).findAllByAdPk(anyInt());
    }

    @Test
    public void findByIdShouldCallRepositoryFindByIdMethod() {
        when(repository.findById(any())).thenReturn(any());

        commentService.findById(0);

        verify(repository, only()).findById(any());
    }


    @Test
    public void createCommentShouldSaveAndReturnNewComment() {
        CreateCommentDto testCCD = new CreateCommentDto();
        testCCD.setText("text");

        when(userService.findUserByEmail(anyString())).thenReturn(new User());
        when(adService.findById(anyInt())).thenReturn(Optional.of(new Ad()));
        when(repository.save(any())).thenReturn(new Comment());

        Comment result = commentService.createComment("email", 0, testCCD);

        verify(repository, times(1)).save(any());
        assertInstanceOf(Comment.class, result);
    }


    @Test
    public void editCommentShouldSaveEditedCommentAndReturnFullCommentDto() {
        FullCommentDto testFCD = new FullCommentDto();
        testFCD.setText("newText");

        Comment testTargetComment = new Comment(new Ad(), new User(), 1223L, "text");
        Comment testEditedComment = new Comment(new Ad(), new User(), 5354364L, testFCD.getText());
        FullCommentDto testEditedFCD = commentMapper.mapCommentToFullCommentDto(testEditedComment);

        when(repository.findById(any())).thenReturn(Optional.of(testTargetComment));
        when(repository.save(any())).thenReturn(testEditedComment);

        FullCommentDto result = commentService.updateComment(0, testFCD);

        verify(repository, times(1)).save(any());
        assertEquals(testEditedFCD, result);
    }

    @Test
    public void deleteByIdShouldOnlyCallRepositoryDeleteById() {
        doNothing().when(repository).deleteById(any());

        commentService.deleteById(0);

        verify(repository, only()).deleteById(any());
    }
}