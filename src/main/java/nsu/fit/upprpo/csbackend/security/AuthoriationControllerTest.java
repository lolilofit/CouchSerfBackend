package nsu.fit.upprpo.csbackend.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.dto.SecuredUserDTO;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUser;
import nsu.fit.upprpo.csbackend.security.jwt.JwtToken;
import nsu.fit.upprpo.csbackend.shortentity.UserRegisterInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AuthoriationControllerTest {

  @Autowired
  public WebApplicationContext wac;

  public MockMvc mockMvc;
  public ObjectMapper objectMapper;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    this.objectMapper = new ObjectMapper();
  }

  public AuthoriationControllerTest() {}

  @Test
  @Order(2)
  public void loginRequest() throws Exception {
    SecuredUserDTO securedUserDTO = new SecuredUserDTO();
    securedUserDTO.setUsername("Bob");
    securedUserDTO.setPassword("Winner");

    this.mockMvc.perform(post("/auth/login").content(objectMapper.writeValueAsString(securedUserDTO)).contentType("application/json"))
        .andExpect(status().isOk());
  }

  @Test
  @Order(1)
  public void registerRequest() throws Exception {
    UserRegisterInfo userRegisterInfo = new UserRegisterInfo();
    userRegisterInfo.setAge(20);
    SecuredUser securedUser = new SecuredUser();
    securedUser.setUsername("Bob");
    securedUser.setPassword("Winner");
    userRegisterInfo.setSecuredUser(securedUser);

    this.mockMvc.perform(post("/auth/register").content(objectMapper.writeValueAsString(userRegisterInfo)).contentType("application/json"))
        .andExpect(status().isOk());
  }
}