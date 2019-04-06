package com.codegud.flproject;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Transactions {
    private int numberOfSteps;
    private String NameOfPersonWhoAddedSteps;
    private String teamName;
    private String date;
    private Timestamp timestamp;
    private String email;

    public Transactions() {
    }

    public Transactions(int numberOfSteps, String email, String date, String nameOfPersonWhoAddedSteps) {
        this.numberOfSteps = numberOfSteps;
        NameOfPersonWhoAddedSteps = nameOfPersonWhoAddedSteps;
        this.date = date;
        this.timestamp = new Timestamp(new Date());
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public String getNameOfPersonWhoAddedSteps() {
        return NameOfPersonWhoAddedSteps;
    }

    public void setNameOfPersonWhoAddedSteps(String nameOfPersonWhoAddedSteps) {
        NameOfPersonWhoAddedSteps = nameOfPersonWhoAddedSteps;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
