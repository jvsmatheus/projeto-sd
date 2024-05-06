package middlewares;


import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class JsonMiddleware {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String objectToJson(User object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static User jsonToObject(String json, Object object) {
        try {
            return (User) objectMapper.readValue(json, Object.class);
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON input: " + e.getMessage());
            return null;  // ou poderia lançar uma RuntimeException se preferir tratar como erro fatal
        }
    }

    public static String constructJson(Map<String, String> jsonFields) throws IOException {
        StringWriter sw = new StringWriter();
        objectMapper.writeValue(sw, jsonFields); // mapper is a ObjectMapper instance
        return sw.toString();
    }

    public static String stringToJson(Map<String, String> fields) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(fields);
    }
}
