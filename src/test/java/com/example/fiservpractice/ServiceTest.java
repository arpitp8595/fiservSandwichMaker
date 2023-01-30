package com.example.fiservpractice;

import com.example.fiservpractice.dao.OrderDao;
import com.example.fiservpractice.model.Order;
import com.example.fiservpractice.model.OrderEntity;
import com.example.fiservpractice.repository.OrderRepository;
import com.example.fiservpractice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderDao orderDao;

    @Test
    public void testCreateOrder() {
        log.info("entered in testCreateOrder().!");
        Order order = new Order("White", "Beef", true, true);
        orderService.placeAnOrder(order);
        verify(orderDao, times(1)).placeAnOrder(order);
    }

    @Test
    public void deleteApplication() {
        OrderEntity orderEntity = new OrderEntity(1L, "White", "beef", true, false, "6");
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        orderDao.removeOrder(1L);
        verify(orderRepository, times(1)).delete(orderEntity);
    }

    @Test
    public void getOrderTest () {
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(new OrderEntity(1L, "White", "beef", true, false, "6")));
        Order orderObj = orderDao.getOrderDetails(1L);
        assertThat(orderObj.getMeat()).isEqualToIgnoringCase("beef");
    }

}
