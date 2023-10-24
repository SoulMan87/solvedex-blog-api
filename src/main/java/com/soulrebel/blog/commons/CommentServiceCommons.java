package com.soulrebel.blog.commons;

import com.soulrebel.blog.entity.Comment;
import com.soulrebel.blog.exception.BlogAPIException;
import com.soulrebel.blog.exception.ResourceNotFoundException;
import com.soulrebel.blog.repository.CommentRepository;
import com.soulrebel.blog.repository.PostRepository;
import com.soulrebel.blog.rest.CommentDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class CommentServiceCommons {

    public static final String POST = "Post";
    public static final String ID = "id";
    public static final String COMMENT = "Comment";
    public static final String COMMENT_DOES_NOT_BELONGS_TO_POST = "Comment does not belong to post";
    public static final String COMMENT_DELETED_SUCCESSFULLY = "Comment deleted successfully";


    protected final ModelMapper modelMapper;
    protected final PostRepository postRepository;
    protected final CommentRepository commentRepository;

    protected CommentDto mapToDto(Comment comment) {
        return modelMapper.map (comment, CommentDto.class);
    }

    protected Comment mapToEntity(CommentDto commentDto) {
        return modelMapper.map (commentDto, Comment.class);
    }

    protected Comment verifyPostCommentRelation(long postId, long commentId) {
        final var post = postRepository.findById (postId)
                .orElseThrow (() -> new ResourceNotFoundException (POST, ID, postId));

        final var comment = commentRepository.findById (commentId)
                .orElseThrow (() -> new ResourceNotFoundException (COMMENT, ID, commentId));

        if (!comment.getPost ().getId ().equals (post.getId ())) {
            throw new BlogAPIException (HttpStatus.BAD_REQUEST, COMMENT_DOES_NOT_BELONGS_TO_POST);
        }
        return comment;
    }

    protected <T> T validateAndUpdate(T value, Consumer<T> updateFunction) {
        if (value != null) {
            updateFunction.accept (value);
        }
        return value;
    }
}
