package main.controllers;

import main.repository.AdvertRepository;
import main.repository.CommentRepository;
import main.service.UserService;
import main.tables.Advert;
import main.tables.Comment;
import main.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/comments")
public class CommentsController {
    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;


    //remove return comment
    @RequestMapping(value = "/{adId}/add", method = RequestMethod.POST, produces ="application/json")
    @ResponseBody
    public Comment leaveComment(@RequestBody String message, @PathVariable Long adId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();

        User author = userService.findUserByUsername(username);
        Advert advert = advertRepository.findByAdId(adId);

        Comment newComment = new Comment();
        newComment.setMessage(message);
        newComment.setAuthor(author);
        newComment.setCommentAdvert(advert);

        return commentRepository.save(newComment);
    }
}
