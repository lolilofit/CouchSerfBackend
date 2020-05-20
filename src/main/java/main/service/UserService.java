package main.service;

import main.tables.User;

public interface UserService {
    void addNewUser(String username, Integer age);
    User findUserByUsername(String username);
}
