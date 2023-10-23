package com.soulrebel.blog.service;

import com.soulrebel.blog.rest.PostDto;
import com.soulrebel.blog.rest.model.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDirection);

    PostDto getPostById(Long id);

    PostDto updatePost(Long id, PostDto postDto);

    void deletePost(Long id);

    List<PostDto> getPostsByCategoryId(Long categoryId);
}
