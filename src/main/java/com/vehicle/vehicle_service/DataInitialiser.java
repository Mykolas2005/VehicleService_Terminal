package com.vehicle.vehicle_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitialiser implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitialiser(UserRepository userRepository, 
                           VehicleRepository vehicleRepository, 
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User("admin", passwordEncoder.encode("admin123"), Role.ADMIN);
            User mechanic = new User("mechanic", passwordEncoder.encode("mech123"), Role.MECHANIC);
            User customer = new User("customer", passwordEncoder.encode("cust123"), Role.CUSTOMER);

            userRepository.save(admin);
            userRepository.save(mechanic);
            User savedCustomer = userRepository.save(customer);

            // Seed test vehicles for customer
            Vehicle vehicle1 = new Vehicle("AB12 CDE", "Ford", "Focus", 2019, 35000, savedCustomer);
            Vehicle vehicle2 = new Vehicle("XY56 ZHT", "Volkswagen", "Golf", 2021, 18000, savedCustomer);

            vehicleRepository.save(vehicle1);
            vehicleRepository.save(vehicle2);
        }
    }
}