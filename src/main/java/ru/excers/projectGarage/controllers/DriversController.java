package ru.excers.projectGarage.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.excers.projectGarage.dao.DriverScheduleDAO;
import ru.excers.projectGarage.dao.DriversDAO;
import ru.excers.projectGarage.models.Dispatcher;
import ru.excers.projectGarage.models.subModels.TimeTable;

import java.util.ArrayList;
import java.util.List;


//@Data
@Controller
@RequestMapping("/drivers")
public class DriversController {
    private final DriversDAO driverDAO;
    DriverScheduleDAO driverScheduleDAO;
    Dispatcher dispatcher;

    @Autowired
    public DriversController(DriversDAO driverDAO,
                             Dispatcher dispatcher,
                             DriverScheduleDAO driverScheduleDAO) {
        this.driverDAO = driverDAO;
        this.driverScheduleDAO = driverScheduleDAO;
        this.dispatcher = dispatcher;
    }

    @GetMapping
    public String getAll(Model model) {

        model.addAttribute("drivers", driverDAO.index());
        return "drivers/index";

    }


    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model0) {

        model0.addAttribute("driver", driverDAO.getPeersonById(id));

        return "drivers/show";

    }

    @GetMapping("/{id}/timetable")
    public String getById(Model model0,@PathVariable("id") int id) {
        String driverName= driverDAO.getPeersonById(id).getName();

        List<TimeTable> fullList = driverScheduleDAO.getTimeTable();
        List<TimeTable> filteredList = new ArrayList<>();

        for (TimeTable tt: fullList){
            if (tt.getDriverName().equals(driverName)){
                filteredList.add(tt);
            }
        }

        model0.addAttribute("TimeTableList", filteredList);
        return "drivers/timetable";

    }

    @GetMapping("/{id}/timetable/{carId}/drive_and_back")
    public String getById(Model model0,@PathVariable("id") int id, @PathVariable("carId") int carId) {

      /*  AssignedCar assignedCar = d;
        CarDriver driver = new CarDriver(id);
         driver.takeDriveAndReturnCar();
*/


        return "drivers/drive_and_back";

    }




}
