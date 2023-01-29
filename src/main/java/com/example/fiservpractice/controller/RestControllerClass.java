package com.example.fiservpractice.controller;

import com.example.fiservpractice.model.Order;
import com.example.fiservpractice.service.OrderService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/PlaceOrder")
public class RestControllerClass {

    final OrderService orderService;

    public RestControllerClass(OrderService orderService) {
        this.orderService = orderService;
    }

    //@RequestMapping(method = RequestMethod.GET, value ="/getOrderDetails") // optional way
    @GetMapping("/getOrderDetails")
    public Order getOrderDetails(@PathParam("/id") Long id) {
        return orderService.getOrderDetails(id);
    }

    @PostMapping("/orderSandwich")
    public String placeAnOrder(@RequestBody Order order) {
        log.info("inside controller method");
        return orderService.placeAnOrder(order);
    }

    @PostMapping("/removeOrder")
    public String removeOrder(@PathParam("id") Long id) {
        return orderService.removeOrder(id);
    }

}