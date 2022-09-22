package hexagonal.bank.context.account.domain.transaction;

import hexagonal.bank.context.common.domain.money.Money;

import java.time.LocalDateTime;

public record Transaction(TransactionCode code, Money amount, Type type, LocalDateTime dateTime) {

    public enum Type {
        DEPOSIT, WITHDRAWAL
    }

}
