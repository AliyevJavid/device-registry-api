package com.example.deviceregistryapi.controller;

import com.example.deviceregistryapi.dto.DeviceRequestDTO;
import com.example.deviceregistryapi.dto.DeviceResponseDTO;
import com.example.deviceregistryapi.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponseDTO addDevice(@RequestBody @Valid DeviceRequestDTO deviceRequestDTO) {
        return deviceService.addDevice(deviceRequestDTO);
    }

    @GetMapping("/{id}")
    public DeviceResponseDTO getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }

    @GetMapping
    public List<DeviceResponseDTO> listAllDevices() {
        return null;
    }

    @PutMapping("/{id}")
    public DeviceResponseDTO updateDevice(@PathVariable Long id, @RequestBody @Valid DeviceRequestDTO deviceRequestDTO) {
        return null;
    }

    @PatchMapping("/{id}")
    public DeviceResponseDTO partialUpdateDevice(@PathVariable Long id, @RequestBody DeviceRequestDTO deviceRequestDTO) {
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable Long id) {
    }

    @GetMapping
    public List<DeviceResponseDTO> getDevicesByBrand(@RequestParam String brand) {
        return null;
    }

}
