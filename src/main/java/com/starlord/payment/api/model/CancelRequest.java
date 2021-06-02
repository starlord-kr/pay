package com.starlord.payment.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class CancelRequest {

    @NotNull
    @Size(min = 20, max = 20)
    private String paymentSerialNumber; // 관리번호

    @NotNull
    @Min(value = 100)
    @Max(value = 1000000000)
    private Long cancelPrice; // 취소금액(100원 이상, 10억원 이하, 숫자)

    @Column
    @Min(value = 0)
    @Max(value = 1000000000)
    private Long cancelVat; // 취소부가세, 자동계산 수식 : 결제금액 / 11, 소수점이하 반올림

}
