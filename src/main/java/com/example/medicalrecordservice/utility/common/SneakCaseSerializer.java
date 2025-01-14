package com.example.medicalrecordservice.utility.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SneakCaseSerializer extends JsonSerializer<String> {
    private final static Logger logger = LoggerFactory.getLogger(SneakCaseSerializer.class);

    @Override
    public void serialize(String value, JsonGenerator generator, SerializerProvider serializerProvider) {
        try {
            String snakeCaseValue = toSnakeCase(value);
            generator.writeString(snakeCaseValue);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

    }

    private String toSnakeCase(String value) {
        return value.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

}
