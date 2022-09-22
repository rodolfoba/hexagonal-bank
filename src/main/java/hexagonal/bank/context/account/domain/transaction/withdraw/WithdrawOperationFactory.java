package hexagonal.bank.context.account.domain.transaction.withdraw;

import hexagonal.bank.context.account.domain.account.Account;
import hexagonal.bank.context.account.domain.account.CalculatedBalanceRepository;
import hexagonal.bank.context.account.domain.account.basic.BasicAccountWithdrawOperation;
import hexagonal.bank.context.account.domain.account.premium.PremiumAccountWithdrawOperation;
import hexagonal.bank.context.account.domain.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithdrawOperationFactory {

    private final CalculatedBalanceRepository calculatedBalanceRepository;
    private final TransactionRepository transactionRepository;

    public WithdrawOperation getInstance(Account account) {
        return switch (account.getType()) {
            case BASIC -> new BasicAccountWithdrawOperation(transactionRepository, calculatedBalanceRepository);
            case PREMIUM -> new PremiumAccountWithdrawOperation();
        };
    }
}
