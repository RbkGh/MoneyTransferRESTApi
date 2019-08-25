package com.revolut;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.controllers.AccountController;
import com.revolut.domain.AccountEntity;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.module.AppModule;
import com.revolut.util.JsonParser;
import com.revolut.util.JsonParserImpl;

import static spark.Spark.*;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 19:00
 */
public class MoneyTransferAPIMainApp {


    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        port(8080);
        System.out.println("running on port 8080");

        post("/account", "application/json", (request, response) -> {

            AccountController accountController = injector.getInstance(AccountController.class);

            JsonParser jsonParser = injector.getInstance(JsonParserImpl.class);

            AccountEntity accountEntity = jsonParser.toJsonPOJO(request.body(), AccountEntity.class);
            EndpointOperationResponsePayload endpointOperationResponsePayload = accountController.createAccount(accountEntity);

            response.type("application/json");
            response.status(endpointOperationResponsePayload.getStatusCode());

            String messageResponseBodyWhenDataIsNull = endpointOperationResponsePayload.getReason();
            String messageResponseBodyWhenDataIsPresent = jsonParser.toJSONString(endpointOperationResponsePayload.getData());

            return (endpointOperationResponsePayload.getData() == null)
                    ? messageResponseBodyWhenDataIsNull
                    : messageResponseBodyWhenDataIsPresent;
        });

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
