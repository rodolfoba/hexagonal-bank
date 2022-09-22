package hexagonal.bank.context.account.adapter.web;

import hexagonal.bank.context.account.application.GetAccountBalanceUseCase;
import hexagonal.bank.context.account.application.GetAccountBalanceUseCase.InputData;
import hexagonal.bank.context.account.application.GetAccountBalanceUseCase.SuccessResult;
import hexagonal.bank.context.account.domain.account.AccountId;
import hexagonal.bank.context.common.domain.failure.ResourceNotFoundFailure;
import hexagonal.bank.context.common.domain.money.Money;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GetAccountBalanceController.class)
class GetAccountBalanceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GetAccountBalanceUseCase useCase;

    @Autowired
    GetAccountBalanceUseCase.Presenter presenter;

    @Test
    void accountNotFound() throws Exception {
        var id = 0L;
        var expectedInputData = new InputData(new AccountId(id));

        doAnswer(answer((i) -> presenter.present(new ResourceNotFoundFailure("Account not found"))))
            .when(useCase).execute(any(), any());

        mockMvc.perform(get(url(id))).andExpect(status().isNotFound());

        verify(useCase, times(1)).execute(expectedInputData, presenter);
    }

    @Test
    void success() throws Exception {
        var id = 1L;
        var expectedInputData = new InputData(new AccountId(id));

        doAnswer(answer((i) -> presenter.present(new SuccessResult(Money.TEN, LocalDateTime.parse("2022-01-01T00:00:00.000")))))
            .when(useCase).execute(any(), any());

        mockMvc.perform(get(url(id))).andExpect(status().isOk());

        verify(useCase, times(1)).execute(expectedInputData, presenter);
    }

    private Answer<Object> answer(Consumer<InvocationOnMock> consumer) {
        return invocation -> {
            consumer.accept(invocation);
            return null;
        };
    }

    private String url(long id) {
        return MessageFormat.format("{0}/{1}/balance", AccountWebAdapterConstants.PATH, id);
    }

}