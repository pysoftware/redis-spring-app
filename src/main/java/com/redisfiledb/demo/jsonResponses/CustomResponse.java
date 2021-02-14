package com.redisfiledb.demo.jsonResponses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@NoArgsConstructor
public abstract class CustomResponse {
    private HttpStatus status;

    public int getStatus() {
        if (!Objects.nonNull(status)) {
            status = OK;
        }
        return status.value();
    }
}
