package hexagonal.bank.context.customer.domain;

import framework.valueobject.ValueObject;
import lombok.NonNull;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record CustomerId(@NonNull UUID value) implements Serializable, ValueObject<UUID> {

    public CustomerId() {
        this(UUID.randomUUID());
    }
}

