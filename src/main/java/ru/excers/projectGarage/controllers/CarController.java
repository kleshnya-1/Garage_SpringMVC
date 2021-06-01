package ru.excers.projectGarage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.excers.projectGarage.dao.CarsDAO;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarsDAO carsDAO;

    @Autowired
    public CarController(CarsDAO carsDAO) {
        this.carsDAO = carsDAO;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("cars", carsDAO.index());
        return "cars/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") String id, Model model) {
        model.addAttribute("car", carsDAO.getById(id));
        return "cars/show";
    }

    @GetMapping("/{id}/drive_and_back")
    public String getById(Model model0,@PathVariable("id") int id, @PathVariable("carId") int carId) {


        return "drivers/drive_and_back";

    }


}
