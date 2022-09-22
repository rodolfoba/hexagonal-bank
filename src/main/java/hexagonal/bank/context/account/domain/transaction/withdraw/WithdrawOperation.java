package hexagonal.bank.context.account.domain.transaction.withdraw;

import hexagonal.bank.context.account.domain.account.Account;
import hexagonal.bank.context.account.domain.transaction.Transaction;
import hexagonal.bank.context.common.domain.failure.BusinessFailure;
import hexagonal.bank.context.common.domain.money.Money;
import io.vavr.control.Either;

public abstract class WithdrawOperation {

    public abstract Either<WithdrawFailure, Transaction> execute(Account account, Money amount);

    public interface WithdrawFailure extends BusinessFailure {
        record DailyLimitExceeded(String message) implements WithdrawFailure {}
        record InsufficientFunds(String message) implements WithdrawFailure {}
    }
}
