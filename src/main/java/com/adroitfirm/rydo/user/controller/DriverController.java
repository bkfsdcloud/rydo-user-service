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

import com.adroitfirm.rydo.dto.DriverDto;
import com.adroitfirm.rydo.user.entity.Driver;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import com.adroitfirm.rydo.user.mapper.DriverMapper;
import com.adroitfirm.rydo.user.service.DriverService;
import com.adroitfirm.rydo.user.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService svc;
    private final DriverMapper mapper;

    public DriverController(DriverService svc, DriverMapper mapper) { this.svc = svc; this.mapper = mapper; }

    @PostMapping
    public ResponseEntity<ApiResponse<DriverDto>> create(@Valid @RequestBody DriverDto d) {
        Driver saved = svc.create(mapper.toEntity(d));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(saved), "Driver created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DriverDto>>> all() {
        List<DriverDto> list = svc.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(list, "ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DriverDto>> byId(@PathVariable Long id) {
        Driver driver = svc.findById(id).orElseThrow(() -> new ResourceNotFoundException("Driver not found: " + id));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(driver), "ok"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}