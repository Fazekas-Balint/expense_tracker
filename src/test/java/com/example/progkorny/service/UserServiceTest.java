package com.example.progkorny.service;

import com.example.progkorny.model.User;
import com.example.progkorny.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testGetAllUsers() {
        // GIVEN
        List<User> mockUsers = List.of(new User(1L, "Teszt Elek", "teszt@valami.hu", "titok"));
        when(userRepository.findAll()).thenReturn(mockUsers);

        // WHEN
        List<User> result = userService.getAllUsers();

        // THEN
        assertEquals(1, result.size());
        assertEquals("Teszt Elek", result.get(0).getName());
    }

    @Test
    void testGetUserById() {
        // GIVEN
        User user = new User(2L, "Alma Béla", "alma@bela.hu", "alma123");
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        // WHEN
        User result = userService.getUserById(2L);

        // THEN
        assertNotNull(result);
        assertEquals("Alma Béla", result.getName());
    }

    @Test
    void testCreateUser() {
        // GIVEN
        User input = new User(null, "Körte Kata", "korte@kata.hu", "korte123");
        User saved = new User(3L, "Körte Kata", "korte@kata.hu", "korte123");
        when(userRepository.save(input)).thenReturn(saved);

        // WHEN
        User result = userService.createUser(input);

        // THEN
        assertNotNull(result);
        assertEquals("Körte Kata", result.getName());
    }

    @Test
    void testUpdateUser() {
        // GIVEN
        User existing = new User(4L, "Régi Név", "regi@email.hu", "pass");
        User updated = new User(null, "Új Név", "uj@email.hu", "ujpass");

        when(userRepository.findById(4L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any())).thenReturn(new User(4L, "Új Név", "uj@email.hu", "ujpass"));

        // WHEN
        User result = userService.updateUser(4L, updated);

        // THEN
        assertEquals("Új Név", result.getName());
        assertEquals("uj@email.hu", result.getEmail());
    }

    @Test
    void testDeleteUser() {
        // GIVEN
        Long id = 5L;
        doNothing().when(userRepository).deleteById(id);

        // WHEN
        userService.deleteUser(id);

        // THEN
        verify(userRepository, times(1)).deleteById(id);
    }
}