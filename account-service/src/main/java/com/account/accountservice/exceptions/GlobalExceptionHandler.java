package com.account.accountservice.exceptions;

import com.account.accountservice.dto.ErrorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.account.accountservice.producer.LogProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private LogProducer logProducer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> argNotValidExc(MethodArgumentNotValidException e){

        ErrorResponseDto errorMessage = ErrorResponseDto.builder()
                .status(400)
                .error("Bad Request")
                .message(e.getFieldError().getDefaultMessage())
                .build();
        logAsJson(errorMessage, "Response");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> constraintViolationExc(HttpMessageNotReadableException e){

        ErrorResponseDto errorMessage = ErrorResponseDto.builder()
                .status(400)
                .error("Bad Request")
                .message("Invalid Id or AccountType")
                .build();
        logAsJson(errorMessage, "Response");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> invalidReqFormatExc(MethodArgumentTypeMismatchException e) {

        String error =String.format(
                "Invalid value for %s", e.getName()
        );

        ErrorResponseDto errorMessage = ErrorResponseDto.builder()
                .status(400)
                .error("Bad Request")
                .message(error)
                .build();
        logAsJson(errorMessage, "Response");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> accountNotFoundExc(AccountNotFoundException e){
        ErrorResponseDto resp = ErrorResponseDto.builder()
                                    .status(404)
                                    .error("Not Found")
                                    .message(e.getMessage())
                                    .build();
        logAsJson(resp, "Response");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNotFoundExc(UserNotFoundException e){
        ErrorResponseDto resp = ErrorResponseDto.builder()
                .status(404)
                .error("Not Found")
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponseDto> insufficientFunds(InsufficientFundsException e){
        ErrorResponseDto resp = ErrorResponseDto.builder()
                .status(400)
                .error("Bad Request")
                .message(e.getMessage())
                .build();
        logAsJson(resp, "Response");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> unhandledExceptions(Exception e){
        ErrorResponseDto resp = ErrorResponseDto.builder()
                .status(500)
                .error("Internal Server Error")
                .message(e.getMessage())
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
