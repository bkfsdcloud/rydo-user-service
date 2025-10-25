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

import com.adroitfirm.rydo.dto.VehicleDto;
import com.adroitfirm.rydo.user.entity.Vehicle;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import com.adroitfirm.rydo.user.mapper.VehicleMapper;
import com.adroitfirm.rydo.user.service.VehicleService;
import com.adroitfirm.rydo.user.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService svc;
    private final VehicleMapper mapper;

    public VehicleController(VehicleService svc, VehicleMapper mapper) { this.svc = svc; this.mapper = mapper; }

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleDto>> create(@Valid @RequestBody VehicleDto v) {
        Vehicle saved = svc.create(mapper.toEntity(v));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(saved), "Vehicle created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleDto>>> all() {
        List<VehicleDto> list = svc.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(list, "ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleDto>> byId(@PathVariable Long id) {
        Vehicle vehicle = svc.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(vehicle), "ok"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}