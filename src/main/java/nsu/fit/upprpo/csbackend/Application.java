package nsu.fit.upprpo.csbackend;

import nsu.fit.upprpo.csbackend.controllers.AuthoriationController;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUser;
import nsu.fit.upprpo.csbackend.shortentity.UserRegisterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    AuthoriationController authoriationController;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) {
        SecuredUser securedUserFirst = new SecuredUser();
        securedUserFirst.setUsername("tester");
        securedUserFirst.setPassword("123");

        UserRegisterInfo userRegisterInfoFirst = new UserRegisterInfo();
        userRegisterInfoFirst.setAge(23);
        userRegisterInfoFirst.setSecuredUser(securedUserFirst);

        authoriationController.registerRequest(userRegisterInfoFirst);

        SecuredUser securedUserSecond = new SecuredUser();
        securedUserSecond.setUsername("usr");
        securedUserSecond.setPassword("1234");

        UserRegisterInfo userRegisterInfoSecond = new UserRegisterInfo();
        userRegisterInfoSecond.setAge(45);
        userRegisterInfoSecond.setSecuredUser(securedUserSecond);

        authoriationController.registerRequest(userRegisterInfoSecond);
    }
}
