package com.starlord.payment.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request")
@NoArgsConstructor
public class PaymentAlreadyCancelException extends PaymentException {

    public PaymentAlreadyCancelException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public PaymentAlreadyCancelException(ExceptionMessage exceptionMessage, Object... arguments) {
        super(exceptionMessage, arguments);
    }
}
