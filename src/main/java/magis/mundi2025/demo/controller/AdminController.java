package magis.mundi2025.demo.controller;

import lombok.RequiredArgsConstructor;
import magis.mundi2025.demo.model.entity.Property;
import magis.mundi2025.demo.model.entity.Room;
import magis.mundi2025.demo.repository.BookingRepository;
import magis.mundi2025.demo.repository.PropertyRepository;
import magis.mundi2025.demo.repository.RoomRepository;
import magis.mundi2025.demo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("propertyCount", propertyRepository.count());
        model.addAttribute("bookingCount", bookingRepository.count());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("properties", propertyRepository.findAll());
        return "admin/dashboard";
    }

    // ── Properties ──────────────────────────────────────
    @GetMapping("/properties/new")
    public String newPropertyForm(Model model) {
        model.addAttribute("property", new Property());
        return "admin/property-form";
    }

    @PostMapping("/properties/save")
    public String saveProperty(@ModelAttribute Property property) {
        propertyRepository.save(property);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/properties/edit/{id}")
    public String editProperty(@PathVariable Long id, Model model) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        model.addAttribute("property", property);
        return "admin/property-form";
    }

    @PostMapping("/properties/delete/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    // ── Rooms ────────────────────────────────────────────
    @GetMapping("/rooms/new/{propertyId}")
    public String newRoomForm(@PathVariable Long propertyId, Model model) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        model.addAttribute("property", property);
        model.addAttribute("room", new Room());
        return "admin/room-form";
    }

    @PostMapping("/rooms/save/{propertyId}")
    public String saveRoom(@PathVariable Long propertyId, @ModelAttribute Room room) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        room.setProperty(property);
        roomRepository.save(room);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }
}
