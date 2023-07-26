package com.example.usermanagementapp.service;

import com.example.usermanagementapp.dto.UserDto;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void shouldReturnListOfUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "Jan", "Kowalski", "1234567890", null));
        users.add(new User(2L, "Andrzej", "Nowak", "9876543210", null));

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(users.size(), result.size());
        assertEquals(users.get(0).getFirstName(), result.get(0).getFirstName());
        assertEquals(users.get(1).getLastName(), result.get(1).getLastName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserWithMatchingPesel() {
        String pesel = "1234567890";
        User user = new User(1L, "Jan", "Kowalski", pesel, null);

        when(userRepository.findByPesel(pesel)).thenReturn(Optional.of(user));

        UserDto result = userService.getUserByPesel(pesel);

        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getPesel(), result.getPesel());

        verify(userRepository, times(1)).findByPesel(pesel);
    }
}
