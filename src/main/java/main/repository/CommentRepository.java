package main.repository;

import main.tables.Advert;
import main.tables.Comment;
import main.tables.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    public List<Comment> findByAuthor(User author);
    public List<Comment> findByCommentAdvert(Advert advert);
}
