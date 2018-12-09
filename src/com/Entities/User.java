package com.Entities;

import java.util.ArrayList;
import java.util.Date;

public class User {
    /**
     *
     */
    private String firstName;
    /**
     *
     */
    private String lastName;
    /**
     *
     */
    private Date birthday;
    /**
     *
     */
    private Location location;
    /**
     *
     */
    private ArrayList<String> imageIDs;

    /**
     * @param firstName
     * @param lastName
     * @param birthday
     * @param location
     */
    public User(String firstName, String lastName, Date birthday, Location location, ArrayList<String> imageIDs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.location = location;
        this.imageIDs = imageIDs;
    }

    /**
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return
     */
    public ArrayList<String> getImageIDs() {
        return imageIDs;
    }

    /**
     * @param imageIDs
     */
    public void setImageIDs(ArrayList<String> imageIDs) {
        this.imageIDs = imageIDs;
    }
}
