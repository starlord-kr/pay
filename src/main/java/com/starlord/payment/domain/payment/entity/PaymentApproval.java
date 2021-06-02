package com.starlord.payment.domain.payment.entity;

import com.starlord.payment.domain.CommonDateEntity;
import com.starlord.payment.domain.card.model.CardCompanyDto;
import com.starlord.payment.domain.payment.application.PaymentServiceHelper;
import com.starlord.payment.domain.payment.model.PaymentCardInfo;
import com.starlord.payment.domain.payment.model.PaymentDto;
import com.starlord.payment.utils.DecryptUtil;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@DynamicUpdate
@Entity(name = "PAYMENT_APPROVAL")
public class PaymentApproval extends CommonDateEntity {

    @Builder
    public PaymentApproval(String paymentSerialNumber,
                           String encryptCardInfo,
                           String cardMonthlyInstallment,
                           Long paymentPrice,
                           Long paymentVat) {
        this.paymentSerialNumber = paymentSerialNumber;
        this.encryptCardInfo = encryptCardInfo;
        this.cardMonthlyInstallment = cardMonthlyInstallment;
        this.paymentPrice = paymentPrice;
        this.paymentVat = paymentVat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "paymentApproval", cascade = CascadeType.PERSIST)
    private List<PaymentCancel> paymentCancellations = new ArrayList<>();

    @Column(name = "PAYMENT_SERIAL_NUMBER", length = 20)
    private String paymentSerialNumber; // 관리번호

    @Column(name = "ENCRYPT_CARD_INFO", length = 300)
    private String encryptCardInfo; // 카드정보|유효기간|cvc

    @Column(name = "CARD_MONTHLY_INSTALLMENT",length = 2)
    private String cardMonthlyInstallment; // 할부개월수 : 0(일시불), 1 ~ 12

    @Column(name = "PAYMENT_PRICE",length = 10)
    private Long paymentPrice; // 결제금액(100원 이상, 10억원 이하, 숫자)

    @Column(name = "PAYMENT_VAT",length = 10)
    private Long paymentVat; // 부가세, 자동계산 수식 : 결제금액 / 11, 소수점이하 반올림

    @Transient
    public PaymentCardInfo getCardInfo() throws GeneralSecurityException {
        String[] splitedCardInfo = DecryptUtil.decrypt(encryptCardInfo).split("\\" + "|");
        return PaymentCardInfo.builder()
                .cardNumber(splitedCardInfo[0])
                .cardExpireDate(splitedCardInfo[1])
                .cardCvc(splitedCardInfo[2])
                .build();
    }

    public Boolean matchVersion(Long version) {
        if(this.getVersion().equals(version)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public void cancelOnApproval(CardCompanyDto cardCompanyDto, PaymentDto paymentDto, Integer version) {

        this.paymentCancellations.add(PaymentServiceHelper.buildPaymentCancel(this, cardCompanyDto, paymentDto));
        this.setVersion(version);
        this.setUpdateDateTime(LocalDateTime.now());
    }
}
