package com.starlord.payment.domain.payment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class PaymentCardInfo {

    @Builder
    public PaymentCardInfo(String cardNumber, String cardExpireDate, String cardCvc) {
        this.cardNumber = cardNumber;
        this.cardExpireDate = cardExpireDate;
        this.cardCvc = cardCvc;
    }

    private String cardNumber; // 카드번호(10 ~ 16자리 숫자)
    private String cardExpireDate; // 유효기간(4자리 숫자, mmyy)
    private String cardCvc; // cvc(3자리 숫자)
}
