package com.soulrebel.blog.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Schema(name = "PostDto", description = "A DTO that represents a Post resource with comments and category")
public class PostDto {

    private long id;

    @NotEmpty(message = "Title is required. It should not be empty.")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters long.")
    private String title;

    @NotEmpty(message = "Description is required. It should not be empty.")
    @Size(min = 3, max = 50, message = "Description must be between 3 and 50 characters long.")
    private String description;

    @NotEmpty(message = "Content is required. It should not be empty.")
    @Size(min = 3, max = 500, message = "Content must be between 3 and 500 characters long.")
    private String content;

    private Set<CommentDto> comments;

    @NotNull(message = "Category ID is required. It should not be null.")
    private Long categoryId;
}

