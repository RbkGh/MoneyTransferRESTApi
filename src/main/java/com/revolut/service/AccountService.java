package com.revolut.service;

import com.revolut.domain.AccountEntity;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.model.ErrorOperationWithReasonPayload;
import com.revolut.model.SuccessfulOperationWithEmptyBodyPayload;
import com.revolut.model.SuccessfulOperationWithJSONBodyResponsePayload;
import com.revolut.repository.AccountRepository;
import com.revolut.util.EmailValidator;
import com.revolut.util.JsonParser;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 19:11
 */
public class AccountService {

    private final AccountRepository accountRepository;
    private JsonParser jsonParser;

    @Inject
    AccountService(AccountRepository accountRepository, JsonParser jsonParser) {
        this.accountRepository = accountRepository;
        this.jsonParser = jsonParser;
    }

    public EndpointOperationResponsePayload createAccount(AccountEntity accountEntity) {
        Map<Boolean, String> isAccountPropertiesValidMap = isAccountEntityPropertiesValid(accountEntity);
        if (isAccountPropertiesValidMap.containsKey(true)) {
            this.accountRepository.saveAccount(accountEntity);
            return new SuccessfulOperationWithEmptyBodyPayload(201);
        }
        return new ErrorOperationWithReasonPayload(400, isAccountPropertiesValidMap.get(false));
    }

    public EndpointOperationResponsePayload getAllAccounts() {
        List<AccountEntity> accountEntities = this.accountRepository.getAllAccounts();
        if (accountEntities.isEmpty())
            return new SuccessfulOperationWithJSONBodyResponsePayload(200, "[]");
        return new SuccessfulOperationWithJSONBodyResponsePayload(200, this.jsonParser.toJSONString(accountEntities));
    }

    public EndpointOperationResponsePayload getAccountById(String id) {
        AccountEntity accountEntity = this.accountRepository.getAccountById(Long.valueOf(id));
        if (Objects.nonNull(accountEntity))
            return new SuccessfulOperationWithJSONBodyResponsePayload(200, this.jsonParser.toJSONString(accountEntity));
        return new ErrorOperationWithReasonPayload(404, "Account with id=" + id + " not found.");
    }

    public EndpointOperationResponsePayload deleteAccountById(String id) {
        if (Objects.nonNull(accountRepository.getAccountById(Long.valueOf(id))))
            return new SuccessfulOperationWithEmptyBodyPayload(200);
        return new ErrorOperationWithReasonPayload(404, "Account with id =" + id + " not found.");
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
