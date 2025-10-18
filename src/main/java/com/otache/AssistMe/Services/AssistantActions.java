package com.otache.AssistMe.Services;

public class AssistantActions implements UserActions {
    @Override
    public void viewActions() {
        System.out.println("Assistant can view all actions");
    }

    @Override
    public void viewMessages() {
        System.out.println("Assistant can view all messages");
    }

    @Override
    public void accessAdminPanel() {
        System.out.println("Limited admin panel access");
    }
}