package org.example.flashcash.controllers;

import jakarta.validation.Valid;
import org.example.flashcash.model.DTO.RegisterDTO;
import org.example.flashcash.model.User;
import org.example.flashcash.repository.UserRepository;
import org.example.flashcash.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String addUser(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute(registerDTO);
        return "register";
    }

    @PostMapping("/register")
    public String validate(Model model, @Valid @ModelAttribute RegisterDTO registerDTO, BindingResult result) {
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            result.addError(
                    new FieldError("registerDTO", "confirmPassword",
                            "Passwords do not match!")
            );
        }

        Optional<User> user = userService.findUserByEmail(registerDTO.getEmail());
        if (user.isPresent()) {
            result.addError(
                    new FieldError("registerDTO", "email",
                            "Email already exists!")
            );
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User newUser = new User();
            newUser.setFirstName(registerDTO.getFirstName());
            newUser.setLastName(registerDTO.getLastName());
            newUser.setEmail(registerDTO.getEmail());
            newUser.setPassword(encoder.encode(registerDTO.getPassword()));

            userService.save(newUser);

            model.addAttribute("registerDTO", new RegisterDTO());
        } catch (Exception ex) {
            result.addError(
                    new FieldError("registerDTO", "firstName"
                    , ex.getMessage())
            );
        }
        return "redirect:/login";
    }
}
