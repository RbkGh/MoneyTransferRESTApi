package com.revolut.util;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 23:03
 * Base JsonParser to convert string to and from string.
 * This is used so that in the future we could slot in different implementations
 * of the parser.Eg we can slot in Jackson framework for Gson by making a new implementation.
 */
public interface JsonParser {

    /**
     * convert string to POJO
     *
     * @param jsonString - string to convert to POJO
     * @param classType  - POJO Type / Class Type to use for the deserialization
     * @param <T>        the returned desirialized POJO
     * @return desiarilized POJO
     */
    <T> T toJsonPOJO(String jsonString, Class<T> classType);

    /**
     * convert from POJO to json
     * @param data POJO to convert to json String
     * @return json string
     */
    String toJSONString(Object data);
}
