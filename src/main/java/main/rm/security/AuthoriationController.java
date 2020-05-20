package main.rm.security;

import main.repository.UsersRepository;
import main.rm.security.data.JpaSecuredUserRepository;
import main.rm.security.data.types.Role;
import main.rm.security.data.types.SecuredUser;
import main.rm.security.jwt.CookieUtils;
import main.rm.security.jwt.JwtToken;
import main.rm.security.jwt.JwtUtils;
import main.shortentity.UserRegisterInfo;
import main.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/auth")
@CrossOrigin("*")
public class AuthoriationController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleHelper roleHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JpaSecuredUserRepository jpaSecuredUserRepository;

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(path = "/login", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<JwtToken> loginRequest(@RequestBody @Valid SecuredUser user, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails userEntry = userDetailsService.loadUserByUsername(user.getUsername());
        String responseToken = JwtUtils.generateToken(userEntry);

        CookieUtils.saveCookie(JwtUtils.getCookieName(), responseToken, JwtUtils.getJWT_TOKEN_VALIDITY(), response);
        return new ResponseEntity<>(new JwtToken(responseToken), HttpStatus.OK);
    }


    @RequestMapping(path = "/register", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<String> registerRequest(@RequestBody @Valid UserRegisterInfo userRegisterInfo) {
        SecuredUser securedUser = userRegisterInfo.getSecuredUser();

        String username = securedUser.getUsername();
        List<SecuredUser> foundUser = jpaSecuredUserRepository.findByUsername(username);

        if (foundUser.size() > 0)
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);

        String encoded = passwordEncoder.encode(securedUser.getPassword());
        securedUser.setPassword(encoded);

        SecuredUser createdUser = jpaSecuredUserRepository.save(securedUser);
        roleHelper.addRoleTo(createdUser.getUserId(), Role.Roles.USER_ROLE);

        User user = new User();
        user.setAge(userRegisterInfo.getAge());
        user.setUsername(securedUser.getUsername());
        usersRepository.save(user);

        return new ResponseEntity<>("User created", HttpStatus.OK);
    }
}
