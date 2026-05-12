package org.example.flashcash.model.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is also required")
    private String password;
}
