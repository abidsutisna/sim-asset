package com.asset.simasset.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseDTO {
    public static <T> ResponseEntity<?> renderJson(T data, String message, HttpStatus httpStatus) {
        Res<T> response = Res.<T>builder()
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

}
