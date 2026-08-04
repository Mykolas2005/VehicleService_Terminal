package com.vehicle.vehicle_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitialiser implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitialiser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin", "admin123", Role.ADMIN));
            userRepository.save(new User("mechanic", "mech123", Role.MECHANIC));
            userRepository.save(new User("customer", "cust123", Role.CUSTOMER));
            System.out.println("Default users initialised: admin, mechanic, customer");
        }
    }
}