package ru.excers.projectGarage.services.fillersAndPutters;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.dao.CarsDAO;
import ru.excers.projectGarage.models.EconomicBlock;
import ru.excers.projectGarage.models.cars.CarResult;
import ru.excers.projectGarage.models.Order;
import ru.excers.projectGarage.models.OrderWithSpecialConditions;
import ru.excers.projectGarage.models.subModels.PriceCalculator;
import ru.excers.projectGarage.services.ExceptionNoCarsFound;
import ru.excers.projectGarage.services.calcsAndConverters.FuelCalc;

import java.util.*;

@Slf4j
@Component
public class CarsOrderPriceAndSalaryPutter {

    @Autowired
    CarsDAO carsDAO;
    @Autowired
    PriceCalculator priceCalculator;
    @Autowired
    FuelCalc fuelCalc;


    //добавляет блок экономики, считает экономические показатели. при наличии топлива, добавляет в возвращаемый список
    public NavigableMap<EconomicBlock, CarResult> make(Order order, OrderWithSpecialConditions subOwSc) throws ExceptionNoCarsFound {
        //просто спиок машин
        List<CarResult> carList = carsDAO.getCarListSuitableCars(order, subOwSc);
        log.info("Машин поступило на расчет топлива: "+carList.size());
        //блок машина+показатели
        NavigableMap<EconomicBlock, CarResult> carsAndPrices = new TreeMap();


        carList.forEach(carResult -> {

            double fuelNeeded = fuelCalc.fuelConsExpectedFromCar(order.getDistanceKm(),
                    order.getNumOfKg(), carResult);
            double price = priceCalculator.calculatePrice(order.getDistanceKm(),
                    order.getNumOfKg(), carResult);

            log.info("на " + order.getDistanceKm() + " км" + " потребуется " + fuelNeeded + " л топлива");

//проверка на наличие топлива для поездки и добавление в список на возврат
            if (fuelNeeded < carResult.getFuel_in_tank()) {
                carsAndPrices.put(new EconomicBlock(0, price, carResult.getEarnedThisWeek()), carResult);
            }


        });
        log.info("Машин с достаточным числом топлива: "+carsAndPrices.size());

        if (carsAndPrices.size() == 0)
            throw new ExceptionNoCarsFound();
        else return carsAndPrices;
    }


  /*  public NavigableMap<EconomicBlock, CarResult> makeFromList(List<CarResult> carList, Order order, OrderWithSpecialConditions subOwSc) {

        NavigableMap<EconomicBlock, CarResult> carsAndPrices = new TreeMap();
        carList.forEach(carResult -> {
            double price = priceCalculator.calculatePrice(order.getDistanceKm(),
                    order.getNumOfKg(), carResult);


            try {
                carsAndPrices.put(new EconomicBlock(0, price, carResult.getEarnedThisWeek()), (CarResult) carResult.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }


        });
        return carsAndPrices;
    }
*/
}
