package com.esia.big_shop_backend.application.usecase.admin;

import com.esia.big_shop_backend.application.usecase.admin.query.GetRevenueStatisticsQuery;
import com.esia.big_shop_backend.application.usecase.admin.result.RevenueStatistics;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetRevenueStatisticsUseCase {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public RevenueStatistics execute(GetRevenueStatisticsQuery query) {
        // Get all delivered orders
        List<Order> allOrders = orderRepository.findAll(0, Integer.MAX_VALUE);
        List<Order> deliveredOrders = allOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .toList();

        // Calculate total revenue
        double totalRevenue = deliveredOrders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        LocalDateTime now = LocalDateTime.now();

        // Calculate daily revenue (today)
        double dailyRevenue = deliveredOrders.stream()
                .filter(order -> order.getCreatedAt().toLocalDate().equals(now.toLocalDate()))
                .mapToDouble(Order::getTotalAmount)
                .sum();

        // Calculate weekly revenue (last 7 days)
        double weeklyRevenue = deliveredOrders.stream()
                .filter(order -> order.getCreatedAt().isAfter(now.minusDays(7)))
                .mapToDouble(Order::getTotalAmount)
                .sum();

        // Calculate monthly revenue (last 30 days)
        double monthlyRevenue = deliveredOrders.stream()
                .filter(order -> order.getCreatedAt().isAfter(now.minusDays(30)))
                .mapToDouble(Order::getTotalAmount)
                .sum();

        // Group revenue by date for the last 30 days
        Map<LocalDate, Double> revenueByDate = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            LocalDate date = now.minusDays(i).toLocalDate();
            double revenue = deliveredOrders.stream()
                    .filter(order -> order.getCreatedAt().toLocalDate().equals(date))
                    .mapToDouble(Order::getTotalAmount)
                    .sum();
            revenueByDate.put(date, revenue);
        }

        return new RevenueStatistics(
                totalRevenue,
                dailyRevenue,
                weeklyRevenue,
                monthlyRevenue,
                revenueByDate
        );
    }

    public RevenueStatistics execute(LocalDate startDate, LocalDate endDate) {
        GetRevenueStatisticsQuery query = new GetRevenueStatisticsQuery(startDate, endDate, "USD");
        return execute(query);
    }
}
