package hexagonal.bank.context.customer.adapter.persistence;

import hexagonal.bank.context.customer.domain.CustomerNumber;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomerNumberJpaConverter implements AttributeConverter<CustomerNumber, Long> {

    @Override
    public Long convertToDatabaseColumn(CustomerNumber attribute) {
        return attribute.value();
    }

    @Override
    public CustomerNumber convertToEntityAttribute(Long dbData) {
        return new CustomerNumber(dbData);
    }
}
