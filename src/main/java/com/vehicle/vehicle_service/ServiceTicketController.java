package com.vehicle.vehicle_service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class ServiceTicketController {

    private final ServiceTicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public ServiceTicketController(ServiceTicketRepository ticketRepository,
                                   VehicleRepository vehicleRepository,
                                   UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listTickets(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) String search,
            HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        List<ServiceTicket> tickets;
        if (user.getRole() == Role.CUSTOMER) {
            User managedUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            tickets = ticketRepository.findByVehicleOwnerAndStatusAndSearch(managedUser, status, search);
        } else {
            tickets = ticketRepository.findByStatusAndSearch(status, search);
        }

        model.addAttribute("tickets", tickets);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("searchKeyword", search);
        return "tickets/list";
    }

    @GetMapping("/new")
    public String showCreateTicketForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        List<Vehicle> userVehicles;
        if (user.getRole() == Role.CUSTOMER) {
            userVehicles = vehicleRepository.findByOwnerId(user.getId());
        } else {
            userVehicles = vehicleRepository.findAll();
        }

        model.addAttribute("vehicles", userVehicles);
        return "tickets/create";
    }

    @PostMapping("/new")
    public String createTicket(HttpSession session,
                              @RequestParam Long vehicleId,
                              @RequestParam String serviceType,
                              @RequestParam String description) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID"));

        ServiceTicket ticket = new ServiceTicket(serviceType, description, vehicle);
        ticketRepository.save(ticket);

        return "redirect:/tickets";
    }

    @PostMapping("/{id}/status")
    public String updateTicketStatus(HttpSession session,
                                     @PathVariable Long id,
                                     @RequestParam TicketStatus status,
                                     @RequestParam(required = false) String repairNotes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/";
        }

        if (user.getRole() == Role.CUSTOMER) {
            return "redirect:/tickets";
        }

        ServiceTicket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket ID"));

        ticket.setStatus(status);
        ticket.setUpdatedAt(LocalDateTime.now());
        
        if (repairNotes != null && !repairNotes.trim().isEmpty()) {
            ticket.setRepairNotes(repairNotes);
        }
        
        ticketRepository.save(ticket);

        return "redirect:/tickets";
    }
}