package com.project.blogbackend.controller;

import com.project.blogbackend.entity.User;
import com.project.blogbackend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User actions api documentation.")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return this.userService.getUserById(id);
    }

    @PostMapping
    public User saveUser(@RequestBody User newUser){
        return this.userService.saveUser(newUser);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return this.userService.updateUser(newUser,id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
    }
}
