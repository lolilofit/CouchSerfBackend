package main.rm.security;

import main.rm.security.data.JpaSecuredUserRepository;
import main.rm.security.data.types.SecuredUser;
import main.rm.security.data.types.SecuredUserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JpaSecuredUserDetailsService implements UserDetailsService {
    @Autowired
    private JpaSecuredUserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) {
        List<SecuredUser> user = userRepository.findByUsername(s);
        if (user.isEmpty())
            throw new UsernameNotFoundException(s);
        SecuredUser foundUser = user.get(0);

        SecuredUserEntry userDetails = new SecuredUserEntry(foundUser.getUserId(), foundUser.getUsername());
        userDetails.setPassword(foundUser.getPassword());
        userDetails.setUserAuthorities(foundUser.getStringRoles());

        return userDetails;
    }
}
