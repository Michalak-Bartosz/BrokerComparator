package org.message.comparator.repository.dashboard;

import org.message.comparator.entity.auth.DashboardUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DashboardUserRepository extends JpaRepository<DashboardUser, Long> {
    Optional<DashboardUser> findByUsername(String username);
}
