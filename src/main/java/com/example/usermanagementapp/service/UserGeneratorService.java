package com.example.usermanagementapp.service;

import com.example.usermanagementapp.model.Communication;
import com.example.usermanagementapp.model.User;
import com.example.usermanagementapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserGeneratorService {

    private final UserRepository userRepository;
    private static Long userId = 1L;

    public void generateAndSaveUsers() {
        List<User> users = generateUsers();
        userRepository.saveAll(users);
    }

    private List<User> generateUsers() {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 15000; i++) {
            User user = generateUser();
            users.add(user);
        }

        return users;
    }

    private User generateUser() {
        String firstName = generateRandomString();
        String lastName = generateRandomString();
        String pesel = generateRandomPesel();

        Communication communication = generateCommunication();

        return new User(userId++, firstName, lastName, pesel, communication);
    }

    private String generateRandomString() {
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

    private String generateRandomPesel() {
        StringBuilder peselBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            int digit = random.nextInt(10);
            peselBuilder.append(digit);
        }

        return peselBuilder.toString();
    }

    private Communication generateCommunication() {
        Communication communication = new Communication();

        List<String> availableFields = new ArrayList<>(Arrays.asList("email", "residentialAddress", "registeredAddress", "privatePhoneNumber", "workPhoneNumber"));
        Random random = new Random();

        int numberOfFields = random.nextInt(3) + 2;

        for (int i = 0; i < numberOfFields; i++) {
            String field = availableFields.get(random.nextInt(availableFields.size()));
            availableFields.remove(field);

            switch (field) {
                case "email":
                    communication.setEmail(generateRandomEmail());
                    break;
                case "residentialAddress":
                    communication.setResidentialAddress(generateRandomAddress());
                    break;
                case "registeredAddress":
                    communication.setRegisteredAddress(generateRandomAddress());
                    break;
                case "privatePhoneNumber":
                    communication.setPrivatePhoneNumber(generateRandomPhoneNumber());
                    break;
                case "workPhoneNumber":
                    communication.setWorkPhoneNumber(generateRandomPhoneNumber());
                    break;
            }
        }

        return communication;
    }

    private String generateRandomEmail() {
        String[] domains = {"gmail.com", "outlook.com", "interia.pl"};
        String[] usernames = {"jan", "andrzej", "piotr", "kamil"};

        Random random = new Random();
        String username = usernames[random.nextInt(usernames.length)];
        String domain = domains[random.nextInt(domains.length)];

        return username + "@" + domain;
    }

    private String generateRandomAddress() {
        String[] streets = {"Marszałkowska", "Nowy Świat", "Krakowskie Przedmieście", "3 Maja"};
        String[] cities = {"Warszawa", "Rzeszów", "Kraków", "Wrocław"};

        Random random = new Random();
        String street = streets[random.nextInt(streets.length)];
        String city = cities[random.nextInt(cities.length)];

        return street + ", " + city;
    }


    private String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);
            phoneNumber.append(digit);
        }

        return phoneNumber.toString();
    }
}

