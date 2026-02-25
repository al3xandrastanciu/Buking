# Buking - Reservation System (Magis Mundi 2025)

This project is a hotel reservation web application developed by extending a base project provided during a technical workshop. The application allows users to browse properties, view room details, and perform real-time bookings with persistent data storage.

##  Key Contributions & Implemented Features

I have extended and enhanced the pre-existing Spring Boot application by integrating the following features:

* **Core Reservation Functionality (CRUD):** Implemented the full business logic for the booking process, ensuring data persistence for both `Booking` and `User` entities.
* **Three-Tier Architecture:** Structured the backend following industry best practices:
    * **Controllers:** To handle HTTP requests and manage web flow (e.g., `BookingController`, `PropertyViewController`).
    * **Services:** To manage business logic and orchestrate data operations (e.g., `BookingService`).
    * **Repositories:** Using Spring Data JPA for efficient database communication (e.g., `BookingRepository`, `UserRepository`).
* **User-Facing Interfaces (Thymeleaf):**
    * **Booking Confirmation Page:** Developed a dedicated view to provide users with immediate feedback and details after a successful reservation.
    * **"My Bookings" Page:** Created a specific history page where users can view their active reservations, room types, and stay periods.

##  Key Technologies

* **Language:** Java 21
* **Framework:** Spring Boot 3.5.6
* **Data Access:** Spring Data JPA
* **Database:** H2 (In-Memory)
* **Template Engine:** Thymeleaf
* **Frontend:** Bootstrap 5 for a responsive UI
* **Utilities:** Lombok to reduce boilerplate code

##  Data Model

The application architecture relies on four main interconnected entities:
1.  **Property:** Represents hotels or accommodation units.
2.  **Room:** Specific rooms belonging to a property.
3.  **User:** Users who perform the bookings.
4.  **Booking:** Connects a user, a property, and a room for a specific timeframe.

##  How to Run the Project

1.  **Prerequisites:** Ensure you have **Java 21** installed on your system.
2.  **Execution:** Run the application using the Maven wrapper:
    ```bash
    ./mvnw spring-boot:run
    ```
3.  **Access:** Open your browser at: `http://localhost:8080/properties`.
4.  **Database Console:** The H2 console is available at `http://localhost:8080/h2-console` (Username: `sa`, no password).

---

