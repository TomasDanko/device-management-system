package sk.practice.project.tomas.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sk.practice.project.tomas.dto.DeviceDto;
import sk.practice.project.tomas.entity.Device;
import sk.practice.project.tomas.exception.ResourceNotFoundException;
import sk.practice.project.tomas.repository.DeviceRepository;
import sk.practice.project.tomas.service.impl.DeviceServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeviceServiceTests {

    @Autowired
    private DeviceServiceImpl deviceService;

    @MockBean
    private DeviceRepository deviceRepository;

    @Test
    public void getAllDevices() {
        Device device1 = new Device();
        device1.setId(1L);
        Device device2 = new Device();
        device2.setId(2L);

        when(deviceRepository.findAll()).thenReturn(Arrays.asList(device1, device2));

        List<DeviceDto> result = deviceService.getAllDevices();

        assertEquals(2, result.size());

    }

    @Test
    public void getDeviceById() {
        Device device = new Device();
        device.setId(1L);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        DeviceDto result = deviceService.getDeviceById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

    }

    @Test
    public void getDeviceByIdWhenNotFound() {
        when(deviceRepository.findById(89L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> deviceService.getDeviceById(99L));
    }

    @Test
    public void createDevice() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(1L);

        Device device = new Device();
        device.setId(1L);

        when(deviceRepository.save(Mockito.any(Device.class))).thenReturn(device);

        DeviceDto result = deviceService.createDevice(deviceDto);

        assertNotNull(result);
        assertEquals(deviceDto.getId(), result.getId());
        verify(deviceRepository, times(1)).save(ArgumentMatchers.any(Device.class));
    }

    @Test
    public void updateExistingDevice() {
        Device existingDevice = new Device();
        existingDevice.setId(1L);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));

        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setHostname("New Hostname");

        DeviceDto result = deviceService.update(1L, deviceDto);

        assertNotNull(result);
        assertEquals("New Hostname", result.getHostname());
        verify(deviceRepository, never()).save(ArgumentMatchers.any());
    }

    @Test
    public void UpdateDeviceWhenNotFound() {
        when(deviceRepository.findById(89L)).thenReturn(Optional.empty());
        DeviceDto deviceDto = new DeviceDto();

        assertThrows(ResourceNotFoundException.class, () -> deviceService.update(89L, deviceDto));
    }

    @Test
    public void DeleteDeviceIfExist() {
        Device device = new Device();
        device.setId(1L);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        deviceService.deleteDevice(1L);

        verify(deviceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteDeviceWhenNotFound() {
        when(deviceRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deviceService.deleteDevice(99L));
        verify(deviceRepository, never()).deleteById(99L);
    }


}
