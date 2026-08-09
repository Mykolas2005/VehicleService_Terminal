package com.vehicle.vehicle_service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

        List<Vehicle> vehicles;
        if (currentUser.getRole() == Role.CUSTOMER) {
            vehicles = vehicleRepository.findByOwner(currentUser);
        } else {
            vehicles = vehicleRepository.findAll();
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

        model.addAttribute("vehicle", new Vehicle());
        return "vehicles/register";
    }

    @PostMapping("/register")
    public String registerVehicle(@ModelAttribute Vehicle vehicle, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return "redirect:/";
        }

        vehicle.setOwner(currentUser);
        vehicleRepository.save(vehicle);

        return "redirect:/vehicles";
    }
}