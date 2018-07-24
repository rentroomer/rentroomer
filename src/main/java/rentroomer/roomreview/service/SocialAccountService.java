package rentroomer.roomreview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rentroomer.roomreview.domain.SocialAccount;
import rentroomer.roomreview.domain.SocialAccountRepository;
import rentroomer.roomreview.dto.SocialProperty;
import rentroomer.roomreview.security.SocialProvider;

import static rentroomer.roomreview.security.UserRole.getBasicRole;

@Service
public class SocialAccountService {

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    public SocialAccount findBySocialId(SocialProperty socialProperty, SocialProvider provider) {
        return socialAccountRepository.findBySocialId(socialProperty.getSocialID())
                .orElseGet(() -> create(socialProperty, provider));
    }

    public SocialAccount create(SocialProperty socialProperty, SocialProvider provider) {
        return socialAccountRepository.save(new SocialAccount(socialProperty, getBasicRole(), provider));
    }
}
