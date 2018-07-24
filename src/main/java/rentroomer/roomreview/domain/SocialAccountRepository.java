package rentroomer.roomreview.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocialAccountRepository extends CrudRepository<SocialAccount, Long> {

    Optional<SocialAccount> findBySocialId(Long id);
}
