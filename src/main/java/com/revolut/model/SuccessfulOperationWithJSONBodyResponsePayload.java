package com.revolut.model;

/**
 * author: acerbk
 * Date: 2019-08-26
 * Time: 03:40
 *
 */
public class SuccessfulOperationWithJSONBodyResponsePayload extends EndpointOperationResponsePayload {

    public SuccessfulOperationWithJSONBodyResponsePayload(int statusCode, String endpointResponseBody) {
        super(statusCode, endpointResponseBody, "");
    }
}
