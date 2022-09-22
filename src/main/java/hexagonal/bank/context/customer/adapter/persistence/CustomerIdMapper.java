package hexagonal.bank.context.customer.adapter.persistence;

import hexagonal.bank.context.customer.domain.CustomerId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface CustomerIdMapper {

    @Mapping(target = "value", source = "uuid")
    CustomerId map(UUID uuid);

}
