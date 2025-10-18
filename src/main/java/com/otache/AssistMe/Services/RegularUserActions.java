package com.otache.AssistMe.Services;

public class RegularUserActions implements UserActions {
    @Override
    public void viewActions() {
        System.out.println("User can view their own actions only");
    }

    @Override
    public void viewMessages() {
        System.out.println("User can view their own messages only");
    }

    @Override
    public void accessAdminPanel() {
        throw new UnsupportedOperationException("No access");
    }
}
