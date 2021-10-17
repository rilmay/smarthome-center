package com.guzov.smarthomecenter.repo;

import com.guzov.smarthomecenter.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepo extends JpaRepository<Device, Long> {
}
