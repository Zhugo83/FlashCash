package org.example.flashcash.services;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.flashcash.model.User;
import org.example.flashcash.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Id not found"));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
