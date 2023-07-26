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
@Tag(name = "Like", description = "Like actions api documentation.")
public class LikeController {
    private final Logger log= LoggerFactory.getLogger(LikeController.class);
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<Like>> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        log.debug("REST request to get all Like or Like is userId {} or Like is postId: {}", userId,postId);
        List<Like> likes=this.likeService.getAllLikes(userId,postId);
        if (likes == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(likes,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Like> getLikeById(@PathVariable Long id){
        log.debug("REST request to get Like");
        Like like=this.likeService.getLikeById(id);
        if (like == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(like,HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id){
        log.debug("REST request to delete Like : {}", id);
        try {
            this.likeService.deleteLike(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
