package magis.mundi2025.demo.controller;

import lombok.RequiredArgsConstructor;
import magis.mundi2025.demo.converter.PropertyConverter;
import magis.mundi2025.demo.model.dto.PropertyDTO;
import magis.mundi2025.demo.model.entity.Property;
import magis.mundi2025.demo.model.entity.User;
import magis.mundi2025.demo.service.PropertyService;
import magis.mundi2025.demo.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PropertyViewController {
    private final PropertyService propertyService;
    private final PropertyConverter propertyConverter;
    private final ReviewService reviewService;

    @GetMapping("/properties")
    public String viewAllProperties(Model model, @RequestParam(required = false) String query) {
        final List<Property> properties;

        if (query != null && !query.trim().isEmpty()) {
            properties = propertyService.searchProperties(query);
            model.addAttribute("query", query);
        } else {
            properties = propertyService.getAllProperties();
        }

        var propertyDTOs = properties.stream()
                .map(p -> {
                    PropertyDTO dto = propertyConverter.convertToDTO(p);
                    dto.setAverageRating(reviewService.getAverageRating(p.getId()));
                    dto.setReviewCount(p.getReviews() != null ? p.getReviews().size() : 0);
                    return dto;
                })
                .collect(Collectors.toList());

        model.addAttribute("properties", propertyDTOs);
        return "properties";
    }

    @GetMapping("/properties/{id}")
    public String viewPropertyDetails(@PathVariable Long id, Model model,
                                      @AuthenticationPrincipal User currentUser) {
        var property = propertyService.getPropertyById(id);
        var propertyDTO = propertyConverter.convertToDTO(property);
        propertyDTO.setAverageRating(reviewService.getAverageRating(id));
        propertyDTO.setReviewCount(property.getReviews() != null ? property.getReviews().size() : 0);
        propertyDTO.setReviews(property.getReviews());

        model.addAttribute("property", propertyDTO);
        model.addAttribute("canReview", reviewService.canUserReview(currentUser.getId(), id));
        return "property-details";
    }
}
