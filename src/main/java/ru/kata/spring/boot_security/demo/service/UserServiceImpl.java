package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //изначальный вариант
//    @Override
//    public User getUser(Long id) {
//        return userRepository.getById(id);
//    }



    //1 вариант решения ошибки
//    @Override
//    public User getUser(Long id) {
//        User user = userRepository.findById(id).orElse(null);
//        System.out.println("Retrieved user: {}" + user);
//        return user;
//    }

    //2 вариант решения ошибки
//    @Override
//    public User getUser(Long id) {
//        Optional<User> userOpt = userRepository.findById(id);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            System.out.println("User found: {}"+ user);
//            return user;
//        } else {
//            System.out.println("User with id {} not found"+ id);
//            return null;
//        }
//    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }



    //save user
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findRoleByRole("USER");
            user.setRoles(Set.of(defaultRole));
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    //update user
    @Override
    public void update(User user) {
//        User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        if (!user.getPassword().equals(existingUser.getPassword())) {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        } else {
//            user.setPassword(existingUser.getPassword());
//        }
//        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
//            existingUser.setRoles(user.getRoles());
//        }
        userRepository.saveAndFlush(user);
    }

//    @Override
//    public void setRolesToUser(User user, Long[] roleIds) {
//        Set<Role> roles = new HashSet<>();
//        if (roleIds != null && roleIds.length > 0) {
//            for (Long roleId : roleIds) {
//                Role role = roleRepository.findById(roleId).orElse(null);
//                if (role != null) {
//                    roles.add(role);
//                } else {
//                    System.out.println("Role not found with id: " + roleId);
//                }
//            }
//        } else {
//            Role defaultRole = roleRepository.findByRole("ROLE_USER");
//            if (defaultRole != null) {
//                roles.add(defaultRole);
//            }
//        }
//        user.setRoles(roles);
//    }

    @Override
    public void setRolesToUser(User user, Long[] roles) {
        Set<Role> roleList = new HashSet<>();
        for (Long id : roles) {
            roleList.add(roleRepository.findById(id).orElse(null));
        }
        user.setRoles(roleList);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getById(id); //getOne(id);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
