import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.dto.AdvertDTO;
import nsu.fit.upprpo.csbackend.dto.PlaceDTO;
import nsu.fit.upprpo.csbackend.dto.SecuredUserDTO;
import nsu.fit.upprpo.csbackend.tables.AdvertType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DtoTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void securedUserDtoTest() throws JsonProcessingException {
        SecuredUserDTO securedUserDTO = new SecuredUserDTO();
        securedUserDTO.setUsername("user");
        securedUserDTO.setPassword("123");

        Assert.assertEquals(securedUserDTO, securedUserDTO);
        Assert.assertEquals(securedUserDTO.hashCode(), securedUserDTO.hashCode());
        Assert.assertEquals(securedUserDTO.toString(), objectMapper.writeValueAsString(securedUserDTO));
    }

    @Test
    public void advertDtoTest() throws ParseException, JsonProcessingException {
        AdvertDTO advertDTO = new AdvertDTO();
        advertDTO.setCheckOutDate(new SimpleDateFormat("dd/MM/yyyy").parse("10/11/2020"));
        advertDTO.setArrivingDate(new SimpleDateFormat("dd/MM/yyyy").parse("9/11/2020"));
        advertDTO.setAdvertType(AdvertType.HOUSE_SEARCH);
        advertDTO.setMessage("m");
        advertDTO.setHeader("h");
        advertDTO.setPeopleNumber(1);
        advertDTO.setPublicationDate(new Date(System.currentTimeMillis()));

        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setCity("c");
        placeDTO.setCountry("c");
        placeDTO.setHome("h");

        advertDTO.setPlace(placeDTO);

        Assert.assertEquals(advertDTO, advertDTO);
        Assert.assertEquals(advertDTO.hashCode(), advertDTO.hashCode());
        Assert.assertEquals(advertDTO.toString(), objectMapper.writeValueAsString(advertDTO));

        Assert.assertEquals(placeDTO, placeDTO);
        Assert.assertEquals(placeDTO.hashCode(), placeDTO.hashCode());
        Assert.assertEquals(placeDTO.toString(), objectMapper.writeValueAsString(placeDTO));
    }
}
