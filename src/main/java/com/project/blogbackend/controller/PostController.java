package com.project.blogbackend.controller;

import com.project.blogbackend.entity.Post;
import com.project.blogbackend.repository.PostRepository;
import com.project.blogbackend.service.PostService;
import com.project.blogbackend.service.dto.PostCreateDTO;
import com.project.blogbackend.service.dto.PostUpdateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@Tag(name = "Post", description = "actions api documentation.")
public class PostController {
    private final Logger log= LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final PostRepository postRepository;

    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam Optional<Long> userId){
        log.debug("REST request to get all Posts or Posts is userId: {}",userId);
        List<Post> posts=this.postService.getAllPosts(userId);
        if (posts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        log.debug("REST request to get Post");
        Post post=this.postService.getPostById(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody PostCreateDTO newPostCreateDTO){
        log.debug("REST request to save Post : {}", newPostCreateDTO);
        try {
            Post post=this.postService.savePost(newPostCreateDTO);
            return new ResponseEntity<>(post,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@RequestBody PostUpdateDTO postUpdateDTO, @PathVariable Long id){
        log.debug("REST request to update User : {}, {}", id, postUpdateDTO);
        Post post=this.postService.updatePost(postUpdateDTO,id);
        if (!this.postRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        log.debug("REST request to delete Post : {}", id);
        try {
            this.postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Exception: {}",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
