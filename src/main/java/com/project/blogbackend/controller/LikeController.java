package com.project.blogbackend.controller;

import com.project.blogbackend.entity.Like;
import com.project.blogbackend.service.LikeService;
import com.project.blogbackend.service.dto.LikeCreateDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
@Tag(name = "Like", description = "Like actions api documentation.")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<Like> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return this.likeService.getAllLikes(userId,postId);
    }

    @GetMapping("/{id}")
    public Like getLikeById(@PathVariable Long id){
        return this.likeService.getLikeById(id);
    }

    @PostMapping
    public Like saveLike(@RequestBody LikeCreateDTO likeCreateDTO){
        return this.likeService.saveLike(likeCreateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable Long id){
        this.likeService.deleteLike(id);
    }
}
