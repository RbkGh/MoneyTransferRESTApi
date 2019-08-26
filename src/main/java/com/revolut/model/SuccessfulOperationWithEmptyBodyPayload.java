package com.revolut.model;

/**
 * author: acerbk
 * Date: 2019-08-26
 * Time: 03:44
 */
public class SuccessfulOperationWithEmptyBodyPayload  extends EndpointOperationResponsePayload{
    public SuccessfulOperationWithEmptyBodyPayload(int statusCode) {
        super(statusCode, "", "");
    }
}
