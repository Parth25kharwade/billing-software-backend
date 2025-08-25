package com.parth.billingsoftware.io;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponce {
private Double todaysSale;
private Long totalOrders;
private List<OrderResponce> recentOrders;


}
