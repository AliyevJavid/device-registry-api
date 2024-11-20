package com.example.deviceregistryapi.controller;

import com.example.deviceregistryapi.dto.DeviceRequestDTO;
import com.example.deviceregistryapi.dto.DeviceResponseDTO;
import com.example.deviceregistryapi.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This controller manages all HTTP requests related to devices.
 * It provides CRUD endpoints for devices.
 */
@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    /**
     * Adds a new device.
     *
     * @param deviceRequestDTO the data for the new device (type: {@link DeviceRequestDTO})
     * @return the created device data (type: {@link DeviceResponseDTO})
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponseDTO addDevice(@RequestBody @Valid DeviceRequestDTO deviceRequestDTO) {
        return deviceService.addDevice(deviceRequestDTO);
    }

    /**
     * Gets a device by its ID.
     *
     * @param id the ID of the device (type: {@link Long})
     * @return the found device data (type: {@link DeviceResponseDTO})
     */
    @GetMapping("/{id}")
    public DeviceResponseDTO getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }

    /**
     * Lists all devices with pagination.
     *
     * @param pageable the pagination details (type: {@link Pageable})
     * @return a page of devices (type: {@link Page}<{@link DeviceResponseDTO}>)
     */
    @GetMapping
    public Page<DeviceResponseDTO> listAllDevices(Pageable pageable) {
        return deviceService.listAllDevices(pageable);
    }

    /**
     * Updates a device completely.
     *
     * @param id               the ID of the device to update (type: {@link Long})
     * @param deviceRequestDTO the new data for the device (type: {@link DeviceRequestDTO})
     * @return the updated device data (type: {@link DeviceResponseDTO})
     */
    @PutMapping("/{id}")
    public DeviceResponseDTO updateDevice(@PathVariable Long id, @RequestBody @Valid DeviceRequestDTO deviceRequestDTO) {
        return deviceService.updateDevice(id, deviceRequestDTO);
    }

    /**
     * Partially updates a device.
     *
     * @param id               the ID of the device to update (type: {@link Long})
     * @param deviceRequestDTO the partial data to update (type: {@link DeviceRequestDTO})
     * @return the updated device data (type: {@link DeviceResponseDTO})
     */
    @PatchMapping("/{id}")
    public DeviceResponseDTO partialUpdateDevice(@PathVariable Long id, @RequestBody DeviceRequestDTO deviceRequestDTO) {
        return deviceService.partialUpdateDevice(id, deviceRequestDTO);
    }

    /**
     * Deletes a device by its ID.
     *
     * @param id the ID of the device to delete (type: {@link Long})
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
    }

    /**
     * Searches for devices by their brand.
     *
     * @param brand    the brand name to search for (type: {@link String})
     * @param pageable the pagination details (type: {@link Pageable})
     * @return a page of devices matching the brand (type: {@link Page}<{@link DeviceResponseDTO}>)
     */
    @GetMapping("/search")
    public Page<DeviceResponseDTO> getDevicesByBrand(@RequestParam String brand, Pageable pageable) {
        return deviceService.getDevicesByBrand(brand, pageable);
    }

}
