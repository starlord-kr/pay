package com.starlord.payment.domain.card.application;

import com.starlord.payment.domain.card.entity.CardCompany;
import com.starlord.payment.domain.card.infra.CardCompanyRepository;
import com.starlord.payment.domain.model.CardCompanyRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class CardCompanyServiceTest {

    @Mock CardCompanyRepository cardCompanyRepository;
    @InjectMocks CardCompanyService cardCompanyService;

    @Test
    public void approvePayemnt() {

        CardCompanyRequest cardCompanyRequest = CardCompanyRequest.PAYMENT;
        String paymentSerialNumber = "Pow68ASIGhQs4MwbHS3M";
        String dataPayload = " 416PAYMENT   9Ndbv54Y8WMkYXAIXorn1234567890123456";
        CardCompany expected = CardCompany.builder()
                .paymentSerialNumber(paymentSerialNumber)
                .dataType(cardCompanyRequest)
                .dataPayload(dataPayload)
                .build();
        given(cardCompanyRepository.save(expected)).willReturn(expected);
        when(cardCompanyService.sendRequest(cardCompanyRequest, paymentSerialNumber, dataPayload))
                .thenReturn(expected);

        CardCompany actual = cardCompanyService.sendRequest(cardCompanyRequest, paymentSerialNumber, dataPayload);

        assertThat(actual.getPaymentSerialNumber(), is(expected.getPaymentSerialNumber()));
    }

    @Test
    public void cancelPayemnt() {

        CardCompanyRequest cardCompanyRequest = CardCompanyRequest.CANCEL;
        String paymentSerialNumber = "CSo5Mssaw8Wk9ijqhgqu";
        String dataPayload = " 416CANCEL    CSo5Mssaw8Wk9ijqhgqu1234567890123456";
        CardCompany expected = CardCompany.builder()
                .paymentSerialNumber(paymentSerialNumber)
                .dataType(cardCompanyRequest)
                .dataPayload(dataPayload)
                .build();
        given(cardCompanyRepository.save(expected)).willReturn(expected);
        when(cardCompanyService.sendRequest(cardCompanyRequest, paymentSerialNumber, dataPayload))
                .thenReturn(expected);

        CardCompany actual = cardCompanyService.sendRequest(cardCompanyRequest, paymentSerialNumber, dataPayload);

        assertThat(actual.getPaymentSerialNumber(), is(expected.getPaymentSerialNumber()));
    }
}
