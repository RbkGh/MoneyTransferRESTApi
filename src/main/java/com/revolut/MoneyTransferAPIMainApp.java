package com.revolut;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.controllers.AccountController;
import com.revolut.module.AppModule;

import static spark.Spark.*;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 19:00
 */
public class MoneyTransferAPIMainApp {

    public static final int MAIN_PORT = 8080;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        setupPortNumber();
        AccountController accountController = injector.getInstance(AccountController.class);

        post("/accounts", accountController::createAccount);
        get("/accounts", accountController::getAllAccounts);
        get("/accounts/:id", accountController::getAcountById);
        delete("/accounts/:id", accountController::deleteAccountById);

        System.out.println("running on port " + port());
    }

    public static void setupPortNumber() {
        port(MAIN_PORT);
    }

    /**
     * use this to start spark app for endpoint testing
     */
    public static void startApp() {
        MoneyTransferAPIMainApp.main(null);
        awaitInitialization();
    }

    /**
     * stop spark server
     */
    public static void stopApp() {
        stop();
        awaitStop();
    }
}
