package main.controllers;

import main.shortentity.ShortComment;
import main.repository.AdvertRepository;
import main.repository.CommentRepository;
import main.service.UserService;
import main.tables.Advert;
import main.tables.Comment;
import main.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces ="application/json")
    @ResponseBody
    public Comment leaveComment(@RequestBody ShortComment comment) {
        User author = userService.findUserByUsername(comment.getAuthor());
        Advert advert = advertRepository.findByAdId(comment.getAdId());

        Comment newComment = new Comment();
        newComment.setMessage(comment.getMessage());
        newComment.setAuthor(author);
        newComment.setCommentAdvert(advert);

        return commentRepository.save(newComment);
    }
}
