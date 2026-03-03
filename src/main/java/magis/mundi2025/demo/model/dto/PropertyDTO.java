package magis.mundi2025.demo.model.dto;

import lombok.Data;
import magis.mundi2025.demo.model.entity.Review;

import java.util.List;

@Data
public class PropertyDTO {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Integer starRating;
    private String imageUrl;
    private List<RoomDTO> rooms;
    private Double averageRating;
    private Integer reviewCount;
    private List<Review> reviews;
}
