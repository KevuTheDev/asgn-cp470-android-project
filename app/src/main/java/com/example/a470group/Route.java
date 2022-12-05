package com.example.a470group;


import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Route {
    private final int stopID;
    private ArrayList<Schedule> schedules;

    public Route(int iId, ArrayList<Schedule> sSchedules) {
        this.stopID = iId;
        this.schedules = sSchedules;
    }

    public ArrayList<Schedule> getSchedules() {
        return this.schedules;
    }

    public int getStopID() {
        return this.stopID;
    }

    @NonNull
    public String toString() {
        StringBuilder output = new StringBuilder("Your Stop Route ID: " + stopID + "\n");

        for (Schedule schedule : schedules) {
            output.append(schedule.toString()).append("\n");
        }

        return output.toString();
    }
}