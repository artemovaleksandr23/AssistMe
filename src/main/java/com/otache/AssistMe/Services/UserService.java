package com.otache.AssistMe.Services;

import com.otache.AssistMe.Models.Role.Role;
import com.otache.AssistMe.Models.User.Admin;
import com.otache.AssistMe.Models.User.User;
import com.otache.AssistMe.Repositories.RoleRepository;
import com.otache.AssistMe.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users", e);
        }
    }

    public Optional<User> getUserById(int id) {
        try {
            return userRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user with id: " + id, e);
        }
    }

    public boolean registerUser(User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return false;
            } else {
                userRepository.save(user);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error registering user", e);
        }
    }

    public boolean assignRole(int userId, String roleName) {
        try {
            Optional<Role> roleOpt = roleRepository.findByName(roleName);
            if (roleOpt.isEmpty()) {
                return false;
            }
            int roleId = roleOpt.get().getId();
            return userRepository.addUserRole(userId, roleId);
        } catch (SQLException e) {
            throw new RuntimeException("Error assigning role", e);
        }
    }

    public void registerRegularUser(User user) {
        try {
            boolean success = userRepository.save(user);
            if (!success) {
                throw new RuntimeException("User already exists");
            }

            int userId = userRepository.getLastInsertId();

            assignRole(userId, "USER");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerAdmin(Admin admin) {
        try {
            boolean success = userRepository.save(admin);
            if (!success) {
                throw new RuntimeException("User already exists");
            }

            int userId = userRepository.getLastInsertId();

            assignRole(userId, "ADMIN");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerAssistant(User assistant) {
        try {
            boolean success = userRepository.save(assistant);
            if (!success) {
                throw new RuntimeException("User already exists");
            }

            int userId = userRepository.getLastInsertId();

            assignRole(userId, "ASSISTANT");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
