package magis.mundi2025.demo.controller;

import lombok.RequiredArgsConstructor;
import magis.mundi2025.demo.model.entity.User;
import magis.mundi2025.demo.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/submit")
    public String submitReview(@RequestParam Long propertyId,
                               @RequestParam int rating,
                               @RequestParam String comment,
                               @AuthenticationPrincipal User currentUser) {
        if (reviewService.canUserReview(currentUser.getId(), propertyId)) {
            reviewService.saveReview(currentUser, propertyId, rating, comment);
        }
        return "redirect:/properties/" + propertyId;
    }
}
