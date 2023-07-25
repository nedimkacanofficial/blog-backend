package com.project.blogbackend.repository;

import com.project.blogbackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByUserIdAndPostId(Long postId, Long userId);

    List<Comment> findByPostId(Long postId);

    List<Comment> findByUserId(Long userId);
}
