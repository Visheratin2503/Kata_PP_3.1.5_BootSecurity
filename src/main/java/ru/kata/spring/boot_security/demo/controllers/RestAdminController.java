package ru.kata.spring.boot_security.demo.controllers;

import lombok.extern.slf4j.Slf4j;
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

    private final UserService userService;

    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsersList();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/admin/user")
    public ResponseEntity<User> showAdminByUsername(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.editUser(user);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
//        return ResponseEntity.ok().build();
//        return ResponseEntity.noContent().build();
    }
}

