package com.example.deviceregistryapi.dto;

import java.time.LocalDateTime;

public record DeviceResponseDTO(Long id,
                                String name,
                                String brand,
                                LocalDateTime creationTime) {
}
