package hexagonal.bank.context.account.adapter.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DelegateJpaAccountRepository extends JpaRepository<AccountJpa, Long> {

}
