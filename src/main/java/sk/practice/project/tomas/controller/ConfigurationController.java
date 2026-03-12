package sk.practice.project.tomas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import sk.practice.project.tomas.dto.ConfigurationDto;
import sk.practice.project.tomas.service.ConfigurationService;
import sk.practice.project.tomas.websocket.ConfigurationEventPublisher;

import java.util.List;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    private final ConfigurationEventPublisher eventPublisher;

    public ConfigurationController(ConfigurationService configurationService,
                                   ConfigurationEventPublisher eventPublisher) {
        this.configurationService = configurationService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<ConfigurationDto> create(@RequestBody ConfigurationDto dto) {
        ConfigurationDto created = configurationService.create(dto);

        // 🔔 WebSocket NOTIFIKÁCIA
        eventPublisher.notifyConfigChanged(created.getDeviceId());

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @MessageMapping("/configuration/create")
    @SendTo("/topic/config-changed")
    public ConfigurationDto create_WS(ConfigurationDto configurationDto) {
        ConfigurationDto created = configurationService.create(configurationDto);
        eventPublisher.notifyConfigChanged(created.getDeviceId());
        return created;
    }

    @GetMapping
    public List<ConfigurationDto> getAll() {
        return configurationService.getAllDevices();
    }

    @GetMapping("/{id}")
    public ConfigurationDto getById(@PathVariable Long id) {
        return configurationService.getDeviceById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        configurationService.deleteDevice(id);
    }
}
