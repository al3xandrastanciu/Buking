package magis.mundi2025.demo.controller;

import ch.qos.logback.core.model.Model;
import magis.mundi2025.demo.model.dto.BookingRequest;
import magis.mundi2025.demo.model.entity.Booking;
import magis.mundi2025.demo.model.entity.Property;
import magis.mundi2025.demo.model.entity.Room;
import magis.mundi2025.demo.model.entity.User;
import magis.mundi2025.demo.repository.PropertyRepository;
import magis.mundi2025.demo.repository.RoomRepository;
import magis.mundi2025.demo.repository.UserRepository;
import magis.mundi2025.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @PostMapping("/book")
    public String handleBooking(@ModelAttribute BookingRequest request) { // <-- DTO-ul primit

        // 1. Obține referințele entităților din DTO
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found."));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found."));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setProperty(property);
        booking.setPrice(request.getPrice());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());

        Booking savedBooking = bookingService.saveBooking(booking);

        return "redirect:/booking/confirmation/" + savedBooking.getId();
    }

    @GetMapping("/booking/confirmation/{id}")
    public String showConfirmation(@PathVariable Long id, org.springframework.ui.Model model) {
        Booking booking = bookingService.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        model.addAttribute("booking", booking);
        return "booking-confirmation";
    }

    @GetMapping("/my-bookings")
    public String showMyBookings(org.springframework.ui.Model model) {
        Long userID=1L;
        List<Booking> myBookings = bookingService.findByUserID(userID);
        model.addAttribute("bookings", myBookings);
        return "my-bookings";
    }




}
