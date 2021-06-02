package com.starlord.payment.api;


import com.fasterxml.jackson.core.JsonParseException;
import com.starlord.payment.api.model.PaymentResponse;
import com.starlord.payment.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import java.security.InvalidParameterException;
import java.util.Optional;

@Slf4j
@RestControllerAdvice(
        annotations = RestController.class,
        basePackages = "com.starlord.insurance.api")
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = {
            JsonParseException.class,
            InvalidParameterException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public PaymentResponse<?> badRequestException(Exception ex, WebRequest request) {
        log.error(" HttpStatus.BAD_REQUEST1 : {}", ex.getMessage());

        return PaymentResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ex.getMessage())
            .data(Optional.empty())
            .build();
    }

    @ExceptionHandler(value = {
            EntityNotFoundException.class,
            NoHandlerFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public PaymentResponse<?> notFoundException(PaymentException ex, WebRequest request) {
        log.error(" HttpStatus.NOT_FOUND : {}", ex.getMessage());

        return PaymentResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
    }

    @ExceptionHandler(value = {
            PaymentException.class,
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public PaymentResponse<?> paymentServiceException(PaymentException ex, WebRequest request) {
        log.error(" HttpStatus.INTERNAL_SERVER_ERROR : {}", ex.getMessage());
        log.error(" PaymentException : {}", ex.getMessage());

        return PaymentResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
    }

    @ExceptionHandler(value = {RuntimeException.class,})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public PaymentResponse<?> unknownException(RuntimeException ex, WebRequest request) {
        log.error(" HttpStatus.INTERNAL_SERVER_ERROR : {}", ex.getMessage());

        return PaymentResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
    }

}
