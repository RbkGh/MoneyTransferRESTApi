package com.revolut.util;

import com.google.gson.Gson;

/**
 * author: acerbk
 * Date: 2019-08-24
 * Time: 23:06
 */
public class JsonParserImpl implements JsonParser {


    @Override
    public <T> T toJsonPOJO(String jsonString, Class<T> classType) {
        return new Gson().fromJson(jsonString, classType);
    }

    public String toJSONString(Object data){
        return new Gson().toJson(data);
    }
}
