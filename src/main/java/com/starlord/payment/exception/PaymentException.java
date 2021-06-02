package com.starlord.payment.exception;

import lombok.NoArgsConstructor;

import java.text.MessageFormat;

@NoArgsConstructor
public class PaymentException extends RuntimeException {
    public PaymentException(ExceptionMessage message) {
        super(message.getMessage());
    }

    public PaymentException(ExceptionMessage message, Object... arguments) {
        super(MessageFormat.format(message.getMessage(), arguments));
    }

    public PaymentException(ExceptionMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }
}
