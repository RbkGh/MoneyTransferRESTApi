package com.revolut.controllers;

import com.revolut.MoneyTransferAPIMainApp;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;


/**
 * author: acerbk
 * Date: 2019-08-25
 * Time: 01:16
 */
public class AccountControllerTest {

    public static final String ACCOUNTS_ENDPOINT = "/accounts";


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
                post(ACCOUNTS_ENDPOINT).
                then().
                assertThat().statusCode(400);

    }

    @Test
    public void get_all_accounts_through_http_endpoint_expect_empty() {

        Response res = get(ACCOUNTS_ENDPOINT);
        String body = res.asString();
        assertEquals(body, "[]");

    }

    @Test
    public void create_account_through_http_endpoint_expect_success_201_status() {

        given().
                when().
                body("{name:rodney,emailAddress:rodney@gmail.com}").
                post(ACCOUNTS_ENDPOINT).
                then().
                assertThat().statusCode(201);

    }

    @Test
    public void get_account_by_id_through_http_endpoint_expect_404_status() {

        String id = "1";
        Response res = post(ACCOUNTS_ENDPOINT + "/" + id);

        assertEquals(res.statusCode(), 404);
    }

    @Test
    public void delete_account_by_id_through_http_endpoint_expect_404_status() {

        String id = "1";
        Response res = delete(ACCOUNTS_ENDPOINT + "/" + id);

        assertEquals(res.statusCode(), 404);
    }

    @Test
    public void create_money_transfer_transaction_through_http_endpoint_expect_404_status() {

        String accountId = "3";

        Response resp = given().
                when().
                body("{sendingAccountId:3,receivingAccountId:2,transactionAmount:300}").
                post(ACCOUNTS_ENDPOINT + "/" + accountId + "/transactions");

        assertEquals(404, resp.getStatusCode());
    }


    @Test
    public void get_all_money_transFer_transactions_through_http_endpoint_expect_404_status() {


        String accountId = "3";
        Response resp = given().
                when().
                get(ACCOUNTS_ENDPOINT + "/" + accountId + "/transactions");

        System.out.println("response body--"+resp.asString());
        assertEquals(404, resp.getStatusCode());
    }

}
