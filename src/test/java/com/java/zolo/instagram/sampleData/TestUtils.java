package com.java.zolo.instagram.sampleData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zolo.alpha.api.ResponseBody;

public class TestUtils {

    public static <S> String convertToJsonString(S Object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json_String = mapper.writeValueAsString(Object);
        return json_String;
    }
}

