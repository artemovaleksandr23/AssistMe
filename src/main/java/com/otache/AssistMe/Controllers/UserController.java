package com.otache.AssistMe.Controllers;

import com.otache.AssistMe.DTO.AdminDTO;
import com.otache.AssistMe.DTO.AssistantDTO;
import com.otache.AssistMe.DTO.RegularUserDTO;
import com.otache.AssistMe.DTO.UserResponseDTO;
import com.otache.AssistMe.Models.Role.RoleType;
import com.otache.AssistMe.Models.User.Admin;
import com.otache.AssistMe.Models.User.Assistant;
import com.otache.AssistMe.Models.User.RegularUser;
import com.otache.AssistMe.Models.User.User;
import com.otache.AssistMe.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() throws SQLException {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> response = new ArrayList<>();
        for (User user : users) {
            List<String> roles = userService.getRolesForUser(user.getId()); // новый метод
            response.add(new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), roles));
        }
        return response;
    }


    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegularUserDTO dto) {
        try {
            userService.registerUser(
                    new RegularUser(0, dto.getUsername(), dto.getPassword(), dto.getEmail()),
                    RoleType.USER
            );
            return ResponseEntity.status(HttpStatus.CREATED).build(); // пустой успешный ответ
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // пустой ответ при ошибке
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<Void> registerAdmin(@RequestBody AdminDTO dto) {
        try {
            userService.registerUser(
                    new Admin(0, dto.getUsername(), dto.getPassword(), dto.getEmail()),
                    RoleType.ADMIN
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register-assistant")
    public ResponseEntity<Void> registerAssistant(@RequestBody AssistantDTO dto) {
        try {
            userService.registerUser(
                    new Assistant(0, dto.getUsername(), dto.getPassword(), dto.getEmail()),
                    RoleType.ASSISTANT
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

