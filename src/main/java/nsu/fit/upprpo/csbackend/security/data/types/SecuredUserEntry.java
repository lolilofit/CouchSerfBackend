package nsu.fit.upprpo.csbackend.security.data.types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SecuredUserEntry implements UserDetails {
    private static final Logger logger = Logger.getLogger(SecuredUserEntry.class);

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

    @Transactional
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> resultedAuthorities = new ArrayList<>();

        userAuthorities.forEach(role -> resultedAuthorities.add(new SimpleGrantedAuthority(role)));
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

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    @Override
    public int hashCode() {
        int hc = userAuthorities.stream().mapToInt(String::hashCode).sum();
        return username.hashCode() + password.hashCode() + hc;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof SecuredUserEntry))
            return false;
        SecuredUserEntry securedUserEntry = (SecuredUserEntry) o;

        List<String> userAuth = securedUserEntry.getUserAuthorities();
        if(userAuth.size() != this.userAuthorities.size())
            return false;
        for(int i = 0; i < userAuthorities.size(); i++)
            if(!userAuth.get(i).equals(this.userAuthorities.get(i)))
                return false;
        return username.equals(securedUserEntry.getUsername()) && password.equals(securedUserEntry.getPassword());
    }
}
