package com.otache.AssistMe.Models.User;

import com.otache.AssistMe.Services.AssistantActions;

public class Assistant extends User {
    public Assistant(int id, String name, String password, String email) {
        super(id, name, password, email);
        this.setActions(new AssistantActions());
    }

    @Override
    public void accessAdminPanel() {
        super.accessAdminPanel();
        logAssistantAccess();
    }

    public void logAssistantAccess() {
        System.out.println("Assistant " + this.getName() + " accessed the admin panel.");
    }
}
