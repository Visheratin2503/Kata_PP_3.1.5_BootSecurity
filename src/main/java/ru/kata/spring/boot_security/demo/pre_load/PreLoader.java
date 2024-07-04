package ru.kata.spring.boot_security.demo.pre_load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class PreLoader {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public PreLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void loadData() {

        createRolesIfNeeded();

        // Проверка и добавление пользователей
        createUserIfNeeded("Vlad", "Visheratin", 25, "vl.visheratin@gmail.com", "1234", "ROLE_ADMIN");
        createUserIfNeeded("Elena", "Sidorova", 35, "Elena@mail.ru", "1111", "ROLE_USER");
    }

    private void createRolesIfNeeded() {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
    }

    private void createRoleIfNotExists(String roleName) {
        Role role = roleService.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setRole(roleName);
            roleService.save(role);
        }
    }

    private void createUserIfNeeded(String name, String lastName, int age, String username, String password, String roleName) {
        User existingUser = userService.findByUsername(username);
        if (existingUser == null) {
            User user = new User();
            user.setName(name);
            user.setAge(age);
            user.setUsername(username);
            user.setLastName(lastName);
            user.setPassword(password);

            Role role = roleService.findByName(roleName);
            if (role != null) {
                user.setRoles(Set.of(role));
            }

            userService.addUser(user);
        } else {
            System.out.println("Пользователь '" + username + "' уже существует в базе данных.");
        }
    }
}

