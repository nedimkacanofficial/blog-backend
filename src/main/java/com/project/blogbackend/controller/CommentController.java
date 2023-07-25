package com.project.blogbackend.controller;

import com.project.blogbackend.entity.Comment;
import com.project.blogbackend.service.CommentService;
import com.project.blogbackend.service.dto.CommentCreateDTO;
import com.project.blogbackend.service.dto.CommentUpdateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment", description = "Comment actions api documentation.")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComments(@RequestParam Optional<Long> postId, @RequestParam Optional<Long> userId){
        return this.commentService.getAllComments(postId,userId);
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id){
        return this.commentService.getCommentById(id);
    }

    @PostMapping
    public Comment saveComment(@RequestBody CommentCreateDTO commentCreateDTO){
        return this.commentService.saveComment(commentCreateDTO);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@RequestBody CommentUpdateDTO commentUpdateDTO, @PathVariable Long id){
        return this.commentService.updateComment(commentUpdateDTO,id);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        this.commentService.deleteComment(id);
    }
}
