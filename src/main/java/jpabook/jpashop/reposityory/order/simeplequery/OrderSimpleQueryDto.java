package jpabook.jpashop.reposityory.order.simeplequery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderSimpleQueryDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderData;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderData, OrderStatus orderStatus, Address address) {
            this.orderId =orderId;
            this.name =name;// LAZY 초기화
            this.orderData =orderData;
            this.orderStatus= orderStatus;
            this.address =address; // LAZY 초기화
        }

}
