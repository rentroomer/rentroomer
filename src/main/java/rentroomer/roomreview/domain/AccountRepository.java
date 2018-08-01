package rentroomer.roomreview.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findBySocialIdAndProviderName(Long socialId, String providerName);
}
