package com.project.blogbackend.controller;

import com.project.blogbackend.entity.Like;
import com.project.blogbackend.service.LikeService;
import com.project.blogbackend.service.dto.LikeCreateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
@Tag(name = "Like", description = "actions api documentation.")
public class LikeController {
    private final Logger log= LoggerFactory.getLogger(LikeController.class);
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    /**
     * Retrieves all Like entities based on the provided optional parameters.
     *
     * @param userId Optional parameter to filter Likes by user ID.
     * @param postId Optional parameter to filter Likes by post ID.
     * @return ResponseEntity with a list of Like entities that match the specified criteria.
     *         Returns HTTP status 200 (OK) if Likes are found, or HTTP status 404 (Not Found)
     *         if no Likes are found for the given parameters.
     */
    @GetMapping
    public ResponseEntity<List<Like>> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        log.debug("REST request to get all Like or Like is userId {} or Like is postId: {}", userId,postId);
        List<Like> likes=this.likeService.getAllLikes(userId,postId);
        if (likes == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(likes,HttpStatus.OK);
    }

    /**
     * Retrieves a Like entity by its unique identifier.
     *
     * @param id The unique identifier of the Like to retrieve.
     * @return ResponseEntity with the Like entity if found, or HTTP status 404 (Not Found)
     *         if no Like is found for the given identifier.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Like> getLikeById(@PathVariable Long id){
        log.debug("REST request to get Like Id {}",id);
        Like like=this.likeService.getLikeById(id);
        if (like == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(like,HttpStatus.OK);
    }

    /**
     * Saves a new Like based on the provided LikeCreateDTO.
     *
     * @param likeCreateDTO The data object containing the details of the Like to be saved.
     * @return ResponseEntity with the saved Like entity if successful, or HTTP status 500 (Internal Server Error)
     *         if an unexpected error occurs during the saving process.
     */
    @PostMapping
    public ResponseEntity<Like> saveLike(@RequestBody LikeCreateDTO likeCreateDTO){
        log.debug("REST request to save Like : {}", likeCreateDTO);
        try {
            Like like=this.likeService.saveLike(likeCreateDTO);
            return new ResponseEntity<>(like,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a Like entity by its unique identifier.
     *
     * @param id The unique identifier of the Like entity to be deleted.
     * @return ResponseEntity with HTTP status 200 (OK) if the Like is successfully deleted,
     *         or HTTP status 500 (Internal Server Error) if an unexpected error occurs during the deletion process.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id){
        log.debug("REST request to delete Like : {}", id);
        try {
            this.likeService.deleteLike(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Exception: {}",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
