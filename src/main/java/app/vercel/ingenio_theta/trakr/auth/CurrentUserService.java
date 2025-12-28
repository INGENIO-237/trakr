package app.vercel.ingenio_theta.trakr.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.users.User;

@Service
public class CurrentUserService {
    private Principal getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Principal) {
            return (Principal) principal;
        }

        return null;
    }

    public User getUser() {
        Principal principal = getPrincipal();
        return principal == null ? null : principal.getUser();
    }

    public String getUserId() {
        Principal principal = getPrincipal();

        return principal == null ? null : principal.getId();
    }
}
