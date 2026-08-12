# VehicleService_Terminal
An enterprise ready vehicle service and ticket management application designed to streamline operations between vehicle owners, mechanics, and administrators. The system manages vehicle registrations, tracks service workflows, updates repair timelines, and enforces role based access control across all operations.

TECH STACK

Language & Core Framework: Java 17, Spring Boot 3.x / 4.x
Web & Security: Spring MVC, Spring Security, Session Management
Persistence & Database: Spring Data JPA, H2 Database
Validation: Jakarta Bean Validation (spring-boot-starter-validation)
Frontend Template Engine: Thymeleaf, Bootstrap 5.3

KEY FEATURES and ROLE BASED ACCESS CONTROL

Customer:

Register vehicles under their personal profile.
Submit service tickets for their registered vehicles.
View real-time status updates and repair notes on active tickets.
Filter and search their submitted tickets.

Mechanic: 

View all open and in progress service tickets across all vehicles.
Update ticket statuses (OPEN, IN_PROGRESS, COMPLETED).
Log technical repair notes and completion details.

System Administrator:

Full administrative access across vehicles, tickets, and user accounts.
Access to the Admin Dashboard and system metrics.
Global search and status filtering for operational monitoring.

DEFAULT CREDENTIALS

Use the following pre seeded user accounts to test role based access control and system workflows:

admin - admin123

mechanic - mech123

customer - cust123
