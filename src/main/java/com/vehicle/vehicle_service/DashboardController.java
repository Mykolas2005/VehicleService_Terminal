package com.vehicle.vehicle_service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final ServiceTicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;

    public DashboardController(ServiceTicketRepository ticketRepository, 
                               VehicleRepository vehicleRepository) {
        this.ticketRepository = ticketRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        // Aggregate statistics and metrics for staff/admin dashboard
        if (user.getRole() != Role.CUSTOMER) {
            long totalVehicles = vehicleRepository.count();
            long openTicketsCount = ticketRepository.countByStatus(TicketStatus.OPEN);
            long inProgressCount = ticketRepository.countByStatus(TicketStatus.IN_PROGRESS);
            long completedServicesCount = ticketRepository.countByStatus(TicketStatus.COMPLETED);
            long activeTicketsCount = openTicketsCount + inProgressCount;

            List<ServiceTicket> recentOpenTickets = ticketRepository.findByStatus(TicketStatus.OPEN);

            model.addAttribute("totalVehicles", totalVehicles);
            model.addAttribute("activeTickets", activeTicketsCount);
            model.addAttribute("completedServices", completedServicesCount);
            model.addAttribute("recentOpenTickets", recentOpenTickets);
        }

        return "dashboard";
    }
}