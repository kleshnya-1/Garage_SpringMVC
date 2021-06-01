package ru.excers.projectGarage.models.cars;


import lombok.Data;
import ru.excers.projectGarage.models.subModels.FuelType;

@Data
public class Car {
    private String id;
    private String brand;
    private  String model;
    private boolean is_ok = true;
    private int driver_id;
    private double consumption;
    private FuelType fuelType;
    private double consumption_loaded;
    private double fuel_in_tank;
    private  int reliabilityInPercents;
    private int seats;
    private int maxLoadKg;
    private int tankCapacity;
    private int odometer;
}
