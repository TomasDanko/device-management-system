package sk.practice.project.tomas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sk.practice.project.tomas.dto.DeviceDto;
import sk.practice.project.tomas.service.DeviceService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DeviceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllDevices() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setId(1L);
        device.setHostname("Router-1");

        when(deviceService.getAllDevices())
                .thenReturn(List.of(device));

        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].hostname").value("Router-1"));
    }

    @Test
    void shouldReturnDeviceById() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setId(1L);
        device.setHostname("Router-1");

        when(deviceService.getDeviceById(1L))
                .thenReturn(device);

        mockMvc.perform(get("/api/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.hostname").value("Router-1"));
    }

    @Test
    void shouldCreateDevice() throws Exception {
        DeviceDto request = new DeviceDto();
        request.setHostname("Router-1");

        DeviceDto response = new DeviceDto();
        response.setId(1L);
        response.setHostname("Router-1");

        when(deviceService.createDevice(any(DeviceDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.hostname").value("Router-1"));
    }

    @Test
    void shouldUpdateDevice() throws Exception {
        DeviceDto request = new DeviceDto();
        request.setHostname("Router-updated");

        DeviceDto response = new DeviceDto();
        response.setId(1L);
        response.setHostname("Router-updated");

        when(deviceService.update(eq(1L), any(DeviceDto.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/devices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hostname").value("Router-updated"));
    }

    @Test
    void shouldDeleteDevice() throws Exception {
        mockMvc.perform(delete("/api/devices/1"))
                .andExpect(status().isOk());

        verify(deviceService).deleteDevice(1L);
    }
}
