package com.Dennis.BookApp.email;

public enum EmailTemplate {

    ACTIVATE_ACCOUNT("activate_account");
    private final String name;

    public String getName() {
        return name;
    }


    EmailTemplate(String name) {
        this.name = name;
    }
}
