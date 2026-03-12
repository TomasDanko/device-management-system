package sk.practice.project.tomas.service;

import sk.practice.project.tomas.dto.ConfigurationDto;

import java.util.List;

public interface ConfigurationService {

    ConfigurationDto create(ConfigurationDto dto);

    List<ConfigurationDto> getAllDevices();

    ConfigurationDto getDeviceById(Long id);

    void deleteDevice(Long id);
}
