package org.example.flashcash.controllers;

import org.example.flashcash.model.User;
import org.example.flashcash.model.UserAccount;
import org.example.flashcash.services.UserAccountService;
import org.example.flashcash.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserAccountService userAccountService;

    public UserController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    // to be used for admin pannel.
    //@RequestMapping("/user/list")
    //public String home(Model model)
    //{
    //    model.addAttribute("users", userService.findAll());
    //    return "user/list";
    //}

    @PostMapping("/transfer")
    public String transfer(@Valid @ModelAttribute UserAccount userAccount, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("Something bad happened");
            return "redirect:/";
        }
        System.out.println(userAccount);
        // Get both ibans then remove amount from one and add to the other with the 0.5% for moving money
        
        //userAccountService.save();
        return "redirect:/";
    }

    @PostMapping("/addiban")
    public String addiban(@Valid @ModelAttribute UserAccount userAccount, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("Something bad happened");
            return "redirect:/";
        }
        System.out.println(userAccount);
        UserAccount wheremoneygo = userAccountService.findById(userAccount.getAccountId());

        // boolean iban = userAccountService.findExistingIban();
        //if (iban){
        //    System.out.println("Iban already exists");
        //    return "redirect:/";
        //}

        // TESTING
        if (userAccount.getAmount() > 0){
            wheremoneygo.plus(userAccount.getAmount());
        } else {
            wheremoneygo.minus(userAccount.getAmount());
        }

        wheremoneygo.setIban(userAccount.getIban());

        userAccountService.save(wheremoneygo);
        return "redirect:/";
    }
}
