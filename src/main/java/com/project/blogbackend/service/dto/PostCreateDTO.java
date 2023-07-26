package com.project.blogbackend.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PostCreateDTO")
public class PostCreateDTO {
    private Long id;
    private String text;
    private String title;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
