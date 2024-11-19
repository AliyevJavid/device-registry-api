package com.example.deviceregistryapi.service;

import com.example.deviceregistryapi.dto.DeviceRequestDTO;
import com.example.deviceregistryapi.dto.DeviceResponseDTO;
import com.example.deviceregistryapi.model.Device;
import com.example.deviceregistryapi.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceResponseDTO addDevice(DeviceRequestDTO deviceRequestDTO) {
        Device saved = deviceRepository.save(new Device(deviceRequestDTO.name(), deviceRequestDTO.brand()));
        return new DeviceResponseDTO(saved.getId(), saved.getName(), saved.getBrand(), saved.getCreatedAt());
    }
}
