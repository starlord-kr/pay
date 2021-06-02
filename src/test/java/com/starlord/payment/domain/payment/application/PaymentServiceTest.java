package com.starlord.payment.domain.payment.application;

import com.starlord.payment.api.model.ApproveRequest;
import com.starlord.payment.domain.card.application.CardCompanyService;
import com.starlord.payment.domain.card.entity.CardCompany;
import com.starlord.payment.domain.card.infra.CardCompanyRepository;
import com.starlord.payment.domain.card.model.CardCompanyDto;
import com.starlord.payment.domain.model.CardCompanyRequest;
import com.starlord.payment.domain.payment.infra.PaymentApprovalRepository;
import com.starlord.payment.domain.payment.infra.PaymentCancelRepository;
import com.starlord.payment.domain.payment.model.PaymentDto;
import com.starlord.payment.domain.payment.model.PaymentResultDto;
import com.starlord.payment.utils.SerialNumberGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class PaymentServiceTest {

    @Mock CardCompanyRepository cardCompanyRepository;
    @InjectMocks CardCompanyService cardCompanyService;

    @Mock PaymentApprovalRepository paymentApprovalRepository;
    @Mock PaymentCancelRepository paymentCancelRepository;

    @InjectMocks PaymentService paymentService;

    @Test
    public void approvePayemnt() {
        CardCompanyRequest cardCompanyRequestType = CardCompanyRequest.PAYMENT;
        ApproveRequest approveRequest = dummyApproveRequest();

        String paymentSerialNumber = SerialNumberGenerator.getSerialNumber();
        PaymentDto paymentDto = PaymentServiceHelper.preparePaymentApproval(approveRequest);
        String dataPayload = PaymentPayloadHelper.createCardCompaynDataPayload(paymentSerialNumber, paymentDto, cardCompanyRequestType);

        CardCompany cardCompany = cardCompanyPayemnt(cardCompanyRequestType, paymentSerialNumber, dataPayload);
        CardCompanyDto cardCompanyDto = PaymentServiceHelper.convertCardCompanyToCardCompanyDto(cardCompany);
        PaymentResultDto expected = PaymentServiceHelper.createResult(cardCompanyDto);

        given(cardCompanyService.sendRequest(CardCompanyRequest.PAYMENT, paymentSerialNumber, dataPayload)).willReturn(cardCompany);
        when(paymentService.approvePayment(approveRequest))
                .thenReturn(expected);

        PaymentResultDto actual = paymentService.approvePayment(approveRequest);

        assertThat(actual.getPaymentSerialNumber(), is(expected.getPaymentSerialNumber()));
    }

    ApproveRequest dummyApproveRequest() {
        return ApproveRequest.builder()
                .cardNumber("1234567890123456")
                .cardExpireDate("0225")
                .cardCvc("110")
                .cardMonthlyInstallment("0")
                .paymentPrice(11000L)
                .paymentVat(null)
                .build();
    }

    CardCompany cardCompanyPayemnt(CardCompanyRequest cardCompanyRequest, String paymentSerialNumber, String dataPayload) {
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
        return expected;
    }


}
