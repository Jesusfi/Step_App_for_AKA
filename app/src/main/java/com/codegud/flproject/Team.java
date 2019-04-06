package com.codegud.flproject;

import java.util.ArrayList;

public class Team {
    private String TeamName;
    private String summary;
    private long totalNumberOfSteps;
    private long totalNumberOfStepsWeekly;
    private ArrayList<User> memberList;


    public  Team (){

    }

    public Team (String teamName){
        this.TeamName  = teamName;
        this.totalNumberOfSteps = 0;
        this.totalNumberOfStepsWeekly = 0;
        this.summary = "";
        this.memberList = new ArrayList<>();
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getTotalNumberOfSteps() {
        return totalNumberOfSteps;
    }

    public void setTotalNumberOfSteps(long totalNumberOfSteps) {
        this.totalNumberOfSteps = totalNumberOfSteps;
    }

    public long getTotalNumberOfStepsWeekly() {
        return totalNumberOfStepsWeekly;
    }

    public void setTotalNumberOfStepsWeekly(long totalNumberOfStepsWeekly) {
        this.totalNumberOfStepsWeekly = totalNumberOfStepsWeekly;
    }

    public ArrayList<User> getMemberList() {
        return memberList;
    }
    public void addNewMember(User user){
        this.memberList.add(user);
    }

    public void setMemberList(ArrayList<User> memberList) {
        this.memberList = memberList;
    }
}
