package main.service;

import main.tables.User;

public interface UserService {
    public void addNewUser(String username, Integer age);
    public User findUserByUsername(String username);
}
