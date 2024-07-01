package com.shawn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.TimeZone;


public class JsonUtils {

    public static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static String toJson(Object object, String... excludeField) {
        try {
            // 创建一个不序列化sex和weight的过滤器
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(excludeField);
            // 将上面的过滤器和ID为myFilter的注解进行关联
            FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", filter);
            return mapper.writer(filters).writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T fromJson(String jsonString, Class<T> valueType) {
//        Assert.notNull(valueType, "valueType is null ");
        if (jsonString == null || "".equals(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T fromJson(InputStream is, Class<T> valueType) {
//        Assert.notNull(valueType, "valueType is null ");
//        Assert.notNull(is, "inputStream is null");
        try {
            return mapper.readValue(is, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T extends Collection<S>, S> T fromJson(InputStream is, Class<T> collectionType, Class<S> elementType) {
//        Assert.notNull(collectionType, "collectionType is null");
//        Assert.notNull(elementType, "elementType is null");
        if (is == null) {
            return null;
        }
        try {
            return mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(collectionType, elementType));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static <T extends Collection<S>, S> T fromJson(String jsonString, Class<T> collectionType, Class<S> elementType) {
//        Assert.notNull(collectionType, "collectionType is null");
//        Assert.notNull(elementType, "elementType is null");
        if (jsonString == null || "".equals(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(collectionType, elementType));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
//        Assert.notNull(typeReference, "typeReference is null");
        if (jsonString == null || "".equals(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}
