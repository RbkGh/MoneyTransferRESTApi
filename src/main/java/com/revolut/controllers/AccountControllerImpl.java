package com.revolut.controllers;

import com.revolut.domain.AccountEntity;
import com.revolut.domain.AccountTransactionEntity;
import com.revolut.service.AccountService;
import com.revolut.service.AccountTransactionService;
import com.revolut.util.JsonParser;
import com.revolut.util.ResponseCreator;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 22:51
 */
public class AccountControllerImpl implements AccountController {

    private AccountService accountService;
    private JsonParser jsonParser;
    private ResponseCreator responseCreator;
    private AccountTransactionService accountTransactionService;

    @Inject
    AccountControllerImpl(AccountService accountService, JsonParser jsonParser, ResponseCreator responseCreator, AccountTransactionService accountTransactionService) {
        this.accountService = accountService;
        this.jsonParser = jsonParser;
        this.responseCreator = responseCreator;
        this.accountTransactionService = accountTransactionService;
    }

    @Override
    public String createAccount(Request request, Response response) {
        AccountEntity accountEntity =
                jsonParser.toJsonPOJO(request.body(), AccountEntity.class);
        return responseCreator
                .respondToHttpEndpoint(response, accountService.createAccount(accountEntity));
    }

    @Override
    public String getAllAccounts(Request request, Response response) {
        return responseCreator
                .respondToHttpEndpoint(response, accountService.getAllAccounts());
    }

    @Override
    public String getAcountById(Request request, Response response) {
        return responseCreator
                .respondToHttpEndpoint(response, accountService.getAccountById(request.params("id")));
    }

    @Override
    public String deleteAccountById(Request request, Response response) {
        return responseCreator
                .respondToHttpEndpoint(response, accountService.deleteAccountById(request.params("id")));
    }

    @Override
    public String createAccountTransaction(Request request, Response response) throws Exception {
        AccountTransactionEntity accountTransactionEntity =
                jsonParser.toJsonPOJO(request.body(), AccountTransactionEntity.class);

        String accountId = request.params("id");

        return responseCreator
                .respondToHttpEndpoint(response, accountService.createAccountTransaction(accountId, accountTransactionEntity));
    }

    @Override
    public String getAllTransactionsOfAccount(Request request, Response response) {
        String accountId = request.params("id");

        return responseCreator
                .respondToHttpEndpoint(response, accountTransactionService.getAccountTransactionsByAccountId(accountId));
    }


}
