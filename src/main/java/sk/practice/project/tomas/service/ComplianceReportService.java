package sk.practice.project.tomas.service;

import sk.practice.project.tomas.dto.ComplianceReportDto;

import java.util.List;

public interface ComplianceReportService {

    ComplianceReportDto create(ComplianceReportDto dto);

    List<ComplianceReportDto> getAll();
}
