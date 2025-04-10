package com.example.progkorny.controller;

import com.example.progkorny.model.User;
import com.example.progkorny.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * User service for business logic.
     */
    private final UserService service;

    /**
     * Constructor for UserController.
     *
     * @param service the user service
     */
    public UserController(final UserService service) {
        this.service = service;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user
     * @return the user object, or null if not found
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable final Long id) {
        return service.getUserById(id);
    }

    /**
     * Creates a new user.
     *
     * @param user the user object to create
     * @return the created user object
     */
    @PostMapping
    public User createUser(@RequestBody final User user) {
        return service.createUser(user);
    }

    /**
     * Updates an existing user.
     *
     * @param id   the ID of the user to update
     * @param user the updated user object
     * @return the updated user object, or null if not found
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable final Long id,
                           @RequestBody final User user) {
        return service.updateUser(id, user);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable final Long id) {
        service.deleteUser(id);
    }
}
