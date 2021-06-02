package com.starlord.payment.domain.payment.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class PaymentCancelDto {

    @Builder
    public PaymentCancelDto(String paymentSerialNumber, Long cancelPrice, Long cancelVat) {
        this.paymentSerialNumber = paymentSerialNumber;
        this.cancelPrice = cancelPrice;
        this.cancelVat = cancelVat;
    }

    private String paymentSerialNumber; // 관리번호
    private Long cancelPrice; // 취소금액(100원 이상, 10억원 이하, 숫자)
    private Long cancelVat; // 취소 부가세, 자동계산 수식 : 결제금액 / 11, 소수점이하 반올림

}
