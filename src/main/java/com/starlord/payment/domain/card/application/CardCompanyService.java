package com.starlord.payment.domain.card.application;

import com.starlord.payment.domain.card.entity.CardCompany;
import com.starlord.payment.domain.card.infra.CardCompanyRepository;
import com.starlord.payment.domain.model.CardCompanyRequest;
import com.starlord.payment.exception.CardCompanyNotFoundException;
import com.starlord.payment.exception.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardCompanyService {
    private final CardCompanyRepository cardCompanyRepository;

    public CardCompany sendRequest(CardCompanyRequest cardCompanyRequest,String paymentSerialNumber, String dataPayload) {
        CardCompany cardApprovalRequest = CardCompanyServiceHelper.buildCardCompany(cardCompanyRequest, paymentSerialNumber, dataPayload);
        return Optional
                .ofNullable(cardCompanyRepository.save(cardApprovalRequest))
                .orElseThrow(() -> new CardCompanyNotFoundException(ExceptionMessage.PAYMENT_NOT_FOUND_EXCEPTION));
    }

    public CardCompany findCardInfoByPaymentSerialNumber(String paymentSerialNumber) {
        return cardCompanyRepository.findByPaymentSerialNumber(paymentSerialNumber)
                .orElseThrow(() -> new CardCompanyNotFoundException(ExceptionMessage.PAYMENT_NOT_FOUND_EXCEPTION));
    }
}
