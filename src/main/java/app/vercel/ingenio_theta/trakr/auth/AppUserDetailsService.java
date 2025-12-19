package app.vercel.ingenio_theta.trakr.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import app.vercel.ingenio_theta.trakr.users.User;
import app.vercel.ingenio_theta.trakr.users.UserRepository;

@Component
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return new Principal(user.get());
        }
    }
    
}
