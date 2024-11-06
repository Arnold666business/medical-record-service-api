package com.example.medicalrecordservice.exception.disease;

public class DataValidateException extends RuntimeException {
    public DataValidateException(String message){
        super(message);
    }
}
