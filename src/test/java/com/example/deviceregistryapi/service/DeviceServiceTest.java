package com.example.deviceregistryapi.service;

import com.example.deviceregistryapi.dto.DeviceRequestDTO;
import com.example.deviceregistryapi.dto.DeviceResponseDTO;
import com.example.deviceregistryapi.exception.ResourceNotFoundException;
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

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> deviceService.getDeviceById(deviceId));
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

    @Test
    void updateDevice_shouldUpdateAndReturnDeviceResponseDTO_whenDeviceExists() {
        Long deviceId = 1L;
        DeviceRequestDTO requestDTO = new DeviceRequestDTO("Updated Device Name", "Updated Brand");

        Device existingDevice = new Device("Old Device Name", "Old Brand");
        existingDevice.setId(deviceId);
        existingDevice.setCreatedAt(LocalDateTime.now());

        Device updatedDevice = new Device("Updated Device Name", "Updated Brand");
        updatedDevice.setId(deviceId);
        updatedDevice.setCreatedAt(existingDevice.getCreatedAt());

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.save(existingDevice)).thenReturn(updatedDevice);

        DeviceResponseDTO result = deviceService.updateDevice(deviceId, requestDTO);

        assertNotNull(result);
        assertEquals(deviceId, result.id());
        assertEquals("Updated Device Name", result.name());
        assertEquals("Updated Brand", result.brand());
        assertEquals(existingDevice.getCreatedAt(), result.createdAt());

        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(1)).save(existingDevice);
    }

    @Test
    void updateDevice_shouldThrowResourceNotFoundException_whenDeviceDoesNotExist() {
        Long deviceId = 1L;
        DeviceRequestDTO requestDTO = new DeviceRequestDTO("Updated Device Name", "Updated Brand");

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                deviceService.updateDevice(deviceId, requestDTO));
        assertEquals("Device with id 1 not found", exception.getMessage());

        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, never()).save(any());
    }

    @Test
    void deleteDevice_shouldCallDeleteById() {
        Long deviceId = 1L;

        deviceService.deleteDevice(deviceId);

        verify(deviceRepository, times(1)).deleteById(deviceId);
    }

    @Test
    void getDevicesByBrand_shouldReturnPageOfDeviceResponseDTOs() {
        String brand = "Brand TEST";
        Pageable pageable = PageRequest.of(0, 10);
        Device device1 = new Device("Device TEST1", "Brand TEST1");
        device1.setId(1L);
        device1.setCreatedAt(LocalDateTime.now());

        Device device2 = new Device("Device TEST2", "Brand TEST1");
        device2.setId(2L);
        device2.setCreatedAt(LocalDateTime.now());

        List<Device> devices = List.of(device1, device2);
        Page<Device> devicePage = new PageImpl<>(devices, pageable, devices.size());

        when(deviceRepository.findAllByBrand(brand, pageable)).thenReturn(devicePage);

        Page<DeviceResponseDTO> result = deviceService.getDevicesByBrand(brand, pageable);

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
        assertEquals("Brand TEST1", responseDTO2.brand());
        assertNotNull(responseDTO2.createdAt());

        verify(deviceRepository, times(1)).findAllByBrand(brand, pageable);
    }

    @Test
    void partialUpdateDevice_shouldUpdateNonNullFields_whenDeviceExists() {
        Long deviceId = 1L;
        DeviceRequestDTO requestDTO = new DeviceRequestDTO("Updated Device Name", null);

        Device existingDevice = new Device("Old Device Name", "Existing Brand");
        existingDevice.setId(deviceId);
        existingDevice.setCreatedAt(LocalDateTime.now());

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.save(existingDevice)).thenReturn(existingDevice);

        DeviceResponseDTO result = deviceService.partialUpdateDevice(deviceId, requestDTO);

        assertNotNull(result);
        assertEquals(deviceId, result.id());
        assertEquals("Updated Device Name", result.name());
        assertEquals("Existing Brand", result.brand());
        assertNotNull(result.createdAt());

        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(1)).save(existingDevice);
    }

    @Test
    void partialUpdateDevice_shouldThrowResourceNotFoundException_whenDeviceDoesNotExist() {
        Long deviceId = 1L;
        DeviceRequestDTO requestDTO = new DeviceRequestDTO("Updated Device Name", "Updated Brand");

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                deviceService.partialUpdateDevice(deviceId, requestDTO));
        assertEquals("Device with id 1 not found", exception.getMessage());

        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, never()).save(any());
    }
}