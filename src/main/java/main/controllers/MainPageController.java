package main.controllers;

import main.ShortEntity.ShortAdvert;
import main.repository.AdvertRepository;
import main.service.AdvertService;
import main.service.UserService;
import main.tables.Advert;
import main.tables.AdvertType;
import main.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping(value = "/")
//@CrossOrigin(origins = "*")
public class MainPageController {
    @Autowired
    private AdvertService advertService;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Advert> getAllAdverts() {
        //get short version
        return advertService.getAllAdverts();

    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Advert newHouseSearchAdvert(@RequestBody ShortAdvert advert) {
        User owner = userService.findUserByUsername(advert.getUsername());
        if(owner == null)
            return null;

        return advertService.addNewAdvert(owner,
                advert.getHeader(),
                advert.getMessage(),
                advert.getPeopleNumber(),
                advert.getArrivingDate(),
                advert.getCheckOutDate(),
                advert.getCountry(),
                advert.getCity(),
                advert.getHome(),
                AdvertType.HOUSE_SEARCH
        );
    }
}
