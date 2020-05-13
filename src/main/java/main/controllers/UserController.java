package main.controllers;

import main.service.UserService;
import main.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "user/{username}", method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public User getUserInfo(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }
}
