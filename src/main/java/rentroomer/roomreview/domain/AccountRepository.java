package rentroomer.roomreview.domain;

import org.springframework.data.repository.CrudRepository;
import rentroomer.roomreview.security.OAuthProviderInfo;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findBySocialIdAndProviderInfo(Long socialId, OAuthProviderInfo providerInfo);
}
