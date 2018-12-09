package com.Entities;

public class Account {
    /**
     *
     */
    private String username;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private User user;

    /**
     * @param username
     * @param password
     * @param user
     */
    public Account(String username, String password, User user) {
        this.username = username;
        this.password = password;
        this.user = user;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
