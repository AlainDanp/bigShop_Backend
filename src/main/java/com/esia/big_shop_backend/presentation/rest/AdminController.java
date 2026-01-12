package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.admin.GetLowStockProductsUseCase;
import com.esia.big_shop_backend.application.usecase.admin.GetRevenueStatisticsUseCase;
import com.esia.big_shop_backend.application.usecase.admin.GetTopSellingProductsUseCase;
import com.esia.big_shop_backend.application.usecase.admin.GetUserGrowthStatisticsUseCase;
import com.esia.big_shop_backend.application.usecase.admin.result.RevenueStatistics;
import com.esia.big_shop_backend.application.usecase.admin.result.TopProductStatistic;
import com.esia.big_shop_backend.application.usecase.admin.result.UserGrowthStatistics;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.presentation.dto.response.product.ProductResponse;
import com.esia.big_shop_backend.presentation.mapper.ProductRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Administrative operations")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final GetRevenueStatisticsUseCase getRevenueStatisticsUseCase;
    private final GetTopSellingProductsUseCase getTopSellingProductsUseCase;
    private final GetUserGrowthStatisticsUseCase getUserGrowthStatisticsUseCase;
    private final GetLowStockProductsUseCase getLowStockProductsUseCase;
    private final ProductRestMapper productMapper;

    @GetMapping("/revenue")
    @Operation(summary = "Get revenue statistics")
    public ResponseEntity<RevenueStatistics> getRevenueStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        RevenueStatistics stats = getRevenueStatisticsUseCase.execute(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/top-products")
    @Operation(summary = "Get top selling products")
    public ResponseEntity<List<TopProductStatistic>> getTopProducts(
            @RequestParam(defaultValue = "10") int limit) {
        List<TopProductStatistic> stats = getTopSellingProductsUseCase.execute(limit);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/user-growth")
    @Operation(summary = "Get user growth statistics")
    public ResponseEntity<UserGrowthStatistics> getUserGrowthStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        UserGrowthStatistics stats = getUserGrowthStatisticsUseCase.execute(startDate, endDate);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock products")
    public ResponseEntity<List<ProductResponse>> getLowStockProducts(
            @RequestParam(defaultValue = "10") int threshold) {
        List<Product> products = getLowStockProductsUseCase.execute(threshold);
        return ResponseEntity.ok(products.stream().map(productMapper::toResponse).collect(Collectors.toList()));
    }
}
