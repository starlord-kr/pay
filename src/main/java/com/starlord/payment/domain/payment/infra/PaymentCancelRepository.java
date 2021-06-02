package com.starlord.payment.domain.payment.infra;

import com.starlord.payment.domain.payment.entity.PaymentApproval;
import com.starlord.payment.domain.payment.entity.PaymentCancel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentCancelRepository extends JpaRepository<PaymentCancel, Long> {
    Optional<PaymentCancel> findByPaymentSerialNumber(String paymentSerialNumber);
//    List<PaymentCancel> findByPaymentApproval(PaymentApproval paymentApproval);
}
