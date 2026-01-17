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
@RequestMapping("/admin")
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

}
