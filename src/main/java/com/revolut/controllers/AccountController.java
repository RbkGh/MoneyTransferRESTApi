package com.revolut.controllers;

import spark.Request;
import spark.Response;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 22:28
 */
public interface AccountController {

    String createAccount(Request request, Response response);

    String getAllAccounts(Request request, Response response);

}
