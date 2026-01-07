package com.anton3413.taskmanager.dto.user;


import com.anton3413.taskmanager.validation.annotation.PasswordMatch;
import com.anton3413.taskmanager.validation.annotation.UniqueEmail;
import com.anton3413.taskmanager.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatch
public class CreateUserDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username: 3-20 characters required")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Username: Use only letters, digits and underscores"
    )
    @UniqueUsername
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 20, message = "Password: 6-20 characters required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$",
            message = "Password: Use letter, digit and symbol (@$!%*#?&_)"
    )
    private String password;

    private String confirmPassword;

    @Email
    @UniqueEmail(message = "User with that email already exists")
    private String email;



}
