package ru.excers.projectGarage.models.cars;


import lombok.Data;
import ru.excers.projectGarage.models.subModels.TimeInterval;


@Data
public class AssignedCar  {

    private String order;
    private double priceForOrder;
    private double orderDistance;
    private double fuelExpectedConsumption;
    private int numOfElements;
    TimeInterval timeInterval;
    CarResult carResultInAssignedCar;

    public AssignedCar(CarResult carResultInAssignedCar) {
        this.carResultInAssignedCar = carResultInAssignedCar;

    }

    public AssignedCar( ) {

    }

//    public void set(CarResult c){
//        this. = (AssignedCar)c;
//    }



}
