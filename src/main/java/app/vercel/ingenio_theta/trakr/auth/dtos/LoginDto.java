package app.vercel.ingenio_theta.trakr.auth.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
                @NotBlank(message = "Email address must be provided") @Email(message = "Invalid email format") String email,
                @NotBlank(message = "Password must be provided") @Length(min = 6, message = "Password must be at least 6 characters long") String password) {
}