package app.vercel.ingenio_theta.trakr.users.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;

public record UpdateUserDto(
                @Length(min = 2, message = "Name must be at least 2 characters long") String name,

                @Email(message = "Invalid email address format") String email,
                
                @Length(min = 6, message = "Password must be at least 2 characters long") String password) {
}
