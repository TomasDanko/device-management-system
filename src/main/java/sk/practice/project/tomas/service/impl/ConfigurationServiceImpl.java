package sk.practice.project.tomas.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sk.practice.project.tomas.dto.ConfigurationDto;
import sk.practice.project.tomas.entity.Configuration;
import sk.practice.project.tomas.entity.Device;
import sk.practice.project.tomas.exception.ResourceNotFoundException;
import sk.practice.project.tomas.mapper.ConfigurationMapper;
import sk.practice.project.tomas.repository.ConfigurationRepository;
import sk.practice.project.tomas.repository.DeviceRepository;
import sk.practice.project.tomas.service.ConfigurationService;
import sk.practice.project.tomas.websocket.ConfigurationEventPublisher;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    private final DeviceRepository deviceRepository;

    private final ConfigurationEventPublisher configurationEventPublisher;

    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository,
                                    DeviceRepository deviceRepository, ConfigurationEventPublisher configurationEventPublisher) {
        this.configurationRepository = configurationRepository;
        this.deviceRepository = deviceRepository;
        this.configurationEventPublisher = configurationEventPublisher;
    }

    @Override
    public ConfigurationDto create(ConfigurationDto dto) {
        Device device = deviceRepository.findById(dto.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        Configuration configuration = new Configuration(
                device,
                dto.getContent(),
                dto.getChecksum()
        );

        configurationRepository.save(configuration);
        configurationEventPublisher.notifyConfigChanged(device.getId());


        return ConfigurationMapper.toDto(configuration);
    }

    @Override
    public List<ConfigurationDto> getAllDevices() {
        return configurationRepository.findAll()
                .stream()
                .map(ConfigurationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigurationDto getDeviceById(Long id) {
        Configuration configuration = configurationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not found"));

        return ConfigurationMapper.toDto(configuration);
    }

    @Override
    public void deleteDevice(Long id) {
        configurationRepository.deleteById(id);
    }
}
