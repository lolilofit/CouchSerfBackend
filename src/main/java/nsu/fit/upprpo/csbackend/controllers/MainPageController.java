package nsu.fit.upprpo.csbackend.controllers;

import nsu.fit.upprpo.csbackend.dto.AdvertDTO;
import nsu.fit.upprpo.csbackend.dto.PlaceDTO;
import nsu.fit.upprpo.csbackend.repository.AdvertRepository;
import nsu.fit.upprpo.csbackend.repository.CommentRepository;
import nsu.fit.upprpo.csbackend.repository.PlaceRepository;
import nsu.fit.upprpo.csbackend.service.AdvertService;
import nsu.fit.upprpo.csbackend.service.PlaceService;
import nsu.fit.upprpo.csbackend.service.UserService;
import nsu.fit.upprpo.csbackend.shortentity.AdvertContainer;

import nsu.fit.upprpo.csbackend.tables.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/")
public class MainPageController {

    private static final Logger logger = Logger.getLogger(MainPageController.class);

    @Autowired
    private AdvertService advertService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private PlaceRepository placeRepository;

    private Advert addNewAd(Advert advert) {
        User owner = userService.findUserByUsername(advert.getOwner().getUsername());
        if(owner == null) {
            return null;
        }

        return advertService.addNewAdvert(advert, owner);
    }

    private List<Advert> sortByDate(List<Advert> adverts) {
        return adverts.stream().sorted(Comparator.comparing(Advert::getPublicationDate)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/advert/count", method = RequestMethod.GET)
    @ResponseBody
    public Long getAdvertsCount() {
        logger.info("Get adverts count");
        return advertRepository.count();
    }


    @RequestMapping(value = "/advert", method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public List<Advert> getAllAdverts(@RequestParam(value = "limit", required = false) Integer limit,
                                      @RequestParam(value = "pos", required = false) Integer pos,
                                      @RequestParam(value = "type", required = false) AdvertType advertType) {
        List<Advert> allAdverts =  advertService.getAllAdverts();
        if(limit == null) {
            logger.info("Get all adverts without filters");
            return sortByDate(allAdverts);
        }
        else {
            if(pos == null)
                pos = 0;
            if(allAdverts.size() < pos)
                pos = 0;
            logger.info("Get all adverts with pos=" + pos.toString() + " limit=" + limit.toString());

            allAdverts = sortByDate(allAdverts);

            List<Advert> res = new ArrayList<>();
            for(int i = pos; i < (pos + limit) && i < allAdverts.size(); i++) {
                res.add(allAdverts.get(i));
            }
            return res;
        }
    }

    @RequestMapping(value = "/adchange/add", method = RequestMethod.POST, produces ="application/json")
    @ResponseBody
    public Advert newAdvert(@RequestBody AdvertDTO advertDTO, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User owner = userService.findUserByUsername(username);
        if(owner == null) {
            logger.error("Create advert with username = " + username + " user entity not found");
            return null;
        }
        logger.info("Create advert with username = " + username);

        Place place;
        PlaceDTO placeDTO = advertDTO.getPlace();
        List<Place> places =  placeService.getPlaceWithFilters(placeDTO.getCountry(), placeDTO.getCity(), placeDTO.getHome());
        if(!places.isEmpty())
                place = places.get(0);
        else
                place = placeRepository.save(new Place(placeDTO));
        advertDTO.setPlace(null);

        Advert advert = new Advert(advertDTO);
        advert.setOwner(owner);
        advert.setPlace(place);

        return addNewAd(advert);
    }

    @RequestMapping(value = "/advert/{adId}", method = RequestMethod.GET)
    @ResponseBody
    public AdvertContainer getAdvertInfo(@PathVariable Long adId) {
        logger.info("Get advert with id=" + adId.toString());

        Advert advert = advertRepository.findByAdId(adId);
        List<Comment> comments = commentRepository.findCommentsByCommentAdvert(advert);

        AdvertContainer advertContainer = new AdvertContainer();
        advertContainer.setAdvert(advert);
        advertContainer.setComments(comments);

        return advertContainer;
    }

    @RequestMapping(value = "/adchange/{adId}/addsubscriber", method = RequestMethod.PUT)
    @ResponseBody
    public Advert addSubscriber(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable Long adId) {
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);

        if(user == null) {
            logger.error("Add subscriber with username = " + username + " user entity not found, adId=" + adId);
            return null;
        }

        Advert advert = advertRepository.findByAdId(adId);
        if(advert == null) {
            logger.error("Add subscriber with username = " + username + " advert not found, adId=" + adId);
            return null;
        }

        logger.info("Add subscriber with username = " + username + " , adId=" + adId);

        if(!advert.getSubscribers().contains(user))
            advert.getSubscribers().add(user);
        return advertRepository.save(advert);
    }

}
