package hexagonal.bank.context.account.adapter.persistence.account;

import hexagonal.bank.context.account.domain.account.Account;
import hexagonal.bank.context.account.domain.account.AccountId;
import hexagonal.bank.context.account.domain.account.AccountRepository;
import hexagonal.bank.context.customer.adapter.persistence.CustomerIdMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class JpaAccountRepository implements AccountRepository {

    private final AccountMapper accountMapper;
    private final DelegateJpaAccountRepository delegate;

    @Override
    public Optional<Account> findById(AccountId accountId) {
        var possibleAccountJpa = delegate.findById(accountId.value());
        return possibleAccountJpa.map(accountMapper::map);
    }

    @Mapper(uses = CustomerIdMapper.class)
    interface AccountMapper {

        @Mapping(target = "customerId", source = "customerId")
        @Mapping(target = "type", source = "type")
        Account map(AccountJpa entity);

        @Mapping(target = "value", source = "id")
        AccountId map(Long id);

    }

}
