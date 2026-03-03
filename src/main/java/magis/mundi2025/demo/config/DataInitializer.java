package magis.mundi2025.demo.config;

import lombok.RequiredArgsConstructor;
import magis.mundi2025.demo.model.entity.*;
import magis.mundi2025.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Creează admin dacă nu există
        if (userRepository.findByEmail("admin@buking.ro").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@buking.ro");
            admin.setName("Administrator");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
        }

        // Creează user demo dacă nu există
        if (userRepository.findByEmail("user@buking.ro").isEmpty()) {
            User user = new User();
            user.setEmail("user@buking.ro");
            user.setName("Alexandra");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
        }

        // Creează proprietăți demo dacă nu există
        if (propertyRepository.count() == 0) {
            Property p1 = new Property();
            p1.setName("Hotel Intercontinental București");
            p1.setAddress("Bulevardul Nicolae Bălcescu 4, București");
            p1.setDescription("Hotel de lux în centrul Bucureștiului, cu priveliște spre Piața Universității. Facilități premium, spa și restaurant fine dining.");
            p1.setStarRating(5);
            p1.setImageUrl("https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800");
            propertyRepository.save(p1);

            Property p2 = new Property();
            p2.setName("Hotel Mercure Sinaia");
            p2.setAddress("Bulevardul Carol I 8, Sinaia");
            p2.setDescription("Hotel elegant la poalele munților Bucegi, perfect pentru ski și drumeții. Restaurant tradițional și piscină acoperită.");
            p2.setStarRating(4);
            p2.setImageUrl("https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=800");
            propertyRepository.save(p2);

            Property p3 = new Property();
            p3.setName("Casa Veche Sibiu");
            p3.setAddress("Piața Mare 12, Sibiu");
            p3.setDescription("Pensiune de charme în centrul istoric din Sibiu. Clădire renovată din secolul XVIII, mic dejun inclus.");
            p3.setStarRating(3);
            p3.setImageUrl("https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?w=800");
            propertyRepository.save(p3);

            // Camere pentru Hotel Intercontinental
            addRoom(p1, "101", "Single", new BigDecimal("350"), 1, "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=600");
            addRoom(p1, "201", "Double", new BigDecimal("550"), 2, "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=600");
            addRoom(p1, "301", "Suite", new BigDecimal("1200"), 4, "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?w=600");

            // Camere pentru Mercure Sinaia
            addRoom(p2, "101", "Single", new BigDecimal("280"), 1, "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=600");
            addRoom(p2, "202", "Double Mountain View", new BigDecimal("420"), 2, "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=600");

            // Camere pentru Casa Veche
            addRoom(p3, "1", "Cameră dublă", new BigDecimal("180"), 2, "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=600");
            addRoom(p3, "2", "Apartament", new BigDecimal("280"), 3, "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=600");
        }
    }

    private void addRoom(Property property, String number, String type, BigDecimal price, int capacity, String imageUrl) {
        Room room = new Room();
        room.setRoomNumber(number);
        room.setRoomType(type);
        room.setPricePerNight(price);
        room.setCapacity(capacity);
        room.setImageUrl(imageUrl);
        room.setProperty(property);
        roomRepository.save(room);
    }
}
