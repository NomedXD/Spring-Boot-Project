package by.teachmeskills.project.principal;

import by.teachmeskills.project.domain.Role;
import by.teachmeskills.project.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
public class UserPrincipal extends org.springframework.security.core.userdetails.User {
    private User user;

    public UserPrincipal(User user) {
        super(user.getMail(), user.getPassword(), user.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).toList());
        this.user = user;
    }
}
