package com.otache.AssistMe.Services;

public class AdminActions implements UserActions {
    @Override
    public void viewActions() {
        System.out.println("Admin can view all actions");
    }

    @Override
    public void viewMessages() {
        System.out.println("Admin can view all messages");
    }

    @Override
    public void accessAdminPanel() {
        System.out.println("Admin panel access granted");
    }
}
