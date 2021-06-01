package ru.excers.projectGarage.models.subModels;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.dao.GasStationDAO;
import ru.excers.projectGarage.models.cars.CarResult;
import ru.excers.projectGarage.services.calcsAndConverters.FuelCalc;

@Component
public class PriceCalculator {




        private FuelCalc fuelCalc;

    private GasStationDAO gasStationDAO;

    public PriceCalculator(){

    }

    @Autowired
    public PriceCalculator(FuelCalc fuelCalc, GasStationDAO gasStationDAO) {
        this.fuelCalc = fuelCalc;
        this.gasStationDAO = gasStationDAO;
    }


    public double calculatePrice(double distance, int load, CarResult carResult) {



        double fuelCunsumption = getFuelConsumption(  distance,  load,  carResult);

        double fuelPrice = gasStationDAO.getFuelPrice(carResult.getFuelType());

        return fuelPrice * fuelCunsumption;

    }

    public double getFuelConsumption(double distance, int load, CarResult carResult) {
        return fuelCalc.fuelConsExpected(distance, carResult.getConsumption(),
                carResult.getConsumption_loaded(), load, carResult.getMaxLoadKg());
    }

}
