package com.example.medicalrecordservice.validation.customValidate;

import com.example.medicalrecordservice.controller.view.request.DiseaseGeneralInfo;
import com.example.medicalrecordservice.exception.disease.DataValidateException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckStartAndEndDateOfDisease implements ConstraintValidator<DateValidator, DiseaseGeneralInfo> {
    @Override
    public void initialize(DateValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(DiseaseGeneralInfo value, ConstraintValidatorContext context) {
        if (value.getEndDateOfDisease() != null && (value.getStartDateOfDisease().isAfter(value.getEndDateOfDisease()) || value.getStartDateOfDisease().isEqual(value.getEndDateOfDisease()))) {
            throw new DataValidateException("The dates are set incorrectly");
        }
        return true;
    }

}
