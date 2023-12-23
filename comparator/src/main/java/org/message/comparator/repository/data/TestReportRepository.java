package org.message.comparator.repository.data;

import org.message.comparator.entity.data.TestReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestReportRepository extends JpaRepository<TestReport, UUID> {
}
