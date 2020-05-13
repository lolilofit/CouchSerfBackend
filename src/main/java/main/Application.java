package main;

import com.google.gson.Gson;
import main.ShortEntity.ShortAdvert;
import main.repository.CommentRepository;
import main.service.AdvertService;
import main.service.UserService;
import main.tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Autowired
    AdvertService advertService;

    @Autowired
    CommentRepository commentRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws ParseException {

        userService.addNewUser("FirstUser", 23);
            userService.addNewUser("Sec", 34);
        /*
        User user = userService.findUserByUsername("First User");

        advertService.addNewAdvert(user.getUserid(),
                "Simple header",
                "some text",
                1,
                new SimpleDateFormat("dd/MM/yyyy").parse("12/08/2020"),
                new SimpleDateFormat("dd/MM/yyyy").parse("13/08/2020"),
                "Russia", "Nsk", "Lenina",
                AdvertType.HOUSE_SEARCH
                );

        List<Advert> all = advertService.getAllAdverts();

        all.forEach(ad -> {
            System.out.println(ad.getOwner() + " " + ad.getPublicationDate());
        });
        */

    }
}
