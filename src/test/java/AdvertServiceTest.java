import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.service.AdvertService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdvertServiceTest {
    @Autowired
    private AdvertService advertService;

    @Test
    public void addNewAdvert() {

    }
}
