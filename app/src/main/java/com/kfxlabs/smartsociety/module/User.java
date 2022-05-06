package com.kfxlabs.smartsociety.module;

public class User {


    private  String  email;
    private String name;




    public User(String email, String name, String school) {

        this.email = email;
        this.name = name;

    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}



