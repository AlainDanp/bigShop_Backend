package com.esia.big_shop_backend.application.usecase.admin;

import com.esia.big_shop_backend.application.usecase.admin.result.UserGrowthStatistics;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
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
public class GetUserGrowthStatisticsUseCase {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserGrowthStatistics execute() {
        List<User> allUsers = userRepository.findAll(0, Integer.MAX_VALUE);
        long totalUsers = allUsers.size();


        long activeUsers = allUsers.stream()
                .filter(User::isActive)
                .count();

        LocalDateTime now = LocalDateTime.now();

        // Count new users today
        long newUsersToday = allUsers.stream()
                .filter(user -> user.getCreatedAt().toLocalDate().equals(now.toLocalDate()))
                .count();

        // Count new users this week (last 7 days)
        long newUsersThisWeek = allUsers.stream()
                .filter(user -> user.getCreatedAt().isAfter(now.minusDays(7)))
                .count();

        // Count new users this month (last 30 days)
        long newUsersThisMonth = allUsers.stream()
                .filter(user -> user.getCreatedAt().isAfter(now.minusDays(30)))
                .count();

        // Group user growth by date for the last 30 days
        Map<LocalDate, Long> userGrowthByDate = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            LocalDate date = now.minusDays(i).toLocalDate();
            long newUsers = allUsers.stream()
                    .filter(user -> user.getCreatedAt().toLocalDate().equals(date))
                    .count();
            userGrowthByDate.put(date, newUsers);
        }

        return new UserGrowthStatistics(
                totalUsers,
                activeUsers,
                newUsersToday,
                newUsersThisWeek,
                newUsersThisMonth,
                userGrowthByDate
        );
    }

    public UserGrowthStatistics execute(LocalDate startDate, LocalDate endDate) {
        List<User> allUsers = userRepository.findAll(0, Integer.MAX_VALUE);

        long totalUsers = allUsers.size();

        long activeUsers = allUsers.stream()
                .filter(User::isActive)
                .count();

        // Count new users in the specified date range
        long newUsersInRange = allUsers.stream()
                .filter(user -> {
                    LocalDate createdDate = user.getCreatedAt().toLocalDate();
                    return (createdDate.isEqual(startDate) || createdDate.isAfter(startDate)) &&
                           (createdDate.isEqual(endDate) || createdDate.isBefore(endDate));
                })
                .count();

        // Group user growth by date within the specified range
        Map<LocalDate, Long> userGrowthByDate = new HashMap<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            LocalDate date = currentDate;
            long newUsers = allUsers.stream()
                    .filter(user -> user.getCreatedAt().toLocalDate().equals(date))
                    .count();
            userGrowthByDate.put(date, newUsers);
            currentDate = currentDate.plusDays(1);
        }

        return new UserGrowthStatistics(
                totalUsers,
                activeUsers,
                newUsersInRange,
                0,
                0,
                userGrowthByDate
        );
    }
}
