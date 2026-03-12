package sk.practice.project.tomas.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hostname", nullable = false, unique = true)
    @NotNull
    private String hostname;

    @Column(name = "ip_address", nullable = false, unique = true)
    @NotNull
    private String ipAddress;

    @Column(name = "vendor", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Vendor vendor;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Configuration> configurations = new ArrayList<>();

    public Device() {
    }

    public Device(String hostname, String ipAddress, Vendor vendor) {
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.vendor = vendor;
        this.createdAt = LocalDateTime.now();

    }

    public Long getId() {
        return id;
    }

    public String getHostname() {
        return hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void addConfiguration(Configuration configuration) {
        configurations.add(configuration);
        configuration.setDevice(this);
    }


}
