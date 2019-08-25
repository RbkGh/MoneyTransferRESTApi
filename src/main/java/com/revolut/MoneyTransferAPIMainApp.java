package com.revolut;


import com.revolut.controllers.AccountController;
import com.revolut.controllers.AccountControllerImpl;
import com.revolut.domain.AccountEntity;
import com.revolut.model.EndpointOperationResponsePayload;
import com.revolut.repository.AccountReposityDefaultImpl;
import com.revolut.service.AccountService;
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

        port(8080);
        System.out.println("running on port 8080");

        post("/account", "application/json", (request, response) -> {

            AccountController accountController = new AccountControllerImpl(new AccountService(new AccountReposityDefaultImpl()));

            JsonParser jsonParser = new JsonParserImpl();

            AccountEntity accountEntity = jsonParser.toJsonPOJO(request.body(), AccountEntity.class);
            EndpointOperationResponsePayload endpointOperationResponsePayload = accountController.createAccount(accountEntity);

            response.type("application/json");
            response.status(endpointOperationResponsePayload.getStatusCode());


            System.out.println(endpointOperationResponsePayload.getReason());


            if (endpointOperationResponsePayload.getData() == null) {
                return endpointOperationResponsePayload.getReason();
            } else {
                return jsonParser.toJSONString(endpointOperationResponsePayload.getData());
            }
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
