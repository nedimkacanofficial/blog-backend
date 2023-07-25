package com.project.blogbackend.service;

import com.project.blogbackend.entity.Like;
import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.LikeRepository;
import com.project.blogbackend.service.dto.LikeCreateDTO;
import com.project.blogbackend.service.mapper.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LikeService {
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
        if (userId.isPresent() && postId.isPresent()){
            return likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
        } else if(userId.isPresent()){
            return likeRepository.findByUserId(userId.get());
        } else if(postId.isPresent()){
            return likeRepository.findByPostId(postId.get());
        } else {
            return likeRepository.findAll();
        }
    }

    public Like getLikeById(Long id) {
        return likeRepository.findById(id).orElse(null);
    }

    public Like saveLike(LikeCreateDTO likeCreateDTO) {
        Post post=postService.getPostById(likeCreateDTO.getId());
        User user=userService.getUserById(likeCreateDTO.getId());
        if (user != null && post != null) {
            Like like= LikeMapper.toEntity(likeCreateDTO,post,user);
            return likeRepository.save(like);
        }
        return null;
    }

    public void deleteLike(Long id) {
        this.likeRepository.deleteById(id);
    }
}