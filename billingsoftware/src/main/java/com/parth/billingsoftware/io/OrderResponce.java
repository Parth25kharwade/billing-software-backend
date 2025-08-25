package com.parth.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponce {
    private String orderId;
    private  String customerName;
    private  String phoneNumber;
    private List<OrderResponce.OrderItemResponce> items;
    private  Double subTotal;
    private  Double tax;
    private  Double grandTotal;
    private  PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private PaymentDetails paymentDetails;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderItemResponce {
        private String itemId;
        private String name;
        private Double price;
        private Integer quantity;
    }
}
