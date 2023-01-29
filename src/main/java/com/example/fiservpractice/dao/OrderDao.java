package com.example.fiservpractice.dao;

import com.example.fiservpractice.model.Order;
import com.example.fiservpractice.model.OrderEntity;
import com.example.fiservpractice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class OrderDao {

    private final OrderRepository orderRepository;

    public OrderDao(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String placeAnOrder(Order order) {
        int totalAmount = 0;
        if (order.getBread().equals("White") || order.getBread().equals("Grain")) {
            totalAmount = totalAmount + 2;
        }
        if (order.getMeat().equals("Veggie")) {
            totalAmount = totalAmount + 1;
        } else if (order.getMeat().equals("Chicken")) {
            totalAmount = totalAmount + 2;
        } else if (order.getMeat().equals("Beef")) {
            totalAmount = totalAmount + 3;
        }
        if (order.isToppings()) {
            totalAmount = totalAmount + 1;
        }
        if (order.isCheese()) {
            totalAmount = totalAmount + 1;
        }
        log.info("Value after cheese: " + totalAmount);
        orderRepository.save(new OrderEntity(null,
                                             order.getBread(),
                                             order.getMeat(),
                                             order.isToppings(),
                                             order.isCheese(),
                                             String.valueOf(totalAmount)));
        log.info("Order has been saved in DB");
        return "Total payable amount is: $" + String.valueOf(totalAmount);
    }

    public Order getOrderDetails(Long orderId) throws IllegalStateException {
        Optional<OrderEntity> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new IllegalStateException("Cannot find product with orderId: " + orderId);
        }
        OrderEntity orderEntity = orderOptional.get();
        return Order.builder()
                .bread(orderEntity.getBread())
                .meat(orderEntity.getMeat())
                .cheese(orderEntity.isCheese())
                .toppings(orderEntity.isToppings())
                .build();
    }

    public void removeOrder(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        orderRepository.delete(orderEntity.get());
        log.info("Deletion is done.!");
    }

}