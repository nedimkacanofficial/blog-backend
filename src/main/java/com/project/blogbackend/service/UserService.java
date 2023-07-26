package com.project.blogbackend.service;

import com.project.blogbackend.entity.User;
import com.project.blogbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    /**
     * Retrieves a list of all users.
     *
     * This method fetches all user records from the repository in a read-only manner.
     * It is advised for use in read-only scenarios to prevent unintentional modifications
     * to the data. If you need to modify user data, consider using a different method
     * with appropriate transactional settings.
     *
     * @return A list of User objects representing all users in the system.
     * @throws DataAccessException if there is any problem accessing the underlying data store.
     * @see UserRepository#findAll()
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        log.debug("Request to get all Users");
        return this.userRepository.findAll();
    }

    /**
     * Retrieves a user by their unique identifier (ID).
     *
     * This method fetches a specific user from the repository based on the provided ID.
     * It operates in a read-only manner to prevent unintentional modifications to the data.
     * If you need to modify user data, consider using a different method with appropriate
     * transactional settings.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return The User object representing the user with the specified ID,
     *         or null if no user with the given ID exists in the system.
     * @throws DataAccessException if there is any problem accessing the underlying data store.
     * @see UserRepository#findById(Object)
     * @see java.util.Optional
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id){
        log.debug("Request to get User : {}", id);
        return this.userRepository.findById(id).orElse(null);
    }

    /**
     * Saves a new user or updates an existing user in the system.
     *
     * This method is used to persist a new user entity or update an existing user
     * in the data store. It delegates the saving process to the underlying repository.
     * If the user being saved already has an ID (indicating an existing user), it will
     * be updated in the data store; otherwise, a new user will be created with a
     * generated ID. The method operates within a transaction, ensuring that the data
     * is consistently managed.
     *
     * @param newUser The User object representing the user to be saved or updated.
     * @return The User object that was saved or updated in the data store.
     * @throws DataAccessException if there is any problem accessing the underlying data store.
     * @see UserRepository#save(Object)
     */
    public User saveUser(User newUser){
        log.debug("Request to save User : {}", newUser);
        return this.userRepository.save(newUser);
    }

    /**
     * Updates an existing user with the provided data.
     *
     * This method is used to update an existing user entity in the data store
     * based on the specified user ID. It first retrieves the user by the given ID
     * and then updates its username and password with the values from the 'newUser'
     * object. The method then saves the updated user back to the data store and returns
     * the updated User object. If no user with the given ID is found, the method
     * returns null.
     *
     * @param newUser The User object containing the updated data for the user.
     * @param id The unique identifier of the user to be updated.
     * @return The updated User object representing the user with the specified ID,
     *         or null if no user with the given ID exists in the system.
     * @throws DataAccessException if there is any problem accessing the underlying data store.
     * @see UserRepository#findById(Object)
     * @see UserRepository#save(Object)
     */
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

    /**
     * Deletes a user from the system by their unique identifier (ID).
     *
     * This method is used to remove a user entity from the data store based on the
     * provided user ID. The method delegates the deletion process to the underlying
     * repository's 'deleteById' method. If a user with the specified ID exists in
     * the system, it will be deleted; otherwise, no action will be taken.
     *
     * @param id The unique identifier of the user to be deleted.
     * @throws DataAccessException if there is any problem accessing the underlying data store.
     * @see UserRepository#deleteById(Object)
     */
    public void deleteUser(Long id) {
        log.debug("Request to delete User : {}", id);
        this.userRepository.deleteById(id);
    }
}
