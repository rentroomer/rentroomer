package rentroomer.roomreview.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.dto.ReviewDto;
import rentroomer.roomreview.resolvers.Login;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String create(@Login Account account, ReviewDto reviewDto) {
        logger.debug("Review DTO: {}", reviewDto);
        return "redirect:/";
    }
}
