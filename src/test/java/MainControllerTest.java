import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.dto.AdvertDTO;
import nsu.fit.upprpo.csbackend.dto.PlaceDTO;
import nsu.fit.upprpo.csbackend.repository.UsersRepository;
import nsu.fit.upprpo.csbackend.security.TokenAuthenticationFilter;
import nsu.fit.upprpo.csbackend.security.data.types.Role;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUserEntry;
import nsu.fit.upprpo.csbackend.tables.AdvertType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MainControllerTest {

    @Autowired
    public WebApplicationContext wac;

    @Autowired
    public UsersRepository usersRepository;

    @Autowired
    public TokenAuthenticationFilter tokenAuthenticationFilter;

    public MockMvc mockMvc;
    public ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(tokenAuthenticationFilter).build();
        this.objectMapper = new ObjectMapper();
    }


    @Test
    public void getAllAdverts() throws Exception {
        String uri = "/advert/count";

        MvcResult mvcResult = mockMvc.perform(get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
    }


    private Cookie takeToken() throws Exception {
        String user = "{\n" +
                "\t\"username\" : \"tester\",\n" +
                "\t\"password\" : \"123\"\n" +
                "}";
        MvcResult mvcResult = this.mockMvc.perform(post("/auth/login").content(user).contentType("application/json")).andReturn();
        return mvcResult.getResponse().getCookie("JWTToken");
    }

    private static RequestPostProcessor sessionUser(final UserDetails userDetails) {
        return new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(final MockHttpServletRequest request) {
                final SecurityContext securityContext = new SecurityContextImpl();
                securityContext.setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                );
                request.getSession().setAttribute(
                        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext
                );
                return request;
            }
        };
    }


    @Test
    public void addAndGetAdvert() throws Exception {
        Cookie cookie = takeToken();

        AdvertDTO advertDTO = new AdvertDTO();
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setCity("Novosibirsk");
        placeDTO.setCountry("Russia");
        placeDTO.setHome("vla");
        advertDTO.setPlace(placeDTO);
        advertDTO.setAdvertType(AdvertType.HOUSE_SEARCH);
        advertDTO.setHeader("header");
        advertDTO.setMessage("Message");
        advertDTO.setArrivingDate(new SimpleDateFormat("dd/MM/yyyy").parse("9/11/2020"));
        advertDTO.setCheckOutDate(new SimpleDateFormat("dd/MM/yyyy").parse("10/11/2020"));
        advertDTO.setPeopleNumber(1);

        String jsonAd = objectMapper.writeValueAsString(advertDTO);

    SecuredUserEntry securedUserEntry = new SecuredUserEntry(1, "tester");
        List<String> auth = new ArrayList<>();
        auth.add(Role.Roles.USER_ROLE.name());

        securedUserEntry.setUserAuthorities(auth);

        mockMvc.perform(post("/adchange/add").cookie(cookie).content(jsonAd)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


        mockMvc.perform(get("/advert")).andReturn();
    }
}
