package com.soulrebel.blog.controller;

import com.soulrebel.blog.rest.PostDto;
import com.soulrebel.blog.rest.model.PostResponse;
import com.soulrebel.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.soulrebel.blog.commons.Constants.MESSAGE;
import static com.soulrebel.blog.commons.Constants.POST_DELETED;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostController implements IPostController {

    private final PostService postService;

    @Override
    public ResponseEntity<PostDto> createPost(final PostDto postDto) {
        return new ResponseEntity<> (postService.createPost (postDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PostResponse> getAllPosts(final int pageNo, final int pageSize, final String sortBy,
                                                    final String sortDirection) {
        return new ResponseEntity<> (postService.getAllPosts (pageNo, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostDto> getPostById(final Long id) {
        return new ResponseEntity<> (postService.getPostById (id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostDto> updatePost(final Long id, final PostDto postDto) {
        return new ResponseEntity<> (postService.updatePost (id, postDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, String>> deletePost(final Long id) {
        postService.deletePost (id);
        return new ResponseEntity<> (Map.of (MESSAGE, POST_DELETED), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PostDto>> getPostsByCategory(final Long id) {
        return new ResponseEntity<> (postService.getPostsByCategoryId (id), HttpStatus.OK);
    }
}
