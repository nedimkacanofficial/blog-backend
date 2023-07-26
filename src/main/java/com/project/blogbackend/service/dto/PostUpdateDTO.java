package com.project.blogbackend.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PostUpdateDTO")
public class PostUpdateDTO {
    private String title;
    private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
