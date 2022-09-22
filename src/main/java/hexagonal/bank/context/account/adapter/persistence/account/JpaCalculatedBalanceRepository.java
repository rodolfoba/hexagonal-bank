package hexagonal.bank.context.account.adapter.persistence.account;

import hexagonal.bank.context.account.domain.account.AccountId;
import hexagonal.bank.context.account.domain.account.CalculatedBalance;
import hexagonal.bank.context.account.domain.account.CalculatedBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JpaCalculatedBalanceRepository implements CalculatedBalanceRepository {

    @Override
    public CalculatedBalance getByAccountId(AccountId accountId) {
        return null;
    }

    @Override
    public void save(CalculatedBalance referencedBalance) {
        // TODO
    }
}
