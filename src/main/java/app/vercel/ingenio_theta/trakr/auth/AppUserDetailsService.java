package app.vercel.ingenio_theta.trakr.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import app.vercel.ingenio_theta.trakr.users.User;
import app.vercel.ingenio_theta.trakr.users.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(username);

        if (user.isPresent()) {
            return new Principal(user.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

}
