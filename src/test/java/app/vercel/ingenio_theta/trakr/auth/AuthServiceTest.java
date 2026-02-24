package app.vercel.ingenio_theta.trakr.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import app.vercel.ingenio_theta.trakr.auth.dtos.LoginDto;
import app.vercel.ingenio_theta.trakr.auth.dtos.LoginResponse;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.UnauthorizedException;
import app.vercel.ingenio_theta.trakr.users.UserService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AuthMapper authMapper;
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService service;

    @Test
    void testLogin_OK() {
        String email = "john@doe.com";
        String password = "12345";

        String accessToken = "generated-access-token";

        LoginDto credentials = new LoginDto(email, password);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password,
                AuthorityUtils.createAuthorityList("USER"));

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)))
                .thenReturn(authentication);

        when(jwtService.generateToken(email)).thenReturn(accessToken);

        try {
            LoginResponse response = service.login(credentials);

            assertNotNull(response);
            assertEquals(response.getAccessToken(), accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testLogin_InvalidCredentials() {
        String email = "john@doe.com";
        String password = "00000";

        LoginDto credentials = new LoginDto(email, password);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)))
                .thenThrow(new UnauthorizedException("Invalid login credentials"));

        assertThrows(UnauthorizedException.class, () -> service.login(credentials));
    }

    @Test
    void testRegister() {

    }
}
