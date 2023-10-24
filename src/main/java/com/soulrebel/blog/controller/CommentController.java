package com.soulrebel.blog.controller;

import com.soulrebel.blog.rest.CommentDto;
import com.soulrebel.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.soulrebel.blog.commons.Constants.MESSAGE;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController implements ICommentController {

    private final CommentService commentService;

    @Override
    public ResponseEntity<CommentDto> createComment(final CommentDto commentDto, final long postId) {
        return new ResponseEntity<> (commentService.createComment (postId, commentDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(final long postId) {
        return new ResponseEntity<> (commentService.getCommentsByPostId (postId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDto> getCommentById(final long postId, final long commentId) {
        return new ResponseEntity<> (commentService.getCommentById (postId, commentId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDto> updateComment(final long postId, final long commentId,
                                                    final CommentDto commentDto) {
        return new ResponseEntity<> (commentService.updateComment (postId, commentId, commentDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, String>> deleteComment(long postId, long commentId) {
        return new ResponseEntity<> (Map.of (
                MESSAGE, commentService.deleteComment (postId, commentId)), HttpStatus.OK);
    }
}
