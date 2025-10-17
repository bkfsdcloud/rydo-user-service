package com.adroitfirm.rydo.user.repository;

import com.adroitfirm.rydo.user.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}