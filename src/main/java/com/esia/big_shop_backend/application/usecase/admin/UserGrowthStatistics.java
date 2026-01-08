package com.esia.big_shop_backend.application.usecase.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@AllArgsConstructor
public class UserGrowthStatistics {
    private final long totalUsers;
    private final long activeUsers;
    private final long newUsersToday;
    private final long newUsersThisWeek;
    private final long newUsersThisMonth;
    private final Map<LocalDate, Long> userGrowthByDate; // For charts
}
