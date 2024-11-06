package com.example.medicalrecordservice.validation.constants;

public class RegexpPatterns {
    public static final String name_pattern = "^[A-Za-zА-ЯЁа-яё]+(-[A-Za-zА-ЯЁа-яё]+)*$";

    public static final String omc_policy_pattern = "^\\d{16}$";

    public static final String mkb10_policy = "^[A-Za-zА-Яа-яЁё0-9]+$";
}
