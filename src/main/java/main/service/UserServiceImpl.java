package main.service;

import main.repository.UsersRepository;
import main.tables.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;


    /*
    @Override
    public Boolean checkUserPassword(String username, String password) {
        List<User> users = usersRepository.findUsersByUsername(username);

        if(users.size() < 1)
            return false;
        for(User user : users) {
            if(user.getPassword().equals(password))
                return true;
        }
        return false;
    }
*/
    @Override
    public void addNewUser(String username, Integer age) {
        User user = new User();
        user.setUsername(username);
        user.setAge(age);

        usersRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        List<User> users = usersRepository.findUsersByUsername(username);
        if(users.size() == 0)
            return null;
        //username is unique
        return users.get(0);
    }
}
