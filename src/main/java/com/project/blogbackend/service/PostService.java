package com.project.blogbackend.service;

import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.PostRepository;
import com.project.blogbackend.service.dto.PostCreateDTO;
import com.project.blogbackend.service.dto.PostUpdateDTO;
import com.project.blogbackend.service.mapper.PostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final Logger log= LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository=postRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts(Optional<Long> userId){
        log.debug("Request to get all Posts or Posts is userId: {}",userId);
        if (userId.isPresent()) {
            return this.postRepository.findByUserId(userId.get());
        }
        return this.postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id){
        log.debug("Request to get Post : {}", id);
        return this.postRepository.findById(id).orElse(null);
    }

    public Post savePost(PostCreateDTO postCreateDTO) {
        log.debug("Request to save User : {}", postCreateDTO);
        User user = this.userService.getUserById(postCreateDTO.getUserId());
        if (user == null) {
            return null;
        }
        Post post = PostMapper.toEntity(postCreateDTO, user);
        return this.postRepository.save(post);
    }

    public Post updatePost(PostUpdateDTO postUpdateDTO, Long id) {
        Optional<Post> post=this.postRepository.findById(id);
        if (post.isPresent()){
            Post convertPost=PostMapper.toUpdateEntity(postUpdateDTO,post.get());
            log.debug("Request to update User : {}", convertPost);
            return this.postRepository.save(convertPost);
        }
        log.debug("Request to update Id is null: {}", id);
        return null;
    }

    public void deletePost(Long id) {
        log.debug("Request to delete Post : {}", id);
        this.postRepository.deleteById(id);
    }
}
