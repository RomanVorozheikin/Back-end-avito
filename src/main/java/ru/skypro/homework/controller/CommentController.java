package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

@RestController
@RequestMapping("/ads")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class CommentController {
    private final CommentMapper commentMapper;
    private final AdService adService;
    private final CommentService commentService;
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getAdComment(@PathVariable("id") Integer adId) {
        if (isAdExisted(adId)) {
            return ResponseEntity.ok(commentMapper.mapCommentListToWrapper(commentService.findAllByAdId(adId)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private boolean isAdExisted(Integer adId) {
        return adService.findById(adId).isPresent();
    }
}
