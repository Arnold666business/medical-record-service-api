package com.example.medicalrecordservice.exception.common;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }

}
