package rentroomer.roomreview.domain;

import org.springframework.data.repository.CrudRepository;
import rentroomer.roomreview.security.SocialProvider;

import java.util.Optional;

public interface SocialAccountRepository extends CrudRepository<SocialAccount, Long> {

    Optional<SocialAccount> findBySocialIdAndProvider(Long id, SocialProvider provider);
}
