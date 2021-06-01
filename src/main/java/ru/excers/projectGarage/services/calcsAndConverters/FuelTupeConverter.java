package ru.excers.projectGarage.services.calcsAndConverters;


import ru.excers.projectGarage.models.subModels.FuelType;

public class FuelTupeConverter {




    public FuelType fuelTypeChooser(String s){
        FuelType forReturn = null;
        switch (s){
            case ("gasoline"):
                forReturn = FuelType.gasoline;
                break;

            case ("gas"):
                forReturn = FuelType.gas;
                break;

            case ("diesel"):
                forReturn = FuelType.diesel;
                break;

            case ("electric"):
                forReturn = FuelType.electric;
                break;
        }
        return forReturn;
    }
}
