package com.project.blogbackend.service;

import com.project.blogbackend.entity.Comment;
import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.CommentRepository;
import com.project.blogbackend.service.dto.CommentCreateDTO;
import com.project.blogbackend.service.dto.CommentUpdateDTO;
import com.project.blogbackend.service.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    private final Logger log= LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository=commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    /**
     * Retrieves a list of comments based on the provided optional parameters.
     *
     * If both userId and postId are present, it returns comments that match both the given userId and postId.
     * If only postId is present, it returns comments associated with the specified postId.
     * If only userId is present, it returns comments associated with the specified userId.
     * If neither userId nor postId is present, it returns all comments in the system.
     *
     * @param userId Optional parameter representing the ID of the user whose comments are to be retrieved.
     * @param postId Optional parameter representing the ID of the post for which comments are to be retrieved.
     * @return A list of Comment objects that match the given criteria, or all comments if no specific criteria are provided.
     */
    @Transactional(readOnly = true)
    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        log.debug("Request to get all Comments or Comments is userId {} or Comments is postId {}",userId,postId);
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

    /**
     * Retrieves a single comment based on the provided comment ID.
     *
     * This method fetches the comment with the specified ID from the repository.
     *
     * @param id The unique identifier of the comment to be retrieved.
     * @return The Comment object with the given ID, or null if no comment exists with the provided ID.
     */
    @Transactional(readOnly = true)
    public Comment getCommentById(Long id) {
        log.debug("Request to get Comment : {}", id);
        return this.commentRepository.findById(id).orElse(null);
    }

    /**
     * Saves a new comment based on the information provided in the CommentCreateDTO.
     *
     * This method creates and saves a new comment using the data from the CommentCreateDTO, which contains the necessary
     * details to create a comment. The comment is associated with a specific user and a post.
     *
     * @param commentCreateDTO The CommentCreateDTO object containing the data required to create the comment.
     * @return The newly created Comment object if the associated user and post exist and the comment is successfully saved,
     *         otherwise returns null if either the user or post does not exist.
     */
    public Comment saveComment(CommentCreateDTO commentCreateDTO) {
        log.debug("Request to save Comment : {}", commentCreateDTO);
        User user=this.userService.getUserById(commentCreateDTO.getUserId());
        Post post=this.postService.getPostById(commentCreateDTO.getPostId());
        if (user != null && post != null){
            Comment comment= CommentMapper.toEntity(commentCreateDTO,user,post);
            return this.commentRepository.save(comment);
        }
        return null;
    }

    /**
     * Updates an existing comment with the provided changes specified in the CommentUpdateDTO.
     *
     * This method first retrieves the comment with the given ID from the repository. If the comment exists,
     * it updates the comment's properties based on the changes specified in the CommentUpdateDTO. The updated comment
     * is then saved back to the repository.
     *
     * @param commentUpdateDTO The CommentUpdateDTO object containing the changes to be applied to the comment.
     * @param id The unique identifier of the comment to be updated.
     * @return The updated Comment object if the comment with the specified ID exists and the update is successful,
     *         otherwise returns null if no comment is found with the provided ID.
     */
    public Comment updateComment(CommentUpdateDTO commentUpdateDTO, Long id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()){
            Comment convertComment=CommentMapper.toUpdateEntity(commentUpdateDTO,comment.get());
            log.debug("Request to update Comment : {}", convertComment);
            return this.commentRepository.save(convertComment);
        }
        log.debug("Request to update Id is null: {}", id);
        return null;
    }

    /**
     * Deletes a comment with the specified ID.
     *
     * This method removes the comment with the provided ID from the repository, effectively deleting it from the system.
     *
     * @param id The unique identifier of the comment to be deleted.
     */
    public void deleteComment(Long id) {
        log.debug("Request to delete Comment : {}", id);
        this.commentRepository.deleteById(id);
    }
}
