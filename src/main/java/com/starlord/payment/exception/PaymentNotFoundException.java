package com.starlord.payment.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
@NoArgsConstructor
public class PaymentNotFoundException extends PaymentException {

    public PaymentNotFoundException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }

    public PaymentNotFoundException(ExceptionMessage exceptionMessage, Object... arguments) {
        super(exceptionMessage, arguments);
    }
}
