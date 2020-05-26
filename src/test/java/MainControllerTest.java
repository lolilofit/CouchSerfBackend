import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.dto.AdvertDTO;
import nsu.fit.upprpo.csbackend.dto.PlaceDTO;
import nsu.fit.upprpo.csbackend.repository.AdvertRepository;
import nsu.fit.upprpo.csbackend.repository.CommentRepository;
import nsu.fit.upprpo.csbackend.repository.UsersRepository;
import nsu.fit.upprpo.csbackend.security.TokenAuthenticationFilter;
import nsu.fit.upprpo.csbackend.security.data.types.Role;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUserEntry;
import nsu.fit.upprpo.csbackend.shortentity.AdvertContainer;
import nsu.fit.upprpo.csbackend.shortentity.ShortComment;
import nsu.fit.upprpo.csbackend.tables.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public AdvertRepository advertRepository;

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
        Iterable<Advert> prevAdverts = advertRepository.findAll();
        AtomicInteger prevSize = new AtomicInteger();
        prevAdverts.forEach(advert -> {
            prevSize.getAndIncrement();
        });

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

        Iterable<Advert> adverts = advertRepository.findAll();
        AtomicInteger size = new AtomicInteger();
        adverts.forEach(advert -> {
            size.getAndIncrement();
        });
        Assert.assertTrue(size.get() > 0);

        Advert advert = adverts.iterator().next();
        Place place = advert.getPlace();

        Assert.assertEquals(placeDTO.getCity(), place.getCity());
        Assert.assertEquals(placeDTO.getCountry(), place.getCountry());
        Assert.assertEquals(placeDTO.getHome(), place.getHome());

        Assert.assertEquals(advertDTO.getMessage(), advert.getMessage());
        Assert.assertEquals(advert.getAdvertType(), advertDTO.getAdvertType());
        Assert.assertEquals(advertDTO.getArrivingDate(), advert.getArrivingDate());
        Assert.assertEquals(advertDTO.getCheckOutDate(), advert.getCheckOutDate());
        Assert.assertEquals(advertDTO.getPeopleNumber(), advert.getPeopleNumber());
        Assert.assertEquals(advertDTO.getHeader(), advert.getHeader());

        Date publicationDate = advert.getPublicationDate();
        Date currentDate = new Date(System.currentTimeMillis());
        Assert.assertTrue(publicationDate.equals(currentDate) || (publicationDate.compareTo(currentDate) < 0));

        User owner = advert.getOwner();
        Assert.assertEquals("tester", owner.getUsername());
        Assert.assertEquals(23, owner.getAge());
    }


    @Test
    public void getAdverts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/advert?pos=0&limit=10")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals( 200, status);

        String result = mvcResult.getResponse().getContentAsString();
        Advert[] adverts = objectMapper.readValue(result, Advert[].class);

        Assert.assertTrue(adverts.length > 0);
    }

    @Test
    public void getAdvertsWithHSType() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/advert?pos=0&limit=10&type=HOUSE_SEARCH")).andReturn();

        int getStatus = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, getStatus);

        String result = mvcResult.getResponse().getContentAsString();
        Advert[] adverts = objectMapper.readValue(result, Advert[].class);

        Assert.assertEquals(1, adverts.length);
    }


    @Test
    public void getAdvertsWithHPType() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/advert?pos=0&limit=10&type=HOUSE_PROVISION")).andReturn();

        int getStatus = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, getStatus);

        String result = mvcResult.getResponse().getContentAsString();
        Advert[] adverts = objectMapper.readValue(result, Advert[].class);

        Assert.assertEquals(0, adverts.length);
    }


    @Test
    public void getOneAdvert() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/advert/1")).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals( 200, status);

        AdvertContainer advert = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AdvertContainer.class);
        Assert.assertNotNull(advert);
    }

    @Test
    public void subscriberAdd() throws Exception {
        cookie = AuthorizationUtils.takeToken("usr", "1234", mockMvc, objectMapper);

        MvcResult mvcResult = mockMvc.perform(put("/adchange/1/addsubscriber").cookie(cookie)).andReturn();

        MockHttpServletResponse resp = mvcResult.getResponse();
        Assert.assertEquals(200, resp.getStatus());

        String res = resp.getContentAsString();
        Advert advert = objectMapper.readValue(res, Advert.class);

        Long adId = advert.getAdId();
        Assert.assertEquals(new Long(1), adId);
    }

    @Test
    public void leaveComment() throws Exception {
        ShortComment shortComment = new ShortComment();
        shortComment.setMessage("leave message");

        MvcResult mvcResult = mockMvc.perform(post("/comments/1/add").
                content(objectMapper.writeValueAsBytes(shortComment)).contentType(MediaType.APPLICATION_JSON).cookie(cookie)).andReturn();

        MockHttpServletResponse resp = mvcResult.getResponse();
        Assert.assertEquals(200, resp.getStatus());

        String res = resp.getContentAsString();
        AdvertContainer advertContainer = objectMapper.readValue(res, AdvertContainer.class);

        Advert advert = advertRepository.findByAdId(1L);
        List<Comment> comments = commentRepository.findCommentsByCommentAdvert(advert);
        int commentsSize = comments.size();
        Assert.assertTrue(commentsSize > 0);

        List<Comment> advertComments = advertContainer.getComments();
        Assert.assertEquals("leave message", advertComments.get(0).getMessage());


        Comment comment = advertComments.get(0);
        Assert.assertEquals(comment.toString(), objectMapper.writeValueAsString(comment));
        Assert.assertTrue(comment.hashCode() > 0);
        Assert.assertEquals(comment, comment);
    }
}
