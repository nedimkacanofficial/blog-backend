package com.project.blogbackend.service;

import com.project.blogbackend.entity.Like;
import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.LikeRepository;
import com.project.blogbackend.service.dto.LikeCreateDTO;
import com.project.blogbackend.service.mapper.LikeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LikeService {
    private final Logger log= LoggerFactory.getLogger(LikeService.class);
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public LikeService(LikeRepository likeRepository, PostService postService, UserService userService) {
        this.likeRepository=likeRepository;
        this.postService = postService;
        this.userService = userService;
    }

    /**
     * Retrieves a list of Likes based on the provided criteria.
     *
     * This method fetches Likes from the repository based on the optional userId and postId parameters.
     * If both userId and postId are present, it returns Likes that match both the user and the post.
     * If only userId is present, it returns Likes associated with the specified user.
     * If only postId is present, it returns Likes associated with the specified post.
     * If neither userId nor postId is provided, it returns all Likes available in the repository.
     *
     * @param userId An optional parameter representing the unique identifier of the user whose Likes are to be retrieved.
     * @param postId An optional parameter representing the unique identifier of the post whose Likes are to be retrieved.
     * @return A list of Like objects that match the provided criteria, or all Likes if no specific criteria are given.
     * @Transactional(readOnly = true) Indicates that this method is read-only and does not modify the database.
     * @see Like
     */
    @Transactional(readOnly = true)
    public List<Like> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        log.debug("Request to get all Likes or Likes is userId {} or Likes is postId {}",userId,postId);
        if (userId.isPresent() && postId.isPresent()){
            return this.likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
        } else if(userId.isPresent()){
            return this.likeRepository.findByUserId(userId.get());
        } else if(postId.isPresent()){
            return this.likeRepository.findByPostId(postId.get());
        } else {
            return this.likeRepository.findAll();
        }
    }

    /**
     * Retrieves a single Like object based on the provided unique identifier (ID).
     *
     * This method queries the repository to find a Like with the specified ID and returns it.
     * If a Like with the given ID is found, it is returned as the result.
     * If no Like with the provided ID is found, null is returned.
     *
     * @param id The unique identifier of the Like to be retrieved.
     * @return The Like object with the specified ID, or null if no Like is found with the given ID.
     * @Transactional(readOnly = true) Indicates that this method is read-only and does not modify the database.
     * @see Like
     */
    @Transactional(readOnly = true)
    public Like getLikeById(Long id) {
        log.debug("Request to get Like : {}", id);
        return this.likeRepository.findById(id).orElse(null);
    }

    /**
     * Saves a new Like based on the provided LikeCreateDTO.
     *
     * This method creates and saves a new Like object in the repository based on the information
     * provided in the LikeCreateDTO. It first retrieves the corresponding Post and User objects
     * using the PostService and UserService, respectively, by their unique identifiers (IDs) from
     * the LikeCreateDTO. If both the User and Post are found, a new Like entity is created using
     * the LikeMapper, and then it is saved in the repository using the LikeRepository.
     *
     * @param likeCreateDTO The LikeCreateDTO object containing the necessary information to create the Like.
     * @return The newly created Like object if both the User and Post are found and the Like is successfully saved,
     *         or null if either the User or the Post is not found, and the Like cannot be saved.
     * @see LikeCreateDTO
     * @see Like
     * @see Post
     * @see User
     * @see LikeMapper
     * @see LikeRepository
     * @see PostService
     * @see UserService
     */
    public Like saveLike(LikeCreateDTO likeCreateDTO) {
        log.debug("Request to save Like : {}", likeCreateDTO);
        Post post=this.postService.getPostById(likeCreateDTO.getPostId());
        User user=this.userService.getUserById(likeCreateDTO.getUserId());
        if (user != null && post != null) {
            Like like= LikeMapper.toEntity(likeCreateDTO,post,user);
            return this.likeRepository.save(like);
        }
        return null;
    }

    /**
     * Deletes a Like with the specified unique identifier (ID).
     *
     * This method deletes the Like object from the repository that matches the provided ID.
     * If a Like with the given ID exists, it is removed from the repository.
     * If no Like with the provided ID is found, no action is taken.
     *
     * @param id The unique identifier of the Like to be deleted.
     * @see Like
     * @see LikeRepository
     */
    public void deleteLike(Long id) {
        log.debug("Request to delete Post : {}", id);
        this.likeRepository.deleteById(id);
    }
}
