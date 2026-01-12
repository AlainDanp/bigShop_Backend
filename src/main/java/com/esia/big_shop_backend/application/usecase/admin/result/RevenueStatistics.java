package com.esia.big_shop_backend.application.usecase.admin.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@AllArgsConstructor
public class RevenueStatistics {
    private final double totalRevenue;
    private final double dailyRevenue;
    private final double weeklyRevenue;
    private final double monthlyRevenue;
    private final Map<LocalDate, Double> revenueByDate;
}
