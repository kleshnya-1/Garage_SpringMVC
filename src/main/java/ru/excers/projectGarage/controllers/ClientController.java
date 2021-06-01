package ru.excers.projectGarage.controllers;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.excers.projectGarage.models.Dispatcher;
import ru.excers.projectGarage.models.Order;
import ru.excers.projectGarage.models.cars.AssignedCar;

@Log4j2
@Controller
@RequestMapping("/client")
public class ClientController {
    final    Dispatcher dispatcher;

    public ClientController(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @GetMapping("/startClientPage")
    public String startClient() {

        return "client/startClientPage";
    }

    @GetMapping("/new_order")
    public String getAll(@ModelAttribute("order")  Order order) {

        return "client/orders/new_order";
    }



    @PostMapping()
    public String makeOrder(@ModelAttribute("order")  Order order) {
        order.createCalendarAndIncrementStartTime(order.getMinutesBeforeOrder());

        AssignedCar assignedCar =
                dispatcher.publishOrderAndReturnAssignedCar(order,null);

        return "client/orders/thankForOrder";
    }

   @GetMapping("/new_order_special")
    public String getAll() {

        return "client/orders/new_order_special";
    }



}
