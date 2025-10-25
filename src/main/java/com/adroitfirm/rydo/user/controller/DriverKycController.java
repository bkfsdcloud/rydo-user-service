package com.adroitfirm.rydo.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adroitfirm.rydo.dto.DriverKycDto;
import com.adroitfirm.rydo.user.entity.DriverKyc;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import com.adroitfirm.rydo.user.mapper.DriverKycMapper;
import com.adroitfirm.rydo.user.service.DriverKycService;
import com.adroitfirm.rydo.utility.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/driver-kyc")
public class DriverKycController {
    private final DriverKycService svc;
    private final DriverKycMapper mapper;

    public DriverKycController(DriverKycService svc, DriverKycMapper mapper) { this.svc = svc; this.mapper = mapper; }

    @PostMapping
    public ResponseEntity<ApiResponse<DriverKycDto>> create(@Valid @RequestBody DriverKycDto d) {
        DriverKyc saved = svc.create(mapper.toEntity(d));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(saved), "Driver KYC created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DriverKycDto>>> all() {
        List<DriverKycDto> list = svc.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(list, "ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DriverKycDto>> byId(@PathVariable Long id) {
        DriverKyc k = svc.findById(id).orElseThrow(() -> new ResourceNotFoundException("DriverKyc not found: " + id));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(k), "ok"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}