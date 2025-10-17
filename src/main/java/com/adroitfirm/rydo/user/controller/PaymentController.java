package com.adroitfirm.rydo.user.controller;

import com.adroitfirm.rydo.user.dto.PaymentDto;
import com.adroitfirm.rydo.user.entity.Payment;
import com.adroitfirm.rydo.user.mapper.PaymentMapper;
import com.adroitfirm.rydo.user.service.PaymentService;
import com.adroitfirm.rydo.user.util.ApiResponse;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService svc;
    private final PaymentMapper mapper;

    public PaymentController(PaymentService svc, PaymentMapper mapper) { this.svc = svc; this.mapper = mapper; }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentDto>> create(@Valid @RequestBody PaymentDto p) {
        Payment saved = svc.create(mapper.toEntity(p));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(saved), "Payment created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentDto>>> all() {
        List<PaymentDto> list = svc.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(list, "ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentDto>> byId(@PathVariable Long id) {
        Payment p = svc.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + id));
        return ResponseEntity.ok(ApiResponse.success(mapper.toDto(p), "ok"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}