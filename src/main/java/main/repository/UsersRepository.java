package main.repository;

import main.tables.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    User getUserByUserid(Long userid);
    List<User> findUsersByUsername(String username);
}
