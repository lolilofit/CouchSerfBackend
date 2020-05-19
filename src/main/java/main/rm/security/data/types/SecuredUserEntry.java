package main.rm.security.data.types;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SecuredUserEntry implements UserDetails {
    @NotEmpty
    private final Integer uid;

    @NotEmpty
    private final String username;

    @NotEmpty
    @Setter
    private String password;

    @NotEmpty
    @Setter
    private List<String> userAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> resultedAuthorities = new ArrayList<>();

        userAuthorities.forEach((role) -> resultedAuthorities.add(new SimpleGrantedAuthority(role)));
        return resultedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
