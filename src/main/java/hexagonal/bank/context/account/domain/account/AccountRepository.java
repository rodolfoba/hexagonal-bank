package hexagonal.bank.context.account.domain.account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findById(AccountId accountId);

}
