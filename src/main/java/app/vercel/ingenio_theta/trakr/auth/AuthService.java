package app.vercel.ingenio_theta.trakr.auth;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.auth.dtos.LoginDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.UnauthorizedException;

@Service
public class AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String register() {
        return "Register";
    }

    public String login(LoginDto credentials) throws Exception {
        try {
            Authentication authToken = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(credentials.email(),
                            credentials.password()));

            if (!authToken.isAuthenticated()) {
                throw new UnauthorizedException("Invalid login credentials");
            }

            return jwtService.generateToken(credentials.email());

        } catch (Exception e) {
            throw new AuthenticationException("Invalid login credentials");
        }

    }
}
