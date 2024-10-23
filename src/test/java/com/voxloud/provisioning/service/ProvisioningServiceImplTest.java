package com.voxloud.provisioning.service;

import com.voxloud.provisioning.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProvisioningServiceImplTest {

    @Autowired
    private ProvisioningService provisioningService;

    @Test
    @DisplayName("Not found MAC")
    void throwsNotFound() {
        String mac = "aa-bb-cc-11-22-33";
        assertThrows(EntityNotFoundException.class, () -> provisioningService.getProvisioningFile(mac));
        assertThrows(CustomException.class, () -> provisioningService.getProvisioningFile(null));
        assertThrows(CustomException.class, () -> provisioningService.getProvisioningFile(""));
    }

    @Test
    @DisplayName("Normal Desk Config")
    void getDeskConfigNormalTest() {
        String mac = "aa-bb-cc-dd-ee-ff";
        String expected = "username=john\n" +
                "password=doe\n" +
                "domain=sip.voxloud.com\n" +
                "port=5060\n" +
                "codecs=G711,G729,OPUS";
        assertEquals(provisioningService.getProvisioningFile(mac), expected);
    }

    @Test
    @DisplayName("Override Desk Config")
    void getDeskConfigOverrideTest() {
        String mac = "a1-b2-c3-d4-e5-f6";
        String expected = "username=walter\n" +
                "password=white\n" +
                "domain=sip.anotherdomain.com\n" +
                "port=5161\n" +
                "codecs=G711,G729,OPUS\n" +
                "timeout=10";
        assertEquals(provisioningService.getProvisioningFile(mac), expected);
    }

    @Test
    @DisplayName("Normal Conference Config")
    void getConferenceConfigNormalTest() {
        String mac = "f1-e2-d3-c4-b5-a6";
        String expected = "{\"username\":\"sofia\",\"password\":\"red\",\"domain\":\"sip.voxloud.com\"," +
                "\"port\":\"5060\",\"codecs\":\"G711,G729,OPUS\"}";
        assertEquals(provisioningService.getProvisioningFile(mac), expected);
    }

    @Test
    @DisplayName("Override Conference Config")
    void getConferenceConfigOverrideTest() {
        String mac = "1a-2b-3c-4d-5e-6f";
        String expected = "{\"username\":\"eric\",\"password\":\"blue\",\"domain\":\"sip.anotherdomain.com\"," +
                "\"port\":\"5161\",\"codecs\":\"G711,G729,OPUS\",\"timeout\":\"10\"}";
        assertEquals(provisioningService.getProvisioningFile(mac), expected);
    }
}
