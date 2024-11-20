package com.example.deviceregistryapi.integration;

import com.example.deviceregistryapi.dto.DeviceRequestDTO;
import com.example.deviceregistryapi.model.Device;
import com.example.deviceregistryapi.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll();
    }

    @Test
    void shouldCreateDevice() throws Exception {
        DeviceRequestDTO request = new DeviceRequestDTO("iPhone 16", "Apple");
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("iPhone 16"))
                .andExpect(jsonPath("$.brand").value("Apple"));
    }

    @Test
    void shouldRetrieveDeviceById() throws Exception {
        Device device = deviceRepository.save(new Device("iPhone 16", "Apple"));

        mockMvc.perform(get("/api/v1/devices/{id}", device.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(device.getId()))
                .andExpect(jsonPath("$.name").value("iPhone 16"))
                .andExpect(jsonPath("$.brand").value("Apple"));
    }

    @Test
    void shouldListAllDevices() throws Exception {
        deviceRepository.save(new Device("iPhone 16", "Apple"));
        deviceRepository.save(new Device("Galaxy S24", "Samsung"));

        mockMvc.perform(get("/api/v1/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name").value("iPhone 16"))
                .andExpect(jsonPath("$.content[1].name").value("Galaxy S24"));
    }

    @Test
    void shouldUpdateDevice() throws Exception {
        Device device = deviceRepository.save(new Device("iPhone 16", "Apple"));
        DeviceRequestDTO updateRequest = new DeviceRequestDTO("iPhone 16 Pro", "Apple");
        String requestBody = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/api/v1/devices/{id}", device.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("iPhone 16 Pro"));
    }

    @Test
    void shouldPartiallyUpdateDevice() throws Exception {
        Device device = deviceRepository.save(new Device("iPhone 16", "Apple"));

        DeviceRequestDTO patchRequest = new DeviceRequestDTO("iPhone 16 Pro", null);
        String patchRequestBody = objectMapper.writeValueAsString(patchRequest);

        mockMvc.perform(patch("/api/v1/devices/{id}", device.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("iPhone 16 Pro"))
                .andExpect(jsonPath("$.brand").value("Apple"));
    }

    @Test
    void shouldDeleteDevice() throws Exception {
        Device device = deviceRepository.save(new Device("iPhone 16", "Apple"));

        mockMvc.perform(delete("/api/v1/devices/{id}", device.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/devices/{id}", device.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeviceDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/devices/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Device with id 1 not found"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Apple", "Samsung"})
    void shouldSearchDevicesByBrand(String brand) throws Exception {
        deviceRepository.save(new Device("Device 1", brand));

        mockMvc.perform(get("/api/v1/devices/search")
                        .param("brand", brand))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value(brand));
    }
}
