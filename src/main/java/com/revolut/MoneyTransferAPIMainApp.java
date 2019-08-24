package com.revolut;


import static spark.Spark.get;
import static spark.Spark.port;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 19:00
 */
public class MoneyTransferAPIMainApp {

    public static void main(String[] args) {
        System.out.println("running");
        port(8080);
        get("/hello", (request, response) -> {
            response.status(300);
            return "{}";
        });

    }
}
