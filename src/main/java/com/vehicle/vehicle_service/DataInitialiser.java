package com.vehicle.vehicle_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitialiser implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceTicketRepository ticketRepository;

    public DataInitialiser(UserRepository userRepository, 
                           VehicleRepository vehicleRepository,
                           ServiceTicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Clear in order of dependencies to avoid foreign key violations
        ticketRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();

        User admin = new User("admin", "admin123", Role.ADMIN);
        User mechanic = new User("mechanic", "mech123", Role.MECHANIC);
        User customer = new User("customer", "cust123", Role.CUSTOMER);

        userRepository.save(admin);
        userRepository.save(mechanic);
        User savedCustomer = userRepository.save(customer);

        Vehicle vehicle1 = new Vehicle("AB12 CDE", "Ford", "Focus", 2019, 35000, savedCustomer);
        Vehicle vehicle2 = new Vehicle("XY56 ZHT", "Volkswagen", "Golf", 2021, 18000, savedCustomer);

        vehicleRepository.save(vehicle1);
        vehicleRepository.save(vehicle2);
    }
}