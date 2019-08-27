package com.revolut;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.controllers.AccountController;
import com.revolut.module.AppModule;
import spark.Filter;
import spark.Spark;

import java.util.HashMap;

import static spark.Spark.*;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 19:00
 */
public class MoneyTransferAPIMainApp {

    public static final int MAIN_PORT = 8080;

    public static void main(String[] args) {
        setupPortNumber();
        applyCORSFilter();
        initializeRoutes();
    }

    public static void initializeRoutes() {
        Injector injector = Guice.createInjector(new AppModule());
        AccountController accountController = injector.getInstance(AccountController.class);

        post("/accounts", accountController::createAccount);
        get("/accounts", accountController::getAllAccounts);
        get("/accounts/:id", accountController::getAcountById);
        delete("/accounts/:id", accountController::deleteAccountById);
        post("/accounts/:id/transactions", accountController::createAccountTransaction);
        get("/accounts/:id/transactions", accountController::getAllTransactionsOfAccount);
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

    private static final HashMap<String, String> corsHeaders = new HashMap<>();

    static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
    }

    /**
     * apply right before routes initialization to prevent error.
     */
    public final static void applyCORSFilter() {
        Filter filter = (request, response) -> corsHeaders.forEach((key, value) -> response.header(key, value));
        Spark.after(filter);
    }
}
