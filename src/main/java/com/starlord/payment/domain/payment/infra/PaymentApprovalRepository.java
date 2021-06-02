package com.starlord.payment.domain.payment.infra;

import com.starlord.payment.domain.payment.entity.PaymentApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentApprovalRepository extends JpaRepository<PaymentApproval, Long> {
    Optional<PaymentApproval> findByPaymentSerialNumber(String paymentSerialNumber);
}

