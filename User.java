package com.company;

public class User {

    private String username;
    private String password;
    private Role role;
    private String vardas;
    private String pavarde;
    private int amzius;

    private boolean isDeleted;

    public User(String username, String password, Role role, String vardas, String pavarde, int amzius) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.vardas = vardas;
        this.pavarde = pavarde;
        this.amzius = amzius;
        this.isDeleted = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", vardas='" + vardas + '\'' +
                ", pavarde='" + pavarde + '\'' +
                ", amzius=" + amzius +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getVardas() {
        return vardas;
    }

    public String getPavarde() {
        return pavarde;
    }

    public int getAmzius() {
        return amzius;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    }
