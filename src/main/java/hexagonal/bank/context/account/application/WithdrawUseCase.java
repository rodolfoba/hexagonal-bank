package hexagonal.bank.context.account.application;

import framework.usecase.UseCase;
import hexagonal.bank.context.account.domain.account.CalculatedBalanceRepository;
import hexagonal.bank.context.account.domain.transaction.TransactionRepository;
import hexagonal.bank.context.account.domain.transaction.withdraw.WithdrawOperationFactory;
import hexagonal.bank.context.account.domain.account.Account;
import hexagonal.bank.context.account.domain.account.AccountId;
import hexagonal.bank.context.account.domain.account.AccountRepository;
import hexagonal.bank.context.account.domain.transaction.TransactionCode;
import hexagonal.bank.context.common.domain.failure.BusinessFailure;
import hexagonal.bank.context.common.domain.failure.ResourceNotFoundFailure;
import hexagonal.bank.context.common.domain.money.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static hexagonal.bank.context.account.domain.account.basic.BasicAccountWithdrawOperation.BasicAccountWithdrawFailure.DailyLimitExceeded;
import static hexagonal.bank.context.account.domain.account.basic.BasicAccountWithdrawOperation.BasicAccountWithdrawFailure.InsufficientFunds;
import static hexagonal.bank.context.account.domain.account.basic.BasicAccountWithdrawOperation.BasicAccountWithdrawFailure.MaxAmountExceeded;

@Service
@RequiredArgsConstructor
public class WithdrawUseCase implements UseCase<WithdrawUseCase.InputData, WithdrawUseCase.Presenter> {

    private final AccountRepository accountRepository;
    private final CalculatedBalanceRepository calculatedBalanceRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void execute(InputData inputData, Presenter presenter) {
        var possibleAccount = accountRepository.findById(inputData.accountId());

        possibleAccount.ifPresentOrElse(account -> execute(account, inputData.amount(), presenter),
            () -> presenter.present(new ResourceNotFoundFailure("Account not found")));
    }

    private void execute(Account account, Money amount, Presenter presenter) {
        var operation = new WithdrawOperationFactory(calculatedBalanceRepository, transactionRepository).getInstance(account);

        var result = operation.execute(account, amount);
        result.peek(transaction -> presenter.present(new Success(transaction.code(), transaction.dateTime())))
            .peekLeft(withdrawFailure -> {
                if (withdrawFailure instanceof DailyLimitExceeded failure) {
                    presenter.present(failure);
                } else if (withdrawFailure instanceof InsufficientFunds failure) {
                    presenter.present(failure);
                } else if (withdrawFailure instanceof MaxAmountExceeded failure) {
                    presenter.present(failure);
                } else {
                    presenter.present(withdrawFailure);
                }
            });
    }

    public record InputData(AccountId accountId, Money amount) implements UseCase.InputData {}

    public record Success(TransactionCode receipt, LocalDateTime time) {}

    public interface Presenter extends UseCase.Presenter {
        void present(Success result);
        void present(ResourceNotFoundFailure result);
        void present(BusinessFailure failure);
        void present(InsufficientFunds failure);
        void present(DailyLimitExceeded failure);
        void present(MaxAmountExceeded failure);
    }

}
