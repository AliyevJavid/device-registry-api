package com.example.deviceregistryapi.repository;

import com.example.deviceregistryapi.model.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findAllByBrand(String brand, Pageable pageable);
}
