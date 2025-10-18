package com.otache.AssistMe.Models;

import com.otache.AssistMe.Services.RegularUserActions;

public class RegularUser extends User {
    public RegularUser(int id, String name, String password, String email) {
        super(id, name, password, email);
        this.setActions(new RegularUserActions());
    }
}
