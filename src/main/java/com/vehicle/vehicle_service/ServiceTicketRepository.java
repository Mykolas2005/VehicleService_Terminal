package com.vehicle.vehicle_service.repository;

import com.vehicle.vehicle_service.model.ServiceTicket;
import com.vehicle.vehicle_service.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ServiceTicketRepository extends JpaRepository<ServiceTicket, Long> {

    @Query("SELECT t FROM ServiceTicket t WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:search IS NULL OR :search = '' OR CAST(t.id AS string) = :search OR LOWER(t.vehicle.registrationNumber) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<ServiceTicket> findByStatusAndSearch(@Param("status") TicketStatus status, @Param("search") String search);

    @Query("SELECT t FROM ServiceTicket t WHERE t.vehicle.customer.username = :username AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:search IS NULL OR :search = '' OR CAST(t.id AS string) = :search OR LOWER(t.vehicle.registrationNumber) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<ServiceTicket> findByCustomerUsernameAndStatusAndSearch(@Param("username") String username, @Param("status") TicketStatus status, @Param("search") String search);
}