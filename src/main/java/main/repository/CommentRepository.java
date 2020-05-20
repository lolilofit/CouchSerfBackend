package main.repository;

import main.tables.Advert;
import main.tables.Comment;
import main.tables.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByAuthor(User author);
    List<Comment> findCommentsByCommentAdvert(Advert advert);
}
