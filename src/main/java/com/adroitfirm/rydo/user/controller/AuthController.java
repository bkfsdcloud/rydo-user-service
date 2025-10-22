package com.adroitfirm.rydo.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adroitfirm.rydo.user.dto.UserDto;
import com.adroitfirm.rydo.user.exception.ResourceNotFoundException;
import com.adroitfirm.rydo.user.service.OtpService;
import com.adroitfirm.rydo.user.service.UserService;
import com.adroitfirm.rydo.user.util.ApiResponse;
import com.adroitfirm.rydo.user.util.JwtUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final UserService userService;
	private final OtpService otpService;
	private final JwtUtils jwtUtils;

	@PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<String>> sendOtp(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        
        try {
        	userService.findByPhone(phone);
    		String otp = otpService.generateOtp(phone);
    		return ResponseEntity.ok(ApiResponse.success(otp, "OTP sent to mobile"));
    	} catch (ResourceNotFoundException e) {
    		return ResponseEntity.badRequest().body(ApiResponse.error("Mobile not registered"));
    	}
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody Map<String, String> body) {
        String phone = body.get("phone");
        
        try {
        	UserDto userDto = userService.findByPhone(phone);
        	String otp = body.get("otp");
        	int result = otpService.validateOtp(phone, otp);
        	if (result == 0) {
        		String token = jwtUtils.generateToken(userDto);
        		return ResponseEntity.ok(ApiResponse.success(token, "Successfully Validated"));
        	} else if (result == 1) {
        		return ResponseEntity.badRequest().body(ApiResponse.error("OTP expired"));
        	}
    	} catch (ResourceNotFoundException e) {
    		return ResponseEntity.badRequest().body(ApiResponse.error("Mobile not registered"));
    	}
        return ResponseEntity.badRequest().body(ApiResponse.error("Invalid OTP"));
    }
}
