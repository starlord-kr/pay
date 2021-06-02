package com.starlord.payment.domain.card.infra;

import com.starlord.payment.domain.card.entity.CardCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardCompanyRepository extends JpaRepository<CardCompany, Long> {
    Optional<CardCompany> findByPaymentSerialNumber(String paymentSerialNumber);
}

