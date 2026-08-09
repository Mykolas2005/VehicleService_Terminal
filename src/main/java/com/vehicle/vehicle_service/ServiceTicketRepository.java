package com.vehicle.vehicle_service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTicketRepository extends JpaRepository<ServiceTicket, Long> {
    List<ServiceTicket> findByVehicleOwner(User owner);
    List<ServiceTicket> findByVehicle(Vehicle vehicle);
}