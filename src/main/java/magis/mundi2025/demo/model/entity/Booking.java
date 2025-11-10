package magis.mundi2025.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Column;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="property_id")
    private Property property;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="room_id")
    private Room room; // <-- CORECTAT: schimbat din List<Room> rooms în Room room

    @Column(name = "check_in")
    private String checkIn;

    @Column(name = "check_out")
    private String checkOut;

    @Column(name = "price")
    private Double price;

}
