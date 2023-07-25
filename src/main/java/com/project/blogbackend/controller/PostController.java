package com.project.blogbackend.controller;

import com.project.blogbackend.entity.Post;
import com.project.blogbackend.service.PostService;
import com.project.blogbackend.service.dto.PostCreateDTO;
import com.project.blogbackend.service.dto.PostUpdateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@Tag(name = "Post", description = "Post actions api documentation.")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts(@RequestParam Optional<Long> id){
        return this.postService.getAllPosts(id);

    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id){
        return this.postService.getPostById(id);
    }

    @PostMapping
    public Post savePost(@RequestBody PostCreateDTO newPostCreateDTO){
        return this.postService.savePost(newPostCreateDTO);
    }

    @PutMapping("/{id}")
    public Post updatePost(@RequestBody PostUpdateDTO postUpdateDTO, @PathVariable Long id){
        return this.postService.updatePost(postUpdateDTO,id);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){
        this.postService.deletePost(id);
    }
}
