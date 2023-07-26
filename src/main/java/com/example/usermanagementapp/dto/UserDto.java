package com.example.usermanagementapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private String firstName;
    private String lastName;
    private String pesel;
    private CommunicationDto communication;
}

