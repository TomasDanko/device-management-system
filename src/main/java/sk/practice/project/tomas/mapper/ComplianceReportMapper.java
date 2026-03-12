package sk.practice.project.tomas.mapper;

import sk.practice.project.tomas.dto.ComplianceReportDto;
import sk.practice.project.tomas.entity.ComplianceReport;

public class ComplianceReportMapper {

    public static ComplianceReportDto toDto(ComplianceReport complianceReport) {
        if (complianceReport == null) {
            return null;
        } else {
            return new ComplianceReportDto(
                    complianceReport.getId(),
                    complianceReport.getDevice().getId(),
                    complianceReport.isCompliant(),
                    complianceReport.getDetails(),
                    complianceReport.getCreatedAt()
            );
        }
    }
}
