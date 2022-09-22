package hexagonal.bank.context.account.adapter.web;

import hexagonal.bank.context.account.application.DepositUseCase;
import hexagonal.bank.context.account.application.DepositUseCase.Success;
import hexagonal.bank.context.account.domain.account.AccountId;
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

@RestController
@RequestScope
@RequestMapping("/api/account")
@RequiredArgsConstructor
class DepositController {

    private final DepositUseCase useCase;
    private final Presenter presenter;

    @PostMapping("/{id}/deposit")
    ResponseEntity<Object> handle(@PathVariable(name = "id") Long accountId) {
        var inputData = new DepositUseCase.InputData(new AccountId(accountId), Money.ONE);
        useCase.execute(inputData, presenter);
        return presenter.getResponseEntity();
    }

    @Component
    @RequestScope
    @RequiredArgsConstructor
    static class Presenter extends AbstractRestPresenter implements DepositUseCase.Presenter {

        private final DtoMapper mapper;

        @Override
        public void present(Success result) {
            setResponseEntity(ResponseEntity.ok(mapper.toRestDto(result)));
        }
    }

    @Mapper
    interface DtoMapper {

        @Mapping(target = "receipt", source = "receipt.value")
        @Mapping(target = "date", source = "time")
        ResponseRestDto toRestDto(DepositUseCase.Success result);

    }

    record ResponseRestDto(String receipt, String date) {}

}
