package app.vercel.ingenio_theta.trakr.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.auth.dtos.LoginDto;
import app.vercel.ingenio_theta.trakr.auth.dtos.LoginResponse;
import app.vercel.ingenio_theta.trakr.auth.dtos.RegisterDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.UnauthorizedException;
import app.vercel.ingenio_theta.trakr.users.UserService;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@Service
public class AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthMapper mapper;

    public UserResponse register(RegisterDto dto) {
        return userService.create(mapper.toCreateUserDto(dto));
    }

    public LoginResponse login(LoginDto credentials) throws Exception {
        try {
            Authentication authToken = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(credentials.email(),
                            credentials.password()));

            if (!authToken.isAuthenticated()) {
                throw new UnauthorizedException("Invalid login credentials");
            }

            String accessToken = jwtService.generateToken(credentials.email());

            return new LoginResponse(accessToken);

        } catch (Exception e) {
            throw new UnauthorizedException("Invalid login credentials");
        }

    }
}
