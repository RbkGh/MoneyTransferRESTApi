package com.revolut.util;

import com.revolut.model.EndpointOperationResponsePayload;
import spark.Response;

/**
 * author: acerbk
 * Date: 2019-08-26
 * Time: 16:57
 * Single entry point for all endpoint responses
 */
public interface ResponseCreator {
    /**
     * respond with a consistent format in all http endpoints
     *
     * @param response
     * @param endpointOperationResponsePayload
     * @return
     */
    String respondToHttpEndpoint(Response response, EndpointOperationResponsePayload endpointOperationResponsePayload);
}
