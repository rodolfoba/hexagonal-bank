package hexagonal.bank.context.account.domain.account;

import hexagonal.bank.context.account.domain.transaction.Transaction;
import hexagonal.bank.context.account.domain.transaction.TransactionCode;
import hexagonal.bank.context.common.domain.money.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CalculatedBalance {

    private final AccountId accountId;
    private TransactionCode reference;
    private Money value;

    public void apply(Transaction transaction) {
        value = value.add(transaction.amount());
        reference = transaction.code();
    }

}
