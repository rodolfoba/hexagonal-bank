package hexagonal.bank.context.account.domain.transaction;

import lombok.NonNull;

import java.util.UUID;

public record TransactionCode(@NonNull UUID value) {

    public static TransactionCode generate() {
        return new TransactionCode(UUID.randomUUID());
    }

}
