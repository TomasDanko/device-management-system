package sk.practice.project.tomas.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sk.practice.project.tomas.dto.ComplianceReportDto;
import sk.practice.project.tomas.entity.ComplianceReport;
import sk.practice.project.tomas.entity.Device;
import sk.practice.project.tomas.exception.ResourceNotFoundException;
import sk.practice.project.tomas.mapper.ComplianceReportMapper;
import sk.practice.project.tomas.repository.ComplianceReportRepository;
import sk.practice.project.tomas.repository.DeviceRepository;
import sk.practice.project.tomas.service.ComplianceReportService;
import sk.practice.project.tomas.websocket.ComplianceEventPublisher;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComplianceReportServiceImpl implements ComplianceReportService {

    private final ComplianceReportRepository complianceReportRepository;

    private final DeviceRepository deviceRepository;

    private final ComplianceEventPublisher complianceEventPublisher;

    public ComplianceReportServiceImpl(ComplianceReportRepository complianceReportRepository, DeviceRepository deviceRepository, ComplianceEventPublisher complianceEventPublisher) {
        this.complianceReportRepository = complianceReportRepository;
        this.deviceRepository = deviceRepository;
        this.complianceEventPublisher = complianceEventPublisher;
    }

    @Override
    public ComplianceReportDto create(ComplianceReportDto dto) {
        Device device = deviceRepository.findById(dto.getDeviceId()).orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        ComplianceReport report = new ComplianceReport(device, dto.isCompliant(), dto.getDetails());
        complianceReportRepository.save(report);

        ComplianceReportDto createdDto = ComplianceReportMapper.toDto(report);

        complianceEventPublisher.notifyComplianceResult(createdDto);

        return createdDto;
    }

    @Override
    public List<ComplianceReportDto> getAll() {
        return complianceReportRepository.findAll()
                .stream()
                .map(ComplianceReportMapper::toDto)
                .collect(Collectors.toList());
    }
}
