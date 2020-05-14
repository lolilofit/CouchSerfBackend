package main.controllers;

import main.ShortEntity.AdvertContainer;
import main.ShortEntity.ShortAdvert;
import main.repository.AdvertRepository;
import main.repository.CommentRepository;
import main.repository.UsersRepository;
import main.service.AdvertService;
import main.service.UserService;
import main.tables.Advert;
import main.tables.AdvertType;
import main.tables.Comment;
import main.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/")
public class MainPageController {
    @Autowired
    private AdvertService advertService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private CommentRepository commentRepository;


    private Advert addNewAd(ShortAdvert advert, AdvertType advertType) {
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
                advertType
        );

    }

    private List<Advert> sortByDate(List<Advert> adverts) {
        return adverts.stream().sorted(new Comparator<Advert>() {
            @Override
            public int compare(Advert o1, Advert o2) {
                return o1.getPublicationDate().compareTo(o2.getPublicationDate());
            }
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = "/advert/count", method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public Long getAdvertsCount() {
        return advertRepository.count();
    }


    @RequestMapping(value = "/advert", method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public List<Advert> getAllAdverts(@RequestParam(value = "limit", required = false) Integer limit,
                                      @RequestParam(value = "pos", required = false) Integer pos,
                                      @RequestParam(value = "type", required = false) AdvertType advertType) {
        //get short version
        List<Advert> allAdverts =  advertService.getAllAdverts();
        if(limit == null) {
            //add showwith pos
            return sortByDate(allAdverts);
        }
        else {
            if(pos == null)
                pos = 0;
            if(allAdverts.size() < pos)
                pos = 0;
            allAdverts = sortByDate(allAdverts);

            List<Advert> res = new ArrayList<>();
            for(int i = pos; i < (pos + limit) && i < allAdverts.size(); i++) {
                res.add(allAdverts.get(i));
            }
            return res;
        }
    }

    @RequestMapping(value = "/hsad", method = RequestMethod.POST, produces ="application/json")
    @ResponseBody
    public Advert newHouseSearchAdvert(@RequestBody ShortAdvert advert) {
        return addNewAd(advert, AdvertType.HOUSE_SEARCH);
    }


    @RequestMapping(value = "/hpad", method = RequestMethod.POST, produces ="application/json")
    @ResponseBody
    public Advert newHouseProvisionAdvert(@RequestBody ShortAdvert advert) {
        return addNewAd(advert, AdvertType.HOUSE_PROVISION);
    }

    @RequestMapping(value = "/advert/{adId}", method = RequestMethod.GET)
    @ResponseBody
    public AdvertContainer getAdvertInfo(@PathVariable Long adId) {
        Advert advert = advertRepository.findByAdId(adId);
        List<Comment> comments = commentRepository.findCommentsByCommentAdvert(advert);

        AdvertContainer advertContainer = new AdvertContainer();
        advertContainer.setAdvert(advert);
        advertContainer.setComments(comments);

        return advertContainer;
    }

    @RequestMapping(value = "/adver/addsubscriber", method = RequestMethod.PUT)
    @ResponseBody
    public Advert addSubscriber(@RequestParam(value = "username", required = true) String username,
                                @RequestParam(value = "ad", required = true) Long adId) {
        User user = userService.findUserByUsername(username);

        if(user == null)
            return null;

        Advert advert = advertRepository.findByAdId(adId);
        if(advert == null)
            return null;

        if(!advert.getSubscribers().contains(user))
            advert.getSubscribers().add(user);
        return advertRepository.save(advert);
    }

}
