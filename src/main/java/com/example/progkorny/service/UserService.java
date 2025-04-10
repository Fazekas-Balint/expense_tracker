package com.example.progkorny.service;

import com.example.progkorny.model.User;
import com.example.progkorny.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    /**
     * User repository for database operations.
     */
    private final UserRepository userRepo;

    /**
     * Constructor for UserService.
     *
     * @param userRepo the user repository
     */
    public UserService(final UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user
     * @return the user object, or null if not found
     */
    public User getUserById(final Long id) {
        return userRepo.findById(id).orElse(null);
    }

    /**
     * Creates a new user.
     *
     * @param user the user object to create
     * @return the created user object
     */
    public User createUser(final User user) {
        return userRepo.save(user);
    }

    /**
     * Updates an existing user.
     *
     * @param id the ID of the user to update
     * @param updatedUser the updated user object
     * @return the updated user object, or null if not found
     */

    public User updateUser(final Long id, final User updatedUser) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return userRepo.save(user);
        }
        return null;
    }
    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(final Long id) {
        userRepo.deleteById(id);
    }
}
