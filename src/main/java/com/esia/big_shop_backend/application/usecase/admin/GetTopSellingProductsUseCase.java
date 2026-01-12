package com.esia.big_shop_backend.application.usecase.admin;

import com.esia.big_shop_backend.application.usecase.admin.query.GetTopSellingProductsQuery;
import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.valueobject.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetTopSellingProductsUseCase {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<TopProductStatistic> execute(GetTopSellingProductsQuery query) {
        // Get all delivered orders
        List<Order> allOrders = orderRepository.findAll(0, Integer.MAX_VALUE);
        List<Order> deliveredOrders = allOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .collect(Collectors.toList());

        // Aggregate product sales
        Map<Long, ProductSalesData> productSalesMap = new HashMap<>();

        for (Order order : deliveredOrders) {
            for (OrderItem item : order.getItems()) {
                Long productId = item.getProductId().getValue();
                productSalesMap.putIfAbsent(productId, new ProductSalesData(item.getProductName()));

                ProductSalesData data = productSalesMap.get(productId);
                data.totalSold += item.getQuantity();
                data.revenue += item.getSubtotal();
            }
        }

        // Convert to list and sort by total sold
        return productSalesMap.entrySet().stream()
                .map(entry -> new TopProductStatistic(
                        entry.getKey(),
                        entry.getValue().productName,
                        entry.getValue().totalSold,
                        entry.getValue().revenue
                ))
                .sorted((a, b) -> Integer.compare(b.getTotalSold(), a.getTotalSold()))
                .limit(query.getLimit())
                .collect(Collectors.toList());
    }

    private static class ProductSalesData {
        String productName;
        int totalSold = 0;
        double revenue = 0.0;

        ProductSalesData(String productName) {
            this.productName = productName;
        }
    }
}
