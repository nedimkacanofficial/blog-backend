package com.project.blogbackend.controller;

import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.UserRepository;
import com.project.blogbackend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "actions api documentation.")
public class UserController {
    private final Logger log= LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * GET endpoint to retrieve all users.
     *
     * This method handles incoming HTTP GET requests to fetch a list of all users.
     *
     * @return ResponseEntity<List<User>> A ResponseEntity containing a list of User objects if successful,
     *         HttpStatus.OK (200) status code. If no users are found, it returns HttpStatus.NOT_FOUND (404).
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        log.debug("REST request to get all Users");
        List<User> user=this.userService.getAllUsers();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * GET endpoint to retrieve a user by their ID.
     *
     * This method handles incoming HTTP GET requests to fetch a user with the specified ID.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return ResponseEntity<User> A ResponseEntity containing the User object if found,
     *         HttpStatus.OK (200) status code. If no user is found with the given ID,
     *         it returns HttpStatus.NOT_FOUND (404).
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        log.debug("REST request to get User Id {}",id);
        User user=this.userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    /**
     * POST endpoint to create a new user or update an existing user.
     *
     * This method handles incoming HTTP POST requests to save a new user or update an existing user.
     *
     * @param newUser The User object to be saved or updated. It should contain the necessary user information.
     * @return ResponseEntity<User> A ResponseEntity containing the saved or updated User object if successful,
     *         HttpStatus.CREATED (201) status code. If there was an error during the save operation,
     *         it returns HttpStatus.INTERNAL_SERVER_ERROR (500).
     */
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User newUser){
        log.debug("REST request to save User : {}", newUser);
        try {
            User user=this.userService.saveUser(newUser);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT endpoint to update an existing user by their ID.
     *
     * This method handles incoming HTTP PUT requests to update an existing user with the specified ID.
     *
     * @param newUser The updated User object containing the new information to be saved for the user.
     * @param id The unique identifier of the user to be updated.
     * @return ResponseEntity<User> A ResponseEntity containing the updated User object if successful,
     *         HttpStatus.OK (200) status code. If no user is found with the given ID,
     *         it returns HttpStatus.NOT_FOUND (404).
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable Long id){
        log.debug("REST request to update User : {}, {}", id, newUser);
        User user=this.userService.updateUser(newUser,id);
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newUser,HttpStatus.OK);
    }

    /**
     * DELETE endpoint to delete a user by their ID.
     *
     * This method handles incoming HTTP DELETE requests to delete a user with the specified ID.
     *
     * @param id The unique identifier of the user to be deleted.
     * @return ResponseEntity<Void> A ResponseEntity with no content if the deletion is successful,
     *         HttpStatus.OK (200) status code. If there was an error during the deletion,
     *         it returns HttpStatus.INTERNAL_SERVER_ERROR (500).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        log.debug("REST request to delete User : {}", id);
        try {
            this.userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.debug("Exception: {}",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
