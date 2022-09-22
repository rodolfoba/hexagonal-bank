package hexagonal.bank.context.account.adapter.web;

import hexagonal.bank.context.account.application.WithdrawUseCase;
import hexagonal.bank.context.account.domain.transaction.withdraw.WithdrawOperation.WithdrawFailure.DailyLimitExceeded;
import hexagonal.bank.context.account.domain.transaction.withdraw.WithdrawOperation.WithdrawFailure.InsufficientFunds;
import hexagonal.bank.context.account.domain.account.AccountId;
import hexagonal.bank.context.account.domain.account.basic.BasicAccountWithdrawOperation.BasicAccountWithdrawFailure.MaxAmountExceeded;
import hexagonal.bank.context.common.domain.money.Money;
import hexagonal.bank.infrastructure.rest.AbstractRestPresenter;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import static org.springframework.http.HttpStatus.PAYLOAD_TOO_LARGE;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestScope
@RequestMapping("/api/account")
@RequiredArgsConstructor
class WithdrawController {

    private final WithdrawUseCase useCase;
    private final Presenter presenter;

    @PostMapping("/{id}/withdraw")
    ResponseEntity<Object> handle(@PathVariable(name = "id") Long accountId) {
        var inputData = new WithdrawUseCase.InputData(new AccountId(accountId), Money.ONE);
        useCase.execute(inputData, presenter);
        return presenter.getResponseEntity();
    }

    @Component
    @RequestScope
    @RequiredArgsConstructor
    static class Presenter extends AbstractRestPresenter implements WithdrawUseCase.Presenter {

        private final DtoMapper mapper;

        @Override
        public void present(WithdrawUseCase.Success result) {
            setResponseEntity(ResponseEntity.ok(mapper.toRestDto(result)));
        }

        @Override
        public void present(InsufficientFunds failure) {
            setResponseEntity(ResponseEntity.status(UNPROCESSABLE_ENTITY).body(failure.message()));
        }

        @Override
        public void present(DailyLimitExceeded failure) {
            setResponseEntity(ResponseEntity.status(TOO_MANY_REQUESTS).body(failure.message()));
        }

        @Override
        public void present(MaxAmountExceeded failure) {
            setResponseEntity(ResponseEntity.status(PAYLOAD_TOO_LARGE).body(failure.message()));
        }
    }

    @Mapper
    interface DtoMapper {

        @Mapping(target = "receipt", source = "receipt.value")
        @Mapping(target = "date", source = "time")
        ResponseRestDto toRestDto(WithdrawUseCase.Success result);

    }

    record ResponseRestDto(String receipt, String date) {}

}
