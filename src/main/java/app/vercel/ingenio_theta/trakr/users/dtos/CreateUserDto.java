package app.vercel.ingenio_theta.trakr.users.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank(message = "Email address must be provided") @Email(message = "Invalid email format") String email,

        @NotBlank(message = "Name must be provided") @Length(min = 2, message = "Name must be at least 2 characters long") String name,

        @NotBlank(message = "Password must be provided") @Length(min = 6, message = "Password must be at least 6 characters long") String password) {
}
