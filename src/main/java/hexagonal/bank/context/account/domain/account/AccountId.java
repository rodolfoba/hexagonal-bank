package hexagonal.bank.context.account.domain.account;

import framework.valueobject.ValueObject;
import lombok.NonNull;

public record AccountId(@NonNull Long value) implements ValueObject<Long> {}

