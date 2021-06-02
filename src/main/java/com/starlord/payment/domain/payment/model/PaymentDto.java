package com.starlord.payment.domain.payment.model;

import com.starlord.payment.domain.model.CardCompanyRequest;
import lombok.*;

@Builder
@Getter
@ToString
public class PaymentDto {

    private String cardNumber; // 카드번호(10 ~ 16자리 숫자)
    private String cardExpireDate; // 유효기간(4자리 숫자, mmyy)
    private String cardCvc; // cvc(3자리 숫자)
    private String cardMonthlyInstallment; // 할부개월수 : 0(일시불), 1 ~ 12
    private Long paymentPrice; // 결제금액(100원 이상, 10억원 이하, 숫자)
    private Long paymentVat; // 부가세, 자동계산 수식 : 결제금액 / 11, 소수점이하 반올림

    private String encryptCardInfo;
    private Long calculatedVat;

    private Long cancelPrice;
    private Long cancelVat;

    private CardCompanyRequest dataType; // 데이터구분
    private String paymentSerialNumber; // 관리번호
    private String dataPayload; // String 데이터

    public void setCalculatedVat(Long calculatedVat) {
        this.calculatedVat = calculatedVat;
    }
}
