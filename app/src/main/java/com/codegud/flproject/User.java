package com.codegud.flproject;

public class User {
    private  String firstName;
    private String lastName;
    private String email;
    private long totalStepsTaken;
    private long dailyStepsTake;
    private String membership;

    public  User(){

    }
    public  User(String email, String firstName, String lastName){
        this.firstName=  firstName;
        this.email = email;
        this.lastName  = lastName;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTotalStepsTaken() {
        return totalStepsTaken;
    }

    public void setTotalStepsTaken(long totalStepsTaken) {
        this.totalStepsTaken = totalStepsTaken;
    }

    public long getDailyStepsTake() {
        return dailyStepsTake;
    }

    public void setDailyStepsTake(long dailyStepsTake) {
        this.dailyStepsTake = dailyStepsTake;
    }
}
