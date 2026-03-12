package sk.practice.project.tomas.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import sk.practice.project.tomas.dto.ComplianceReportDto;

@Component
public class ComplianceEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public ComplianceEventPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyComplianceResult(ComplianceReportDto dto) {
        messagingTemplate.convertAndSend(
                "/topic/compliance",
                dto
        );
    }
}
