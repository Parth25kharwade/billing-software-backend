package com.parth.billingsoftware.controller;

import com.parth.billingsoftware.io.DashboardResponce;
import com.parth.billingsoftware.io.OrderResponce;
import com.parth.billingsoftware.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final OrderService orderService;
    @GetMapping(produces = "application/json")
    public DashboardResponce getDashboardData() {
        LocalDate today = LocalDate.now();
        Double salesToday = orderService.sumSalesByDate(today);
        Long ordersToday = orderService.countByOrderDate(today);
        List<OrderResponce> recentOrders = orderService.findRecentOrder();
        return new DashboardResponce(
                salesToday!=null?salesToday:0.0,
                ordersToday!=null?ordersToday:0l,
                recentOrders
        );

    }
}
