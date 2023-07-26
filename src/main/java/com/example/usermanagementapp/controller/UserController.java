package com.example.usermanagementapp.controller;

import com.example.usermanagementapp.dto.CommunicationDto;
import com.example.usermanagementapp.dto.UserDto;
import com.example.usermanagementapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/{id}/communication")
    public ResponseEntity<UserDto> addUserCommunication(@PathVariable Long id, @RequestBody CommunicationDto communicationDto) {
        UserDto updatedUser = userService.addUserCommunication(id, communicationDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{pesel}")
    public ResponseEntity<UserDto> getUserByPesel(@PathVariable String pesel) {
        UserDto userDto = userService.getUserByPesel(pesel);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/view")
    public ModelAndView showUserList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-list");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUsersToJson() {
        try {
            userService.saveUsersToJson();
            return ResponseEntity.ok("Users saved to JSON file");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving users to JSON file");
        }
    }
}
