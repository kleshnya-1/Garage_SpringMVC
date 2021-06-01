package ru.excers.projectGarage.models;

import lombok.Data;
import ru.excers.projectGarage.models.subModels.FuelType;

@Data
public class OrderWithSpecialConditions {
    private String brand;
    private FuelType fuelType;
    private boolean is_male;


    public OrderWithSpecialConditions(String brand, FuelType fuelType, boolean is_male) {
        this.brand = brand;
        this.fuelType = fuelType;
        this.is_male = is_male;
    }

    public String getBrand() {
        return brand;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public boolean isIs_male() {
        return is_male;
    }
}
