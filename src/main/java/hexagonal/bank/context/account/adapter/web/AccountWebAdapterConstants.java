package hexagonal.bank.context.account.adapter.web;

import hexagonal.bank.infrastructure.web.WebAdapterConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class AccountWebAdapterConstants {

    static final String PATH = WebAdapterConstants.PATH + "/account";

}
