package com.chandramouli.urlshortner.utility;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

public class MapperUtil {

    private static final JsonFactory factory = new JsonFactory();
    private static final ObjectMapper mapper = newMapperInstance();

    private static final TypeReference<JsonMap<String, Object>> typeRef = new TypeReference<>() {
    };

    public static JsonMap<String, Object> readValueToMap(String json) {
        if (json == null || json.length() == 0) return null;
        try {
            return mapper.readValue(json, typeRef);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T readValue(String json, Class<T> tClass) {
        if (json == null || json.length() == 0) return null;
        try {
            return mapper.readValue(json, tClass);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T readValue(String json, TypeReference<T> typeReference) {
        if (json == null || json.length() == 0) return null;
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String toJson(Object o) {
        if (o == null) return null;
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }

    public static void readToExistingObject(String fromJson, Object toObject) {
        if (toObject == null || fromJson == null)
            throw new RuntimeException("Either toObject or json string is null");
        try {
            mapper.readerForUpdating(toObject).readValue(fromJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static ObjectMapper newMapperInstance() {
        ObjectMapper mapper = new ObjectMapper(factory);

        DateFormat df = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss a zz");
        df.setTimeZone(TimeZone.getDefault());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);

        return mapper;
    }

    public static Map<String, Object> convertToMap(Object obj) {
        if (obj == null) return null;
        return mapper.convertValue(obj, new TypeReference<>() {});
    }
}
