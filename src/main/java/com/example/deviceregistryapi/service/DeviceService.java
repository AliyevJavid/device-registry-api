package com.example.deviceregistryapi.service;

import com.example.deviceregistryapi.dto.DeviceRequestDTO;
import com.example.deviceregistryapi.dto.DeviceResponseDTO;
import com.example.deviceregistryapi.model.Device;
import com.example.deviceregistryapi.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceResponseDTO addDevice(DeviceRequestDTO deviceRequestDTO) {
        Device saved = deviceRepository.save(new Device(deviceRequestDTO.name(), deviceRequestDTO.brand()));
        return new DeviceResponseDTO(saved.getId(), saved.getName(), saved.getBrand(), saved.getCreatedAt());
    }

    public DeviceResponseDTO getDeviceById(Long id) {
        Device device = deviceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Device with id %d not found", id)));
        return new DeviceResponseDTO(device.getId(), device.getName(), device.getBrand(), device.getCreatedAt());
    }

    public Page<DeviceResponseDTO> listAllDevices(Pageable pageable) {
        return deviceRepository
                .findAll(pageable)
                .map(device -> new DeviceResponseDTO(
                        device.getId(),
                        device.getName(),
                        device.getBrand(),
                        device.getCreatedAt()));
    }

    public DeviceResponseDTO updateDevice(Long id, DeviceRequestDTO deviceRequestDTO) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Device with id %d not found", id)));

        device.setName(deviceRequestDTO.name());
        device.setBrand(deviceRequestDTO.brand());

        Device updatedDevice = deviceRepository.save(device);

        return new DeviceResponseDTO(
                updatedDevice.getId(),
                updatedDevice.getName(),
                updatedDevice.getBrand(),
                updatedDevice.getCreatedAt()
        );
    }
}
