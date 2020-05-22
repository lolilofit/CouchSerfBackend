import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AuthorizationTest {

    @Autowired
    public WebApplicationContext wac;

    public MockMvc mockMvc;
    public ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.objectMapper = new ObjectMapper();
    }


    @Test
    public void getAllAdverts() throws Exception {
        String uri = "/advert/count";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
    }

/*
    @Test
    public void testRegister() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        String uri = "/auth/register";
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new AuthoriationController()).build();

        SecuredUser securedUser = new SecuredUser();
        securedUser.setUsername("darya");
        securedUser.setPassword("123");

        UserRegisterInfo userRegisterInfo = new UserRegisterInfo();
        userRegisterInfo.setSecuredUser(securedUser);
        userRegisterInfo.setAge(34);

        //String userJson = objectMapper.writeValueAsString(userRegisterInfo);
        String userJson = "{\n" +
                "\"securedUser\": {\n" +
                "\"username\": \"gddd\",\n" +
                "\"password\": \"ew\"\n" +
                "},\n" +
                "\"age\": 21\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).content(userJson)).andReturn();

        String resp = mvcResult.getResponse().getErrorMessage();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);

    }
    */

}
