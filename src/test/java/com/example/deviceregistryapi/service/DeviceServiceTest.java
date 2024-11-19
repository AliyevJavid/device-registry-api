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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

        Device saved = getDevice();

        when(deviceRepository.save(any(Device.class))).thenReturn(saved);

        DeviceResponseDTO responseDTO = deviceService.addDevice(deviceRequestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.id());
        assertEquals("Device TEST", responseDTO.name());
        assertEquals("Brand TEST", responseDTO.brand());
        assertNotNull(responseDTO.createdAt());

        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    private static Device getDevice() {
        Device device = new Device("Device TEST", "Brand TEST");
        device.setId(1L);
        device.setCreatedAt(LocalDateTime.now());
        return device;
    }

    @Test
    void getDeviceById_shouldReturnDeviceResponseDTO_whenDeviceExists() {
        Long deviceId = 1L;
        Device device = getDevice();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        DeviceResponseDTO responseDTO = deviceService.getDeviceById(deviceId);

        assertNotNull(responseDTO);
        assertEquals(deviceId, responseDTO.id());
        assertEquals("Device TEST", responseDTO.name());
        assertEquals("Brand TEST", responseDTO.brand());
        assertNotNull(responseDTO.createdAt());

        verify(deviceRepository, times(1)).findById(deviceId);
    }

    @Test
    void getDeviceById_shouldThrowException_whenDeviceDoesNotExist() {
        Long deviceId = 1L;
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deviceService.getDeviceById(deviceId));
        assertEquals("Device with id 1 not found", exception.getMessage());

        verify(deviceRepository, times(1)).findById(deviceId);
    }
}