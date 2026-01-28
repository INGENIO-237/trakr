package app.vercel.ingenio_theta.trakr.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.vercel.ingenio_theta.trakr.auth.dtos.LoginDto;
import app.vercel.ingenio_theta.trakr.auth.dtos.LoginResponse;
import app.vercel.ingenio_theta.trakr.auth.dtos.RegisterDto;
import app.vercel.ingenio_theta.trakr.shared.response.AppApiResponse;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AppApiResponse<UserResponse>> register(@Valid @RequestBody RegisterDto dto) {
        UserResponse user = service.register(dto);

        AppApiResponse<UserResponse> response = AppApiResponse.of(user, "User registered successfully", HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AppApiResponse<LoginResponse>> login(@Valid @RequestBody LoginDto credentials) throws Exception {
        LoginResponse tokens = service.login(credentials);

        AppApiResponse<LoginResponse> response = AppApiResponse.of(tokens, "Logged in successfully");

        return ResponseEntity.ok(response);
    }
}
