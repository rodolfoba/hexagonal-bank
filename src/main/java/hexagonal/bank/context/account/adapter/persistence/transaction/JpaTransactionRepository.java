package hexagonal.bank.context.account.adapter.persistence.transaction;

import hexagonal.bank.context.account.domain.transaction.Transaction;
import hexagonal.bank.context.account.domain.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JpaTransactionRepository implements TransactionRepository {

    @Override
    public void save(Transaction transaction) {

    }
}
