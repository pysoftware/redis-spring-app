package com.redisfiledb.demo.jsonResponses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class SimpleJsonResponse extends CustomResponse {
    public SimpleJsonResponse(HttpStatus httpStatus, Object response) {
        super(httpStatus);
        this.response = response;
    }

    @Getter
    private final Object response;
}
