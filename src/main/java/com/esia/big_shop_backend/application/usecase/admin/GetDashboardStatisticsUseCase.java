package com.esia.big_shop_backend.application.usecase.admin;

import com.esia.big_shop_backend.application.usecase.admin.result.DashboardStatistics;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetDashboardStatisticsUseCase {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public DashboardStatistics execute() {
        List<Order> allOrders = orderRepository.findAll(0, Integer.MAX_VALUE);
        long totalOrders = allOrders.size();

        long pendingOrders = allOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                .count();

        double totalRevenue = allOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getTotalAmount())
                .sum();

        List<User> allUsers = userRepository.findAll(0, Integer.MAX_VALUE);
        long totalUsers = allUsers.size();
        long activeUsers = allUsers.stream()
                .filter(User::isActive)
                .count();

        List<Product> allProducts = productRepository.findAll(0, Integer.MAX_VALUE);
        long totalProducts = allProducts.size();

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
