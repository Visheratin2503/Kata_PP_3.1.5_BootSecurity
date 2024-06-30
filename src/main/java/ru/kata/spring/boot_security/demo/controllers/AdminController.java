package ru.kata.spring.boot_security.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String getAllUsers(Model model, Principal principal) {
        List<User> users = userService.getUsersList();
        String email = principal.getName();
        User currentUser = userRepository.findByEmail(email);
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserRoles", currentUser.getRolesToString());
        return "admin";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user, @RequestParam("roles_select") Long[] roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleService.findById(roleId);
            if (role != null) {
                roles.add(role);
            } else {
                log.error("Role not found with id: {}", roleId);
            }
        }
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam("roles_select") Long[] roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleService.findById(roleId);
            if (role != null) {
                roles.add(role);
            } else {
                log.error("Role not found with id: {}", roleId);
            }
        }
        user.setRoles(roles);
        userService.editUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
