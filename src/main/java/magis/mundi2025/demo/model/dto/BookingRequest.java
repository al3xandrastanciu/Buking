package magis.mundi2025.demo.model.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long roomId;
    private Long propertyId;
    private String checkIn;
    private String checkOut;
}
