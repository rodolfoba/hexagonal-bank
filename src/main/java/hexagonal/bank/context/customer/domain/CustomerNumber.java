package hexagonal.bank.context.customer.domain;

import framework.valueobject.ValueObject;

public record CustomerNumber(Long value) implements ValueObject<Long> {
}
