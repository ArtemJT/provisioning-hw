package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.CustomException;
import com.voxloud.provisioning.mapper.DeviceResponseMapper;
import com.voxloud.provisioning.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ProvisioningServiceImpl implements ProvisioningService {

    private final DeviceRepository deviceRepository;
    private final DeviceResponseMapper deviceResponseMapper;

    public String getProvisioningFile(String macAddress) {
        if (macAddress == null || macAddress.isEmpty()) {
            throw new CustomException("Invalid mac address");
        }

        Device device = deviceRepository.findByMacAddress(macAddress.toLowerCase())
                .orElseThrow(() -> new EntityNotFoundException(macAddress));
        return deviceResponseMapper.mapToStringResponse(device);
    }
}
