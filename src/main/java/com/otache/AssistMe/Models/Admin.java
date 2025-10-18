package com.otache.AssistMe.Models;

import com.otache.AssistMe.Services.AdminActions;

public class Admin extends User {
    public Admin(int id, String name, String password, String email) {
        super(id, name, password, email);
        this.setActions(new AdminActions());
    }

    @Override
    public void accessAdminPanel() {
        super.accessAdminPanel();
        logAdminAccess();
    }

    public void logAdminAccess() {
        System.out.println("Admin " + this.getName() + " has logged in.");
    }
}
