package com.starlord.payment.domain.payment.application;

import com.starlord.payment.api.model.ApproveRequest;
import com.starlord.payment.api.model.CancelRequest;
import com.starlord.payment.domain.card.entity.CardCompany;
import com.starlord.payment.domain.card.model.CardCompanyDto;
import com.starlord.payment.domain.model.CardCompanyRequest;
import com.starlord.payment.domain.payment.entity.PaymentApproval;
import com.starlord.payment.domain.payment.entity.PaymentCancel;
import com.starlord.payment.domain.payment.infra.PaymentApprovalRepository;
import com.starlord.payment.domain.payment.infra.PaymentCancelRepository;
import com.starlord.payment.domain.payment.model.PaymentCardInfo;
import com.starlord.payment.domain.payment.model.PaymentDto;
import com.starlord.payment.domain.payment.model.PaymentResultDto;
import com.starlord.payment.domain.payment.model.PaymentSearchResultDto;
import com.starlord.payment.exception.ExceptionMessage;
import com.starlord.payment.exception.PaymentNotFoundException;
import com.starlord.payment.utils.EncryptUtil;
import com.starlord.payment.utils.PaymentVatUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.security.GeneralSecurityException;

import static java.util.Arrays.asList;

public final class PaymentServiceHelper {

    private static final String DELIMITER = "|";

    static PaymentApproval findPaymentApprovalByPaymentSerialNumber(PaymentApprovalRepository repository,
                                                                    String paymentSerialNumber) throws PaymentNotFoundException {
        return repository.findByPaymentSerialNumber(paymentSerialNumber)
                .orElseThrow(() -> new PaymentNotFoundException(ExceptionMessage.PAYMENT_NOT_FOUND_EXCEPTION));
    }

    static PaymentCancel findPaymentCancelByPaymentSerialNumber(PaymentCancelRepository repository,
                                                                String paymentSerialNumber) throws PaymentNotFoundException {
        return repository.findByPaymentSerialNumber(paymentSerialNumber)
                .orElseThrow(() -> new PaymentNotFoundException(ExceptionMessage.PAYMENT_NOT_FOUND_EXCEPTION));
    }

    static PaymentApproval buildPaymentApproval(CardCompanyDto cardCompanyDto, PaymentDto updatedPaymentDto) {
        return PaymentApproval.builder()
                .paymentSerialNumber(cardCompanyDto.getPaymentSerialNumber())
                .encryptCardInfo(updatedPaymentDto.getEncryptCardInfo())
                .cardMonthlyInstallment(updatedPaymentDto.getCardMonthlyInstallment())
                .paymentPrice(updatedPaymentDto.getPaymentPrice())
                .paymentVat(updatedPaymentDto.getCalculatedVat())
                .build();
    }

    public static PaymentCancel buildPaymentCancel(PaymentApproval paymentApproval, CardCompanyDto cardCompanyDto, PaymentDto updatedPaymentDto) {
        return PaymentCancel.builder()
                .paymentApproval(paymentApproval)
                .paymentSerialNumber(cardCompanyDto.getPaymentSerialNumber())
                .encryptCardInfo(updatedPaymentDto.getEncryptCardInfo())
                .cardMonthlyInstallment(updatedPaymentDto.getCardMonthlyInstallment())
                .cancelPrice(updatedPaymentDto.getCancelPrice())
                .cancelVat(updatedPaymentDto.getCalculatedVat())
                .build();
    }

    public static PaymentDto preparePaymentApproval(ApproveRequest approveRequest) {

        return PaymentDto.builder()
                .cardNumber(approveRequest.getCardNumber())
                .cardExpireDate(approveRequest.getCardExpireDate())
                .cardCvc(approveRequest.getCardCvc())
                .cardMonthlyInstallment(approveRequest.getCardMonthlyInstallment())
                .paymentPrice(approveRequest.getPaymentPrice())
                .paymentVat(approveRequest.getPaymentVat())
                .encryptCardInfo(
                        getEncryptCardInfo(
                                approveRequest.getCardNumber(),
                                approveRequest.getCardExpireDate(),
                                approveRequest.getCardCvc())
                )
                .calculatedVat(
                        PaymentVatUtil.calculatePaymentVat(
                                approveRequest.getPaymentPrice(),
                                approveRequest.getPaymentVat())
                )
                .build();
    }

    private static String getEncryptCardInfo(String cardNumber, String cardExpireDate, String cardCvc) {
        try {
            return EncryptUtil.encrypt(String.join(DELIMITER, asList(cardNumber, cardExpireDate, cardCvc)));
        } catch(GeneralSecurityException ex) {
            return StringUtils.EMPTY;
        }
    }

    public static PaymentDto preparePaymentCancel(CancelRequest cancelRequest, PaymentApproval paymentApproval) throws GeneralSecurityException {
        PaymentCardInfo paymentCardInfo = paymentApproval.getCardInfo();
        return PaymentDto.builder()
                .paymentSerialNumber(cancelRequest.getPaymentSerialNumber())
                .cardNumber(paymentCardInfo.getCardNumber())
                .cardExpireDate(paymentCardInfo.getCardExpireDate())
                .cardCvc(paymentCardInfo.getCardCvc())
                .cardMonthlyInstallment(paymentApproval.getCardMonthlyInstallment())
                .paymentPrice(paymentApproval.getPaymentPrice())
                .paymentVat(paymentApproval.getPaymentVat())
                .cancelPrice(cancelRequest.getCancelPrice())
                .cancelVat(cancelRequest.getCancelVat())
                .encryptCardInfo(paymentApproval.getEncryptCardInfo())
                .calculatedVat(
                        PaymentVatUtil.calculatePaymentVat(
                                cancelRequest.getCancelPrice(),
                                cancelRequest.getCancelVat())
                )
                .build();
    }

    static PaymentResultDto createResult(CardCompanyDto cardCompanyDto) {

        return PaymentResultDto.builder()
                .dataType(cardCompanyDto.getDataType())
                .paymentSerialNumber(cardCompanyDto.getPaymentSerialNumber())
                .dataPayload(cardCompanyDto.getDataPayload())
                .build();
    }

    @SneakyThrows
    static PaymentSearchResultDto buildPaymentDto(PaymentApproval paymentApproval) {
        return PaymentSearchResultDto.builder()
                .paymentSerialNumber(paymentApproval.getPaymentSerialNumber())
                .dataType(CardCompanyRequest.PAYMENT)
                .cardNumber(paymentApproval.getCardInfo().getCardNumber())
                .cardExpireDate(paymentApproval.getCardInfo().getCardExpireDate())
                .cardCvc(paymentApproval.getCardInfo().getCardCvc())
                .price(paymentApproval.getPaymentPrice())
                .vat(paymentApproval.getPaymentVat())
                .build();
    }

    @SneakyThrows
    static PaymentSearchResultDto buildPaymentDto(PaymentCancel paymentCancel) {
        return PaymentSearchResultDto.builder()
                .paymentSerialNumber(paymentCancel.getPaymentSerialNumber())
                .dataType(CardCompanyRequest.CANCEL)
                .cardNumber(paymentCancel.getCardInfo().getCardNumber())
                .cardExpireDate(paymentCancel.getCardInfo().getCardExpireDate())
                .cardCvc(paymentCancel.getCardInfo().getCardCvc())
                .price(paymentCancel.getCancelPrice())
                .vat(paymentCancel.getCancelVat())
                .build();
    }

    public static CardCompanyDto convertCardCompanyToCardCompanyDto(CardCompany cardCompany) {
        return CardCompanyDto.builder()
                .dataType(cardCompany.getDataType())
                .paymentSerialNumber(cardCompany.getPaymentSerialNumber())
                .dataPayload(cardCompany.getDataPayload())
                .build();
    }
}
