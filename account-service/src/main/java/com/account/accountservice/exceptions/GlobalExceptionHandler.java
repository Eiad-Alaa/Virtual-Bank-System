package com.account.accountservice.exceptions;

import com.account.accountservice.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> argNotValidExc(MethodArgumentNotValidException e){

        ErrorResponseDto errorMessage = ErrorResponseDto.builder()
                .status(400)
                .error("Bad Request")
                .message(e.getFieldError().getDefaultMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> constraintViolationExc(HttpMessageNotReadableException e){

        ErrorResponseDto errorMessage = ErrorResponseDto.builder()
                .status(400)
                .error("Bad Request")
                .message("Invalid Id or AccountType")
                .build();

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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> accountNotFoundExc(AccountNotFoundException e){
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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> unhandledExceptions(Exception e){
        ErrorResponseDto resp = ErrorResponseDto.builder()
                .status(500)
                .error("Internal Server Error")
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }
}
