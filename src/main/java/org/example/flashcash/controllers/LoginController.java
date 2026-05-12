package org.example.flashcash.controllers;

import jakarta.validation.Valid;
import org.example.flashcash.model.DTO.LoginDTO;
import org.example.flashcash.model.DTO.RegisterDTO;
import org.example.flashcash.model.User;
import org.example.flashcash.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String addUser(Model model) {
        LoginDTO loginDTO = new LoginDTO();
        model.addAttribute(loginDTO);
        return "login";
    }

    @PostMapping("/login")
    public String validate(Model model, @Valid @ModelAttribute RegisterDTO registerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "login";
        }
        return "redirect:/index";
    }

}
