package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getUsersList());
        return "admin";
    }

    @GetMapping("/{id}/page")
    public String getUserById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        return "page";
    }

    @GetMapping("/new")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "new_user";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles",roleRepository.findAll());
            return "new_user";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    //@PatchMapping("/{id}/edit")
    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", roleRepository.findAll());
        return "edit_user";
    }
    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleRepository.findAll());
            return "edit_user";
        }
        userService.editUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
