package com.otache.AssistMe.Services;

import com.otache.AssistMe.DTO.LoginResponseDTO;
import com.otache.AssistMe.Models.Role.Role;
import com.otache.AssistMe.Models.User.User;
import com.otache.AssistMe.Repositories.RoleRepository;
import com.otache.AssistMe.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public LoginResponseDTO login(String username, String password) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.getPassword().equals(password)) {
                throw new RuntimeException("Invalid password");
            }

            // Получаем роли пользователя
            List<Role> roles = userRepository.getUserRoles(user.getId());
            List<String> roleNames = roles.stream()
                    .map(Role::getName)
                    .map(String::toLowerCase) // чтобы JS мог делать includes("admin")
                    .toList();

            // Передаем ID пользователя в ответ
            return new LoginResponseDTO(user.getId(), user.getName(), roleNames);

        } catch (SQLException e) {
            throw new RuntimeException("Login failed", e);
        }
    }

}
