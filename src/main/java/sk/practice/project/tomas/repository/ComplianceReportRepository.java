package sk.practice.project.tomas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.practice.project.tomas.entity.ComplianceReport;

@Repository
public interface ComplianceReportRepository extends JpaRepository<ComplianceReport, Long> {
}
