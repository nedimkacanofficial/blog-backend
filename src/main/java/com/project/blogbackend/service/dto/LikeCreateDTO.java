package com.project.blogbackend.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LikeCreateDTO")
public class LikeCreateDTO {
    private Long id;
    private Long userId;
    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
