package com.example.a470group;

import androidx.annotation.NonNull;

public class Schedule {
    public enum Day {
        WEEKDAY,
        SUNDAY,
        SATURDAY
    }

    private Day day;
    private final String arrivalTime;

    public String getDay() {
        return this.day.name();
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }

    public Schedule(String dDay, String aArrivalTime){
        day = stringToDay(dDay);
        arrivalTime = aArrivalTime;
    }

    @NonNull
    public String toString() {
        return this.day.name() +" | " +  this.arrivalTime;
    }

    public Day stringToDay(String value) {
        switch (value) {
            case "WEEKDAY":
                return Day.WEEKDAY;
            case "SUNDAY":
                return Day.SUNDAY;
            case "SATURDAY":
                return Day.SATURDAY;
            default:
                return Day.WEEKDAY;
        }
    }
}
