package nsu.fit.upprpo.csbackend.controllers;

import nsu.fit.upprpo.csbackend.repository.AdvertRepository;
import nsu.fit.upprpo.csbackend.repository.CommentRepository;
import nsu.fit.upprpo.csbackend.service.UserService;
import nsu.fit.upprpo.csbackend.shortentity.AdvertContainer;
import nsu.fit.upprpo.csbackend.tables.Advert;
import nsu.fit.upprpo.csbackend.tables.Comment;
import nsu.fit.upprpo.csbackend.tables.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/comments")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CommentsController {

    private static final Logger logger = Logger.getLogger(CommentsController.class);

    private final AdvertRepository advertRepository;

    private final CommentRepository commentRepository;

    private final UserService userService;

    @Autowired
    public CommentsController(AdvertRepository advertRepository,
        CommentRepository commentRepository, UserService userService) {
        this.advertRepository = advertRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @PostMapping(value = "/{adId}/add", produces ="application/json")
    @ResponseBody
    public AdvertContainer leaveComment(@RequestBody String message, @PathVariable Long adId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        logger.info("add comment " + message + "; author " + username);

        User author = userService.findUserByUsername(username);
        Advert advert = advertRepository.findByAdId(adId);

        Comment newComment = new Comment();
        newComment.setMessage(message);
        newComment.setAuthor(author);
        newComment.setCommentAdvert(advert);

        commentRepository.save(newComment);

        AdvertContainer advertContainer =  new AdvertContainer();
        advertContainer.setAdvert(advert);

        List<Comment> comments = commentRepository.findCommentsByCommentAdvert(advert);
        advertContainer.setComments(comments);

        return advertContainer;
    }
}
