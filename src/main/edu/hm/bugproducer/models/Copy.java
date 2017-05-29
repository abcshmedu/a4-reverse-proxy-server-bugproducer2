package edu.hm.bugproducer.models;

/**
 * Copy Class.
 * @author Mark Tripolt
 * @author Johannes Arzt
 * @author Tom Maier
 * @author Patrick Kuntz
 */
public class Copy {
    /** the superordinate of disc and book */
    private Medium medium;
    /** name of the person who borrowed a copy */
    private User user;
    /** the running number for connecting a exact copy to the user */
    private int lfnr;

    /**
     * Copy Constructor.
     */
    public Copy() {
    }

    /**
     * Copy Constructor with parameters.
     * @param medium book or disc
     * @param user person who borrowed the medium
     * @param lfnr running number for the specific copy
     */
    public Copy(Medium medium, User user, int lfnr) {
        this.medium = medium;
        this.user = user;
        this.lfnr = lfnr;
    }

    /**
     * getLfnr method.
     * gives you the lfnr of a borrowed medium
     * @return lfnr
     */
    public int getLfnr() {
        return lfnr;
    }

    /**
     * getMedium method.
     * gives you the borrowed medium
     * @return medium
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     * getUser method.
     * gives you the name of the user
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * setUser method.
     * sets the name of the user
     * @param user name
     */
    public void setUser(User user) {
        this.user = user;
    }
}
