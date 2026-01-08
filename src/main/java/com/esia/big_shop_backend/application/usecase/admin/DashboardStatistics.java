package com.esia.big_shop_backend.application.usecase.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardStatistics {
    private final long totalOrders;
    private final long totalUsers;
    private final long totalProducts;
    private final double totalRevenue;
    private final long pendingOrders;
    private final long activeUsers;
}
