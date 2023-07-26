package com.example.usermanagementapp.service;

import com.example.usermanagementapp.dto.CommunicationDto;
import com.example.usermanagementapp.dto.UserDto;
import com.example.usermanagementapp.exception.UserNotFoundException;
import com.example.usermanagementapp.model.Communication;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto createUser(UserDto userDto) {
        User user = convertDtoToUser(userDto);
        User createdUser = userRepository.save(user);
        return convertUserToDto(createdUser);
    }

    public UserDto addUserCommunication(Long id, CommunicationDto communicationDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        Communication communication = convertDtoToCommunication(communicationDto);
        user.setCommunication(communication);

        User updatedUser = userRepository.save(user);
        return convertUserToDto(updatedUser);
    }

    public UserDto getUserByPesel(String pesel) {
        User user = userRepository.findByPesel(pesel)
                .orElseThrow(() -> new UserNotFoundException("User not found with PESEL: " + pesel));
        return convertUserToDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return convertToDtoList(users);
    }

    private UserDto convertUserToDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .pesel(user.getPesel())
                .communication(user.getCommunication() != null ? convertCommunicationToDto(user.getCommunication()) : null)
                .build();
    }

    private User convertDtoToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .pesel(userDto.getPesel())
                .communication(userDto.getCommunication() != null ? convertDtoToCommunication(userDto.getCommunication()) : null)
                .build();
    }

    private List<UserDto> convertToDtoList(List<User> users) {
        return users.stream()
                .map(this::convertUserToDto)
                .collect(Collectors.toList());
    }

    public CommunicationDto convertCommunicationToDto(Communication communication) {
        return CommunicationDto.builder()
                .email(communication.getEmail())
                .residentialAddress(communication.getResidentialAddress())
                .registeredAddress(communication.getRegisteredAddress())
                .privatePhoneNumber(communication.getPrivatePhoneNumber())
                .workPhoneNumber(communication.getWorkPhoneNumber())
                .build();
    }

    public Communication convertDtoToCommunication(CommunicationDto communicationDto) {
        return Communication.builder()
                .email(communicationDto.getEmail())
                .residentialAddress(communicationDto.getResidentialAddress())
                .registeredAddress(communicationDto.getRegisteredAddress())
                .privatePhoneNumber(communicationDto.getPrivatePhoneNumber())
                .workPhoneNumber(communicationDto.getWorkPhoneNumber())
                .build();
    }

    public void saveUsersToJson() throws IOException {
        List<UserDto> users = getAllUsers();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("users.json"), users);
    }
}
