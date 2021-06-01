package ru.excers.projectGarage.models;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.cars.AssignedCar;
import ru.excers.projectGarage.models.cars.CarResult;
import ru.excers.projectGarage.models.subModels.TimeTable;
import ru.excers.projectGarage.services.*;
import ru.excers.projectGarage.dao.DriverScheduleDAO;
import ru.excers.projectGarage.services.fillersAndPutters.CarsOrderPriceAndSalaryPutter;
import ru.excers.projectGarage.services.fillersAndPutters.CarsSuitableIndexesPutter;

import java.util.*;


@Slf4j
@Component
@Data
public class Dispatcher {


    private double indexOfClientParkMoneyRelationship = 0;
    // 1 - клиент получает минимальную цену
    // 0 - парк отдает заказ самому "бедному" водителю за неделю
    private double percentOfMargin = 30;

    @Autowired
    CarsOrderPriceAndSalaryPutter carsOrderPriceAndSalaryPutter ;
    @Autowired
    CarsSuitableIndexesPutter carsSuitableIndexesPutter ;
    @Autowired
    DriverScheduleDAO driverScheduleDAO;
    @Autowired
    AssignedCarCreator assignedCarCreator;

    public Dispatcher(double percentOfMargin, double indexOfClientParkMoneyRelationship) {
        this.percentOfMargin = percentOfMargin;
        this.indexOfClientParkMoneyRelationship = indexOfClientParkMoneyRelationship;
    }

    public Dispatcher(double indexOfClientParkMoneyRelationship) {
        this.indexOfClientParkMoneyRelationship = indexOfClientParkMoneyRelationship;
        log.info("Диспетчер создан с индексом " + indexOfClientParkMoneyRelationship);
    }


    // по заказу выбирает машину
    private CarResult publishOrderAndReturnChoosedCar(Order order, OrderWithSpecialConditions oWsC) {


        //запрос на лист машин и присвоение машинам эконом. показателей.
        // eсли пуст - выкинет ошибку
        NavigableMap<EconomicBlock, CarResult> carsAndPrices = new TreeMap<>();
        try {
            carsAndPrices = carsOrderPriceAndSalaryPutter.make(order, oWsC);
        } catch (ExceptionNoCarsFound exceptionNoCarsFound) {
            exceptionNoCarsFound.printStackTrace();
        }


        log.info("Диспетчер(" + indexOfClientParkMoneyRelationship + "). Машин с присвоенными ценами: " + carsAndPrices.size());

            //присвоение машинам в листе индекса подходимости под заказ
            NavigableMap<EconomicBlock, CarResult> carsAndPricesAndIndexes =
                    carsSuitableIndexesPutter.putIndexes(carsAndPrices, indexOfClientParkMoneyRelationship);

            log.info("Диспетчер(" + indexOfClientParkMoneyRelationship + "). Машин с присвоенными рейтингами: " + carsAndPricesAndIndexes.size());
            log.debug("\n" + carsAndPricesAndIndexes.toString());

            //TreeMap<EconomicBlock, CarResult> s = new TreeMap<>();
            //s.putAll(carsAndPricesAndIndexes);

            return carsAndPricesAndIndexes.firstEntry().getValue();




    }

    // переданную машину преобразует и ДЕЛАЕТ запись в рассписании.
    private AssignedCar assignToRaceAndReturnAssignedCar(CarResult choosedCar, Order orderForAssigning) {

        if (choosedCar == null) throw new NullPointerException("Машина не передана для обработки");


        AssignedCar assignedCar = assignedCarCreator.create(choosedCar, orderForAssigning,  percentOfMargin);
        //все посчитать

        try {
            driverScheduleDAO.addInSchedule(assignedCar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // добавить водителю в рассписание

        return assignedCar;


    }


    //по заказу выбирает машину, машину преобразует и ДЕЛАЕТ запись в рассписании.
    public AssignedCar publishOrderAndReturnAssignedCar(Order order, OrderWithSpecialConditions oWsC){

        log.info(order.getOrderExplaining()+" created");
        try {

            return assignToRaceAndReturnAssignedCar(publishOrderAndReturnChoosedCar(order, oWsC), order);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void printSchedule(){



        String
                format =
                "%-20s%-10s%-15s%" +
                "-20s%-25s%-10s%" +
                "-15s%-15s%-15s%n";

        System.out.printf(format,
                "Водитель", "Номер А/М","Машина", "Дата и время",
                 "Задание", "Кол-во",
                "Расстояние", "Цена", "Примечание");
        System.out.println();



       List<TimeTable> l = driverScheduleDAO.getTimeTable();
        TimeTable tPrev =l.get(0);
        for (TimeTable t: l){

            if (!t.getDriverName().equals(tPrev.getDriverName())) {

                System.out.println("***");
                tPrev = t;

            } else if (t!=l.get(0))t.setDriverName(" ");


            System.out.printf(format,
                    t.getDriverName(), t.getCarId(), t.getCarBrand(),
                    t.getBusyTime(),                     t.getOrder(), t.getNumOfEl(),
                    t.getDistance(), t.getPrice(), t.getNote()
            );

        }

    }




}