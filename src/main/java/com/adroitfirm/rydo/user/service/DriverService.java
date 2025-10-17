package com.adroitfirm.rydo.user.service;

import com.adroitfirm.rydo.user.entity.Driver;
import com.adroitfirm.rydo.user.repository.DriverRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DriverService {
    private final DriverRepository repo;

    public DriverService(DriverRepository repo) {
        this.repo = repo;
    }

    public Driver create(Driver d) { return repo.save(d); }
    public List<Driver> findAll() { return repo.findAll(); }
    public Optional<Driver> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}