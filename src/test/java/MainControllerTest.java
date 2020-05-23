import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.dto.AdvertDTO;
import nsu.fit.upprpo.csbackend.dto.PlaceDTO;
import nsu.fit.upprpo.csbackend.repository.UsersRepository;
import nsu.fit.upprpo.csbackend.security.TokenAuthenticationFilter;
import nsu.fit.upprpo.csbackend.security.data.types.Role;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUserEntry;
import nsu.fit.upprpo.csbackend.shortentity.AdvertContainer;
import nsu.fit.upprpo.csbackend.tables.Advert;
import nsu.fit.upprpo.csbackend.tables.AdvertType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MainControllerTest {

    @Autowired
    public WebApplicationContext wac;

    @Autowired
    public UsersRepository usersRepository;

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
    public void getAllAdverts() throws Exception {
        String uri = "/advert/count";

        MvcResult mvcResult = mockMvc.perform(get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);
    }

    @Test
    public void addAdvert() throws Exception {

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
    }


    @Test
    public void getAdverts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/advert?pos=0&limit=10")).andReturn();

        int getStatus = mvcResult.getResponse().getStatus();
        Assert.assertEquals(getStatus, 200);

        String result = mvcResult.getResponse().getContentAsString();
        Advert[] adverts = objectMapper.readValue(result, Advert[].class);

        Assert.assertEquals(adverts.length, 1);
    }

    @Test
    public void getAdvertsWithHSType() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/advert?pos=0&limit=10&type=HOUSE_SEARCH")).andReturn();

        int getStatus = mvcResult.getResponse().getStatus();
        Assert.assertEquals(getStatus, 200);

        String result = mvcResult.getResponse().getContentAsString();
        Advert[] adverts = objectMapper.readValue(result, Advert[].class);

        Assert.assertEquals(adverts.length, 1);
    }


    @Test
    public void getAdvertsWithHPType() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/advert?pos=0&limit=10&type=HOUSE_PROVISION")).andReturn();

        int getStatus = mvcResult.getResponse().getStatus();
        Assert.assertEquals(getStatus, 200);

        String result = mvcResult.getResponse().getContentAsString();
        Advert[] adverts = objectMapper.readValue(result, Advert[].class);

        Assert.assertEquals(adverts.length, 0);
    }


    @Test
    public void getOneAdvert() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/advert/50")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(status, 200);

        AdvertContainer advert = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AdvertContainer.class);
        Assert.assertNotNull(advert);
    }

    @Test
    public void subscriberAdd() throws Exception {
        cookie = AuthorizationUtils.takeToken("usr", "1234", mockMvc, objectMapper);

        mockMvc.perform(put("/adchange/50/addsubscriber").cookie(cookie)).andExpect(status().isOk());
    }

    @Test
    public void leaveComment() throws Exception {
        mockMvc.perform(post("/comments/50/add").content("leave message").cookie(cookie))
                .andExpect(status().isOk());
    }
}
