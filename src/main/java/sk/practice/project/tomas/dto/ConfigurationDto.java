package sk.practice.project.tomas.dto;

import java.time.LocalDateTime;

public class ConfigurationDto {

    private Long id;

    private Long deviceId;

    private String content;

    private String checksum;

    private LocalDateTime createdAt;

    public ConfigurationDto() {
    }

    public ConfigurationDto(Long id, Long deviceId, String content, String checksum, LocalDateTime createdAt) {
        this.id = id;
        this.deviceId = deviceId;
        this.content = content;
        this.checksum = checksum;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
