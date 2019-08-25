package com.revolut.service;

import com.revolut.domain.AccountEntity;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.repository.AccountRepository;
import com.revolut.util.EmailValidator;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 19:11
 */
public class AccountService {

    private final AccountRepository accountRepository;

    @Inject
    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public EndpointOperationResponsePayload createAccount(AccountEntity accountEntity) {
        Map<Boolean, String> isAccountPropertiesValidMap = isAccountEntityPropertiesValid(accountEntity);
        if (isAccountPropertiesValidMap.containsKey(true)) {
            this.accountRepository.saveAccount(accountEntity);
            return new EndpointOperationResponsePayload(201, null, null);
        }
        return new EndpointOperationResponsePayload(400, null, isAccountPropertiesValidMap.get(false));
    }

    /**
     * validate account properties,
     * at each point,map returned will contain either a {@link Boolean#TRUE} or {@link Boolean#FALSE} only as key.
     *
     * @param accountEntity
     * @return
     */
    private Map<Boolean, String> isAccountEntityPropertiesValid(AccountEntity accountEntity) {

        //is name field null or empty?
        if (Objects.isNull(accountEntity.getName()) || accountEntity.getName().isEmpty())
            return new HashMap<Boolean, String>() {{
                put(false, "Name is required");
            }};

        //is email address field null or empty?
        if (Objects.isNull(accountEntity.getEmailAddress()) || accountEntity.getEmailAddress().isEmpty())
            return new HashMap<Boolean, String>() {{
                put(false, "Email address is required");
            }};

        //is email format valid?
        if (!EmailValidator.isValidEmailAddress(accountEntity.getEmailAddress()))
            return new HashMap<Boolean, String>() {{
                put(false, "Email address format is incorrect.Must be of format : email@email.com");
            }};

        //all checks completed ,thus return true
        return new HashMap<Boolean, String>() {{
            put(true, null);
        }};

    }
}
