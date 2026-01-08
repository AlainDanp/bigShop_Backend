package com.esia.big_shop_backend.application.usecase.admin;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetRevenueStatisticsUseCase {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public RevenueStatistics execute() {
        // Get all delivered orders
        Page<Order> allOrders = orderRepository.findAll(Pageable.unpaged());
        List<Order> deliveredOrders = allOrders.getContent().stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .collect(Collectors.toList());

        // Calculate total revenue
        double totalRevenue = deliveredOrders.stream()
                .mapToDouble(order -> order.getTotalAmount().getAmount())
                .sum();

        LocalDateTime now = LocalDateTime.now();

        // Calculate daily revenue (today)
        double dailyRevenue = deliveredOrders.stream()
                .filter(order -> order.getCreatedAt().toLocalDate().equals(now.toLocalDate()))
                .mapToDouble(order -> order.getTotalAmount().getAmount())
                .sum();

        // Calculate weekly revenue (last 7 days)
        double weeklyRevenue = deliveredOrders.stream()
                .filter(order -> order.getCreatedAt().isAfter(now.minusDays(7)))
                .mapToDouble(order -> order.getTotalAmount().getAmount())
                .sum();

        // Calculate monthly revenue (last 30 days)
        double monthlyRevenue = deliveredOrders.stream()
                .filter(order -> order.getCreatedAt().isAfter(now.minusDays(30)))
                .mapToDouble(order -> order.getTotalAmount().getAmount())
                .sum();

        // Group revenue by date for the last 30 days
        Map<LocalDate, Double> revenueByDate = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            LocalDate date = now.minusDays(i).toLocalDate();
            double revenue = deliveredOrders.stream()
                    .filter(order -> order.getCreatedAt().toLocalDate().equals(date))
                    .mapToDouble(order -> order.getTotalAmount().getAmount())
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
}
