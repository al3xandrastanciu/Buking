package magis.mundi2025.demo.repository;

import magis.mundi2025.demo.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("SELECT p FROM Property p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.address) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Property> searchProperties(@Param("query") String query);
}
