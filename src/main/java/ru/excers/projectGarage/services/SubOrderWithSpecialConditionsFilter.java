package ru.excers.projectGarage.services;



import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.cars.CarResult;
import ru.excers.projectGarage.models.OrderWithSpecialConditions;

import java.util.ArrayList;
import java.util.List;


@Component
public class SubOrderWithSpecialConditionsFilter {


    public List<CarResult> filter(List<CarResult> l, OrderWithSpecialConditions sOrder){
        List<CarResult> filteredCars = new ArrayList<>(l);




        for (CarResult c : l){


            if (c.isIs_male()!=sOrder.isIs_male())
                filteredCars.remove(c);

            if (sOrder.getFuelType()!=null&&!c.getFuelType().equals(sOrder.getFuelType()))
                filteredCars.remove(c);

            if ((sOrder.getBrand()!=null)&&(!c.getBrand().equals(sOrder.getBrand())) )
                filteredCars.remove(c);

        }
        System.out.println(filteredCars);



        return filteredCars;

    }
}
