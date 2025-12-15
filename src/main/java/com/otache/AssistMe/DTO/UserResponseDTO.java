package com.otache.AssistMe.DTO;

import java.util.List;

public class UserResponseDTO {
    private int id;
    private String username;
    private String email;
    private List<String> roles;

    public UserResponseDTO(int id, String username, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
    
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
