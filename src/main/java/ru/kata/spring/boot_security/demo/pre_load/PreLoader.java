package ru.kata.spring.boot_security.demo.pre_load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class PreLoader {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public PreLoader(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void loadData() {

        createRolesIfNeeded();

        // Проверка и добавление пользователей
        createUserIfNeeded("Vlad", "Visheratin", "Vlad", "1234", "ROLE_ADMIN");
        createUserIfNeeded("Elena", "Sidorova", "Elena", "1111", "ROLE_USER");
    }

    private void createRolesIfNeeded() {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
    }

    private void createRoleIfNotExists(String roleName) {
        Role role = roleRepository.findByRole(roleName);
        if (role == null) {
            role = new Role();
            role.setRole(roleName);
            roleRepository.save(role);
        }
    }

    private void createUserIfNeeded(String name, String lastName, String username, String password, String roleName) {
        User existingUser = userService.findByUsername(username);
        if (existingUser == null) {
            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setLastName(lastName);
            user.setPassword(password);

            Role role = roleRepository.findByRole(roleName);
            if (role != null) {
                user.setRoles(Set.of(role));
            }

            userService.addUser(user);
        } else {
            System.out.println("Пользователь '" + username + "' уже существует в базе данных.");
        }
    }
}
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Role userRole = new Role(1L, "ROLE_USER");
//        Role adminRole = new Role(2L, "ROLE_ADMIN");
//        roleRepository.save(userRole);
//        roleRepository.save(adminRole);
//
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(adminRole);
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//
//        User existingUser = userService.findByUsername("Vlad");
//        User user1 = new User();
//        if (existingUser == null) {
//            user1.setName("Vlad");
//            user1.setLastName("Visheratin");
//            user1.setUsername("Vlad");
//            user1.setPassword("1234");
//            user1.setRoles(adminRoles);
//        } else {
//            System.out.println("Пользователь 'Vlad' уже существует в базе данных.");
//        }
//
//        User user2 = new User();
//        user2.setName("Elena");
//        user2.setLastName("Sidorova");
//        user2.setUsername("elena");
//        user2.setPassword("1111");
//        user2.setRoles(userRoles);
//
//        userService.addUser(user1);
//        userService.addUser(user2);
//
//    }

