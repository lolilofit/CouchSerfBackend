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

import java.util.Optional;

@Service
public class JpaSecuredUserDetailsService implements UserDetailsService {
    @Autowired
    private JpaSecuredUserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<SecuredUser> user = userRepository.findByUsername(s);
        if (!user.isPresent())
            throw new UsernameNotFoundException(s);
        SecuredUser foundUser = user.get();

        SecuredUserEntry userDetails = new SecuredUserEntry(foundUser.getUserId(), foundUser.getUsername());
        userDetails.setPassword(foundUser.getPassword());
        userDetails.setUserAuthorities(foundUser.getStringRoles());

        return userDetails;
    }
}
