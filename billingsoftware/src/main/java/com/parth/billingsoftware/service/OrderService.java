package com.parth.billingsoftware.service;

import com.parth.billingsoftware.entity.OrderEntity;
import com.parth.billingsoftware.io.OrderRequest;
import com.parth.billingsoftware.io.OrderResponce;
import com.parth.billingsoftware.io.PaymentVerificationRequest;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderResponce createOrder(OrderRequest request);
    void deleteOrder(String orderId);
    List<OrderResponce> getLatestOrders();

    OrderResponce verifyPayment(PaymentVerificationRequest request);

    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);
    List<OrderResponce> findRecentOrder();
}
