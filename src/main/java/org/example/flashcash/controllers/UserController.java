package org.example.flashcash.controllers;

import org.example.flashcash.model.Transfer;
import org.example.flashcash.model.User;
import org.example.flashcash.model.UserAccount;
import org.example.flashcash.services.UserAccountService;
import org.example.flashcash.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserAccountService userAccountService;
    private final UserService userService;

    public UserController(UserAccountService userAccountService, UserService userService) {
        this.userAccountService = userAccountService;
        this.userService = userService;
    }

    // to be used for admin pannel.
    //@RequestMapping("/user/list")
    //public String home(Model model)
    //{
    //    model.addAttribute("users", userService.findAll());
    //    return "user/list";
    //}

    @PostMapping("/transfer")
    public String transfer(@Valid @ModelAttribute Transfer transfer, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("Something bad happened");
            return "redirect:/";
        }
        System.out.println(transfer);
        // Get both ibans then remove amount from one and add to the other with the 0.5% for moving money

        Double finalTax = transfer.getAmountBeforeTax() - (transfer.getAmountBeforeTax() * 0.05);
        transfer.setAmountAfterTax(finalTax);
        User transferredFrom = userService.findById(transfer.getFrom().getId());
        User transferredTo = userService.findById(transfer.getTo().getId());

        UserAccount accountFrom = userAccountService.findById(transferredFrom.getId());
        UserAccount accountTo = userAccountService.findById(transferredTo.getId());

        if (accountFrom.getAmount() - finalTax < 0) {
            System.out.println("You cannot transfer higher amount of money that you currently have in your account");
            return "redirect:/";
        }

        accountFrom.minus(finalTax);
        accountTo.plus(finalTax);

        userAccountService.save(accountFrom);
        userAccountService.save(accountTo);


        System.out.println("Transfer from " + transferredFrom.getId() + " to " + transferredTo.getId());
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

    @PostMapping("/addmoney")
    public String addmoney(@Valid @ModelAttribute UserAccount userAccount, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("Something bad happened");
            return "redirect:/";
        }
        System.out.println(userAccount);
        UserAccount wheremoneygo = userAccountService.findById(userAccount.getAccountId());

        if (userAccount.getAmount() > 0){
            wheremoneygo.plus(userAccount.getAmount());
        } else {
            wheremoneygo.minus(userAccount.getAmount());
        }
        userAccountService.save(wheremoneygo);
        return "redirect:/";
    }
}
