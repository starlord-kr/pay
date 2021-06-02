package com.starlord.payment.utils;

import com.starlord.payment.exception.ExceptionMessage;
import com.starlord.payment.exception.PaymentVatException;

public class PaymentVatUtil {
    private static final Long DIVISION = 11L;

    public static Long calculatePaymentVat(Long originPaymentPrice, Long originPaymentVat) throws PaymentVatException {
        Double paymentPrice = Double.valueOf(originPaymentPrice);
        Long paymentVat = dividePrice(paymentPrice, originPaymentVat);
        if (paymentVat > paymentPrice) {
            throw new PaymentVatException(ExceptionMessage.VAT_GREATER_THAN_PRICE);
        }
        return paymentVat;
    }

    private static Long dividePrice(Double paymentPrice, Long originPaymentVat) throws PaymentVatException {
        if(originPaymentVat == null) {
            return Math.round(paymentPrice / DIVISION);
        } else {
            return originPaymentVat;
        }
    }
}
