package com.vehicle.vehicle_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ServiceTicketRepository extends JpaRepository<ServiceTicket, Long> {

    List<ServiceTicket> findByVehicleOwner(User owner);

    long countByStatus(TicketStatus status);

    List<ServiceTicket> findByStatus(TicketStatus status);

    @Query("SELECT t FROM ServiceTicket t WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:search IS NULL OR :search = '' OR CAST(t.id AS string) = :search OR LOWER(t.vehicle.registrationNumber) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<ServiceTicket> findByStatusAndSearch(@Param("status") TicketStatus status, @Param("search") String search);

    @Query("SELECT t FROM ServiceTicket t WHERE t.vehicle.owner = :owner AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:search IS NULL OR :search = '' OR CAST(t.id AS string) = :search OR LOWER(t.vehicle.registrationNumber) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<ServiceTicket> findByVehicleOwnerAndStatusAndSearch(@Param("owner") User owner, @Param("status") TicketStatus status, @Param("search") String search);
}