package app.vercel.ingenio_theta.trakr.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.vercel.ingenio_theta.trakr.auth.dtos.LoginDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    public String register() {
        return service.register();
    }

    @RequestMapping("/login")
    public String login(@Valid @RequestBody LoginDto credentials) throws Exception {
        return service.login(credentials);
    }
}
