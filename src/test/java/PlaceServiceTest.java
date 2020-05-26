import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.service.PlaceService;
import nsu.fit.upprpo.csbackend.tables.Place;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceServiceTest {
    @Autowired
    private PlaceService placeService;

    @Test
    public void addNewPlace() {
        Place place = placeService.addNewPlace("Russia", "Tomsk", "Vertk");
        Assert.assertNotNull(place);
    }

    @Test
    public void getPlace() {
        List<Place> places = placeService.getPlaceWithFilters("Russia", "Tomsk", "Vertk");
        Assert.assertEquals(1, places.size());

        Place place = places.get(0);

        Assert.assertEquals("Tomsk", place.getCity());
        Assert.assertEquals("Russia", place.getCountry());
        Assert.assertEquals("Vertk", place.getHome());

        places = placeService.getPlaceWithFilters(null, "Tomsk", "Vertk");
        Assert.assertEquals(0, places.size());

    }
}
