package sk.practice.project.tomas.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_reports")
public class ComplianceReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "compliant", nullable = false)
    private boolean compliant;

    @Lob
    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    public ComplianceReport() {
    }

    public ComplianceReport(Device device, boolean compliant, String details) {
        this.device = device;
        this.compliant = compliant;
        this.details = details;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Device getDevice() {
        return device;
    }

    public boolean isCompliant() {
        return compliant;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCompliant(boolean compliant) {
        this.compliant = compliant;
    }
}
