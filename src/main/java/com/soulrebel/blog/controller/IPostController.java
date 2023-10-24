package com.soulrebel.blog.controller;

import com.soulrebel.blog.commons.Constants;
import com.soulrebel.blog.rest.PostDto;
import com.soulrebel.blog.rest.model.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Validated
@Tag(name = "Posts", description = "REST API designed to facilitate the management and interaction with Post resources")
@RequestMapping("/posts")
public interface IPostController {

    @Operation(
            summary = "Create a post",
            description = "Create a post object.",
            tags = {"Post Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PostDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto);

    @Operation(
            summary = "Retrieve all post ",
            description = "Get all post objects.",
            tags = {"Get Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PostResponse.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping
    ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue =
            Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo, @RequestParam(value = "pageSize",
            defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize, @RequestParam(value = "sortBy",
            defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDirection",
            defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection);

    @Operation(
            summary = "Retrieve a post by Id",
            description = "Get a post object by specifying its id.",
            tags = {"Get Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PostDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id);

    @Operation(
            summary = "Update a post by id ",
            description = "Update a post object.",
            tags = {"Patch Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PostDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDto postDto);

    @Operation(
            summary = "Delete a post ",
            description = "Delete a post object.",
            tags = {"Delete Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PostDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, String>> deletePost(@PathVariable(name = "id") final Long id);

    @Operation(
            summary = "Retrieve post by categories ",
            description = "Get all post by categories objects.",
            tags = {"Get Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PostDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/category/{id}")
    ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id") Long id);
}
