package com.adroitfirm.rydo.user.controller;

import com.adroitfirm.rydo.user.dto.RideDto;
import com.adroitfirm.rydo.user.entity.Ride;
import com.adroitfirm.rydo.user.mapper.RideMapper;
import com.adroitfirm.rydo.user.service.RideService;
import com.adroitfirm.rydo.user.util.ApiResponse;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rides")
public class RideController {
    private final RideService svc;
    private final RideMapper mapper;

    public RideController(RideService svc, RideMapper mapper) { this.svc = svc; this.mapper = mapper; }

    @PostMapping
    public ResponseEntity<ApiResponse<RideDto>> create(@Valid @RequestBody RideDto r) {
        Ride saved = svc.create(mapper.toEntity(r));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(saved), "Ride created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RideDto>>> all() {
        List<RideDto> list = svc.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(list, "ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RideDto>> byId(@PathVariable Long id) {
        Ride ride = svc.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ride not found: " + id));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(ride), "ok"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}