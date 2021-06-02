package com.starlord.payment.api.model;

import com.starlord.payment.domain.model.CardCompanyRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class SearchResponse {

    private CardCompanyRequest dataType; // 데이터구분
    private String paymentSerialNumber; // 관리번호
    private String cardNumber; // 카드번호(10 ~ 16자리 숫자)
    private String cardExpireDate; // 유효기간(4자리 숫자, mmyy)
    private String cardCvc; // cvc(3자리 숫자)
    private Long paymentPrice;
    private Long paymentVat;

}
