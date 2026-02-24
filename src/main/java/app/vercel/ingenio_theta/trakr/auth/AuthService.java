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
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthMapper mapper;

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
