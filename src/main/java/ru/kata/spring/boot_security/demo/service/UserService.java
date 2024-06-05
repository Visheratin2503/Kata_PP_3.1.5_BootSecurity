package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsersList();

    User getUser(Long id);

    void addUser(User user);

    void deleteUser(Long id);

    void editUser(User user);

    User findByUsername(String username);
}
