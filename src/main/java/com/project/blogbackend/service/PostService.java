package com.project.blogbackend.service;

import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.PostRepository;
import com.project.blogbackend.service.dto.PostCreateDTO;
import com.project.blogbackend.service.dto.PostUpdateDTO;
import com.project.blogbackend.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository=postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> id){
        if (id.isPresent()) {
            return postRepository.findByUserId(id.get());
        }
        return postRepository.findAll();
    }

    public Post getPostById(Long id){
        return postRepository.findById(id).orElse(null);
    }

    public Post savePost(PostCreateDTO postCreateDTO) {
        User user = userService.getUserById(postCreateDTO.getUserId());
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
            return this.postRepository.save(convertPost);
        }
        return null;
    }

    public void deletePost(Long id) {
        this.postRepository.deleteById(id);
    }
}
