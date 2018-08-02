package rentroomer.roomreview.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rentroomer.roomreview.domain.Account;
import rentroomer.roomreview.resolvers.Login;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String create(@Login Account account) {

        return "";
    }


}
