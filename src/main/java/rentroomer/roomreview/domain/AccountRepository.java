package rentroomer.roomreview.domain;

import org.springframework.data.repository.CrudRepository;
import rentroomer.roomreview.security.SocialProvider;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findBySocialIdAndSocialProvider(Long socialId, SocialProvider provider);
}
