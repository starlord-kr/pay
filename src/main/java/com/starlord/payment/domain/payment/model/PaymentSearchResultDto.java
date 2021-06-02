package com.starlord.payment.domain.payment.model;

import com.starlord.payment.domain.model.CardCompanyRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PaymentSearchResultDto {

    @Builder
    public PaymentSearchResultDto(CardCompanyRequest dataType,
                                  String paymentSerialNumber,
                                  String cardNumber,
                                  String cardExpireDate,
                                  String cardCvc,
                                  Long price,
                                  Long vat) {
        this.dataType = dataType;
        this.paymentSerialNumber = paymentSerialNumber;
        this.cardNumber = cardNumber;
        this.cardExpireDate = cardExpireDate;
        this.cardCvc = cardCvc;
        this.price = price;
        this.vat = vat;
    }

    private CardCompanyRequest dataType; // 데이터구분
    private String paymentSerialNumber; // 관리번호
    private String cardNumber; // 카드번호(10 ~ 16자리 숫자)
    private String cardExpireDate; // 유효기간(4자리 숫자, mmyy)
    private String cardCvc; // cvc(3자리 숫자)
    private Long price;
    private Long vat;

}
