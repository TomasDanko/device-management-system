package sk.practice.project.tomas.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.practice.project.tomas.dto.DeviceDto;
import sk.practice.project.tomas.entity.Device;
import sk.practice.project.tomas.exception.ResourceNotFoundException;
import sk.practice.project.tomas.mapper.DeviceMapper;
import sk.practice.project.tomas.repository.DeviceRepository;
import sk.practice.project.tomas.service.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    @Override
    public List<DeviceDto> getAllDevices() {
        return deviceRepository.findAll()
                .stream()
                .map(DeviceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDto getDeviceById(Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        return DeviceMapper.toDto(device);
    }

    @Override
    public DeviceDto createDevice(DeviceDto deviceDto) {
        Device device = DeviceMapper.toEntity(deviceDto);
        Device saved = deviceRepository.save(device);
        return DeviceMapper.toDto(saved);
    }

    @Override
    public DeviceDto update(Long id, DeviceDto deviceDto) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        DeviceMapper.updateEntity(device, deviceDto);
        return DeviceMapper.toDto(device);

    }

    @Override
    public void deleteDevice(Long id) {
        deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        deviceRepository.deleteById(id);


    }
}
