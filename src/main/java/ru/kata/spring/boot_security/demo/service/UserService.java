package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    void save(User user);

    void deleteUser(Long id);

    void update(User user);

    User getUserByName(String name);

    Role getRoleById(Long id);

    List<Role> findAllRoles();

    void setRolesToUser(User user, Long[] roles);
}
