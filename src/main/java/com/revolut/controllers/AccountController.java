package com.revolut.controllers;

import com.revolut.domain.AccountEntity;
import com.revolut.model.EndpointOperationResponsePayload;
import spark.Route;

import static spark.Spark.get;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 22:28
 */
public interface AccountController {

    EndpointOperationResponsePayload createAccount(AccountEntity accountEntity);

}
