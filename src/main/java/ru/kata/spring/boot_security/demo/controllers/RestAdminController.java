package ru.kata.spring.boot_security.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.entity.User;


import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
public class RestAdminController {

    private static final Logger logger = LoggerFactory.getLogger(RestAdminController.class);

    private final UserService userService;

    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }
    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        logger.info("Trying to fetch user with id: {}", id);
        User user = userService.getUserById(id);
        if (user == null) {
            logger.warn("User with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        logger.info("Successfully fetched user: {}", user);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/admin/user")
    public ResponseEntity<User> showAdminByUsername(Principal principal) {
        User user = userService.getUserByName(principal.getName());
        return ResponseEntity.ok().body(user);
    }
    @GetMapping("/user/user")
    public ResponseEntity<User> showUserByUsername(Principal principal) {
        User user = userService.getUserByName(principal.getName());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

