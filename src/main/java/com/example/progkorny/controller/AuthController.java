package com.example.progkorny.controller;

import com.example.progkorny.model.User;
import com.example.progkorny.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Data
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the login form.
     *
     * @param model the model to add attributes to
     * @return the view name for the login form
     */
    @GetMapping("/")
    public String showLoginForm(final Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    /**
     * Displays the registration form.
     *
     * @param model the model to add attributes to
     * @return the view name for the registration form
     */
    @GetMapping("/register")
    public String showRegistrationForm(final Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Handles user registration.
     *
     * @param user the user object containing registration details
     * @return the view name for redirection
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute final User user) {
        userService.createUser(user);
        return "redirect:/";
    }

    /**
     * Handles user login.
     *
     * @param user the user object containing login credentials
     * @param model the model to add attributes to
     * @return the view name for redirection or error display
     */
    @PostMapping("/login")
    public String login(@ModelAttribute final User user,
                        final Model model,
                        final HttpSession session) {
        User existing = userService.getUserByName(user.getName());
        if (existing != null) {
            session.setAttribute("loggedInUser", existing);
            return "redirect:/expenses";
        } else {
            model.addAttribute("error", "Nincs ilyen felhasználó!");
            return "login";
        }
    }
}
