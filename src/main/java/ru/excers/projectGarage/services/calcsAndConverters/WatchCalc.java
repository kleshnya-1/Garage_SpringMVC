package ru.excers.projectGarage.services.calcsAndConverters;


import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.Order;

import java.util.Calendar;

@Component
public class WatchCalc {


    public int returnWatch(Order order) {


        try {
            if (order.getOrderStartTime().get(Calendar.HOUR_OF_DAY) > 12) return 2;
            if (order.getOrderStartTime().get(Calendar.HOUR_OF_DAY) <= 12) return 1;
            else throw new Exception("invalid watch parameters");

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }
}
