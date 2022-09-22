package hexagonal.bank.context.common.domain.money;

import lombok.NonNull;

import java.math.BigDecimal;

public record Money(@NonNull BigDecimal value) {

    public static Money ZERO = new Money(BigDecimal.ZERO);
    public static Money ONE = new Money(BigDecimal.ONE);
    public static Money TEN = new Money(BigDecimal.TEN);

    public int compareTo(Money money) {
        return this.value.compareTo(money.value());
    }

    public Money negate() {
        return new Money(this.value.negate());
    }

    public Money add(@NonNull Money amount) {
        return new Money(this.value.add(amount.value()));
    }

    public Money subtract(@NonNull Money amount) {
        return new Money(this.value.subtract(amount.value()));
    }
}
