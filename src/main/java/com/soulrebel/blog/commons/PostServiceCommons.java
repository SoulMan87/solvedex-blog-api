package com.soulrebel.blog.commons;

import com.soulrebel.blog.entity.Post;
import com.soulrebel.blog.rest.PostDto;
import com.soulrebel.blog.rest.model.PostResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class PostServiceCommons {

    public static final String CATEGORY = "Category";
    public static final String ID = "id";
    public static final String PAGE_NUMBER_CANNOT_BE_LESS_THAN_ZERO = "Page number cannot be less than zero!";
    public static final String INVALID_SORT_DIRECTION = "Invalid sort direction: ";
    public static final String POST = "Post";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private final ModelMapper modelMapper;

    protected Post mapToEntity(PostDto postDto) {
        return modelMapper.map (postDto, Post.class);
    }

    protected PostDto mapToDto(Post post) {
        return modelMapper.map (post, PostDto.class);
    }

    protected PostResponse postResponseBuilder(List<PostDto> listOfPosts, Page<Post> posts) {
        return PostResponse.builder ()
                .content (listOfPosts)
                .pageNo (posts.getNumber ())
                .pageSize (posts.getSize ())
                .totalElements (posts.getTotalElements ())
                .totalPages (posts.getTotalPages ())
                .last (posts.isLast ())
                .build ();
    }
}

