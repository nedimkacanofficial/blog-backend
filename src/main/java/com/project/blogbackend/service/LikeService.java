package com.project.blogbackend.service;

import com.project.blogbackend.entity.Like;
import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.LikeRepository;
import com.project.blogbackend.service.dto.LikeCreateDTO;
import com.project.blogbackend.service.mapper.LikeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LikeService {
    private final Logger log= LoggerFactory.getLogger(LikeService.class);
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public LikeService(LikeRepository likeRepository, PostService postService, UserService userService) {
        this.likeRepository=likeRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public List<Like> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        log.debug("Request to get all Likes or Likes is userId {} or Likes is postId {}",userId,postId);
        if (userId.isPresent() && postId.isPresent()){
            return this.likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
        } else if(userId.isPresent()){
            return this.likeRepository.findByUserId(userId.get());
        } else if(postId.isPresent()){
            return this.likeRepository.findByPostId(postId.get());
        } else {
            return this.likeRepository.findAll();
        }
    }

    public Like getLikeById(Long id) {
        log.debug("Request to get Like : {}", id);
        return this.likeRepository.findById(id).orElse(null);
    }

    public Like saveLike(LikeCreateDTO likeCreateDTO) {
        log.debug("Request to save Like : {}", likeCreateDTO);
        Post post=this.postService.getPostById(likeCreateDTO.getPostId());
        User user=this.userService.getUserById(likeCreateDTO.getUserId());
        if (user != null && post != null) {
            Like like= LikeMapper.toEntity(likeCreateDTO,post,user);
            return this.likeRepository.save(like);
        }
        return null;
    }

    public void deleteLike(Long id) {
        log.debug("Request to delete Post : {}", id);
        this.likeRepository.deleteById(id);
    }
}
