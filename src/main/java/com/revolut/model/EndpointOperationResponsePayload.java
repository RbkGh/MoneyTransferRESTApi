package com.revolut.model;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:04
 * Use this as base class for all endpoint responses by <br>
 * setting response status code into {@link EndpointOperationResponsePayload#statusCode} field <br>
 * and the appropriate endpointResponseBody into {@link EndpointOperationResponsePayload#endpointResponseBody} field
 */
public class EndpointOperationResponsePayload {

    /**
     * response status code to return to client
     */
    private int statusCode;

    /**
     * json formatted string to be sent as response body
     */
    private String endpointResponseBody;

    /**
     * any extra errorReason to be passed to client,
     * eg. errorReason why entity did not save in backend.
     */
    private String errorReason;

    public EndpointOperationResponsePayload(int statusCode, String endpointResponseBody) {
        this.statusCode = statusCode;
        this.endpointResponseBody = endpointResponseBody;
    }

    public EndpointOperationResponsePayload(int statusCode, String endpointResponseBody, String errorReason) {
        this.statusCode = statusCode;
        this.endpointResponseBody = endpointResponseBody;
        this.errorReason = errorReason;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public EndpointOperationResponsePayload setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getEndpointResponseBody() {
        return endpointResponseBody;
    }

    public EndpointOperationResponsePayload setEndpointResponseBody(String endpointResponseBody) {
        this.endpointResponseBody = endpointResponseBody;
        return this;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public EndpointOperationResponsePayload setErrorReason(String errorReason) {
        this.errorReason = errorReason;
        return this;
    }
}
