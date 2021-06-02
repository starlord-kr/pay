package com.starlord.payment.domain.payment.application;

import com.starlord.payment.api.model.CancelRequest;
import com.starlord.payment.domain.payment.entity.PaymentApproval;
import com.starlord.payment.domain.payment.entity.PaymentCancel;
import com.starlord.payment.domain.payment.model.PaymentDto;
import com.starlord.payment.exception.ExceptionMessage;
import com.starlord.payment.exception.PaymentCancelException;
import com.starlord.payment.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public final class PaymentCancellationValidator {

    static void validateCancellation(PaymentApproval approvedPayment,
                                     PaymentDto paymentDto,
                                     CancelRequest cancelRequest) throws PaymentException {

        List<PaymentCancel> paymentCancellations = approvedPayment.getPaymentCancellations();
        if(paymentCancellations.size() < 1) return;

        Long paymentPrice = approvedPayment.getPaymentPrice();
        Long sumOfCancelPrice = paymentCancellations.stream().mapToLong(paymentCancel -> paymentCancel.getCancelPrice()).sum();
        if(sumOfCancelPrice >= paymentPrice) {
            log.warn("PaymentException: cancel-price is over1");
            throw new PaymentCancelException(ExceptionMessage.PAYMENT_ALREADY_CANCELD_EXCEPTION);
        }

        Long currentCancelPrice = sumOfCancelPrice + paymentDto.getCancelPrice();
        if(currentCancelPrice > paymentPrice) {
            log.warn("PaymentException: cancel-price is over2");
            throw new PaymentCancelException(ExceptionMessage.PAYMENT_CANCEL_PRICE_OVER_EXCEPTION);
        }

        Long paymentVat = approvedPayment.getPaymentVat();
        Long sumOfCancelVat = paymentCancellations.stream().mapToLong(paymentCancel -> paymentCancel.getCancelVat()).sum();
        if(sumOfCancelVat >=  paymentVat) {
            log.warn(" PaymentException: cancel-vat is over1");
            throw new PaymentCancelException(ExceptionMessage.PAYMENT_CANCEL_VAT_OVER_EXCEPTION);
        }

        Long currentCancelVat = sumOfCancelVat + paymentDto.getCalculatedVat();
        Long remainedVat = currentCancelVat - paymentVat;
        Long remainedPrice = currentCancelPrice - paymentPrice;
        log.warn(" remainedPrice:{}, remainedVat:{}", remainedPrice, remainedVat);
        if(remainedPrice == 0 && remainedVat < 0) {
            log.warn(" PaymentException: cancel-vat is over3");
            throw new PaymentCancelException(ExceptionMessage.PAYMENT_CANCEL_VAT_REMAINED_EXCEPTION);
        }

        // case3
        if(cancelRequest.getCancelVat() == null && remainedPrice == 0 && remainedVat > 0) {
            paymentDto.setCalculatedVat(paymentVat);
            return;
        }

        // case2
        if(currentCancelVat > paymentVat) {
            log.warn(" PaymentException: cancel-vat is over2");
            throw new PaymentCancelException(ExceptionMessage.PAYMENT_CANCEL_VAT_OVER_EXCEPTION);
        }

    }
}
