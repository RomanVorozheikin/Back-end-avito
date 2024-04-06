package ru.rvorozheikin.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rvorozheikin.homework.entity.Ad;
import ru.rvorozheikin.homework.entity.Comment;
import ru.rvorozheikin.homework.exception.NonExistentAdException;
import ru.rvorozheikin.homework.exception.NonExistentCommentException;
import ru.rvorozheikin.homework.dto.comment.CreateCommentDto;
import ru.rvorozheikin.homework.dto.comment.FullCommentDto;
import ru.rvorozheikin.homework.entity.User;
import ru.rvorozheikin.homework.mapper.CommentMapper;
import ru.rvorozheikin.homework.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author rvorozheikin
 */
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AdService adService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    /**
     * Return all comments of target ad
     * @param adId id of target ad
     * @return {@link List} of {@link Comment}
     * @author rvorozheikin
     */
    public List<Comment> findAllByAdId(Integer adId) {
        return commentRepository.findAllByAdPk(adId);
    }

    /**
     * Create new comment
     * @param email author's username (email)
     * @param createCommentDto object with new comment's data
     * @param adId id of target ad
     * @return created {@link Comment}
     * @author rvorozheikin
     */
    public Comment createComment(String email, Integer adId, CreateCommentDto createCommentDto) {
        User author = userService.findUserByEmail(email);
        Ad targetAd = adService.findById(adId).orElseThrow(NonExistentAdException::new);

        return save(new Comment(
                targetAd,
                author,
                System.currentTimeMillis(),
                createCommentDto.getText()
        ));
    }

    /**
     * Save comment
     * @param comment target {@link Comment}
     * @return saved {@link Comment}
     * @author rvorozheikin
     */
    private Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * Return comment with target id
     * @param commentId target comment's id
     * @return {@link Optional} with {@link Comment} if target comment exist, <br>
     * {@link Optional#empty()} otherwise
     * @author rvorozheikin
     */
    public Optional<Comment> findById(Integer commentId) {
        return commentRepository.findById(commentId);
    }

    /**
     * Delete target comment
     * @param commentId target comment's id
     * @author rvorozheikin
     */
    public void deleteById(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    /**
     * Update comment
     * @param commentId id of target comment
     * @param fullCommentDto object with new comment's data
     * @return edited comment in {@link FullCommentDto} instance
     * @author rvorozheikin
     */
    public FullCommentDto updateComment(Integer commentId, FullCommentDto fullCommentDto) {
        Comment targetComment = findById(commentId).orElseThrow(NonExistentCommentException::new);
        targetComment.setText(fullCommentDto.getText());
        targetComment.setCreatingTime(System.currentTimeMillis());
        return commentMapper.mapCommentToFullCommentDto(save(targetComment));
    }
}
