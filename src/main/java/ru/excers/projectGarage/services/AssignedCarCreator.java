package ru.excers.projectGarage.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.*;
import ru.excers.projectGarage.models.cars.AssignedCar;
import ru.excers.projectGarage.models.cars.CarResult;
import ru.excers.projectGarage.models.subModels.PriceCalculator;
import ru.excers.projectGarage.models.subModels.TimeInterval;
import ru.excers.projectGarage.services.calcsAndConverters.Calculator;
import ru.excers.projectGarage.services.calcsAndConverters.TimeForOrderCalc;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class AssignedCarCreator {



    PriceCalculator priceCalculator ;
    TimeForOrderCalc timeForOrderCalc ;
    Calculator calculator ;

    public AssignedCarCreator() {

    }
    @Autowired
    public AssignedCarCreator(PriceCalculator priceCalculator, TimeForOrderCalc timeForOrderCalc, Calculator calculator) {
        this.priceCalculator = priceCalculator;
        this.timeForOrderCalc = timeForOrderCalc;
        this.calculator = calculator;
    }

    public AssignedCar create(CarResult choosedCar, Order orderForAssigning, double percentOfMargin) {


        AssignedCar a = new AssignedCar (choosedCar);

//заполняем данные, необходимые далее в экземпляре
        a.setOrder(orderForAssigning.getOrderExplaining());
        a.setOrderDistance(orderForAssigning.getDistanceKm()+calculator.getUnloadedDistanceForCalc());
        a.setFuelExpectedConsumption(priceCalculator.getFuelConsumption(orderForAssigning.getDistanceKm(), orderForAssigning.getNumOfKg(), choosedCar));
        a.setPriceForOrder((1 + percentOfMargin / 100) * priceCalculator.calculatePrice(orderForAssigning.getDistanceKm(), orderForAssigning.getNumOfKg(), choosedCar));

        //расчет временного интервала
        TimeInterval timeIntervalForThisOrder = new TimeInterval();

        Calendar orderStartTime = orderForAssigning.getOrderStartTime();
        //время начала заказа минус время на дорогу
        orderStartTime.add(Calendar.MINUTE, -timeForOrderCalc.getMinutesOneWayTime());
        timeIntervalForThisOrder.setStartDate(orderStartTime);


        Calendar finish = new GregorianCalendar();
        int timeForOrder = timeForOrderCalc.minutesForOrderExpected(orderForAssigning.getDistanceKm());

        //конец заказа это начало + ожидаемое время на выполнение заказа
        finish.setTime(orderStartTime.getTime());
        finish.add(Calendar.MINUTE, timeForOrder);

        timeIntervalForThisOrder.setEndDate(finish);


        a.setTimeInterval(timeIntervalForThisOrder);

        if (orderForAssigning.getNumOfPass() != 0)
            a.setNumOfElements(orderForAssigning.getNumOfPass());
        else
            a.setNumOfElements(orderForAssigning.getNumOfKg());
        return a;

    }
}
