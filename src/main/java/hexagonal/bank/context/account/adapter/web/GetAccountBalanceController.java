package hexagonal.bank.context.account.adapter.web;

import hexagonal.bank.context.account.application.GetAccountBalanceUseCase;
import hexagonal.bank.context.account.application.GetAccountBalanceUseCase.InputData;
import hexagonal.bank.context.account.domain.account.AccountId;
import hexagonal.bank.infrastructure.rest.AbstractRestPresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestScope
@RequestMapping(AccountWebAdapterConstants.PATH)
@RequiredArgsConstructor
class GetAccountBalanceController {

    private final GetAccountBalanceUseCase useCase;
    private final Presenter presenter;

    @GetMapping("/{id}/balance")
    ResponseEntity<Object> handle(@PathVariable(name = "id") Long accountId) {
        var inputData = new InputData(new AccountId(accountId));

        useCase.execute(inputData, presenter);
        return presenter.getResponseEntity();
    }

    @Component
    @RequestScope
    static class Presenter extends AbstractRestPresenter implements GetAccountBalanceUseCase.Presenter {

        @Override
        public void present(GetAccountBalanceUseCase.SuccessResult result) {
            setResponseEntity(ResponseEntity.ok(new ResponseDto(result.accountBalance().value().toPlainString(), result.balanceTime().toString())));
        }

    }

    private record ResponseDto(String balance, String instant) {}

}
