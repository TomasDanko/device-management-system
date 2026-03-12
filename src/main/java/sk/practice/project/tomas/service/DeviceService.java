package sk.practice.project.tomas.service;

import sk.practice.project.tomas.dto.DeviceDto;

import java.util.List;

public interface DeviceService {

    List<DeviceDto> getAllDevices();

    DeviceDto getDeviceById(Long id);

    DeviceDto createDevice(DeviceDto deviceDto);

    DeviceDto update(Long id, DeviceDto deviceDto);

    void deleteDevice(Long id);
}
