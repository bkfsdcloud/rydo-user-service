package com.adroitfirm.rydo.user.service;

import com.adroitfirm.rydo.user.entity.Ride;
import com.adroitfirm.rydo.user.repository.RideRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {
    private final RideRepository repo;

    public RideService(RideRepository repo) { this.repo = repo; }

    public Ride create(Ride r) { return repo.save(r); }
    public List<Ride> findAll() { return repo.findAll(); }
    public Optional<Ride> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}