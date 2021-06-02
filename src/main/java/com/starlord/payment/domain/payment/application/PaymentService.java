package com.starlord.payment.domain.payment.application;

import com.starlord.payment.api.model.ApproveRequest;
import com.starlord.payment.api.model.CancelRequest;
import com.starlord.payment.domain.card.application.CardCompanyService;
import com.starlord.payment.domain.card.entity.CardCompany;
import com.starlord.payment.domain.card.model.CardCompanyDto;
import com.starlord.payment.domain.model.CardCompanyRequest;
import com.starlord.payment.domain.payment.entity.PaymentApproval;
import com.starlord.payment.domain.payment.infra.PaymentApprovalRepository;
import com.starlord.payment.domain.payment.infra.PaymentCancelRepository;
import com.starlord.payment.domain.payment.model.PaymentDto;
import com.starlord.payment.domain.payment.model.PaymentResultDto;
import com.starlord.payment.domain.payment.model.PaymentSearchResultDto;
import com.starlord.payment.exception.PaymentException;
import com.starlord.payment.utils.SerialNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.GeneralSecurityException;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentApprovalRepository paymentApprovalRepository;
    private final PaymentCancelRepository paymentCancelRepository;
    private final CardCompanyService cardCompanyService;

    @Transactional
    public PaymentResultDto approvePayment(ApproveRequest approveRequest) throws PaymentException {
        String paymentSerialNumber = SerialNumberGenerator.getSerialNumber();
        PaymentDto paymentDto = PaymentServiceHelper.preparePaymentApproval(approveRequest);
        String dataPayload = PaymentPayloadHelper.createCardCompaynDataPayload(paymentSerialNumber, paymentDto, CardCompanyRequest.PAYMENT);

        // Api요청이라면 보통 Feign이나 RestTemplate으로 사용했겠지만, DB에 저장하는 예제이니 다음과 같이 처리
        CardCompany cardCompany = cardCompanyService.sendRequest(CardCompanyRequest.PAYMENT, paymentSerialNumber, dataPayload);
        CardCompanyDto cardCompanyDto = PaymentServiceHelper.convertCardCompanyToCardCompanyDto(cardCompany);

        PaymentApproval paymentApproval = PaymentServiceHelper.buildPaymentApproval(cardCompanyDto, paymentDto);
        paymentApprovalRepository.save(paymentApproval);
        return PaymentServiceHelper.createResult(cardCompanyDto);
    }

    @Transactional
    public PaymentResultDto cancelPayment(CancelRequest cancelRequest) throws PaymentException, GeneralSecurityException {
        String paymentSerialNumber = SerialNumberGenerator.getSerialNumber();
        PaymentApproval approvedPayment = PaymentServiceHelper
                .findPaymentApprovalByPaymentSerialNumber(paymentApprovalRepository, cancelRequest.getPaymentSerialNumber());
        Integer version = approvedPayment.getVersion();

        PaymentDto paymentDto = PaymentServiceHelper.preparePaymentCancel(cancelRequest, approvedPayment);
        String dataPayload = PaymentPayloadHelper.createCardCompaynDataPayload(paymentSerialNumber, paymentDto, CardCompanyRequest.CANCEL);

        PaymentCancellationValidator.validateCancellation(approvedPayment, paymentDto, cancelRequest); // price, vat 검사

        // Api요청이라면 보통 Feign이나 RestTemplate으로 사용했겠지만, DB에 저장하는 예제이니 다음과 같이 처리
        CardCompany cardCompany = cardCompanyService.sendRequest(CardCompanyRequest.CANCEL, paymentSerialNumber, dataPayload);
        CardCompanyDto cardCompanyDto = PaymentServiceHelper.convertCardCompanyToCardCompanyDto(cardCompany);

        approvedPayment.cancelOnApproval(cardCompanyDto, paymentDto, version);
        return PaymentServiceHelper.createResult(cardCompanyDto);
    }

    public PaymentSearchResultDto findOnePayment(String paymentSerialNumber) {
        CardCompany cardCompany = cardCompanyService.findCardInfoByPaymentSerialNumber(paymentSerialNumber);
        if(CardCompanyRequest.PAYMENT.equals(cardCompany.getDataType())) {
            return PaymentServiceHelper.buildPaymentDto(
                    PaymentServiceHelper.findPaymentApprovalByPaymentSerialNumber(paymentApprovalRepository, paymentSerialNumber));
        } else {
            return PaymentServiceHelper.buildPaymentDto(
                    PaymentServiceHelper.findPaymentCancelByPaymentSerialNumber(paymentCancelRepository, paymentSerialNumber));
        }
    }
}
