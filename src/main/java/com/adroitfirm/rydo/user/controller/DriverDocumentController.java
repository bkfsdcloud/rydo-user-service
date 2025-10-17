package com.adroitfirm.rydo.user.controller;

import com.adroitfirm.rydo.user.dto.DriverDocumentDto;
import com.adroitfirm.rydo.user.entity.DriverDocument;
import com.adroitfirm.rydo.user.mapper.DriverDocumentMapper;
import com.adroitfirm.rydo.user.service.DriverDocumentService;
import com.adroitfirm.rydo.user.util.ApiResponse;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/driver-documents")
public class DriverDocumentController {
    private final DriverDocumentService svc;
    private final DriverDocumentMapper mapper;

    public DriverDocumentController(DriverDocumentService svc, DriverDocumentMapper mapper) { this.svc = svc; this.mapper = mapper; }

    @PostMapping
    public ResponseEntity<ApiResponse<DriverDocumentDto>> create(@Valid @RequestBody DriverDocumentDto d) {
        DriverDocument saved = svc.create(mapper.toEntity(d));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(saved), "Driver document created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DriverDocumentDto>>> all() {
        List<DriverDocumentDto> list = svc.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(list, "ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DriverDocumentDto>> byId(@PathVariable Long id) {
        DriverDocument doc = svc.findById(id).orElseThrow(() -> new ResourceNotFoundException("Document not found: " + id));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(doc), "ok"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}