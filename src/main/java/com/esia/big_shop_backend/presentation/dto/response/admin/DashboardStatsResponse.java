package com.esia.big_shop_backend.presentation.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private long totalOrders;
    private long totalUsers;
    private long totalProducts;
    private double totalRevenue;
    private long pendingOrders;
    private long activeUsers;
}
