package ru.excers.projectGarage.models;

public enum CarType {
    T("Truck"),//TRUCK
    P("Passenger"); // PASSENGER

    private final String displayValue;

    private CarType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}
