package com.bff.bffservice.exceptions;

import com.bff.bffservice.dto.ErrorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import com.bff.bffservice.producer.LogProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private LogProducer logProducer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponseDto resp =  ErrorResponseDto.builder()
                .status(404)
                .error("User Not Found")
                .message(ex.getMessage())
                .build();
        logAsJson(resp, "Response");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex) {
        ErrorResponseDto resp =  ErrorResponseDto.builder()
                .status(500)
                .error("Server Error")
                .message("Error Occurred in Servers: " + ex.getMessage())
                .build();
        logAsJson(resp, "Response");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }

    private void logAsJson(Object obj, String type) {
        try {
            String json = objectMapper.writeValueAsString(obj);
            logProducer.sendLog(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
