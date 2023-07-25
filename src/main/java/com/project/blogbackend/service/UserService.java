package com.project.blogbackend.service;

import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public User getUserById(Long id){
        return this.userRepository.findById(id).orElse(null);
    }

    public User saveUser(User newUser){
        return this.userRepository.save(newUser);
    }

    public User updateUser(User newUser,Long id){
        Optional<User> user=this.userRepository.findById(id);
        if (user.isPresent()) {
            User userFound=user.get();
            userFound.setUsername(newUser.getUsername());
            userFound.setPassword(newUser.getPassword());
            this.userRepository.save(userFound);
            return userFound;
        } else {
            return null;
        }
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }
}
