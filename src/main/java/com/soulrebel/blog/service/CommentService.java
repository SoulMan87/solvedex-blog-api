package com.soulrebel.blog.service;

import com.soulrebel.blog.rest.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(final long postId, final CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(final long postId);

    CommentDto getCommentById(final long postId, final long commentId);

    CommentDto updateComment(final long postId, long commentId, final CommentDto commentDto);

    String deleteComment(final long postId, final long commentId);
}
