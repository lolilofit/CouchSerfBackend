package main;

import main.repository.CommentRepository;
import main.service.AdvertService;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;


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
    }
}
