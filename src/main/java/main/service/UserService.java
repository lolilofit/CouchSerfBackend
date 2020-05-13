package main.service;

import main.tables.User;

public interface UserService {
   // public Boolean checkUserPassword(String username, String password);
    public void addNewUser(String username, Integer age);
    public User findUserByUsername(String username);
}
