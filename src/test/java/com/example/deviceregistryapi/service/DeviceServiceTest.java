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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
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

    @Test
    void listAllDevices_shouldReturnPageOfDeviceResponseDTOs() {
        Pageable pageable = PageRequest.of(0, 10);
        Device device1 = new Device("Device TEST1", "Brand TEST1");
        device1.setId(1L);
        device1.setCreatedAt(LocalDateTime.now());

        Device device2 = new Device("Device TEST2", "Brand TEST2");
        device2.setId(2L);
        device2.setCreatedAt(LocalDateTime.now());

        List<Device> devices = List.of(device1, device2);
        Page<Device> devicePage = new PageImpl<>(devices, pageable, devices.size());

        when(deviceRepository.findAll(pageable)).thenReturn(devicePage);

        Page<DeviceResponseDTO> result = deviceService.listAllDevices(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());

        DeviceResponseDTO responseDTO1 = result.getContent().get(0);
        assertEquals(1L, responseDTO1.id());
        assertEquals("Device TEST1", responseDTO1.name());
        assertEquals("Brand TEST1", responseDTO1.brand());
        assertNotNull(responseDTO1.createdAt());

        DeviceResponseDTO responseDTO2 = result.getContent().get(1);
        assertEquals(2L, responseDTO2.id());
        assertEquals("Device TEST2", responseDTO2.name());
        assertEquals("Brand TEST2", responseDTO2.brand());
        assertNotNull(responseDTO2.createdAt());

        verify(deviceRepository, times(1)).findAll(pageable);
    }
}