package com.example.medicalrecordservice.validation.constants;

public class ExceptionValidationMessages {
    public static final String INCORRECT_SIZE = "The field size is incorrect";

    public static final String NOT_NULL = "This field cannot be null";

    public static final String NOT_BLANK = "The field must not be empty";

    public static final String NOT_IN_FUTURE = "The field must not be in the future tense.";

    public static final String INCORRECT_FORMAT = "The format is incorrect. Only letters are allowed"; //"^[A-Za-zА-Яа-яЁё]+$"

    public static final String OMC_POLICY = "The policy consists of 16 consecutive digits"; //"^\\d{16}$"

    public static final String MKB10_POLICY = "The format is incorrect. Only letters and numbers are allowed"; //"^[A-Za-zА-Яа-яЁё0-9]+$"

    public static final String UUID_POLICY = "Invalid format for uuid"; //^[0-9a-fA-F]{8}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{4}\b-[0-9a-fA-F]{12}$

}
