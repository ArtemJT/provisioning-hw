package com.voxloud.provisioning.controller;

import com.voxloud.provisioning.service.ProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class ProvisioningController {

    private final ProvisioningService provisioningService;

    @GetMapping(value = "/provisioning/{mac}")
    public String provisioning(@PathVariable String mac) {
        return provisioningService.getProvisioningFile(mac);
    }
}