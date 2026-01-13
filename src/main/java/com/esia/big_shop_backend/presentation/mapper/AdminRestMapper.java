package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.application.usecase.admin.result.DashboardStatistics;
import com.esia.big_shop_backend.application.usecase.admin.result.RevenueStatistics;
import com.esia.big_shop_backend.presentation.dto.response.admin.DashboardStatsResponse;
import com.esia.big_shop_backend.presentation.dto.response.admin.RevenueStatsResponse;
import org.springframework.stereotype.Component;

@Component
public class AdminRestMapper {

    public DashboardStatsResponse toDashboardResponse(DashboardStatistics stats) {
        return DashboardStatsResponse.builder()
                .totalOrders(stats.getTotalOrders())
                .totalUsers(stats.getTotalUsers())
                .totalProducts(stats.getTotalProducts())
                .totalRevenue(stats.getTotalRevenue())
                .pendingOrders(stats.getPendingOrders())
                .activeUsers(stats.getActiveUsers())
                .build();
    }

    public RevenueStatsResponse toRevenueResponse(RevenueStatistics stats) {
        return RevenueStatsResponse.builder()
                .totalRevenue(stats.getTotalRevenue())
                .dailyRevenue(stats.getDailyRevenue())
                .weeklyRevenue(stats.getWeeklyRevenue())
                .monthlyRevenue(stats.getMonthlyRevenue())
                .revenueByDate(stats.getRevenueByDate())
                .build();
    }
}
