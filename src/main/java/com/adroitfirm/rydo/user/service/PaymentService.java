package com.adroitfirm.rydo.user.service;

import com.adroitfirm.rydo.user.entity.Payment;
import com.adroitfirm.rydo.user.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository repo;

    public PaymentService(PaymentRepository repo) { this.repo = repo; }

    public Payment create(Payment p) { return repo.save(p); }
    public List<Payment> findAll() { return repo.findAll(); }
    public Optional<Payment> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}