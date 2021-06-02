package com.starlord.payment.domain.card.application;

import com.starlord.payment.domain.card.model.CardCompanyDto;
import com.starlord.payment.domain.card.entity.CardCompany;
import com.starlord.payment.domain.model.CardCompanyRequest;

public final class CardCompanyServiceHelper {

//    public static CardCompanyDto convertCardCompanyToCardCompanyDto(CardCompany cardCompany) {
//        return CardCompanyDto.builder()
//                .dataType(cardCompany.getDataType())
//                .paymentSerialNumber(cardCompany.getPaymentSerialNumber())
//                .dataPayload(cardCompany.getDataPayload())
//                .build();
//    }

    static CardCompany buildCardCompany(CardCompanyRequest cardCompanyRequest, String paymentSerialNumber, String dataPaylod) {
        return CardCompany.builder()
                .dataType(cardCompanyRequest)
                .paymentSerialNumber(paymentSerialNumber)
                .dataPayload(dataPaylod)
                .build();
    }
}
