package com.revolut.controllers;

import com.revolut.MoneyTransferAPIMainApp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

/**
 * author: acerbk
 * Date: 2019-08-25
 * Time: 01:16
 */
public class AccountControllerTest {

    @Before
    public void setUp() throws Exception {
        MoneyTransferAPIMainApp.startApp();
    }

    @After
    public void tearDown() throws Exception {
        MoneyTransferAPIMainApp.stopApp();
    }

    @Test
    public void create_account_expect_400_status() {

        given().
                when().
                body("{name:rodney}").
                post("http://localhost:8080/account").
                then().
                assertThat().statusCode(400);

    }
}
