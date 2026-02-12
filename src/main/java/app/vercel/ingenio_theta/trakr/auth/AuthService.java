package app.vercel.ingenio_theta.trakr.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.auth.dtos.LoginDto;
import app.vercel.ingenio_theta.trakr.auth.dtos.LoginResponse;
import app.vercel.ingenio_theta.trakr.auth.dtos.RegisterDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.UnauthorizedException;
import app.vercel.ingenio_theta.trakr.users.UserService;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@Service
public class AuthService {
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private AuthMapper mapper;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager, UserService userService,
            AuthMapper mapper) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.mapper = mapper;
    }

    public UserResponse register(RegisterDto dto) {
        return userService.create(mapper.toCreateUserDto(dto));
    }

    public LoginResponse login(LoginDto credentials) throws UnauthorizedException {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(credentials.email(),
                            credentials.password()));

            String accessToken = jwtService.generateToken(credentials.email());

            return new LoginResponse(accessToken);
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Invalid login credentials");
        }

    }
}
