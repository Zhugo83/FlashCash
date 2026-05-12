package org.example.flashcash.config;

import lombok.RequiredArgsConstructor;
import org.example.flashcash.model.UserAccount;
import org.example.flashcash.repository.UserRepository;
import org.example.flashcash.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form->form
                        .defaultSuccessUrl("/", true)
                )
                .logout(config -> config.logoutSuccessUrl("/login"))
                .build();
    }

    @Bean
    public CommandLineRunner initDatabase(){
        UserAccount account = new UserAccount();
        account.setIban("TestIbanUser");
        account.setAmount(0.0);

        UserAccount account2 = new UserAccount();
        account2.setIban("TestIbanAdmin");
        account2.setAmount(0.0);
        return args -> {
            User user = User.builder()
                    .firstName("User")
                    .lastName("Normal")
                    .email("user@example.com")
                    .password(passwordEncoder().encode("password"))
                    .account(account)
                    .build();
            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("Super")
                    .email("admin@example.com")
                    .password(passwordEncoder().encode("password"))
                    .account(account2)
                    .build();

            userRepository.save(user);
            userRepository.save(admin);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}