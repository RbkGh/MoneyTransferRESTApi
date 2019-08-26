package com.revolut.model;

/**
 * author: acerbk
 * Date: 2019-08-26
 * Time: 03:46
 */
public class ErrorOperationWithReasonPayload extends EndpointOperationResponsePayload {
    public ErrorOperationWithReasonPayload(int statusCode, String errorReason) {
        super(statusCode, "", errorReason);
    }
}
