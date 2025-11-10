package magis.mundi2025.demo.controller;

import lombok.RequiredArgsConstructor;
import magis.mundi2025.demo.converter.PropertyConverter;
import magis.mundi2025.demo.model.entity.Property;
import magis.mundi2025.demo.service.PropertyService;
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

    @GetMapping("/properties")
    public String viewAllProperties(Model model, @RequestParam(required = false) String query) {
        final List<Property> properties; // Folosim List în loc de var, pentru a putea atribui

        if (query != null && !query.trim().isEmpty()) {
            properties = propertyService.searchProperties(query);
            model.addAttribute("query", query);
        } else {
            properties = propertyService.getAllProperties();
        }

        var propertyDTOs = properties.stream()
                .map(propertyConverter::convertToDTO)
                .collect(Collectors.toList());

        model.addAttribute("properties", propertyDTOs);

        return "properties";
    }

    @GetMapping("/properties/{id}")
    public String viewPropertyDetails(@PathVariable Long id, Model model) {
        var property = propertyService.getPropertyById(id);
        var propertyDTO = propertyConverter.convertToDTO(property);
        model.addAttribute("property", propertyDTO);
        return "property-details";
    }
}
