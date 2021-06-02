package com.starlord.payment.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@ToString
public class ApproveRequest {

    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Size(min = 10, max = 16)
    private String cardNumber; // 카드번호(10 ~ 16자리 숫자)

    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Size(min = 4, max = 4)
    private String cardExpireDate; // 유효기간(4자리 숫자, mmyy)

    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Size(min = 3, max = 3)
    private String cardCvc; // cvc(3자리 숫자)

    @NotNull
    @Min(value = 0)
    @Max(value = 12)
    @Column
    private String cardMonthlyInstallment; // 할부개월수 : 0(일시불), 1 ~ 12

    @NotNull
    @Min(value = 100)
    @Max(value = 1000000000)
    @Column
    private Long paymentPrice; // 결제금액(100원 이상, 10억원 이하, 숫자)

    @Column
    @Min(value = 0)
    @Max(value = 1000000000)
    private Long paymentVat; // 부가세, 자동계산 수식 : 결제금액 / 11, 소수점이하 반올림

}
