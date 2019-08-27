package com.revolut.service;

import com.revolut.domain.AccountTransactionEntity;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.model.ErrorOperationWithReasonPayload;
import com.revolut.model.SuccessfulOperationWithJSONBodyResponsePayload;
import com.revolut.repository.AccountEntityRepository;
import com.revolut.repository.AccountTransactionRepository;
import com.revolut.util.JsonParser;

import javax.inject.Inject;
import java.util.List;

/**
 * author: acerbk
 * Date: 2019-08-26
 * Time: 21:39
 */
public class AccountTransactionService {

    private AccountEntityRepository accountEntityRepository;
    private AccountTransactionRepository accountTransactionRepository;
    private JsonParser jsonParser;

    @Inject
    public AccountTransactionService(AccountEntityRepository accountEntityRepository,
                                     AccountTransactionRepository accountTransactionRepository,
                                     JsonParser jsonParser) {
        this.accountEntityRepository = accountEntityRepository;
        this.accountTransactionRepository = accountTransactionRepository;
        this.jsonParser = jsonParser;
    }

    public EndpointOperationResponsePayload getAccountTransactionsByAccountId(String accountId) {
        if (accountEntityRepository.doesAccountExistById(Long.valueOf(accountId))) {

            List<AccountTransactionEntity> accountTransactionEntities = accountTransactionRepository.
                    getAccountTransactionsByAccountId(Long.valueOf(accountId));

            String messageBodyWhenListIsEmpty = "[]";
            String messageBodyWhenListItemmsAreAvailable = jsonParser.toJSONString(accountTransactionEntities);

            return accountTransactionEntities.isEmpty()
                    ? new SuccessfulOperationWithJSONBodyResponsePayload(200, messageBodyWhenListIsEmpty)
                    : new SuccessfulOperationWithJSONBodyResponsePayload(200, messageBodyWhenListItemmsAreAvailable);

        }
        return new ErrorOperationWithReasonPayload(404, "Account with id = " + accountId + " does not exist");
    }
}
