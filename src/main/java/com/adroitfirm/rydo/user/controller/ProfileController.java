package com.adroitfirm.rydo.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adroitfirm.rydo.dto.UserDto;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import com.adroitfirm.rydo.user.service.UserService;
import com.adroitfirm.rydo.user.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService svc;
	    
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signup(@Valid @RequestBody UserDto u) {
    	try {
    		svc.findByPhone(u.getPhone());
    		return ResponseEntity.ok(ApiResponse.error("Phone number already exist"));
    	} catch (ResourceNotFoundException e) {
    		return ResponseEntity.ok(ApiResponse.success(svc.create(u), "User created"));
    	}
    }
}
