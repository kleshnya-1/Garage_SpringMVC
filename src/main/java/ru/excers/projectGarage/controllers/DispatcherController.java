package ru.excers.projectGarage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.excers.projectGarage.dao.CarsDAO;
import ru.excers.projectGarage.models.Dispatcher;
import ru.excers.projectGarage.models.subModels.TimeTable;
import ru.excers.projectGarage.dao.DriverScheduleDAO;

import java.util.List;


@Controller
@RequestMapping("/dispatcher")
public class DispatcherController {



    private  Dispatcher dispatcher;

        private final CarsDAO carsDAO;
    private DriverScheduleDAO driverScheduleDAO;

    public DispatcherController(Dispatcher dispatcher, CarsDAO carsDAO, DriverScheduleDAO driverScheduleDAO) {
        this.dispatcher = dispatcher;
        this.carsDAO = carsDAO;
        this.driverScheduleDAO = driverScheduleDAO;
    }


    @GetMapping("/cars")
    public String getAllCars(Model model) {
        model.addAttribute("cars", carsDAO.index());

        return "cars/index";
    }

    @GetMapping("/timetable")
    public String getTimetable( Model model) {


        List<TimeTable> l = driverScheduleDAO.getTimeTable();


        System.out.println(l);

        model.addAttribute("TimeTableList", l);


        return "dispatcher/timetable";
    }




}
