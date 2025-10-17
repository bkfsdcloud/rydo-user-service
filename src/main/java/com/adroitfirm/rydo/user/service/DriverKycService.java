package com.adroitfirm.rydo.user.service;

import com.adroitfirm.rydo.user.entity.DriverKyc;
import com.adroitfirm.rydo.user.repository.DriverKycRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DriverKycService {
    private final DriverKycRepository repo;

    public DriverKycService(DriverKycRepository repo) { this.repo = repo; }

    public DriverKyc create(DriverKyc k) { return repo.save(k); }
    public List<DriverKyc> findAll() { return repo.findAll(); }
    public Optional<DriverKyc> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}