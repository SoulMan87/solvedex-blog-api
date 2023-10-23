package com.soulrebel.blog.service;

import com.soulrebel.blog.rest.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    String deleteComment(long postId, long commentId);
}
