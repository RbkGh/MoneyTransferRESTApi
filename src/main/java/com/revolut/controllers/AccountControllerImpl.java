package com.revolut.controllers;

import com.revolut.domain.AccountEntity;
import com.revolut.service.AccountService;
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

    @Inject
    AccountControllerImpl(AccountService accountService, JsonParser jsonParser, ResponseCreator responseCreator) {
        this.accountService = accountService;
        this.jsonParser = jsonParser;
        this.responseCreator = responseCreator;
    }

    @Override
    public String createAccount(Request request, Response response) {
        AccountEntity accountEntity = jsonParser.toJsonPOJO(request.body(), AccountEntity.class);
        return responseCreator.respondToHttpEndpoint(response, accountService.createAccount(accountEntity));
    }

    @Override
    public String getAllAccounts(Request request, Response response) {

        return responseCreator.respondToHttpEndpoint(response, accountService.getAllAccounts());
    }

    @Override
    public String getAcountById(Request request, Response response) {
        System.out.println("id ==" + request.params("id"));
        return responseCreator.respondToHttpEndpoint(response, accountService.getAccountById(request.params("id")));
    }
}
