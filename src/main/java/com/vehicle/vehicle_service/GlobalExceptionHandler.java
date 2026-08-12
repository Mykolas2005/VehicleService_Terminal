package com.vehicle.vehicle_service;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Catch Database Connection and JPA/SQL Exceptions
    @ExceptionHandler({DataAccessException.class, SQLException.class})
    public String handleDatabaseException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "A database connection or query issue occurred. System administrators have been notified.");
        model.addAttribute("errorDetails", ex.getMessage());
        return "error";
    }

    // Catch all other unexpected system exceptions
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected system error occurred.");
        model.addAttribute("errorDetails", ex.getMessage());
        return "error";
    }
}