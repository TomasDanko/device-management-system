package sk.practice.project.tomas.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sk.practice.project.tomas.dto.ComplianceReportDto;
import sk.practice.project.tomas.entity.ComplianceReport;
import sk.practice.project.tomas.entity.Device;
import sk.practice.project.tomas.exception.ResourceNotFoundException;
import sk.practice.project.tomas.repository.ComplianceReportRepository;
import sk.practice.project.tomas.repository.DeviceRepository;
import sk.practice.project.tomas.service.impl.ComplianceReportServiceImpl;
import sk.practice.project.tomas.websocket.ComplianceEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComplianceReportServiceTests {

    @Mock
    @Autowired
    private ComplianceReportRepository complianceReportRepository;

    @Mock
    @Autowired
    private DeviceRepository deviceRepository;

    @Mock
    private ComplianceEventPublisher eventPublisher;

    @InjectMocks
    @Autowired
    private ComplianceReportServiceImpl complianceReportService;

    @Test
    void createComplianceReportAndPublishEvent(){

        Device device = new Device();
        device.setHostname("Router -1");

        ComplianceReportDto dto = new ComplianceReportDto();
        dto.setDeviceId(1L);
        dto.setCompliant(true);
        dto.setDetails("All passed");

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(complianceReportRepository.save(any(ComplianceReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ComplianceReportDto result = complianceReportService.create(dto);

        assertNotNull(result);
        assertTrue(result.isCompliant());
        assertEquals("All passed", result.getDetails());

        verify(complianceReportRepository).save(any(ComplianceReport.class));
        verify(eventPublisher).notifyComplianceResult(any(ComplianceReportDto.class));
    }

    @Test
    void shouldThrowExceptionWhenDeviceNotFound() {
        // given
        ComplianceReportDto dto = new ComplianceReportDto();
        dto.setDeviceId(99L);

        when(deviceRepository.findById(99L)).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> complianceReportService.create(dto));

        verify(complianceReportRepository, never()).save(any());
        verify(eventPublisher, never()).notifyComplianceResult(any());
    }

    // ✅ GET ALL
    @Test
    void shouldReturnAllComplianceReports() {
        // given
        Device device = new Device();
        device.setId(1L);

        ComplianceReport report1 = new ComplianceReport(device, true, "OK");
        ComplianceReport report2 = new ComplianceReport(device, false, "Mismatch");

        when(complianceReportRepository.findAll())
                .thenReturn(List.of(report1, report2));

        // when
        List<ComplianceReportDto> result = complianceReportService.getAll();

        // then
        assertEquals(2, result.size());
        assertTrue(result.get(0).isCompliant());
        assertFalse(result.get(1).isCompliant());
    }
}
