package me.akkad.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;

public class Json {
    private static ObjectMapper mapper= defaultMapper();

    private static ObjectMapper defaultMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    public static <T> T readFile(String filePath,Class<T> clazz) throws IOException {
        File file = new File(filePath);
        return mapper.readValue(file, clazz);
    }
    public static JsonNode parse(String jsonSrc) throws JsonProcessingException {
        return mapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return mapper.treeToValue(node, clazz);
    }

    public static JsonNode parseObject(Object obj) {
        return mapper.valueToTree(obj);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException {
        return genrateString(node,false);
    }
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return genrateString(node,true);
    }

    private static String genrateString(JsonNode node,boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = mapper.writer();
        if (pretty) {
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(node);
    }
}
