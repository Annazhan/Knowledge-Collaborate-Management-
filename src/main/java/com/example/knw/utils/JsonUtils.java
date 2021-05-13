package com.example.knw.utils;

import com.example.knw.pojo.Msg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Json转化类
 *
 * @author qanna
 * @date 2021-04-07
 */
@Component
public class JsonUtils {
    private ObjectMapper mapper = new ObjectMapper();

    public <T> T jsonString2Object(String jsonString, String key, Class<T> classType) throws JsonProcessingException {
        if(key != null){
            JsonNode node = mapper.readTree(jsonString).path(key);
            return mapper.treeToValue(node, classType);
        }
        JsonNode node = mapper.readTree(jsonString);
        return mapper.treeToValue(node, classType);
    }

    public <T> String object2Json(T object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

}
