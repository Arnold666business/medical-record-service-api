package com.example.medicalrecordservice.swagger;

import com.example.medicalrecordservice.exception.error.ResponseError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {@ApiResponse(responseCode = "405", description = "The http method is not supported for this resource", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseError.class))),

})
public @interface StandardApiResponses {

}
