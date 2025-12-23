package com.example.analyticsservice.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.analyticsservice.entities.AccountAnalytics;

public interface AccountAnalyticsRepository extends JpaRepository<AccountAnalytics, Long> {
    AccountAnalytics findByAccountId(String accountId);
}
