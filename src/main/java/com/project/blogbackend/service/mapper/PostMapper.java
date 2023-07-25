package com.project.blogbackend.service.mapper;

import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.service.dto.PostCreateDTO;
import com.project.blogbackend.service.dto.PostUpdateDTO;

public class PostMapper {
    public static Post toEntity(PostCreateDTO postCreateDTO, User user) {
        Post post = new Post();
        post.setId(postCreateDTO.getId());
        post.setTitle(postCreateDTO.getTitle());
        post.setText(postCreateDTO.getText());
        post.setUser(user);
        return post;
    }

    /*
    public static PostCreateDTO toDto(Post post) {
        PostCreateDTO postDto = new PostCreateDTO();
        postDto.setId(post.getId());
        postDto.setUserId(post.getUser().getId());
        postDto.setTitle(post.getTitle());
        postDto.setText(post.getText());
        return postDto;
    }
    */

    public static Post toUpdateEntity(PostUpdateDTO postUpdateDTO, Post post){
        post.setTitle(postUpdateDTO.getTitle());
        post.setText(postUpdateDTO.getText());
        return post;
    }
}
