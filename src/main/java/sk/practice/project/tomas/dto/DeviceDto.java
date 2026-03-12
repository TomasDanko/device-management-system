package sk.practice.project.tomas.dto;

import sk.practice.project.tomas.entity.Vendor;

import java.time.LocalDateTime;

public class DeviceDto {

    private Long id;

    private String hostname;

    private String ipAddress;

    private Vendor vendor;

    private LocalDateTime createdAt;

    public DeviceDto() {
    }

    public DeviceDto(Long id, String hostname, String ipAddress, Vendor vendor, LocalDateTime createdAt) {
        this.id = id;
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.vendor = vendor;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
