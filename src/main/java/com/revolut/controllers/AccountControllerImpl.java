package com.revolut.controllers;

import com.revolut.domain.AccountEntity;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.service.AccountService;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 22:51
 */
public class AccountControllerImpl implements AccountController {

    private AccountService accountService;

    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public EndpointOperationResponsePayload createAccount(AccountEntity accountEntity) {
        return accountService.createAccount(accountEntity);
    }
}