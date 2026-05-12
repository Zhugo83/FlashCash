package org.example.flashcash.controllers;

import org.example.flashcash.model.User;
import org.example.flashcash.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // to be used for admin pannel.
    //@RequestMapping("/user/list")
    //public String home(Model model)
    //{
    //    model.addAttribute("users", userService.findAll());
    //    return "user/list";
    //}
}
