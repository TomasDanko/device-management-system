package sk.practice.project.tomas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sk.practice.project.tomas.dto.ConfigurationDto;
import sk.practice.project.tomas.service.ConfigurationService;
import sk.practice.project.tomas.websocket.ConfigurationEventPublisher;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConfigurationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ConfigurationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigurationService configurationService;

    @MockBean
    private ConfigurationEventPublisher eventPublisher;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateConfiguration() throws Exception {
        ConfigurationDto request = new ConfigurationDto();
        request.setDeviceId(1L);
        request.setContent("Test config");

        ConfigurationDto response = new ConfigurationDto();
        response.setDeviceId(1L);
        response.setContent("Test config");
        response.setId(1L);

        when(configurationService.create(any(ConfigurationDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/configurations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.deviceId").value(1))
                .andExpect(jsonPath("$.content").value("Test config"));

        verify(eventPublisher, times(1)).notifyConfigChanged(1L);
    }

    @Test
    void shouldReturnAllConfigurations() throws Exception {
        ConfigurationDto config = new ConfigurationDto();
        config.setId(1L);
        config.setDeviceId(1L);
        config.setContent("Test config");

        when(configurationService.getAllDevices()).thenReturn(List.of(config));

        mockMvc.perform(get("/api/configurations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].deviceId").value(1))
                .andExpect(jsonPath("$[0].content").value("Test config"));
    }

    @Test
    void shouldReturnConfigurationById() throws Exception {
        ConfigurationDto config = new ConfigurationDto();
        config.setId(1L);
        config.setDeviceId(1L);
        config.setContent("Test config");

        when(configurationService.getDeviceById(1L)).thenReturn(config);

        mockMvc.perform(get("/api/configurations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.deviceId").value(1))
                .andExpect(jsonPath("$.content").value("Test config"));
    }

    @Test
    void shouldDeleteConfiguration() throws Exception {
        mockMvc.perform(delete("/api/configurations/1"))
                .andExpect(status().isOk());

        verify(configurationService, times(1)).deleteDevice(1L);
    }
}
