package com.adroitfirm.rydo.user.service;

import com.adroitfirm.rydo.user.entity.Vehicle;
import com.adroitfirm.rydo.user.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private final VehicleRepository repo;

    public VehicleService(VehicleRepository repo) { this.repo = repo; }

    public Vehicle create(Vehicle v) { return repo.save(v); }
    public List<Vehicle> findAll() { return repo.findAll(); }
    public Optional<Vehicle> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}