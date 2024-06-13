package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public String showUserPage(@PathVariable("id") Long id, Model model) {
//        User user = userService.findById(id);
//        model.addAttribute("user", user);
//        return "user";
//    }
    @GetMapping
    public String showUserPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "/user";
    }

//    @GetMapping("/edit-info")
//    public String editUserInfo(Model model, Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        model.addAttribute("user", user);
//        return "edit_user_info";
//    }

    @GetMapping("/{id}/edit-info")
    public String editUserInfo(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit_user_info";
    }

//    @PostMapping("/{id}/edit-info")
//    public String updateUserInfo(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
//                                 Principal principal, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("user", user);
//            return "edit_user_info";
//        }
//        User existingUser = userService.findByUsername(principal.getName());
//        user.setId(existingUser.getId());
//        user.setPassword(existingUser.getPassword());
//        user.setRoles(existingUser.getRoles());
//        userService.editUser(user);
//        return "redirect:/user";
//    }

//    @PostMapping("/{id}/edit-info")
//    public String updateUserInfo(@PathVariable("id") Long id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("user", user);
//            return "edit_user_info";
//        }
//
//        userService.editUser(user);
//        return "redirect:/user";
//    }

//    @PostMapping("/{id}/edit-info")
//    public String updateUserInfo(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Principal principal, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("user", user);
//            return "edit_user_info";
//        }
//
//        User existingUser = userService.findByUsername(principal.getName());
//        user.setId(existingUser.getId());
//        userService.editUser(user);
//
//        return "redirect:/user";
//    }


//    @PostMapping("/{id}/edit-info")
//    public String updateUserInfo(@PathVariable("id") Long id, @ModelAttribute("user") User user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "edit_user_info";
//        }
//        user.setId(id);
//        userService.editUser(user);
//        return "redirect:/user/" + id;
//    }


    @PatchMapping("/{id}/edit-info")
    public String updateUserInfo(@PathVariable("id") Long id,
                                 @ModelAttribute("user") @Valid User user,
                                 BindingResult bindingResult,
                                 Principal principal,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "edit_user_info";
        }
//        User existingUser = userService.findByUsername(principal.getName());

        //вариант 1
//        User existingUser = userService.findById(id);
//        user.setId(existingUser.getId());
//        user.setPassword(existingUser.getPassword());
//        userService.editUser(user);
//        return "redirect:/user";

        //вариант 2
//        user.setId(id);
//        userService.editUser(user);
//        return "redirect:/user";

        //вариант 3
        userService.editUser(user);
        return "redirect:/user";
    }
}
