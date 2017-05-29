package edu.hm.bugproducer.models;

/**
 * User Class.
 * @author Mark Tripolt
 * @author Johannes Arzt
 * @author Tom Maier
 * @author Patrick Kuntz
 */
public class User {
    /** name of the user */
    private String userName;

    /**
     * User Constructor for no name user.
     */
    public User() {
        userName = "No Name";
    }

    /**
     * User Constructor.
     * @param userName name of user
     */
    public User(String userName) {
        this.userName = userName;
    }

    /**
     * getUserName method.
     * gets the name of an user
     * @return userName
     */
    public String getUserName() {
        return userName;
    }
}

