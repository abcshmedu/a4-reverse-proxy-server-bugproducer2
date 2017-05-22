package edu.hm.bugproducer.models;

public class Copy {
    private Medium medium;
    private User user;
    private int lfnr;

    public Copy() {
    }

    public Copy(Medium medium, User user, int lfnr) {
        this.medium = medium;
        this.user = user;
        this.lfnr = lfnr;
    }

    public int getLfnr() {
        return lfnr;
    }

    public Medium getMedium() {
        return medium;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
