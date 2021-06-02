package com.starlord.payment.domain.model;

import com.starlord.payment.utils.StringUtilsWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardCompaynPayload {

    @Builder
    public CardCompaynPayload(String cardNumber,
                              String cardMonthlyInstallment,
                              String cardExpiryDate,
                              String cardCvc,
                              String paymentPrice,
                              String paymentVat,
                              String paymentSerialNumber,
                              String encryptCardInfo,
                              String reserveField) {

        this.cardNumber = StringUtilsWrapper.rightPaddingWithBlank(cardNumber, 20);
        this.cardMonthlyInstallment = StringUtilsWrapper.leftPaddingWithZero(cardMonthlyInstallment, 2);
        this.cardExpiryDate = StringUtilsWrapper.rightPaddingWithBlank(cardExpiryDate, 4);
        this.cardCvc = StringUtilsWrapper.rightPaddingWithBlank(cardCvc, 3);
        this.paymentPrice = StringUtilsWrapper.leftPaddingWithBlank(paymentPrice, 10);
        this.paymentVat = StringUtilsWrapper.leftPaddingWithZero(paymentVat, 10);
        this.paymentSerialNumber = StringUtilsWrapper.rightPaddingWithBlank(paymentSerialNumber, 20);
        this.encryptCardInfo = StringUtilsWrapper.rightPaddingWithBlank(encryptCardInfo, 300);
        this.reserveField = StringUtilsWrapper.rightPaddingWithBlank(reserveField, 47);
    }

    private String cardNumber; // 0.카드번호(10 ~ 16자리 숫자)
    private String cardMonthlyInstallment; // 1.할부개월수
    private String cardExpiryDate; // 2.카드유효기간
    private String cardCvc; // 3.cvc(3자리 숫자)
    private String paymentPrice; // 4.거래금액 결제/취소 금액
    private String paymentVat; // 4.부가가치세 결제/취소 금액의 부가세
    private String paymentSerialNumber; // 5.원거래관리번호 취소시에만 결제 관리번호 저장 결제시에는 공백
    private String encryptCardInfo; // 6.암호화된 카드정보
    private String reserveField; // 7. 예비필드

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cardNumber);
        stringBuilder.append(cardMonthlyInstallment);
        stringBuilder.append(cardExpiryDate);
        stringBuilder.append(cardCvc);
        stringBuilder.append(paymentPrice);
        stringBuilder.append(paymentVat);
        stringBuilder.append(paymentSerialNumber);
        stringBuilder.append(encryptCardInfo);
        stringBuilder.append(reserveField);
        return stringBuilder.toString();
    }

}
