package com.otache.AssistMe.Controllers;

import com.otache.AssistMe.DTO.AdminDTO;
import com.otache.AssistMe.DTO.AssistantDTO;
import com.otache.AssistMe.DTO.RegularUserDTO;
import com.otache.AssistMe.Models.User.Admin;
import com.otache.AssistMe.Models.User.Assistant;
import com.otache.AssistMe.Models.User.RegularUser;
import com.otache.AssistMe.Models.User.User;
import com.otache.AssistMe.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegularUserDTO dto) {
        RegularUser user = new RegularUser(0, dto.getUsername(), dto.getPassword(), dto.getEmail());
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminDTO dto) {
        Admin admin = new Admin(0, dto.getUsername(), dto.getPassword(), dto.getEmail());
        try {
            userService.registerAdmin(admin);
            return ResponseEntity.ok("Admin registered");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @PostMapping("/register-assistant")
    public ResponseEntity<String> registerAssistant(@RequestBody AssistantDTO dto) {
        Assistant assistant = new Assistant(0, dto.getUsername(), dto.getPassword(), dto.getEmail());
        try {
            userService.registerAssistant(assistant);
            return ResponseEntity.ok("Assistant registered");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}

