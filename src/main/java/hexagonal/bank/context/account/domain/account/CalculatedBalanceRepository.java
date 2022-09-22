package hexagonal.bank.context.account.domain.account;

public interface CalculatedBalanceRepository {

    CalculatedBalance getByAccountId(AccountId accountId);
    void save(CalculatedBalance referencedBalance);

}
