package com.starlord.payment.domain.payment.model;

import com.starlord.payment.domain.model.CardCompanyRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PaymentResultDto {

    @Builder
    public PaymentResultDto(CardCompanyRequest dataType, String paymentSerialNumber, String dataPayload) {
        this.dataType = dataType;
        this.paymentSerialNumber = paymentSerialNumber;
        this.dataPayload = dataPayload;
    }

    private CardCompanyRequest dataType; // 데이터구분
    private String paymentSerialNumber; // 관리번호
    private String dataPayload; // String 데이터

}
