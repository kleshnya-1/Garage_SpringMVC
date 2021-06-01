package ru.excers.projectGarage.models;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.dao.CarsDAO;
import ru.excers.projectGarage.dao.DriverScheduleDAO;
import ru.excers.projectGarage.dao.DriversDAO;
import ru.excers.projectGarage.models.cars.AssignedCar;
import ru.excers.projectGarage.services.AccessDriverChecker;
import ru.excers.projectGarage.services.calcsAndConverters.CarBrokingCalculator;

import java.util.Random;


//@Data
@Log4j2
@Component
public class CarDriver {


    private int driverId;
    private AssignedCar car = null;
    //по умолчанию машина в нормальном состоянии. изменить это может только
    // случайность во время заказа или отметка водителя после рейса
    private boolean technicalSatusOk = true;
    private Random randomForDifferencesInRalAndExpectedValues = new Random();
    private int randomRangeInPercentes = 15;  // 0-100

    private int distanceHasPassed;
    private  double fuelHasConsumed;
    private  double driverEarnThisOrder;

    AccessDriverChecker accessDriverChecker ;
    CarBrokingCalculator carBrokingCalculator ;
    DriverScheduleDAO driverScheduleWriterDAO ;
    CarsDAO garageWriteDAO ;
    DriversDAO driversDAO ;


    public CarDriver(int driverId) {
        this.driverId = driverId;

    }

    public CarDriver(int driverId, AssignedCar car) {
        this.driverId = driverId;
        takeTheCar(car);
    }

    public CarDriver() {

    }
@Autowired
    public CarDriver(AccessDriverChecker accessDriverChecker,
                     CarBrokingCalculator carBrokingCalculator,
                     DriverScheduleDAO driverScheduleWriterDAO,
                     CarsDAO garageWriteDAO,
                     DriversDAO driversDAO) {
        this.accessDriverChecker = accessDriverChecker;
        this.carBrokingCalculator = carBrokingCalculator;
        this.driverScheduleWriterDAO = driverScheduleWriterDAO;
        this.garageWriteDAO = garageWriteDAO;
        this.driversDAO = driversDAO;
    }

    public void takeTheCar(  AssignedCar car) {
        log.info("Проверка доступа водителя № " + driverId + " к автомобилю " +
                car.getCarResultInAssignedCar().getId() + " которая закреплена в объекте за " +
                car.getCarResultInAssignedCar().getDriver_id());
        try {

            if (this.car != null) {
                throw new Exception("Водитель уже занят другой машиной");
            } else {

                if (accessDriverChecker.check(driverId, car)) {
                    this.car = car;
                    log.info("Доступ предоставлен");
                    log.info("Водитель № " + driverId + " взял автомобиль " +
                            car.getCarResultInAssignedCar().getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void driveCar() {
        try {
            if (car == null)
                throw new Exception("Машина не назначена");
            else {
                //посчитаем шанс поломки ТС. принимаем, что рейс выполнен,
                // несмотря на результат подсчета


                technicalSatusOk =
                   !carBrokingCalculator.caculateWillTheCarllBeBrokenInTheTrip(car);

                //введем случайность в расход ресурсов

                //посчитаем коэф. случайности отдельно для простоты чтения
                double randomForDistance = (double) (randomForDifferencesInRalAndExpectedValues.
                        nextInt(2* randomRangeInPercentes)-randomRangeInPercentes)/100;
                double randomForFuel =(double) (randomForDifferencesInRalAndExpectedValues.
                        nextInt(2* randomRangeInPercentes)-randomRangeInPercentes)/100;

                // посчитаем сами новые значения пробега и расхода
                 distanceHasPassed = (int) (car.getOrderDistance()*(1+randomForDistance));
                 fuelHasConsumed = car.getFuelExpectedConsumption()*(1+randomForFuel);
                 driverEarnThisOrder = car.getPriceForOrder();


            }}

            catch(Exception e){
                e.printStackTrace();
            }


    }


    public void returnCar(boolean isCarOk){

        if (isCarOk == false){
            technicalSatusOk = false;
            log.info("У водителя сломалась машина!");
        }

        try {
            //деляем отметку в расписании о выполненном заказе
            driverScheduleWriterDAO.makeOrderDone(driverId, car);


        } catch (Exception e) {
            //ошибки с базой данных не обрабатываем
            e.printStackTrace();
        }

        //вносим изменения в состояние автомобиля
        garageWriteDAO.addInfoForCars(car.getCarResultInAssignedCar().getId(), technicalSatusOk, distanceHasPassed,fuelHasConsumed);

        //добавляем данные о заработке водителя
        driversDAO.addMoney(driverId, driverEarnThisOrder);


        log.info("Водитель "+driverId + " вернул машину " +car.getCarResultInAssignedCar().getId());
        car = null;
        distanceHasPassed =0;
        fuelHasConsumed=0;
        driverEarnThisOrder=0;


    }

    public void takeDriveAndReturnCar(AssignedCar car, boolean isOk){
        takeTheCar( car);
        driveCar();
        returnCar(isOk);

    }



}
