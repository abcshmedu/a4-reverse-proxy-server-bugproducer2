package edu.hm.bugproducer.models;

public class User {

    private String userName;

    public User() {
        userName = "No Name";
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
