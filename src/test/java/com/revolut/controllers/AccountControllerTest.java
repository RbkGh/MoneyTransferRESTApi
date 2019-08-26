package com.revolut.controllers;

import com.revolut.MoneyTransferAPIMainApp;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


/**
 * author: acerbk
 * Date: 2019-08-25
 * Time: 01:16
 */
public class AccountControllerTest {

    @Before
    public void setUp() {
        MoneyTransferAPIMainApp.startApp();
    }

    @After
    public void tearDown() {
        MoneyTransferAPIMainApp.stopApp();
    }

    @Test
    public void create_account_through_http_endpoint_expect_400_status() {

        given().
                when().
                body("{name:rodney}").
                post("/account").
                then().
                assertThat().statusCode(400);

    }

    @Test
    public void get_all_accounts_through_http_endpoint_expect_empty() {

        Response res = get("/account");
        String body = res.asString();
        assertEquals(body, "[]");

    }

    @Test
    public void create_account_through_http_endpoint_expect_success_201_status() {

        given().
                when().
                body("{name:rodney,emailAddress:rodney@gmail.com}").
                post("/account").
                then().
                assertThat().statusCode(201);

    }

}
