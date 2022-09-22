package hexagonal.bank.context.account.domain.account;

import hexagonal.bank.context.customer.domain.CustomerId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Account {

    private final AccountId id;
    private final CustomerId customerId;
    private final Account.Type type;

    public enum Type {
        BASIC, PREMIUM
    }

}
