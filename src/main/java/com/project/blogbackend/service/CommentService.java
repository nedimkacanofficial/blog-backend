package com.project.blogbackend.service;

import com.project.blogbackend.entity.Comment;
import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.CommentRepository;
import com.project.blogbackend.service.dto.CommentCreateDTO;
import com.project.blogbackend.service.dto.CommentUpdateDTO;
import com.project.blogbackend.service.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository=commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        if (postId.isPresent() && userId.isPresent()) {
            return this.commentRepository.findByUserIdAndPostId(postId.get(),userId.get());
        } else if(postId.isPresent()){
            return this.commentRepository.findByPostId(postId.get());
        } else if(userId.isPresent()){
            return this.commentRepository.findByUserId(userId.get());
        } else {
            return this.commentRepository.findAll();
        }
    }

    public Comment getCommentById(Long id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    public Comment saveComment(CommentCreateDTO commentCreateDTO) {
        User user=this.userService.getUserById(commentCreateDTO.getUserId());
        Post post=this.postService.getPostById(commentCreateDTO.getPostId());
        if (user != null && post != null){
            Comment comment= CommentMapper.toEntity(commentCreateDTO,user,post);
            return this.commentRepository.save(comment);
        }
        return null;
    }

    public Comment updateComment(CommentUpdateDTO commentUpdateDTO, Long id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()){
            Comment convertComment=CommentMapper.toUpdateEntity(commentUpdateDTO,comment.get());
            return this.commentRepository.save(convertComment);
        } else {
            return null;
        }
    }

    public void deleteComment(Long id) {
        this.commentRepository.deleteById(id);
    }
}
