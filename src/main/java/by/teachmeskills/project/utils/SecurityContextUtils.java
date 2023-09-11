package by.teachmeskills.project.utils;

import by.teachmeskills.project.principal.UserPrincipal;
import by.teachmeskills.project.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityContextUtils {
    public static Optional<User> getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                instanceof UserPrincipal principal ? Optional.of(principal.getUser()) : Optional.empty();
    }
}
