import nsu.fit.upprpo.csbackend.Application;
import nsu.fit.upprpo.csbackend.service.UserService;
import nsu.fit.upprpo.csbackend.tables.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTest {
    @Autowired
    private UserService userService;

    @Test
    public void addNewUser() {
        userService.addNewUser("testUserService", 88);
    }

    @Test
    public void getUser() {
        User user = userService.findUserByUsername("testUserService");
        Assert.assertNotNull(user);

        Assert.assertEquals(user.getUsername(), "testUserService");
    }
}
