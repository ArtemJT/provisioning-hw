package com.voxloud.provisioning.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voxloud.provisioning.dto.DeviceAdditionalFragment;
import com.voxloud.provisioning.dto.DeviceDTO;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import static com.voxloud.provisioning.entity.Device.DeviceModel.DESK;

@Component
@RequiredArgsConstructor
public class DeviceResponseMapper {

    @Value("${provisioning.domain}")
    private String domain;
    @Value("${provisioning.port}")
    private String port;
    @Value("${provisioning.codecs}")
    private String codecs;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String mapToStringResponse(Device device) {
        DeviceDTO deviceDTO = DeviceDTO.builder()
                .username(device.getUsername())
                .password(device.getPassword())
                .domain(domain)
                .port(port)
                .codecs(codecs)
                .build();

        String overrideFragment = device.getOverrideFragment();
        Device.DeviceModel deviceModel = device.getModel();

        try {
            if (deviceModel.equals(DESK)) {
                return mapDeskConfig(overrideFragment, deviceDTO);
            } else {
                return mapConferenceConfig(overrideFragment, deviceDTO);
            }
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
    }

    private String mapDeskConfig(String overrideFragment, DeviceDTO deviceDTO) throws IOException {
        if (overrideFragment != null) {
            Properties props = new Properties();
            props.load(new StringReader(overrideFragment));

            deviceDTO.setDomain(props.getProperty("domain"));
            deviceDTO.setPort(props.getProperty("port"));
            deviceDTO.setTimeout(props.getProperty("timeout"));
        }

        return deviceDTO.toString();
    }

    private String mapConferenceConfig(String overrideFragment, DeviceDTO deviceDTO) throws JsonProcessingException {
        if (overrideFragment != null) {
            DeviceAdditionalFragment fragment = objectMapper.readValue(overrideFragment, DeviceAdditionalFragment.class);
            deviceDTO.setDomain(fragment.getDomain());
            deviceDTO.setPort(fragment.getPort());
            deviceDTO.setTimeout(fragment.getTimeout());
        }

        return objectMapper.writeValueAsString(deviceDTO);
    }
}
