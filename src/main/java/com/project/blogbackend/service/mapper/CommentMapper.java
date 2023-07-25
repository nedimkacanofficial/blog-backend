package com.project.blogbackend.service.mapper;

import com.project.blogbackend.entity.Comment;
import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.service.dto.CommentCreateDTO;
import com.project.blogbackend.service.dto.CommentUpdateDTO;

public class CommentMapper {
    public static Comment toEntity(CommentCreateDTO commentCreateDTO, User user, Post post){
        Comment comment=new Comment();
        comment.setId(commentCreateDTO.getId());
        comment.setUser(user);
        comment.setPost(post);
        comment.setText(commentCreateDTO.getText());
        return comment;
    }

    public static Comment toUpdateEntity(CommentUpdateDTO commentUpdateDTO, Comment comment){
        comment.setText(commentUpdateDTO.getText());
        return comment;
    }
}
