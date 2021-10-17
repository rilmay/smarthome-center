package com.guzov.smarthomecenter.controller;

import com.guzov.smarthomecenter.domain.Device;
import com.guzov.smarthomecenter.repo.DeviceRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("devices")
public class DevicesController {
    private final DeviceRepo deviceRepo;

    @Autowired
    public DevicesController(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    @GetMapping
    public List<Device> deviceList() {
        return deviceRepo.findAll();
    }

    @GetMapping("{id}")
    public Device getDevice(@PathVariable("id") Device device) {
        return device;
    }

    @PostMapping
    public Device createDevice(@RequestBody Device device) {
        device.setCreationDate(LocalDateTime.now());
        return deviceRepo.save(device);
    }

    @PutMapping("{id}")
    public Device updateDevice(@PathVariable("id") Device deviceFromDb, @RequestBody Device device) {
        BeanUtils.copyProperties(device, deviceFromDb, "id");
        return deviceRepo.save(deviceFromDb);

    }

    @DeleteMapping("{id}")
    public void deleteDevice(@PathVariable("id") Device deviceFromDb) {
        deviceRepo.delete(deviceFromDb);
    }
}
