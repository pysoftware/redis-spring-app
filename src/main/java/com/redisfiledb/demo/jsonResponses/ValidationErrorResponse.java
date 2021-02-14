package com.redisfiledb.demo.jsonResponses;

import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ValidationErrorResponse extends CustomResponse {
    public ValidationErrorResponse(HttpStatus status, List<FieldError> fieldErrors) {
        super(status);
        this.fieldErrors = fieldErrors;
    }

    private final List<FieldError> fieldErrors;

    public Map<String, String> getErrors() {
        return fieldErrors
                .stream()
                .collect(
                        Collectors.toMap(
                                FieldError::getField,
                                e -> Objects.requireNonNullElse(e.getDefaultMessage(), "")
                        )
                );
    }

}
