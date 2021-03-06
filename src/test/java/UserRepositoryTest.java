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
        User user = userService.addNewUser("testUserService", 88);
        Assert.assertNotNull(user);
    }

    @Test
    public void getUser() {
        User user = userService.findUserByUsername("testUserService");
        Assert.assertNotNull(user);

        Assert.assertEquals("testUserService", user.getUsername());
        Assert.assertNotEquals(user, new Object());
        Assert.assertEquals(user, user);
    }
}
