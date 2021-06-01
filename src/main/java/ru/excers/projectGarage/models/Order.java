package ru.excers.projectGarage.models;


import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Data
@Log4j2
public class Order {
    private String orderExplaining;
    private double distanceKm;
    private CarType carType;
    private int numOfPass=0;
    private int numOfKg=0;
    private int minutesBeforeOrder=0;
   private Calendar orderStartTime = new GregorianCalendar();

   private final int onePassWeightKg = 75;

    public Order(){

    }
    public Order(double distanceKm, CarType carType,
                 int numOfOrderElements, double hoursBeforeOrder) {

        if (numOfOrderElements < 0 || minutesBeforeOrder < 0|| distanceKm < 1) {
            throw new IllegalArgumentException("not valid!");
        }
        {
            this.distanceKm = distanceKm;
            this.numOfPass = numOfPass;

            this.minutesBeforeOrder = (int)(hoursBeforeOrder*60);
            this.orderStartTime.add(Calendar.HOUR,(int)(hoursBeforeOrder*60));



        }
        this.carType = carType;

        if (carType == carType.T){
            this.numOfKg = numOfOrderElements;
        }
        if (carType == carType.P){
            this.numOfPass = numOfOrderElements;
            this.numOfKg = numOfOrderElements*75;
        }


    }

    public Order(String orderExplaining, double distanceKm, CarType carType,
                 int numOfOrderElements, double hoursBeforeOrder) {


        this.orderExplaining = orderExplaining;

        if (numOfOrderElements < 0 || minutesBeforeOrder < 0|| distanceKm < 1) {
            throw new IllegalArgumentException("not valid!");
        }
        {
            this.distanceKm = distanceKm;



            this.minutesBeforeOrder = (int)(hoursBeforeOrder*60);
            this.orderStartTime.add(Calendar.MINUTE,(int)(hoursBeforeOrder*60));

        }
        this.carType = carType;

        if (carType == carType.T){
            this.numOfKg = numOfOrderElements;
        }
        if (carType == carType.P){
            this.numOfPass = numOfOrderElements;
            this.numOfKg = numOfOrderElements* onePassWeightKg;
        }

    }

    public Order(String orderExplaining, double distanceKm, CarType carType,
                 int numOfOrderElements, Calendar orderStartTime) {


        this.orderExplaining = orderExplaining;
        this.orderStartTime =orderStartTime;



            this.distanceKm = distanceKm;



        this.carType = carType;

        if (carType == carType.T){
            this.numOfKg = numOfOrderElements;
        }
        if (carType == carType.P){
            this.numOfPass = numOfOrderElements;
            this.numOfKg = numOfOrderElements*75;
        }

    }

    public void createCalendarAndIncrementStartTime(int minutes){
        this.orderStartTime = new GregorianCalendar();
        orderStartTime.add(Calendar.MINUTE,minutes);
    }



}
