package nsu.fit.upprpo.csbackend.repository;

import nsu.fit.upprpo.csbackend.tables.Advert;
import nsu.fit.upprpo.csbackend.tables.Comment;
import nsu.fit.upprpo.csbackend.tables.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByAuthor(User author);
    List<Comment> findCommentsByCommentAdvert(Advert advert);
}
