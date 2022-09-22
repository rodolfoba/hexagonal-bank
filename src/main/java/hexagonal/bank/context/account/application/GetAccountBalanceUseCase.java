package hexagonal.bank.context.account.application;

import framework.usecase.UseCase;
import hexagonal.bank.context.account.domain.account.AccountId;
import hexagonal.bank.context.common.domain.failure.ResourceNotFoundFailure;
import hexagonal.bank.context.common.domain.money.Money;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class GetAccountBalanceUseCase implements UseCase<GetAccountBalanceUseCase.InputData, GetAccountBalanceUseCase.Presenter> {

    @Override
    @Transactional
    public void execute(@NonNull InputData inputData, @NonNull Presenter presenter) {
        // TODO
        presenter.present(new SuccessResult(Money.TEN, LocalDateTime.now()));
    }

    public record InputData(AccountId accountId) implements UseCase.InputData {}
    public record SuccessResult(Money accountBalance, LocalDateTime balanceTime) {}

    public interface Presenter extends UseCase.Presenter {
        void present(SuccessResult result);
        void present(ResourceNotFoundFailure failure);
    }

}
