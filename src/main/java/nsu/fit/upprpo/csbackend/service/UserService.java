package nsu.fit.upprpo.csbackend.service;

import nsu.fit.upprpo.csbackend.tables.User;

public interface UserService {
    User addNewUser(String username, Integer age);
    User findUserByUsername(String username);
}
