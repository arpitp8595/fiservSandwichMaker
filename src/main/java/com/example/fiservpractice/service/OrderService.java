package com.example.fiservpractice.service;

import com.example.fiservpractice.dao.OrderDao;
import com.example.fiservpractice.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order getOrderDetails(Long id) {
        log.info("Inside getOrderDetails()");
        return orderDao.getOrderDetails(id);
    }

    public String placeAnOrder(Order order) {
        log.info("inside service method" + order.toString());
        return orderDao.placeAnOrder(order);
    }

    public String removeOrder(Long id) {
        orderDao.removeOrder(id);
        return "Entity has deleted.!";
    }

}