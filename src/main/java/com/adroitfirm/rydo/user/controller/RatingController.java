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

import com.adroitfirm.rydo.dto.RatingDto;
import com.adroitfirm.rydo.user.entity.Rating;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import com.adroitfirm.rydo.user.mapper.RatingMapper;
import com.adroitfirm.rydo.user.service.RatingService;
import com.adroitfirm.rydo.user.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    private final RatingService svc;
    private final RatingMapper mapper;

    public RatingController(RatingService svc, RatingMapper mapper) { this.svc = svc; this.mapper = mapper; }

    @PostMapping
    public ResponseEntity<ApiResponse<RatingDto>> create(@Valid @RequestBody RatingDto r) {
        Rating saved = svc.create(mapper.toEntity(r));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(saved), "Rating created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RatingDto>>> all() {
        List<RatingDto> list = svc.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(list, "ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RatingDto>> byId(@PathVariable Long id) {
        Rating rating = svc.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rating not found: " + id));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(rating), "ok"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}