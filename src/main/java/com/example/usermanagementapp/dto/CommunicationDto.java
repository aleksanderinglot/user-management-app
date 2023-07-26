package com.example.usermanagementapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommunicationDto {
    private String email;
    private String residentialAddress;
    private String registeredAddress;
    private String privatePhoneNumber;
    private String workPhoneNumber;
}

