package org.example.flashcash.controllers;

import jakarta.validation.Valid;
import org.example.flashcash.model.User;
import org.example.flashcash.model.UserAccount;
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
        User user = new User();
        model.addAttribute(user);
        return "register";
    }

    @PostMapping("/register")
    public String validate(Model model, @Valid @ModelAttribute User user, BindingResult result) {
        Optional<User> userExist = userService.findUserByEmail(new User().getEmail());
        if (userExist.isPresent()) {
            result.addError(
                    new FieldError("user", "email",
                            "Email already exists!")
            );
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            UserAccount account = new UserAccount();
            account.setIban("");
            account.setAmount(0.0);

            User newUser = new User();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(encoder.encode(user.getPassword()));
            newUser.setAccount(account);

            userService.save(newUser);

            model.addAttribute("user", new User());
        } catch (Exception ex) {
            result.addError(
                    new FieldError("user", "firstName"
                    , ex.getMessage())
            );
        }
        return "redirect:/login";
    }
}
