package com.guzov.smarthomecenter.controller;

import com.guzov.smarthomecenter.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("devices")
public class DevicesController {
    private int counter = 3;

    public List<Map<String, String>> devices = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String,String>() {{put("id", "1"); put("name", "Yeelight lamp 1");}});
        add(new HashMap<String,String>() {{put("id", "2"); put("name", "Yeelight lamp 2");}});
        add(new HashMap<String,String>() {{put("id", "3"); put("name", "Yeelight lamp 3");}});
    }};

    @GetMapping
    public List<Map<String, String>> deviceList() {
        return devices;
    }

    @GetMapping("{id}")
    public Map<String, String> getDevice(@PathVariable String id){
        return getDeviceById(id);
    }

    private Map<String, String> getDeviceById(@PathVariable String id) {
        return devices.stream()
                .filter(device -> id.equals(device.get("id")))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> createDevice(@RequestBody Map<String, String> device) {
        device.put("id", String.valueOf(++counter));
        devices.add(device);
        return device;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> device) {
        Map<String, String> savedDevice = getDeviceById(id);

        savedDevice.putAll(device);
        savedDevice.put("id", id);
        return savedDevice;

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> device = getDeviceById(id);
        devices.remove(device);
    }
}
