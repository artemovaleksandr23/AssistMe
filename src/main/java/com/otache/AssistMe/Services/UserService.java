package com.otache.AssistMe.Services;

import com.otache.AssistMe.Models.Role.Role;
import com.otache.AssistMe.Models.Role.RoleType;
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

    public void registerUser(User user, RoleType role) {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("User already exists");
            }

            int userId = userRepository.save(user);
            assignRole(userId, role);

        } catch (SQLException e) {
            throw new RuntimeException("Registration failed", e);
        }
    }


    private void assignRole(int userId, RoleType role) throws SQLException {
        Role dbRole = roleRepository.findByName(role.name())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        userRepository.addUserRole(userId, dbRole.getId());
    }

    public List<String> getRolesForUser(int userId) throws SQLException {
        List<Role> roles = userRepository.getUserRoles(userId);
        return roles.stream().map(Role::getName).toList();
    }
}
