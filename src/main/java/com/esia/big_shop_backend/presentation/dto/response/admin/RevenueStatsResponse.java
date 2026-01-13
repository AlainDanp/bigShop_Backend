package com.esia.big_shop_backend.presentation.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueStatsResponse {
    private double totalRevenue;
    private double dailyRevenue;
    private double weeklyRevenue;
    private double monthlyRevenue;
    private Map<LocalDate, Double> revenueByDate;
}
