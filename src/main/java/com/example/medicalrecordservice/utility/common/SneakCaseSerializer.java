package com.example.medicalrecordservice.utility.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class SneakCaseSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator generator, SerializerProvider serializerProvider){
        try {
            String snakeCaseValue = toSnakeCase(value);
            generator.writeString(snakeCaseValue);
        } catch (IOException e){
           log.info(e.getMessage());
        }

    }

    private String toSnakeCase(String value) {
        return value.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
