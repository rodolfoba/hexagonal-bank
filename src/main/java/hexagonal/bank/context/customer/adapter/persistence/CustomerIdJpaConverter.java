package hexagonal.bank.context.customer.adapter.persistence;

import hexagonal.bank.context.customer.domain.CustomerId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class CustomerIdJpaConverter implements AttributeConverter<CustomerId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(CustomerId attribute) {
        return attribute.value();
    }

    @Override
    public CustomerId convertToEntityAttribute(UUID dbData) {
        return new CustomerId(dbData);
    }
}
