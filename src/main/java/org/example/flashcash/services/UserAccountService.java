package org.example.flashcash.services;

import org.example.flashcash.model.User;
import org.example.flashcash.model.UserAccount;
import org.example.flashcash.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {;
        this.userAccountRepository = userAccountRepository;
    }

    public void save(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }

    public UserAccount findById(Integer id) {
        return userAccountRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Id not found"));
    }
}
