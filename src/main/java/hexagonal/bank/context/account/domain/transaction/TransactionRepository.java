package hexagonal.bank.context.account.domain.transaction;

public interface TransactionRepository {

    void save(Transaction transaction);

}
