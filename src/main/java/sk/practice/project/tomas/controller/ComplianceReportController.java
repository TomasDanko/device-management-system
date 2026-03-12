package sk.practice.project.tomas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import sk.practice.project.tomas.dto.ComplianceReportDto;
import sk.practice.project.tomas.service.ComplianceReportService;
import sk.practice.project.tomas.websocket.ComplianceEventPublisher;

import java.util.List;

@RestController
@RequestMapping("api/compliance")
public class ComplianceReportController {

    private final ComplianceReportService complianceReportService;

    private final ComplianceEventPublisher eventPublisher;

    public ComplianceReportController(ComplianceReportService complianceReportService, ComplianceEventPublisher eventPublisher) {
        this.complianceReportService = complianceReportService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public List<ComplianceReportDto> getAll() {
        return complianceReportService.getAll();
    }

    @MessageMapping("/compliance/create")
    @SendTo("/topic/compliance")
    public ComplianceReportDto create_WS(ComplianceReportDto complianceReportDto) {
        ComplianceReportDto created = complianceReportService.create(complianceReportDto);
        eventPublisher.notifyComplianceResult(created);
        return created;

    }

    @PostMapping
    public ResponseEntity<ComplianceReportDto> create(@RequestBody ComplianceReportDto dto) {
        ComplianceReportDto created = complianceReportService.create(dto);

        // 🔔 WebSocket NOTIFIKÁCIA
        eventPublisher.notifyComplianceResult(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
