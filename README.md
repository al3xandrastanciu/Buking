# Buking - Reservation System 

This project is a hotel reservation web application developed by **extending and enhancing a base skeleton provided during the Magis Mundi 2025 technical workshop**. Starting from a minimal Spring Boot structure with basic entities, I implemented the full business logic, security layer, and additional features to build a complete, production-ready application.

##  Key Contributions & Implemented Features

The following features were added on top of the original skeleton:

- **Authentication** — Secure login and registration with Spring Security 6 & BCrypt password encryption
- **Room Availability Check** — Rooms cannot be double-booked; overlapping reservations are automatically rejected using a JPQL overlap query
- **Server-side Price Calculation** — Total price is calculated securely on the server (nights × price/night), not from the frontend form
- **My Bookings** — Each user can view their own reservation history
- **Reviews & Ratings** — Users who have stayed at a property can leave a star rating and comment
- **Admin Panel** — Administrators can add, edit, and delete properties and rooms, and view platform statistics
- **Demo Data Initializer** — Populates the database with sample hotels, rooms and user accounts on startup

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.6 |
| Security | Spring Security 6 |
| Data Access | Spring Data JPA + Hibernate |
| Database | H2 (In-Memory) |
| Template Engine | Thymeleaf |
| Frontend | Bootstrap 5 |
| Utilities | Lombok |

## Data Model

- **User** — Registered users with roles (`ROLE_USER`, `ROLE_ADMIN`)
- **Property** — Hotels with name, address, description, star rating and image
- **Room** — Rooms belonging to a property (type, price per night, capacity)
- **Booking** — Links a user, property and room for a date range (check-in / check-out)
- **Review** — Star rating + comment left by a user for a property after their stay

## How to Run

**Prerequisites:** Java 21, Maven

```bash
mvn spring-boot:run
```

Then open: [http://localhost:8080](http://localhost:8080)

### Demo Accounts

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@buking.ro | admin123 |
| User | user@buking.ro | user123 |

### H2 Database Console

Available at [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

```
JDBC URL:  jdbc:h2:mem:hoteldb
Username:  sa
Password:  (leave empty)
```

## Application Pages

| Page | URL | Access |
|------|-----|--------|
| Login | `/login` | Public |
| Register | `/register` | Public |
| Properties | `/properties` | Logged in |
| Property Details + Book | `/properties/{id}` | Logged in |
| My Bookings | `/my-bookings` | Logged in |
| Booking Confirmation | `/booking/confirmation/{id}` | Logged in |
| Admin Dashboard | `/admin/dashboard` | Admin only |
