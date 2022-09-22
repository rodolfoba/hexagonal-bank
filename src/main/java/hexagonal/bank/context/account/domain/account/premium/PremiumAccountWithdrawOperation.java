package hexagonal.bank.context.account.domain.account.premium;

import hexagonal.bank.context.account.domain.account.Account;
import hexagonal.bank.context.account.domain.transaction.Transaction;
import hexagonal.bank.context.account.domain.transaction.withdraw.WithdrawOperation;
import hexagonal.bank.context.common.domain.money.Money;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PremiumAccountWithdrawOperation extends WithdrawOperation {

    @Override
    public Either<WithdrawFailure, Transaction> execute(Account account, Money amount) {
        return null;
    }
}
