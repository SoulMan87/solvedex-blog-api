package com.soulrebel.blog.controller;

import com.soulrebel.blog.rest.CommentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Validated
@Tag(name = "Comments",
        description = "REST API designed to facilitate the management and interaction with Comments resources")
@RequestMapping("/posts")
public interface ICommentController {

    @Operation(
            summary = "Create a comment",
            description = "Create a comment object.",
            tags = {"Post Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CommentDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/{postId}/comments")
    ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
                                             @PathVariable(value = "postId") long postId);
    @Operation(
            summary = "Retrieve comments by post id",
            description = "Get comments objects by specifying its post id.",
            tags = {"Get Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CommentDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{postId}/comments")
    ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") long postId);

    @Operation(
            summary = "Retrieve a comment by post id and comment id",
            description = "Get a comment object by specifying their ids.",
            tags = {"Get Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CommentDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{postId}/comments/{commentId}")
    ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
                                              @PathVariable(value = "commentId") long commentId);
    @Operation(
            summary = "Update a comment by post id and comment id",
            description = "Update a comment object.",
            tags = {"Patch Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CommentDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PatchMapping("/{postId}/comments/{commentId}")
    ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,
                                             @PathVariable(value = "commentId") long commentId,
                                             @RequestBody CommentDto commentDto);

    @Operation(
            summary = "Delete a comment by post id and comment id",
            description = "Delete a comment object.",
            tags = {"Delete Method"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CommentDto.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/{postId}/comments/{commentId}")
    ResponseEntity<Map<String, String>> deleteComment(@PathVariable(value = "postId") long postId,
                                                      @PathVariable(value = "commentId") long commentId);
}
