package nsu.fit.upprpo.csbackend.service;

import nsu.fit.upprpo.csbackend.repository.UsersRepository;
import nsu.fit.upprpo.csbackend.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public User addNewUser(String username, Integer age) {
        User user = new User();
        user.setUsername(username);
        user.setAge(age);

        return usersRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return usersRepository.findUserByUsername(username);
    }
}
