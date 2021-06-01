package ru.excers.projectGarage.models;


import lombok.Data;



@Data
public class Driver {

    private int driverId=-1;
    private String name;
    private int watch;
    private boolean isMale;
    private double earnedThisWeek;



}
