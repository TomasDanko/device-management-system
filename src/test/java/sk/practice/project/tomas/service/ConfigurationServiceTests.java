package sk.practice.project.tomas.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import sk.practice.project.tomas.dto.ConfigurationDto;
import sk.practice.project.tomas.entity.Configuration;
import sk.practice.project.tomas.entity.Device;
import sk.practice.project.tomas.exception.ResourceNotFoundException;
import sk.practice.project.tomas.repository.ConfigurationRepository;
import sk.practice.project.tomas.repository.DeviceRepository;
import sk.practice.project.tomas.service.impl.ConfigurationServiceImpl;
import sk.practice.project.tomas.websocket.ConfigurationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ConfigurationServiceTests {

    @Mock
    private ConfigurationRepository configurationRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private ConfigurationEventPublisher configurationEventPublisher;

    @InjectMocks
    private ConfigurationServiceImpl configurationServiceImpl;

    @Test
    public void createConfiguration() {

        Device device = new Device();
        device.setId(1L);

        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setId(1L);
        configurationDto.setContent("router config");
        configurationDto.setChecksum("abcd1234");
        configurationDto.setDeviceId(1L);

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(configurationRepository.save(any(Configuration.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ConfigurationDto result = configurationServiceImpl.create(configurationDto);

        assertNotNull(result);
        assertEquals("router config", result.getContent());
        assertEquals("abcd1234", result.getChecksum());

        verify(deviceRepository).findById(1L);
        verify(configurationRepository).save(any(Configuration.class));
        verify(configurationEventPublisher).notifyConfigChanged(1L);
    }

    @Test
    public void createConfigurationWhenDeviceNotFound() {

        ConfigurationDto configurationDto = new ConfigurationDto();
        configurationDto.setDeviceId(99L);

        when(deviceRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> configurationServiceImpl.create(configurationDto));

        verify(deviceRepository).findById(99L);
        verify(configurationRepository, never()).save(any());
    }

    @Test
    public void getAllDevices() {

        Device device = new Device();
        device.setId(1L);

        Configuration configuration = new Configuration(device, "conf test", "checksum");

        when(configurationRepository.findAll()).thenReturn(List.of(configuration));
        List<ConfigurationDto> result = configurationServiceImpl.getAllDevices();
        assertEquals(1, result.size());
        verify(configurationRepository).findAll();
    }

    @Test
    public void getDeviceById_shouldReturnConfiguration() {

        Device device = new Device();
        device.setId(1L);

        Configuration configuration = new Configuration(device, "config", "checksum");

        when(configurationRepository.findById(1L)).thenReturn(Optional.of(configuration));

        ConfigurationDto result = configurationServiceImpl.getDeviceById(1L);

        assertNotNull(result);

        verify(configurationRepository).findById(1L);
    }

    @Test
    public void getDeviceById_shouldThrowException_whenConfigurationNotFound() {

        when(configurationRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> configurationServiceImpl.getDeviceById(5L));

        verify(configurationRepository).findById(5L);
    }

    @Test
    public void deleteDevice_shouldCallRepository() {

        configurationServiceImpl.deleteDevice(1L);

        verify(configurationRepository).deleteById(1L);
    }
}
