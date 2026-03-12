package sk.practice.project.tomas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sk.practice.project.tomas.dto.ComplianceReportDto;
import sk.practice.project.tomas.service.ComplianceReportService;
import sk.practice.project.tomas.websocket.ComplianceEventPublisher;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComplianceReportController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ComplianceReportControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComplianceReportService complianceReportService;

    @MockBean
    private ComplianceEventPublisher eventPublisher;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllComplianceReports() throws Exception {
        ComplianceReportDto report = new ComplianceReportDto();
        report.setId(1L);
        report.setDeviceId(1L);
        report.setCompliant(true);
        report.setDetails("All passed");

        when(complianceReportService.getAll()).thenReturn(List.of(report));

        mockMvc.perform(get("/api/compliance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].deviceId").value(1))
                .andExpect(jsonPath("$[0].compliant").value(true))
                .andExpect(jsonPath("$[0].details").value("All passed"));
    }

    @Test
    void shouldCreateComplianceReport() throws Exception {
        ComplianceReportDto request = new ComplianceReportDto();
        request.setDeviceId(1L);
        request.setCompliant(true);
        request.setDetails("All passed");

        ComplianceReportDto response = new ComplianceReportDto();
        response.setId(1L);
        response.setDeviceId(1L);
        response.setCompliant(true);
        response.setDetails("All passed");

        when(complianceReportService.create(any(ComplianceReportDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/compliance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.deviceId").value(1))
                .andExpect(jsonPath("$.compliant").value(true))
                .andExpect(jsonPath("$.details").value("All passed"));

        verify(eventPublisher, times(1)).notifyComplianceResult(response);
    }

}
