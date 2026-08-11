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
        // Seed initial setup data ONLY if the database is empty
        if (userRepository.count() == 0) {
            User admin = new User("admin", "admin123", Role.ADMIN);
            User mechanic = new User("mechanic", "mech123", Role.MECHANIC);
            User customer = new User("customer", "cust123", Role.CUSTOMER);

            userRepository.save(admin);
            userRepository.save(mechanic);
            User savedCustomer = userRepository.save(customer);

            Vehicle vehicle1 = new Vehicle("07C1235", "Ford", "Focus", 2019, 35000, savedCustomer);
            Vehicle vehicle2 = new Vehicle("12ww5412", "Volkswagen", "Golf", 2021, 18000, savedCustomer);

            vehicleRepository.save(vehicle1);
            vehicleRepository.save(vehicle2);
        }
    }
}