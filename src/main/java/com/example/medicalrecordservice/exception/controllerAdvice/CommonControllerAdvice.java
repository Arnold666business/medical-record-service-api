package com.example.medicalrecordservice.exception.controllerAdvice;

import com.example.medicalrecordservice.exception.common.InternalServerException;
import com.example.medicalrecordservice.exception.error.ResponseError;
import com.example.medicalrecordservice.exception.patient.NotFoundPatientException;
import com.example.medicalrecordservice.validation.ValidationErrorResponse;
import com.example.medicalrecordservice.validation.Violation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class CommonControllerAdvice {
    @ExceptionHandler(NotFoundPatientException.class)
    public ResponseEntity<ResponseError> notFoundPatientHandler(NotFoundPatientException exception) {
        ResponseError response = ResponseError.builder().message(String.format("Patient with ID %s not found", exception.getPatientId())).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> constraintViolationExceptionHandler(ConstraintViolationException exception) {
        final List<Violation> violationList = exception.getConstraintViolations().stream().map(violation -> Violation.builder().message(violation.getMessage()).fieldName(violation.getPropertyPath().toString()).build()).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationErrorResponse.builder().violations(violationList).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        final List<Violation> violationList = exception.getBindingResult().getFieldErrors().stream().map(error -> Violation.builder().message(error.getDefaultMessage()).fieldName(error.getField()).build()).toList();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ValidationErrorResponse.builder().violations(violationList).build());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ResponseError> internalServerExceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseError.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseError> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException exception) {
        String message = "Method " + exception.getMethod() + " is not supported with with request";
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ResponseError.builder().message(message).errors(List.of()).build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseError> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseError.builder().message("Malformed JSON request").errors(List.of(exception.getMessage())).build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseError> noHandlerFoundExceptionHandler(NoHandlerFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseError.builder().message("Resource not found: " + exception.getRequestURL()).errors(List.of(exception.getLocalizedMessage())).build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseError> uuidExceptionHandler(MethodArgumentTypeMismatchException e) {
        String message = e.getName() + " should be of type " + e.getRequiredType().getName();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseError.builder().errors(List.of(e.getMessage())).message(message).build());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ResponseError> missingPathVariableExceptionHandler(MissingPathVariableException exception) {
        String message = "Missing path variable in, " + exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseError.builder().message(message).errors(List.of("There may be extra literals or spaces, or missing characters in the query.")).build());
    }

}
