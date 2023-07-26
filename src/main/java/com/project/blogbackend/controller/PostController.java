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
    private final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final PostRepository postRepository;

    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    /**
     * Retrieves all the Posts or Posts associated with a specific userId from the database.
     *
     * @param userId An optional parameter representing the user ID. If provided, the method will retrieve
     *               Posts associated with the given userId. If not provided, all Posts in the database will be fetched.
     * @return A ResponseEntity containing a list of Post objects if successful, or HttpStatus.NOT_FOUND if no Posts are found.
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam Optional<Long> userId) {
        log.debug("REST request to get all Posts or Posts is userId: {}", userId);
        List<Post> posts = this.postService.getAllPosts(userId);
        if (posts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Retrieves a specific Post from the database by its ID.
     *
     * @param id The ID of the Post to retrieve.
     * @return A ResponseEntity containing the Post object if found, or HttpStatus.NOT_FOUND if the Post with the given ID does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        log.debug("REST request to get Post Id {}",id);
        Post post = this.postService.getPostById(id);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    /**
     * Creates and saves a new Post based on the provided PostCreateDTO in the database.
     *
     * @param newPostCreateDTO The data transfer object containing the information to create the new Post.
     * @return A ResponseEntity containing the newly created Post object if successful, or HttpStatus.INTERNAL_SERVER_ERROR if an error occurs.
     */
    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody PostCreateDTO newPostCreateDTO) {
        log.debug("REST request to save Post : {}", newPostCreateDTO);
        try {
            Post post = this.postService.savePost(newPostCreateDTO);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing Post in the database with the provided data in the PostUpdateDTO.
     *
     * @param postUpdateDTO The data transfer object containing the information to update the Post.
     * @param id            The ID of the Post to be updated.
     * @return A ResponseEntity containing the updated Post object if successful, HttpStatus.NOT_FOUND if the Post with the given ID does not exist,
     * or HttpStatus.OK if the update operation was successful.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@RequestBody PostUpdateDTO postUpdateDTO, @PathVariable Long id) {
        log.debug("REST request to update User : {}, {}", id, postUpdateDTO);
        Post post = this.postService.updatePost(postUpdateDTO, id);
        if (!this.postRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    /**
     * Deletes a Post from the database with the given ID.
     *
     * @param id The ID of the Post to be deleted.
     * @return A ResponseEntity with no content (HttpStatus.OK) if the deletion is successful, or HttpStatus.INTERNAL_SERVER_ERROR
     * if an error occurs during the deletion process.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.debug("REST request to delete Post : {}", id);
        try {
            this.postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Exception: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
