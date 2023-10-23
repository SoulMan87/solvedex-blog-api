package com.soulrebel.blog.service.impl;

import com.soulrebel.blog.commons.CommentServiceCommons;
import com.soulrebel.blog.exception.ResourceNotFoundException;
import com.soulrebel.blog.repository.CommentRepository;
import com.soulrebel.blog.repository.PostRepository;
import com.soulrebel.blog.rest.CommentDto;
import com.soulrebel.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CommentServiceImpl extends CommentServiceCommons implements CommentService {

    public CommentServiceImpl(ModelMapper modelMapper, PostRepository postRepository,
                              CommentRepository commentRepository) {
        super (modelMapper, postRepository, commentRepository);
    }

    @Override
    public CommentDto createComment(final long postId, final CommentDto commentDto) {
        final var comment = mapToEntity (commentDto);
        return postRepository.findById (postId)
                .map (post -> {
                    comment.setPost (post);
                    return commentRepository.save (comment);
                })
                .map (this::mapToDto)
                .orElseThrow (() -> new ResourceNotFoundException (POST, ID, postId));
    }

    @Override
    public List<CommentDto> getCommentsByPostId(final long postId) {
        return commentRepository.findByPostId (postId).stream ()
                .map (this::mapToDto)
                .toList ();
    }

    @Override
    public CommentDto getCommentById(final long postId, final long commentId) {
        return Optional.of (verifyPostCommentRelation (postId, commentId))
                .map (this::mapToDto)
                .orElseThrow (() -> new ResourceNotFoundException (COMMENT, ID, commentId));
    }

    @Override
    public CommentDto updateComment(final long postId, final long commentId, final CommentDto commentDto) {
        final var comment = verifyPostCommentRelation (postId, commentId);

        Stream.of (
                validateAndUpdate (commentDto.getBody (), comment::setBody),
                validateAndUpdate (commentDto.getEmail (), comment::setEmail),
                validateAndUpdate (commentDto.getBody (), comment::setBody)
        ).findFirst ();

        final var updatedComment = commentRepository.save (comment);

        return mapToDto (updatedComment);
    }

    @Override
    public String deleteComment(final long postId, final long commentId) {
        final var comment = verifyPostCommentRelation (postId, commentId);

        commentRepository.delete (comment);

        return COMMENT_DELETED_SUCCESSFULLY;
    }
}
