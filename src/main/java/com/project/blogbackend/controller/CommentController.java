package com.project.blogbackend.controller;

import com.project.blogbackend.entity.Comment;
import com.project.blogbackend.repository.CommentRepository;
import com.project.blogbackend.service.CommentService;
import com.project.blogbackend.service.dto.CommentCreateDTO;
import com.project.blogbackend.service.dto.CommentUpdateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comment", description = "actions api documentation.")
public class CommentController {
    private final Logger log= LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    public CommentController(CommentService commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    /**
     * Retrieves a list of comments based on the provided filters.
     *
     * This endpoint allows fetching all comments or comments filtered by userId or postId.
     *
     * @param postId An optional parameter representing the postId to filter comments by.
     *               If provided, only comments associated with the specified postId will be returned.
     * @param userId An optional parameter representing the userId to filter comments by.
     *               If provided, only comments associated with the specified userId will be returned.
     * @return ResponseEntity<List<Comment>> A ResponseEntity containing the list of Comment objects
     *                                        that match the provided filters, or HttpStatus.NOT_FOUND
     *                                        if no comments are found for the given filters.
     *
     * @see Comment
     * @see CommentService#getAllComments(Optional, Optional)
     */
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(@RequestParam Optional<Long> postId, @RequestParam Optional<Long> userId){
        log.debug("REST request to get all Comments or Comments is userId {} or Comments is postId: {}", userId,postId);
        List<Comment> comments=this.commentService.getAllComments(postId,userId);
        if (comments == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

    /**
     * Retrieves a Comment by its ID using a GET request.
     *
     * @param id The unique identifier of the Comment to retrieve.
     * @return ResponseEntity<Comment> The ResponseEntity containing the retrieved Comment if found, or a NOT_FOUND status if no Comment with the given ID exists.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id){
        log.debug("REST request to get Comment Id {}",id);
        Comment comment=this.commentService.getCommentById(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }

    /**
     * Saves a new Comment using a POST request.
     *
     * @param commentCreateDTO The Data Transfer Object (DTO) containing the information for creating the Comment.
     * @return ResponseEntity<Comment> The ResponseEntity containing the saved Comment if successful, or an INTERNAL_SERVER_ERROR status if an error occurs during the process.
     */
    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody CommentCreateDTO commentCreateDTO){
        log.debug("REST request to save Comment : {}", commentCreateDTO);
        try {
            Comment comment=this.commentService.saveComment(commentCreateDTO);
            return new ResponseEntity<>(comment,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing Comment using a PUT request.
     *
     * @param commentUpdateDTO The Data Transfer Object (DTO) containing the updated information for the Comment.
     * @param id The unique identifier of the Comment to update.
     * @return ResponseEntity<Comment> The ResponseEntity containing the updated Comment if successful, or a NOT_FOUND status if the Comment with the given ID does not exist or an OK status if the update operation is successful but there were no changes to the Comment.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@RequestBody CommentUpdateDTO commentUpdateDTO, @PathVariable Long id){
        log.debug("REST request to update Comment : {}, {}", id, commentUpdateDTO);
        Comment comment=this.commentService.updateComment(commentUpdateDTO,id);
        if (!this.commentRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }

    /**
     * Deletes a Comment by its ID using a DELETE request.
     *
     * @param id The unique identifier of the Comment to delete.
     * @return ResponseEntity<Void> The ResponseEntity indicating the success of the deletion operation, or an INTERNAL_SERVER_ERROR status if an error occurs during the process.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        log.debug("REST request to delete Comment : {}", id);
        try {
            this.commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Exception: {}",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
