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
        placeService.addNewPlace("Russia", "Tomsk", "Vertk");
    }

    @Test
    public void getPlace() {
        List<Place> place = placeService.getPlaceWithFilters("Russia", "Tomsk", "Vertk");
        Assert.assertEquals(1, place.size());

        place = placeService.getPlaceWithFilters(null, "Tomsk", "Vertk");
        Assert.assertEquals(0, place.size());
    }
}
