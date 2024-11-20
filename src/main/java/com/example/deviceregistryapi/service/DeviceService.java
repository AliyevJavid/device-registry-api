package com.example.deviceregistryapi.service;

import com.example.deviceregistryapi.dto.DeviceRequestDTO;
import com.example.deviceregistryapi.dto.DeviceResponseDTO;
import com.example.deviceregistryapi.exception.ResourceNotFoundException;
import com.example.deviceregistryapi.model.Device;
import com.example.deviceregistryapi.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * This service handles all operations related to devices.
 */
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    /**
     * Adds a new device to the database.
     *
     * @param deviceRequestDTO the data for the new device (type: {@link DeviceRequestDTO})
     * @return the data of the created device (type: {@link DeviceResponseDTO})
     */
    public DeviceResponseDTO addDevice(DeviceRequestDTO deviceRequestDTO) {
        Device saved = deviceRepository.save(new Device(deviceRequestDTO.name(), deviceRequestDTO.brand()));
        return new DeviceResponseDTO(saved.getId(), saved.getName(), saved.getBrand(), saved.getCreatedAt());
    }

    /**
     * Gets a device by its ID.
     *
     * @param id the unique ID of the device (type: {@link Long})
     * @return the data of the found device (type: {@link DeviceResponseDTO})
     * @throws ResourceNotFoundException if the device is not found
     */
    public DeviceResponseDTO getDeviceById(Long id) {
        Device device = deviceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Device with id %d not found", id)));
        return new DeviceResponseDTO(device.getId(), device.getName(), device.getBrand(), device.getCreatedAt());
    }

    /**
     * Lists all devices with pagination.
     *
     * @param pageable the pagination details (type: {@link Pageable})
     * @return a page of devices (type: {@link Page}<{@link DeviceResponseDTO}>)
     */
    public Page<DeviceResponseDTO> listAllDevices(Pageable pageable) {
        return deviceRepository
                .findAll(pageable)
                .map(device -> new DeviceResponseDTO(
                        device.getId(),
                        device.getName(),
                        device.getBrand(),
                        device.getCreatedAt()));
    }

    /**
     * Updates the details of an existing device.
     *
     * @param id               the ID of the device to update (type: {@link Long})
     * @param deviceRequestDTO the new data for the device (type: {@link DeviceRequestDTO})
     * @return the updated device data (type: {@link DeviceResponseDTO})
     */
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

    /**
     * Deletes a device by its ID.
     *
     * @param id the ID of the device to delete (type: {@link Long})
     */
    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    /**
     * Searches for devices by their brand.
     *
     * @param brand    the brand name to search for (type: {@link String})
     * @param pageable the pagination details (type: {@link Pageable})
     * @return a page of devices matching the brand (type: {@link Page}<{@link DeviceResponseDTO}>)
     */
    public Page<DeviceResponseDTO> getDevicesByBrand(String brand, Pageable pageable) {
        return deviceRepository
                .findAllByBrand(brand, pageable)
                .map(device -> new DeviceResponseDTO(
                        device.getId(),
                        device.getName(),
                        device.getBrand(),
                        device.getCreatedAt()));
    }

    /**
     * Partially updates a device's details.
     *
     * @param id               the ID of the device to update (type: {@link Long})
     * @param deviceRequestDTO the partial data to update (type: {@link DeviceRequestDTO})
     * @return the updated device data (type: {@link DeviceResponseDTO})
     */
    public DeviceResponseDTO partialUpdateDevice(Long id, DeviceRequestDTO deviceRequestDTO) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Device with id %d not found", id)));

        if (deviceRequestDTO.name() != null && !deviceRequestDTO.name().isBlank()) {
            device.setName(deviceRequestDTO.name());
        }
        if (deviceRequestDTO.brand() != null && !deviceRequestDTO.brand().isBlank()) {
            device.setBrand(deviceRequestDTO.brand());
        }

        Device updatedDevice = deviceRepository.save(device);

        return new DeviceResponseDTO(
                updatedDevice.getId(),
                updatedDevice.getName(),
                updatedDevice.getBrand(),
                updatedDevice.getCreatedAt()
        );
    }
}
