package com.soulrebel.blog.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private long id;

    @NotEmpty(message = "Name is required. It should not be empty.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
    private String name;

    @Email(message = "Email should be valid. Try again")
    private String email;

    @NotEmpty(message = "Body is required. It should not be empty.")
    @Size(min = 3, max = 50, message = "Body must be between 3 and 50 characters long")
    private String body;
}
