package hexagonal.bank.context.account.domain.account.basic;

import hexagonal.bank.context.account.domain.account.Account;
import hexagonal.bank.context.account.domain.account.CalculatedBalanceRepository;
import hexagonal.bank.context.account.domain.account.basic.BasicAccountWithdrawOperation.BasicAccountWithdrawFailure.MaxAmountExceeded;
import hexagonal.bank.context.account.domain.transaction.Transaction;
import hexagonal.bank.context.account.domain.transaction.TransactionCode;
import hexagonal.bank.context.account.domain.transaction.TransactionRepository;
import hexagonal.bank.context.account.domain.transaction.withdraw.WithdrawOperation;
import hexagonal.bank.context.account.domain.transaction.withdraw.WithdrawOperation.WithdrawFailure.InsufficientFunds;
import hexagonal.bank.context.common.domain.money.Money;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class BasicAccountWithdrawOperation extends WithdrawOperation {

    private static final Money MAX_WITHDRAM_AMOUNT = Money.TEN;

    private final TransactionRepository transactionRepository;
    private final CalculatedBalanceRepository calculatedBalanceRepository;

    @Override
    public Either<WithdrawFailure, Transaction> execute(Account account, Money amount) {
        if (amount.compareTo(MAX_WITHDRAM_AMOUNT) > 0) {
            return Either.left(new MaxAmountExceeded("Max amount exceeded"));
        }

        var calculatedBalance = calculatedBalanceRepository.getByAccountId(account.getId());
        if (calculatedBalance.getValue().subtract(amount).compareTo(Money.ZERO) < 0) {
            return Either.left(new InsufficientFunds("Insufficient funds"));
        }

        var transaction = new Transaction(TransactionCode.generate(), amount.negate(), Transaction.Type.WITHDRAWAL, LocalDateTime.now());
        calculatedBalance.apply(transaction);
        transactionRepository.save(transaction);
        calculatedBalanceRepository.save(calculatedBalance);

        return Either.right(transaction);
    }

    public sealed interface BasicAccountWithdrawFailure extends WithdrawFailure {
        record MaxAmountExceeded(String message) implements BasicAccountWithdrawFailure {}
    }

}
