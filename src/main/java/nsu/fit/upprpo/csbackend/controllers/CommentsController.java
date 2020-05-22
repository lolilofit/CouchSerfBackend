package nsu.fit.upprpo.csbackend.controllers;

import nsu.fit.upprpo.csbackend.repository.AdvertRepository;
import nsu.fit.upprpo.csbackend.repository.CommentRepository;
import nsu.fit.upprpo.csbackend.service.UserService;
import nsu.fit.upprpo.csbackend.tables.Advert;
import nsu.fit.upprpo.csbackend.tables.Comment;
import nsu.fit.upprpo.csbackend.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/comments")
@CrossOrigin(origins = "*")
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
