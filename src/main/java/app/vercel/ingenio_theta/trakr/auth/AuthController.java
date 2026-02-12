package app.vercel.ingenio_theta.trakr.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.vercel.ingenio_theta.trakr.auth.dtos.LoginDto;
import app.vercel.ingenio_theta.trakr.auth.dtos.LoginResponse;
import app.vercel.ingenio_theta.trakr.auth.dtos.RegisterDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.UnauthorizedException;
import app.vercel.ingenio_theta.trakr.shared.response.AppApiResponse;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {
    private AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided registration details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @SecurityRequirements
    public ResponseEntity<AppApiResponse<UserResponse>> register(@Valid @RequestBody RegisterDto dto) {
        UserResponse user = service.register(dto);

        AppApiResponse<UserResponse> response = AppApiResponse.of(user, "User registered successfully",
                HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns JWT tokens upon successful login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @SecurityRequirements
    public ResponseEntity<AppApiResponse<LoginResponse>> login(@Valid @RequestBody LoginDto credentials)
            throws UnauthorizedException {
        LoginResponse tokens = service.login(credentials);

        AppApiResponse<LoginResponse> response = AppApiResponse.of(tokens, "Logged in successfully");

        return ResponseEntity.ok(response);
    }
}
