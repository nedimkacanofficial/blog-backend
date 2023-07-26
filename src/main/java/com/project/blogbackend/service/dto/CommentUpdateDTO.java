package com.project.blogbackend.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CommentUpdateDTO")
public class CommentUpdateDTO {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
