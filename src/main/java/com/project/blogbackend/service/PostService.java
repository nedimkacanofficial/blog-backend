package com.project.blogbackend.service;

import com.project.blogbackend.entity.Post;
import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.PostRepository;
import com.project.blogbackend.service.dto.PostCreateDTO;
import com.project.blogbackend.service.dto.PostUpdateDTO;
import com.project.blogbackend.service.mapper.PostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final Logger log= LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository=postRepository;
        this.userService = userService;
    }

    /**
     * Retrieves a list of all Posts or Posts associated with the given user ID.
     *
     * If the user ID is provided, this method will return a list of Posts associated
     * with the specified user. If no user ID is provided, it will return all Posts
     * available in the system.
     *
     * This method operates in read-only mode to avoid modifying any data during the
     * retrieval process.
     *
     * @param userId An optional parameter representing the user ID for which to
     *               retrieve the associated Posts. If not provided (empty), all Posts
     *               will be fetched.
     * @return A list of Post objects either associated with the provided user ID or
     *         all Posts in the system if no user ID is given.
     */
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(Optional<Long> userId){
        log.debug("Request to get all Posts or Posts is userId: {}",userId);
        if (userId.isPresent()) {
            return this.postRepository.findByUserId(userId.get());
        }
        return this.postRepository.findAll();
    }

    /**
     * Retrieves a Post with the specified ID.
     *
     * This method retrieves a Post from the system based on the provided ID. The Post
     * is fetched in read-only mode to avoid any unintentional modifications during the
     * retrieval process.
     *
     * @param id The unique identifier of the Post to be retrieved.
     * @return The Post object with the given ID, or null if no Post exists with the
     *         specified ID.
     */
    @Transactional(readOnly = true)
    public Post getPostById(Long id){
        log.debug("Request to get Post : {}", id);
        return this.postRepository.findById(id).orElse(null);
    }

    /**
     * Saves a new Post based on the information provided in the PostCreateDTO.
     *
     * This method takes a PostCreateDTO object as input, extracts the necessary data,
     * and creates a new Post associated with the corresponding User. The Post is then
     * saved to the system.
     *
     * @param postCreateDTO The data transfer object containing information to create
     *                      the new Post. It must include the user ID to associate the
     *                      Post with the correct User.
     * @return The saved Post object if the creation is successful, or null if the User
     *         associated with the provided user ID does not exist.
     */
    public Post savePost(PostCreateDTO postCreateDTO) {
        log.debug("Request to save User : {}", postCreateDTO);
        User user = this.userService.getUserById(postCreateDTO.getUserId());
        if (user == null) {
            return null;
        }
        Post post = PostMapper.toEntity(postCreateDTO, user);
        return this.postRepository.save(post);
    }

    /**
     * Updates an existing Post with the information provided in the PostUpdateDTO.
     *
     * This method takes a PostUpdateDTO object as input, along with the ID of the Post
     * to be updated. It checks if a Post with the specified ID exists in the system,
     * and if so, updates the Post's properties with the data provided in the
     * PostUpdateDTO. The updated Post is then saved to the system.
     *
     * @param postUpdateDTO The data transfer object containing the updated information
     *                      for the Post.
     * @param id            The unique identifier of the Post to be updated.
     * @return The updated Post object if the update is successful and the specified ID
     *         corresponds to an existing Post; otherwise, it returns null.
     */
    public Post updatePost(PostUpdateDTO postUpdateDTO, Long id) {
        Optional<Post> post=this.postRepository.findById(id);
        if (post.isPresent()){
            Post convertPost=PostMapper.toUpdateEntity(postUpdateDTO,post.get());
            log.debug("Request to update User : {}", convertPost);
            return this.postRepository.save(convertPost);
        }
        log.debug("Request to update Id is null: {}", id);
        return null;
    }

    /**
     * Deletes a Post with the specified ID.
     *
     * This method removes a Post from the system based on the provided ID. The Post
     * with the matching ID will be deleted permanently from the data repository.
     *
     * @param id The unique identifier of the Post to be deleted.
     */
    public void deletePost(Long id) {
        log.debug("Request to delete Post : {}", id);
        this.postRepository.deleteById(id);
    }
}
