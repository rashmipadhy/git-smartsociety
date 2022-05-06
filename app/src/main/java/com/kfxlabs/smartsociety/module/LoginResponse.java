package com.kfxlabs.smartsociety.module;

public class LoginResponse {
    private static User user;
    public LoginResponse(User user)
    {
        this.user = user;
    }

    public static User getUser() {
        return user;
    }
}
