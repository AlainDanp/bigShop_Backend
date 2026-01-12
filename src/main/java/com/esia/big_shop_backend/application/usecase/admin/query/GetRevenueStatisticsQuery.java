package com.esia.big_shop_backend.application.usecase.admin.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRevenueStatisticsQuery {
    private LocalDate startDate;
    private LocalDate endDate;
    private String currency;
}
