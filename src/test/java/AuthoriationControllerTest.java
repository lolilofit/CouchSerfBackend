import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.dto.SecuredUserDTO;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUser;
import nsu.fit.upprpo.csbackend.shortentity.UserRegisterInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
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
  public void LoginTest() throws Exception {
    SecuredUserDTO securedUserDTO = new SecuredUserDTO();
    securedUserDTO.setUsername("Bob");
    securedUserDTO.setPassword("Winner");

    this.mockMvc.perform(post("/auth/login")
            .content(objectMapper.writeValueAsString(securedUserDTO))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

  }

  @Test
  public void aregisterRequest() throws Exception {
    UserRegisterInfo userRegisterInfo = new UserRegisterInfo();
    userRegisterInfo.setAge(20);
    SecuredUser securedUser = new SecuredUser();
    securedUser.setUsername("Bob");
    securedUser.setPassword("Winner");
    userRegisterInfo.setSecuredUser(securedUser);

    this.mockMvc.perform(post("/auth/register")
            .content(objectMapper.writeValueAsString(userRegisterInfo))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

  }
}

