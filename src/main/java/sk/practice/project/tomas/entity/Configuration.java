package sk.practice.project.tomas.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "configurations")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "check_sum", nullable = false)
    private String checksum;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;


    public Configuration() {
    }

    public Configuration(Device device, String content, String checksum) {
        this.device = device;
        this.content = content;
        this.checksum = checksum;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Device getDevice() {
        return device;
    }

    public String getContent() {
        return content;
    }

    public String getChecksum() {
        return checksum;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
