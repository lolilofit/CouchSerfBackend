package nsu.fit.upprpo.csbackend.controllers;

import nsu.fit.upprpo.csbackend.dto.SecuredUserDTO;
import nsu.fit.upprpo.csbackend.repository.UsersRepository;
import nsu.fit.upprpo.csbackend.security.JpaSecuredUserDetailsService;
import nsu.fit.upprpo.csbackend.security.RoleHelper;
import nsu.fit.upprpo.csbackend.security.data.JpaSecuredUserRepository;
import nsu.fit.upprpo.csbackend.security.data.types.Role.Roles;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUser;
import nsu.fit.upprpo.csbackend.security.jwt.CookieUtils;
import nsu.fit.upprpo.csbackend.security.jwt.JwtToken;
import nsu.fit.upprpo.csbackend.security.jwt.JwtUtils;
import nsu.fit.upprpo.csbackend.shortentity.UserRegisterInfo;
import nsu.fit.upprpo.csbackend.tables.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthoriationController {

    private static final Logger logger = Logger.getLogger(AuthoriationController.class);

    @Autowired
    private JpaSecuredUserDetailsService jpaSecuredUserDetailsService;

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


    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JwtToken> loginRequest(@RequestBody @Valid SecuredUserDTO user, HttpServletResponse response) {
        logger.info("Try to login user " + user.getUsername());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails userEntry = jpaSecuredUserDetailsService.loadUserByUsername(user.getUsername());
        String responseToken = JwtUtils.generateToken(userEntry);

        CookieUtils.saveCookie(JwtUtils.getCOOKIE_NAME(), responseToken, JwtUtils.getJWT_TOKEN_VALIDITY(), response);
        return new ResponseEntity<>(new JwtToken(responseToken), HttpStatus.OK);
    }


    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> registerRequest(@RequestBody @Valid UserRegisterInfo userRegisterInfo) {
        SecuredUser securedUser = userRegisterInfo.getSecuredUser();
        String username = securedUser.getUsername();

        logger.info("Try to register user " + username);

        List<SecuredUser> foundUser = jpaSecuredUserRepository.findByUsername(username);

        if (!foundUser.isEmpty()) {
            logger.error("Username already exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String encoded = passwordEncoder.encode(securedUser.getPassword());
        securedUser.setPassword(encoded);

        SecuredUser createdUser = jpaSecuredUserRepository.save(securedUser);
        roleHelper.addRoleTo(createdUser.getUserId(), Roles.USER_ROLE);

        User user = new User();
        user.setAge(userRegisterInfo.getAge());
        user.setUsername(securedUser.getUsername());
        usersRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
