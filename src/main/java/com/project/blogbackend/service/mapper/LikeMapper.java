package com.project.blogbackend.service.mapper;

import com.project.blogbackend.entity.Like;
import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.service.dto.LikeCreateDTO;

public class LikeMapper {
    public static Like toEntity(LikeCreateDTO likeCreateDTO, Post post, User user){
        Like like=new Like();
        like.setId(likeCreateDTO.getId());
        like.setPost(post);
        like.setUser(user);
        return like;
    }
}
