package com.starlord.payment.domain.card.entity;

import com.starlord.payment.domain.CommonDateEntity;
import com.starlord.payment.domain.model.CardCompanyRequest;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


/**
 * 카드사에 전문? 보내는 요청으로 간주함
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@DynamicUpdate
@Entity(name = "CARD_COMPANY")
public class CardCompany extends CommonDateEntity {

    @Builder
    public CardCompany(CardCompanyRequest dataType,
                       String paymentSerialNumber,
                       String dataPayload) {

        this.dataType = dataType;
        this.paymentSerialNumber = paymentSerialNumber;
        this.dataPayload = dataPayload;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private CardCompanyRequest dataType; // 데이터구분

    @Column(length = 20)
    private String paymentSerialNumber; // 관리번호

    @Column(length = 450)
    private String dataPayload; // String 데이터


}
