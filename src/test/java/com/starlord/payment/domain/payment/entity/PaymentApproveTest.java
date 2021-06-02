package com.starlord.payment.domain.payment.entity;

import com.starlord.payment.domain.payment.infra.PaymentApprovalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.security.GeneralSecurityException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@Transactional
public class PaymentApproveTest {

    @Autowired PaymentApprovalRepository paymentApprovalRepository;

    private static final String PAYMENT_SERIAL_NUMBER = "Lz02GVELYiVPFyZtsjND";
    private static final String PAYMENT_ENCTYPT_INFO = "dXzNDNxckOrb7uz2ON0AAInkf0+ztESwzOOm+pz7Kpc=";
    @Test
    public void approvePaymentTest() throws GeneralSecurityException {

        PaymentApproval expected = PaymentApproval.builder()
                .paymentSerialNumber(PAYMENT_SERIAL_NUMBER)
                .encryptCardInfo(PAYMENT_ENCTYPT_INFO)
                .cardMonthlyInstallment("0")
                .paymentPrice(11000L)
                .paymentVat(1000L)
                .build();
        PaymentApproval acaual = paymentApprovalRepository.save(expected);

        assertThat(acaual.getPaymentSerialNumber(), is(expected.getPaymentSerialNumber()));
        assertThat(acaual.getEncryptCardInfo(), is(expected.getEncryptCardInfo()));
        // 카드정보도 잘 반환되는지 확인해본다.
        assertThat(acaual.getCardInfo().getCardNumber(), is(expected.getCardInfo().getCardNumber()));
        assertThat(acaual.getCardInfo().getCardExpireDate(), is(expected.getCardInfo().getCardExpireDate()));
        assertThat(acaual.getCardInfo().getCardCvc(), is(expected.getCardInfo().getCardCvc()));
    }
}
