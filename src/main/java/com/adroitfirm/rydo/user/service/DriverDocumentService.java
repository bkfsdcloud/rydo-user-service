package com.adroitfirm.rydo.user.service;

import com.adroitfirm.rydo.user.entity.DriverDocument;
import com.adroitfirm.rydo.user.repository.DriverDocumentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DriverDocumentService {
    private final DriverDocumentRepository repo;

    public DriverDocumentService(DriverDocumentRepository repo) { this.repo = repo; }

    public DriverDocument create(DriverDocument d) { return repo.save(d); }
    public List<DriverDocument> findAll() { return repo.findAll(); }
    public Optional<DriverDocument> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}