package org.example.flashcash.repository;

import org.example.flashcash.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findByIban(String iban);
}
