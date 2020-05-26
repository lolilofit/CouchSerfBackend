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
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class MainPageController {

    private static final Logger logger = Logger.getLogger(MainPageController.class);

    private final AdvertService advertService;

    private final UserService userService;

    private final AdvertRepository advertRepository;

    private final CommentRepository commentRepository;

    private final PlaceService placeService;

    private final PlaceRepository placeRepository;

    @Autowired
    public MainPageController(AdvertService advertService,
        UserService userService, AdvertRepository advertRepository,
        CommentRepository commentRepository, PlaceService placeService,
        PlaceRepository placeRepository) {
        this.advertService = advertService;
        this.userService = userService;
        this.advertRepository = advertRepository;
        this.commentRepository = commentRepository;
        this.placeService = placeService;
        this.placeRepository = placeRepository;
    }

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

    @GetMapping(value = "/advert/count")
    @ResponseBody
    public Long getAdvertsCount() {
        logger.info("Get adverts count");
        return advertRepository.count();
    }


    @GetMapping(value = "/advert", produces ="application/json")
    @ResponseBody
    public List<Advert> getAllAdverts(@RequestParam(value = "limit") Integer limit,
                                      @RequestParam(value = "pos") Integer pos,
                                      @RequestParam(value = "type", required = false) AdvertType advertType) {
        List<Advert> allAdverts =  advertService.getAllAdverts();
        if(advertType != null)
            allAdverts = allAdverts.stream().filter(advert -> advert.getAdvertType().equals(advertType)).collect(Collectors.toList());

        logger.info("Get all adverts with pos=" + pos.toString() + " limit=" + limit.toString());

        allAdverts = sortByDate(allAdverts);

        List<Advert> res = new ArrayList<>();
        for(int i = pos; i < (pos + limit) && i < allAdverts.size(); i++) {
            res.add(allAdverts.get(i));
        }
        return res;
    }

    @PostMapping(value = "/adchange/add", produces ="application/json")
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

    @GetMapping(value = "/advert/{adId}")
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

    @PutMapping(value = "/adchange/{adId}/addsubscriber")
    @ResponseBody
    public Advert addSubscriber(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable Long adId) {
        String mes = "Add subscriber with username = ";

        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);

        if(user == null) {
            logger.error(mes + username + " user entity not found, adId=" + adId);
            return null;
        }

        Advert advert = advertRepository.findByAdId(adId);
        if(advert == null) {
            logger.error(mes + username + " advert not found, adId=" + adId);
            return null;
        }

        logger.info(mes + username + " , adId=" + adId);

        if(!advert.getSubscribers().contains(user))
            advert.getSubscribers().add(user);
        return advertRepository.save(advert);
    }

}
