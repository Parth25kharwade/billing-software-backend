package com.parth.billingsoftware.service.impl;

import com.parth.billingsoftware.entity.OrderEntity;
import com.parth.billingsoftware.entity.OrderItemEntity;
import com.parth.billingsoftware.io.*;
import com.parth.billingsoftware.repository.OrderEntityRepository;
import com.parth.billingsoftware.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServicceImpl implements OrderService {
    private final OrderEntityRepository orderEntityRepository;
    @Override
    public OrderResponce createOrder(OrderRequest request) {
        OrderEntity newOrder = convertToOrderEntity(request);
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setStatus((PaymentMethod)newOrder.getPaymentMethod() == PaymentMethod.CASH ? PaymentDetails.PaymentStatus.SUCCESSFUL : PaymentDetails.PaymentStatus.PENDING);
        newOrder.setPaymentDetails(paymentDetails);
        List<OrderItemEntity> orderItems = request.getCartItems().stream()
                .map(itemRequest -> convertToOrderItemEntity(itemRequest, newOrder))
                .collect(Collectors.toList());
        newOrder.setItems(orderItems);
        orderEntityRepository.save(newOrder);
        return convertToOrderResponce(newOrder);
    }
    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest itemRequest, OrderEntity order) {
        return OrderItemEntity.builder()
                .itemId(itemRequest.getItemId())
                .name(itemRequest.getName())
                .price(itemRequest.getPrice())
                .quantity(itemRequest.getQuantity())
                .order(order)
                .build();
    }

    private OrderResponce convertToOrderResponce(OrderEntity newOrder) {
       return OrderResponce.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .subTotal(newOrder.getSubTotal())
                .tax(newOrder.getTax())
                .grandTotal(newOrder.getGrandTotal())
                .paymentMethod((PaymentMethod)newOrder.getPaymentMethod())
                .items(newOrder.getItems().stream()
                        .map(this::convertToOrderItemResponce)
                        .collect(Collectors.toList()))
                .paymentDetails(newOrder.getPaymentDetails())
                .createdAt(newOrder.getCreatedAt())
                .build();
    }

    private OrderResponce.OrderItemResponce convertToOrderItemResponce(OrderItemEntity orderItemEntity) {
        return OrderResponce.OrderItemResponce.builder()
                .itemId(orderItemEntity.getItemId())
                .name(orderItemEntity.getName())
                .price(orderItemEntity.getPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }

    private OrderRequest.OrderItemRequest convertToOrderItemRequest(OrderItemEntity orderItemEntity) {
        return OrderRequest.OrderItemRequest.builder()
                .itemId(orderItemEntity.getItemId())
                .name(orderItemEntity.getName())
                .price(orderItemEntity.getPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }

    private OrderEntity convertToOrderEntity(OrderRequest request) {
        return OrderEntity.builder()
                .orderId("ORD" + System.currentTimeMillis())
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subTotal(request.getSubTotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public void deleteOrder(String orderId) {
       OrderEntity orderEntity= orderEntityRepository.findByOrderId(orderId)
                .orElseThrow(()->new RuntimeException("Order not found"));
        orderEntityRepository.delete(orderEntity);

    }

    @Override
    public List<OrderResponce> getLatestOrders() {
       return orderEntityRepository.findAllByOrderByCreatedAtDesc()
               .stream()
               .map(this::convertToOrderResponce)
               .collect(Collectors.toList());
    }

    @Override
    public OrderResponce verifyPayment(PaymentVerificationRequest request) {
       OrderEntity existingOrder = orderEntityRepository.findByOrderId(request.getOrderId())
                .orElseThrow(()->new RuntimeException("Order Not Found"));
       if(!verifyRazorpaySignature(request.getRazorpayOrderId(),request.getRazorpayPaymentId(),
               request.getRazorpaySignature())){
           throw new RuntimeException("Invalid Payment Signature");
       }
       PaymentDetails existingPaymentDetails = existingOrder.getPaymentDetails();
        existingPaymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
        existingPaymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
        existingPaymentDetails.setRazorpaySignature(request.getRazorpaySignature());
        existingPaymentDetails.setStatus(PaymentDetails.PaymentStatus.SUCCESSFUL);

        existingOrder=orderEntityRepository.save(existingOrder);
        return convertToOrderResponce(existingOrder);
    }

    @Override
    public Double sumSalesByDate(LocalDate date) {
        return orderEntityRepository.sumSalesByDate(date);
    }

    @Override
    public Long countByOrderDate(LocalDate date) {
        return orderEntityRepository.countByOrderDate(date);
    }

    @Override
    public List<OrderResponce> findRecentOrder() {
        return orderEntityRepository.findRecentOrder(PageRequest.of(0,5))
                .stream()
                .map(this::convertToOrderResponce)
                .collect(Collectors.toList());
    }

    private boolean verifyRazorpaySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        return true;
    }
}