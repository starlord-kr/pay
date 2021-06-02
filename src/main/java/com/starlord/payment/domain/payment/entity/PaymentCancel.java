package com.starlord.payment.domain.payment.entity;

import com.starlord.payment.domain.CommonDateEntity;
import com.starlord.payment.domain.payment.model.PaymentCardInfo;
import com.starlord.payment.utils.DecryptUtil;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.security.GeneralSecurityException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@DynamicUpdate
@Entity(name = "PAYMENT_CANCEL")
public class PaymentCancel extends CommonDateEntity {

    @Builder
    public PaymentCancel(PaymentApproval paymentApproval,
                         String paymentSerialNumber,
                         String encryptCardInfo,
                         String cardMonthlyInstallment,
                         Long cancelPrice,
                         Long cancelVat) {
        this.paymentApproval = paymentApproval;
        this.paymentSerialNumber = paymentSerialNumber;
        this.encryptCardInfo = encryptCardInfo;
        this.cardMonthlyInstallment = cardMonthlyInstallment;
        this.cancelPrice = cancelPrice;
        this.cancelVat = cancelVat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PAYMENT_APPROVAL_ID", referencedColumnName = "ID", updatable = false)
    private PaymentApproval paymentApproval;

    @Column(name = "PAYMENT_SERIAL_NUMBER", length = 20)
    private String paymentSerialNumber; // 관리번호

    @Column(name = "ENCRYPT_CARD_INFO", length = 300)
    private String encryptCardInfo; // 카드정보|유효기간|cvc

    @Column(name = "CARD_MONTHLY_INSTALLMENT", length = 2)
    private String cardMonthlyInstallment; // 할부개월수 : 0(일시불), 1  ~ 12

    @Column(name = "CANCEL_PRICE", length = 10)
    private Long cancelPrice; // 취소금액(100원 이상, 10억원 이하, 숫자)

    @Column(name = "CANCEL_VAT", length = 10)
    private Long cancelVat; // 취소부가세, 자동계산 수식 : 결제금액 / 11, 소수점이하 반올림

    @Transient
    public PaymentCardInfo getCardInfo() throws GeneralSecurityException {
        String[] splitedCardInfo = DecryptUtil.decrypt(encryptCardInfo).split("\\" + "|");
        return PaymentCardInfo.builder()
                .cardNumber(splitedCardInfo[0])
                .cardExpireDate(splitedCardInfo[1])
                .cardCvc(splitedCardInfo[2])
                .build();
    }
}
