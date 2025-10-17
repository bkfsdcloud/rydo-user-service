package com.adroitfirm.rydo.user.service;

import com.adroitfirm.rydo.user.entity.Rating;
import com.adroitfirm.rydo.user.repository.RatingRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository repo;

    public RatingService(RatingRepository repo) { this.repo = repo; }

    public Rating create(Rating r) { return repo.save(r); }
    public List<Rating> findAll() { return repo.findAll(); }
    public Optional<Rating> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}