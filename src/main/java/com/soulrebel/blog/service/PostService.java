package com.soulrebel.blog.service;

import com.soulrebel.blog.rest.PostDto;
import com.soulrebel.blog.rest.model.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(final PostDto postDto);

    PostResponse getAllPosts(final int pageNumber, final int pageSize, final String sortBy, final String sortDirection);

    PostDto getPostById(final Long id);

    PostDto updatePost(final Long id, final PostDto postDto);

    void deletePost(final Long id);

    List<PostDto> getPostsByCategoryId(final Long categoryId);
}
