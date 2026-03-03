package magis.mundi2025.demo.service;

import lombok.RequiredArgsConstructor;
import magis.mundi2025.demo.model.entity.Property;
import magis.mundi2025.demo.model.entity.Review;
import magis.mundi2025.demo.model.entity.User;
import magis.mundi2025.demo.repository.BookingRepository;
import magis.mundi2025.demo.repository.PropertyRepository;
import magis.mundi2025.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;

    public List<Review> getReviewsForProperty(Long propertyId) {
        return reviewRepository.findByPropertyId(propertyId);
    }

    public Double getAverageRating(Long propertyId) {
        return reviewRepository.findAverageRatingByPropertyId(propertyId);
    }

    public boolean canUserReview(Long userId, Long propertyId) {
        // Utilizatorul poate recenza doar dacă a avut o rezervare la acea proprietate
        // și nu a mai lăsat deja o recenzie
        boolean hasBooking = bookingRepository.findByUserId(userId)
                .stream()
                .anyMatch(b -> b.getProperty().getId().equals(propertyId));
        boolean alreadyReviewed = reviewRepository.existsByUserIdAndPropertyId(userId, propertyId);
        return hasBooking && !alreadyReviewed;
    }

    public void saveReview(User user, Long propertyId, int rating, String comment) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        Review review = new Review();
        review.setUser(user);
        review.setProperty(property);
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
    }
}
