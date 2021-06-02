package com.starlord.payment.domain.payment.application;

import com.starlord.payment.domain.model.CardCompanyRequest;
import com.starlord.payment.domain.model.CardCompaynHeader;
import com.starlord.payment.domain.model.CardCompaynPayload;
import com.starlord.payment.domain.payment.model.PaymentDto;
import org.apache.commons.lang3.StringUtils;

public class PaymentPayloadHelper {

    public static String createCardCompaynDataPayload(String paymentSerialNumber,
                                                      PaymentDto paymentDto,
                                                      CardCompanyRequest cardCompanyRequest) {

        String payload = PaymentPayloadHelper.createCardCompanyPayload(paymentDto, paymentSerialNumber, cardCompanyRequest);
        String header = PaymentPayloadHelper.createCardCompaynHeader(payload.length(), cardCompanyRequest.name(), paymentSerialNumber);
        return header + payload;
    }

    static String createCardCompaynHeader(int dataLength, String dataType, String paymentSerialNumber) {

        return CardCompaynHeader.builder()
                .dataLength(String.valueOf(dataLength))
                .dataType(dataType)
                .paymentSerialNumber(paymentSerialNumber)
                .build()
                .toString();
    }

    static String createCardCompanyPayload(PaymentDto paymentDto, String paymentSerialNumber, CardCompanyRequest cardCompanyRequest) {

        return CardCompaynPayload.builder()
                .cardNumber(paymentDto.getCardNumber())
                .cardMonthlyInstallment(paymentDto.getCardMonthlyInstallment())
                .cardExpiryDate(paymentDto.getCardExpireDate())
                .cardCvc(paymentDto.getCardCvc())
                .paymentPrice(CardCompanyRequest.PAYMENT.equals(cardCompanyRequest)
                        ? String.valueOf(paymentDto.getPaymentPrice())
                        : String.valueOf(paymentDto.getCancelPrice()))
                .paymentVat(String.valueOf(paymentDto.getCalculatedVat()))
                .paymentSerialNumber(CardCompanyRequest.PAYMENT.equals(cardCompanyRequest) ? StringUtils.EMPTY : paymentSerialNumber)
                .encryptCardInfo(paymentDto.getEncryptCardInfo())
                .reserveField(StringUtils.EMPTY)
                .build()
                .toString();
    }

}
