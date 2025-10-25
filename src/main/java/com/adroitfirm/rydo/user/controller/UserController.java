package com.adroitfirm.rydo.user.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adroitfirm.rydo.dto.UserDto;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import com.adroitfirm.rydo.user.service.UserService;
import com.adroitfirm.rydo.utility.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService svc;

    public UserController(UserService svc) { this.svc = svc;}
    
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signup(@Valid @RequestBody UserDto u) {
    	try {
    		svc.findByPhone(u.getPhone());
    		return ResponseEntity.ok(ApiResponse.error("Phone number already exist"));
    	} catch (ResourceNotFoundException e) {
    		return ResponseEntity.ok(ApiResponse.success(svc.create(u), "User created"));
    	}
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> create(@Valid @RequestBody UserDto u) {
        return ResponseEntity.ok(ApiResponse.success(svc.create(u), "User created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> all() {
        return ResponseEntity.ok(ApiResponse.success(svc.findAll(), "ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> byId(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(svc.findById(id), "User details processed"));
    }
    
    @GetMapping("/phone/{phone}")
    public ResponseEntity<ApiResponse<UserDto>> byMobile(@PathVariable String phone) {
    	return ResponseEntity.ok(ApiResponse.success(svc.findByPhone(phone), "User details processed"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}