package com.starlord.payment.domain.card.entity;

import com.starlord.payment.domain.card.infra.CardCompanyRepository;
import com.starlord.payment.domain.model.CardCompanyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@Transactional
public class CardCompanyTest {

    @Autowired
    CardCompanyRepository cardCompanyRepository;

    private static final String PAYMENT_SERIAL_NUMBER = "Lz02GVELYiVPFyZtsjND";
    private static final String PAYMENT_DATA_PAYLOAD = " 416PAYMENT   Lz02GVELYiVPFyZtsjND1234567890123456    000225010     110000000001000                    dXzNDNxckOrb7uz2ON0AAJXx9NxB9i2RTXug8crQpWU=                                                                                                                                                                                                                                                                                                               ";

    @DisplayName("결제 승인을 입력한다")
    @Test
    public void approvePaymentTest() {

        CardCompany expected = CardCompany.builder()
                .dataType(CardCompanyRequest.PAYMENT)
                .paymentSerialNumber(PAYMENT_SERIAL_NUMBER)
                .dataPayload(PAYMENT_DATA_PAYLOAD)
                .build();
        CardCompany acaual = cardCompanyRepository.save(expected);
        assertThat(acaual.getPaymentSerialNumber(), is(expected.getPaymentSerialNumber()));
    }

    @DisplayName("결제 취소를 입력한다")
    @Test
    public void cancelPaymentTest() {

        CardCompany expected = CardCompany.builder()
                .dataType(CardCompanyRequest.CANCEL)
                .paymentSerialNumber(PAYMENT_SERIAL_NUMBER)
                .dataPayload(PAYMENT_DATA_PAYLOAD)
                .build();
        CardCompany acaual = cardCompanyRepository.save(expected);
        assertThat(acaual.getPaymentSerialNumber(), is(expected.getPaymentSerialNumber()));
    }
}
