package magis.mundi2025.demo.controller;

import magis.mundi2025.demo.converter.PropertyConverter;
import magis.mundi2025.demo.model.dto.BookingRequest;
import magis.mundi2025.demo.model.dto.PropertyDTO;
import magis.mundi2025.demo.model.entity.Booking;
import magis.mundi2025.demo.model.entity.Property;
import magis.mundi2025.demo.model.entity.Room;
import magis.mundi2025.demo.model.entity.User;
import magis.mundi2025.demo.repository.PropertyRepository;
import magis.mundi2025.demo.repository.RoomRepository;
import magis.mundi2025.demo.repository.UserRepository;
import magis.mundi2025.demo.service.BookingService;
import magis.mundi2025.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private UserRepository userRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private PropertyRepository propertyRepository;
    @Autowired private PropertyConverter propertyConverter;
    @Autowired private ReviewService reviewService;

    @PostMapping("/book")
    public String handleBooking(@ModelAttribute BookingRequest request,
                                @AuthenticationPrincipal User currentUser,
                                Model model) {

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found."));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found."));

        // Helper to return property-details with an error message
        // (builds the full PropertyDTO the template expects)
        java.util.function.Function<String, String> returnWithError = (errorMsg) -> {
            PropertyDTO dto = propertyConverter.convertToDTO(property);
            dto.setAverageRating(reviewService.getAverageRating(property.getId()));
            dto.setReviewCount(property.getReviews() != null ? property.getReviews().size() : 0);
            dto.setReviews(property.getReviews());
            model.addAttribute("property", dto);
            model.addAttribute("error", errorMsg);
            model.addAttribute("canReview", reviewService.canUserReview(currentUser.getId(), property.getId()));
            return "property-details";
        };

        // Validate dates not empty
        if (request.getCheckIn() == null || request.getCheckIn().isBlank() ||
            request.getCheckOut() == null || request.getCheckOut().isBlank()) {
            return returnWithError.apply("Please select check-in and check-out dates before booking.");
        }

        LocalDate checkIn, checkOut;
        try {
            checkIn  = LocalDate.parse(request.getCheckIn());
            checkOut = LocalDate.parse(request.getCheckOut());
        } catch (Exception e) {
            return returnWithError.apply("Invalid date format. Please select dates from the date picker.");
        }

        if (!checkOut.isAfter(checkIn)) {
            return returnWithError.apply("Check-out date must be after check-in date.");
        }

        if (!bookingService.isRoomAvailable(room.getId(), checkIn, checkOut)) {
            return returnWithError.apply("This room is not available for the selected dates. Please choose different dates.");
        }

        // Calculate price server-side
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double totalPrice = room.getPricePerNight()
                .multiply(BigDecimal.valueOf(nights))
                .doubleValue();

        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setRoom(room);
        booking.setProperty(property);
        booking.setPrice(totalPrice);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);

        Booking savedBooking = bookingService.saveBooking(booking);
        return "redirect:/booking/confirmation/" + savedBooking.getId();
    }

    @GetMapping("/booking/confirmation/{id}")
    public String showConfirmation(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        model.addAttribute("booking", booking);
        return "booking-confirmation";
    }

    @GetMapping("/my-bookings")
    public String showMyBookings(Model model, @AuthenticationPrincipal User currentUser) {
        List<Booking> myBookings = bookingService.findByUserID(currentUser.getId());
        model.addAttribute("bookings", myBookings);
        return "my-bookings";
    }
}
