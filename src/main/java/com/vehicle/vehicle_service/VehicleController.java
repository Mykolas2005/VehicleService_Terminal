package com.vehicle.vehicle_service;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleController(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listVehicles(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return "redirect:/";
        }

        // Restrict customers from viewing the general vehicle management list page
        if (currentUser.getRole() == Role.CUSTOMER) {
            return "redirect:/tickets";
        }

        List<Vehicle> vehicles = vehicleRepository.findAll();
        if (vehicles == null) {
            vehicles = List.of();
        }

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("userRole", currentUser.getRole().name());
        return "vehicles/list";
    }

    @GetMapping("/register")
    public String showRegisterForm(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return "redirect:/";
        }

        // Customers are allowed to access this form to register their own vehicle
        model.addAttribute("vehicle", new Vehicle());
        return "vehicles/register";
    }

    @PostMapping("/register")
    public String registerVehicle(@Valid @ModelAttribute("vehicle") Vehicle vehicle, BindingResult bindingResult, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return "redirect:/";
        }

        // If validation fails, return the user back to the form with the errors
        if (bindingResult.hasErrors()) {
            return "vehicles/register";
        }

        User managedUser = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        vehicle.setOwner(managedUser);
        vehicleRepository.save(vehicle);

        // Redirect customers back to tickets since they don't have access to /vehicles list
        if (currentUser.getRole() == Role.CUSTOMER) {
            return "redirect:/tickets";
        }

        return "redirect:/vehicles";
    }
}