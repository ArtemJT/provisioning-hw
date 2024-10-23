package com.voxloud.provisioning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDTO {

    private String username;
    private String password;
    private String domain;
    private String port;
    private String codecs;
    private String timeout;

    @Override
    public String toString() {
        String result =
                        "username=" + username + '\n' +
                        "password=" + password + '\n' +
                        "domain=" + domain + '\n' +
                        "port=" + port + '\n' +
                        "codecs=" + codecs;
        return (timeout != null) ? result + "\ntimeout=" + timeout : result;
    }
}
