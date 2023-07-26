package com.project.blogbackend.service;

import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final Logger log= LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        log.debug("Request to get all Users");
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id){
        log.debug("Request to get User : {}", id);
        return this.userRepository.findById(id).orElse(null);
    }

    public User saveUser(User newUser){
        log.debug("Request to save User : {}", newUser);
        return this.userRepository.save(newUser);
    }

    public User updateUser(User newUser,Long id){
        Optional<User> user=this.userRepository.findById(id);
        if (user.isPresent()) {
            User userFound=user.get();
            userFound.setUsername(newUser.getUsername());
            userFound.setPassword(newUser.getPassword());
            this.userRepository.save(userFound);
            log.debug("Request to update User : {}", newUser);
            return userFound;
        } else {
            log.debug("Request to update Id is null: {}", id);
            return null;
        }
    }

    public void deleteUser(Long id) {
        log.debug("Request to delete User : {}", id);
        this.userRepository.deleteById(id);
    }
}
