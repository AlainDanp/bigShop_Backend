package com.esia.big_shop_backend.application.usecase.admin;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetDashboardStatisticsUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public DashboardStatistics execute() {
        // Get all orders to calculate statistics
        Page<Order> allOrders = orderRepository.findAll(Pageable.unpaged());
        long totalOrders = allOrders.getTotalElements();

        // Count pending orders
        long pendingOrders = allOrders.getContent().stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                .count();

        // Calculate total revenue from delivered orders
        double totalRevenue = allOrders.getContent().stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getTotalAmount().getAmount())
                .sum();

        // Get user statistics
        Page<com.esia.big_shop_backend.domain.entity.User> allUsers = userRepository.findAll(Pageable.unpaged());
        long totalUsers = allUsers.getTotalElements();
        long activeUsers = allUsers.getContent().stream()
                .filter(com.esia.big_shop_backend.domain.entity.User::isActive)
                .count();

        // Get product statistics
        Page<com.esia.big_shop_backend.domain.entity.Product> allProducts = productRepository.findAll(Pageable.unpaged());
        long totalProducts = allProducts.getTotalElements();

        return new DashboardStatistics(
                totalOrders,
                totalUsers,
                totalProducts,
                totalRevenue,
                pendingOrders,
                activeUsers
        );
    }
}
