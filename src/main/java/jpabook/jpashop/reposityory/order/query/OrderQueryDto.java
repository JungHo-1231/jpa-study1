package jpabook.jpashop.reposityory.order.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderData;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderData, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderData = orderData;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
