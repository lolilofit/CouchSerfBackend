import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.service.AdvertService;
import nsu.fit.upprpo.csbackend.service.UserService;
import nsu.fit.upprpo.csbackend.tables.Advert;
import nsu.fit.upprpo.csbackend.tables.AdvertType;
import nsu.fit.upprpo.csbackend.tables.Place;
import nsu.fit.upprpo.csbackend.tables.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdvertServiceTest {
    @Autowired
    private AdvertService advertService;

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Advert createAdvert(String header, String message, String arriving, String checkout,
                                String country, String city, String home) throws ParseException {
        Advert advert = new Advert();
        advert.setAdvertType(AdvertType.HOUSE_PROVISION);
        advert.setArrivingDate(new SimpleDateFormat("dd/MM/yyyy").parse(arriving));
        advert.setCheckOutDate(new SimpleDateFormat("dd/MM/yyyy").parse(checkout));
        advert.setHeader(header);
        advert.setMessage(message);
        advert.setPeopleNumber(2);

        Place place = new Place();
        place.setCity(city);
        place.setCountry(country);
        place.setHome(home);
        advert.setPlace(place);

        return advert;
    }

    @Test
    public void addNewAdvert() throws ParseException {
        User user = userService.findUserByUsername("tester");

        Advert advert = createAdvert("head", "Mes", "9/11/2020", "10/11/2020",
                "Russia", "Tula", "fr");
        advertService.addNewAdvert(advert, user);


        Advert secondAdvert = createAdvert("headeeer", "Mes2", "9/11/2020", "15/11/2020",
                "Russia", "NSK", "fr");
        advertService.addNewAdvert(secondAdvert, user);
    }

    public List<Advert> getAdverts() {
        List<Advert> adverts = advertService.getAllAdverts();
        int size = adverts.size();
        Assert.assertTrue(size > 0);

        return adverts;
    }

    @Test
    public void getAdvertsTest() {
        getAdverts();
    }

    private void equalsTest(Advert firstAdvert, Advert secondAdvert) {
        Assert.assertEquals(firstAdvert, firstAdvert);
        Assert.assertNotEquals(firstAdvert, secondAdvert);
    }

    private void hashCodeTest(Advert firstAdvert, Advert secondAdvert) {
        Assert.assertNotEquals(firstAdvert.hashCode(), secondAdvert.hashCode());
        Assert.assertEquals(firstAdvert.hashCode(), firstAdvert.hashCode());
    }

    private void toStringTest(Advert firstAdvert) throws JsonProcessingException {
        String advertJson = objectMapper.writeValueAsString(firstAdvert);
        Assert.assertEquals(advertJson, firstAdvert.toString());

        String userJson = objectMapper.writeValueAsString(firstAdvert.getOwner());
        Assert.assertEquals(firstAdvert.getOwner().toString(), userJson);

        String placeJson = objectMapper.writeValueAsString(firstAdvert.getPlace());
        Assert.assertEquals(placeJson, firstAdvert.getPlace().toString());
    }

    @Transactional
    @Test
    public void basicMethodsTest() throws JsonProcessingException {
        List<Advert> adverts = getAdverts();

        Advert firstAdvert = adverts.get(0);
        Advert secondAdvert = adverts.get(1);

        equalsTest(firstAdvert, secondAdvert);
        hashCodeTest(firstAdvert, secondAdvert);
        toStringTest(firstAdvert);
    }
}
