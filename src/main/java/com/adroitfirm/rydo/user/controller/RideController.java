package com.adroitfirm.rydo.user.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adroitfirm.rydo.dto.RideDto;
import com.adroitfirm.rydo.user.enumeration.RideStatus;
import com.adroitfirm.rydo.user.service.RideService;
import com.adroitfirm.rydo.user.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rides")
public class RideController {
    private final RideService svc;

    @PostMapping
    public ResponseEntity<ApiResponse<RideDto>> create(@Valid @RequestBody RideDto r, @RequestHeader("X-USER-ID") Long userId) {
    	r.setCustomerId(userId);
        return ResponseEntity.ok(ApiResponse.success(svc.create(r), "Ride created"));
    }
    
    @PostMapping("/event/{status}")
    public ResponseEntity<ApiResponse<String>> event(@Valid @RequestBody RideDto r, @PathVariable(required = true) RideStatus status) {
        return ResponseEntity.ok(ApiResponse.success(svc.event(r, status), "Event updated"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RideDto>>> all() {
        return ResponseEntity.ok(ApiResponse.success(svc.findAll(), "ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RideDto>> byId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(svc.findById(id), "ok"));
    }

}