package com.revolut.model;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 20:04
 * Use this as base class for all endpoint responses by <br>
 * setting response status code into {@link EndpointOperationResponsePayload#statusCode} field <br>
 * and the appropriate data into {@link EndpointOperationResponsePayload#data} field
 */
public class EndpointOperationResponsePayload {

    private int statusCode;

    private Object data;

    /**
     * any extra reason to be passed to client,
     * eg. reason why entity did not save in backend.
     */
    private String reason=null;

    public EndpointOperationResponsePayload(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public EndpointOperationResponsePayload(int statusCode, Object data, String reason) {
        this.statusCode = statusCode;
        this.data = data;
        this.reason = reason;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public EndpointOperationResponsePayload setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Object getData() {
        return data;
    }

    public EndpointOperationResponsePayload setData(Object data) {
        this.data = data;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public EndpointOperationResponsePayload setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
