import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.security.TokenAuthenticationFilter;
import nsu.fit.upprpo.csbackend.security.data.JpaRoleLinkRepository;
import nsu.fit.upprpo.csbackend.security.data.JpaRoleRepository;
import nsu.fit.upprpo.csbackend.security.data.JpaSecuredUserRepository;
import nsu.fit.upprpo.csbackend.security.data.types.Role;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUser;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUserEntry;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUserRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationEntityTest {
    @Autowired
    private JpaRoleRepository jpaRoleRepository;

    @Autowired
    private JpaRoleLinkRepository jpaRoleLinkRepository;

    @Autowired
    private JpaSecuredUserRepository jpaSecuredUserRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(tokenAuthenticationFilter).build();
    }

        @Test
        @Transactional
    public void securedUserEntityTest() throws Exception {
        AuthorizationUtils.registerNewUser("entity", "123", objectMapper, mockMvc);

        Iterable<SecuredUserRole> roleLink = jpaRoleLinkRepository.findAll();
        SecuredUserRole securedUserRole = roleLink.iterator().next();

        Assert.assertNotNull(securedUserRole);
        Assert.assertEquals(securedUserRole, securedUserRole);
        Assert.assertEquals(securedUserRole.hashCode(), securedUserRole.hashCode());
        Assert.assertEquals(securedUserRole.toString(), objectMapper.writeValueAsString(securedUserRole));
        Assert.assertNotEquals(securedUserRole, new Object());

        Iterable<Role> roles = jpaRoleRepository.findAll();
        Role role = roles.iterator().next();

        Assert.assertNotNull(role);
        Assert.assertEquals(role, role);
        Assert.assertEquals(role.hashCode(), role.hashCode());
        Assert.assertEquals(role.toString(), objectMapper.writeValueAsString(role));

        Iterable<SecuredUser> securedUsers = jpaSecuredUserRepository.findAll();
        SecuredUser securedUser = securedUsers.iterator().next();

        Assert.assertNotNull(securedUser);
        Assert.assertEquals(securedUser, securedUser);
        Assert.assertEquals(securedUser.hashCode(), securedUser.hashCode());
        Assert.assertEquals(securedUser.toString(), objectMapper.writeValueAsString(securedUser));
        Assert.assertNotEquals(securedUser, new Object());

        SecuredUserEntry securedUserEntry = new SecuredUserEntry(0, "username");
        securedUserEntry.setPassword("1");
        securedUserEntry.setUserAuthorities(new ArrayList<>());

        Assert.assertEquals(securedUserEntry.toString(), objectMapper.writeValueAsString(securedUserEntry));
        Assert.assertEquals(securedUserEntry.hashCode(), securedUserEntry.hashCode());
        Assert.assertEquals(securedUserEntry, securedUserEntry);
        Assert.assertNotEquals(securedUserEntry, new Object());
    }
}
