package nsu.fit.upprpo.csbackend.controllers;

import nsu.fit.upprpo.csbackend.repository.UsersRepository;
import nsu.fit.upprpo.csbackend.service.UserService;
import nsu.fit.upprpo.csbackend.tables.User;
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


    @RequestMapping(value = "userinfo/{username}", method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public User getUserInfo(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    //@PutMapping(path = "/user/changeCsRate",  produces = "application/json")
    @RequestMapping(value = "/user/{username}/changeCsRate", method = RequestMethod.PUT)
    @ResponseBody
    public User сhangeUserCsRating(@PathVariable String username,
                                           @RequestParam(value = "rate", required = true) Float rate) {
        if(rate > 5)
            return null;
        User user = userService.findUserByUsername(username);
        user.setCouchSerferRating(user.getCouchSerferRating() + rate);
        user.setCouchSerferRatingsNum(user.getCouchSerferRatingsNum() + 1);

        return usersRepository.save(user);
    }

    @RequestMapping(value = "/user/{username}/changeHcRate", method = RequestMethod.PUT)
    @ResponseBody
    public User сhangeUserHkRating(@PathVariable String username,
                                             @RequestParam(value = "rate", required = true) Float rate) {
        if(rate > 5)
            return null;
        User user = userService.findUserByUsername(username);
        user.setHouseProvisionRating(user.getHouseProvisionRating() + rate);
        user.setHouseProvisionRatingsNum(user.getHouseProvisionRatingsNum() + 1);

        return usersRepository.save(user);
    }


}
