package Middlewares;


import Model.Candidato;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JsonMiddleware {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static String objectListToJson(List<Candidato> object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static Candidato jsonToObject(String json, Object object) {
        try {
            return (Candidato) objectMapper.readValue(json, Object.class);
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON input: " + e.getMessage());
            return null;  // ou poderia lançar uma RuntimeException se preferir tratar como erro fatal
        }
    }

    public static String mapToJson(Map<String, String> fields) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(fields);
    }

    public static JsonNode stringToJsonNode(String json) throws JsonProcessingException {
        JsonNode node = objectMapper.readTree(json);
        return node;
    }


}
