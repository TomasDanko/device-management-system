package sk.practice.project.tomas.dto;

import java.time.LocalDateTime;

public class ComplianceReportDto {

    private Long id;

    private Long deviceId;

    private boolean compliant;

    private String details;

    private LocalDateTime createdAt;

    public ComplianceReportDto() {
    }

    public ComplianceReportDto(Long id, Long deviceId, boolean compliant, String details, LocalDateTime createdAt) {
        this.id = id;
        this.deviceId = deviceId;
        this.compliant = compliant;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isCompliant() {
        return compliant;
    }

    public void setCompliant(boolean compliant) {
        this.compliant = compliant;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
