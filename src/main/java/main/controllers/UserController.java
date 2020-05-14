package main.controllers;

import main.repository.UsersRepository;
import main.service.UserService;
import main.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;


    @RequestMapping(value = "user/{username}", method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public User getUserInfo(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    //username - тот, кому выставляют оценку
    @RequestMapping(value = "/user/changeCsRate", method = RequestMethod.PUT)
    @ResponseBody
    public User ChangeUsersCouchSerfRating(@RequestParam(value = "username", required = true) String username,
                                           @RequestParam(value = "rate", required = true) Float rate) {
        if(rate > 5)
            return null;
        User user = userService.findUserByUsername(username);
        user.setCouchSerferRating(user.getCouchSerferRating() + rate);
        user.setCouchSerferRatingsNum(user.getCouchSerferRatingsNum() + 1);

        return usersRepository.save(user);
    }

    @RequestMapping(value = "/user/changeHcRate", method = RequestMethod.PUT)
    @ResponseBody
    public User ChangeUsersHouseKeeperRating(@RequestParam(value = "username", required = true) String username,
                                             @RequestParam(value = "rate", required = true) Float rate) {
        if(rate > 5)
            return null;
        User user = userService.findUserByUsername(username);
        user.setHouseProvisionRating(user.getHouseProvisionRating() + rate);
        user.setHouseProvisionRatingsNum(user.getHouseProvisionRatingsNum() + 1);

        return usersRepository.save(user);
    }


}
