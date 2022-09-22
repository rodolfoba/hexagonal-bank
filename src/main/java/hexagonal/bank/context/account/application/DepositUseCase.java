package hexagonal.bank.context.account.application;

import framework.usecase.UseCase;
import hexagonal.bank.context.account.domain.account.AccountId;
import hexagonal.bank.context.account.domain.transaction.TransactionCode;
import hexagonal.bank.context.common.domain.failure.BusinessFailure;
import hexagonal.bank.context.common.domain.failure.ResourceNotFoundFailure;
import hexagonal.bank.context.common.domain.money.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DepositUseCase implements UseCase<DepositUseCase.InputData, DepositUseCase.Presenter> {

    @Override
    @Transactional
    public void execute(InputData inputData, Presenter presenter) {
        // TODO
    }

    public record InputData(AccountId accountId, Money amount) implements UseCase.InputData {}

    public record Success(TransactionCode receipt, LocalDateTime time) {}

    public interface Presenter extends UseCase.Presenter {
        void present(DepositUseCase.Success data);
        void present(ResourceNotFoundFailure data);
        void present(BusinessFailure failure);
    }

}
