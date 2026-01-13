package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.admin.GetDashboardStatisticsUseCase;
import com.esia.big_shop_backend.application.usecase.admin.result.DashboardStatistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@Tag(name = "Admin Dashboard", description = "Admin dashboard statistics")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final GetDashboardStatisticsUseCase getDashboardStatisticsUseCase;

    @GetMapping("/stats")
    @Operation(summary = "Get dashboard statistics")
    public ResponseEntity<DashboardStatistics> getDashboardStats() {
        DashboardStatistics stats = getDashboardStatisticsUseCase.execute();
        return ResponseEntity.ok(stats);
    }
}
