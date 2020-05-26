import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.security.TokenAuthenticationFilter;
import nsu.fit.upprpo.csbackend.tables.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserControllerTest {


    @Autowired
    public WebApplicationContext wac;

    @Autowired
    public TokenAuthenticationFilter tokenAuthenticationFilter;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Cookie cookie;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(tokenAuthenticationFilter).build();
        this.objectMapper = new ObjectMapper();
        cookie = AuthorizationUtils.takeToken("tester", "123", mockMvc, objectMapper);
    }

    @Test
    public void getUserInfo() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/userinfo/tester")).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(200, response.getStatus());

        String userJson = response.getContentAsString();
        User user = objectMapper.readValue(userJson, User.class);

        Assert.assertEquals("tester", user.getUsername());
    }

    @Test
    public void changeCsRate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/user/usr/changeCsRate?rate=2").cookie(cookie)).andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.put("/user/usr/changeCsRate?rate=7").cookie(cookie)).andExpect(status().isOk());
    }

    @Test
    public void changeHcRate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/user/usr/changeHcRate?rate=2").cookie(cookie)).andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.put("/user/usr/changeHcRate?rate=7").cookie(cookie)).andExpect(status().isOk());
    }
}
