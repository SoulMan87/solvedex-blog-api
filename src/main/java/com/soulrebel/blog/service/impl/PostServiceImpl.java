package com.soulrebel.blog.service.impl;

import com.soulrebel.blog.commons.PostServiceCommons;
import com.soulrebel.blog.entity.Category;
import com.soulrebel.blog.entity.Post;
import com.soulrebel.blog.exception.ResourceNotFoundException;
import com.soulrebel.blog.repository.CategoryRepository;
import com.soulrebel.blog.repository.PostRepository;
import com.soulrebel.blog.rest.PostDto;
import com.soulrebel.blog.rest.model.PostResponse;
import com.soulrebel.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl extends PostServiceCommons implements PostService {
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository,
                           ModelMapper modelMapper) {
        super (postRepository, categoryRepository, modelMapper);
    }
    @Override
    public PostDto createPost(final PostDto postDto) {
        return categoryRepository.findById (postDto.getCategoryId ())
                .map (category -> {
                    final var post = mapToEntity (postDto);
                    post.setCategory (category);
                    return mapToDto (postRepository.save (post));
                })
                .orElseThrow (() -> new ResourceNotFoundException (CATEGORY, ID, postDto.getCategoryId ()));
    }

    @Override
    public PostResponse getAllPosts(final int pageNumber, final int pageSize, final String sortBy,
                                    final String sortDirection) {
        Optional.of (pageNumber)
                .filter (page -> page >= 0)
                .orElseThrow (() -> new IllegalArgumentException (PAGE_NUMBER_CANNOT_BE_LESS_THAN_ZERO));

        final Sort sort = switch (sortDirection) {
            case ASC -> Sort.by (sortBy).ascending ();
            case DESC -> Sort.by (sortBy).descending ();
            default -> throw new IllegalArgumentException (INVALID_SORT_DIRECTION + sortDirection);
        };

        final Pageable pageable = PageRequest.of (pageNumber, pageSize, sort);

        final Page<Post> posts = postRepository.findAll (pageable);

        final List<PostDto> listOfPosts = posts.get ()
                .map (this::mapToDto)
                .toList ();

        return postResponseBuilder (listOfPosts, posts);

    }

    @Override
    public PostDto getPostById(final Long id) {
        return postRepository.findById (id)
                .map (this::mapToDto)
                .orElseThrow (() -> new ResourceNotFoundException (POST, ID, id));
    }

    @Override
    public PostDto updatePost(final Long id, final PostDto postDto) {
        final Post post = postRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException (POST, ID, id));

        final Category category = Optional.ofNullable (postDto.getCategoryId ())
                .map (categoryRepository::findById)
                .filter (Optional::isPresent)
                .map (Optional::get)
                .orElseThrow (() -> new ResourceNotFoundException (CATEGORY, ID, postDto.getCategoryId ()));

        Optional.ofNullable (postDto.getTitle ()).ifPresent (post::setTitle);
        Optional.ofNullable (postDto.getDescription ()).ifPresent (post::setDescription);
        Optional.ofNullable (postDto.getContent ()).ifPresent (post::setContent);

        if (postDto.getCategoryId () != null) {
            post.setCategory (category);
        }

        final Post updatedPost = postRepository.save (post);

        return mapToDto (updatedPost);
    }

    @Override
    public void deletePost(final Long id) {
        postRepository.findById (id).ifPresentOrElse (postRepository::delete, () -> {
            throw new ResourceNotFoundException (POST, ID, id);
        });
    }

    @Override
    public List<PostDto> getPostsByCategoryId(final Long categoryId) {
        final var category = categoryRepository.findById (categoryId)
                .orElseThrow (() -> new ResourceNotFoundException (CATEGORY, ID, categoryId));

        return postRepository.findAllByCategoryId (category.getId ())
                .stream ()
                .map (this::mapToDto)
                .toList ();
    }

}
