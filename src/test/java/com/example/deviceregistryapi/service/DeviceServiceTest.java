package com.example.deviceregistryapi.service;

import com.example.deviceregistryapi.dto.DeviceRequestDTO;
import com.example.deviceregistryapi.dto.DeviceResponseDTO;
import com.example.deviceregistryapi.model.Device;
import com.example.deviceregistryapi.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void addDevice_shouldSaveDeviceAndReturnResponseDTO() {
        DeviceRequestDTO deviceRequestDTO = new DeviceRequestDTO("Device TEST", "Brand TEST");

        LocalDateTime createdAt = LocalDateTime.now();
        Device saved = new Device("Device TEST", "Brand TEST");
        saved.setId(1L);
        saved.setCreatedAt(createdAt);

        when(deviceRepository.save(any(Device.class))).thenReturn(saved);

        DeviceResponseDTO responseDTO = deviceService.addDevice(deviceRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals("Device TEST", responseDTO.name());
        assertEquals("Brand TEST", responseDTO.brand());
        assertEquals(createdAt, responseDTO.createdAt());

        verify(deviceRepository, times(1)).save(any(Device.class));
    }
}