package com.starlord.payment.domain.card.model;

import com.starlord.payment.domain.model.CardCompanyRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class CardCompanyDto {

    private CardCompanyRequest dataType; // 데이터구분
    private String paymentSerialNumber; // 관리번호
    private String dataPayload; // String 데이터

}
