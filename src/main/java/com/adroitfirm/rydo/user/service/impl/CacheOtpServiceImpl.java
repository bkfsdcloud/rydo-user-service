package com.adroitfirm.rydo.user.service.impl;

import java.time.Duration;
import java.util.Random;

import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.adroitfirm.rydo.user.service.OtpService;

import lombok.AllArgsConstructor;

@Service
@Profile(value = {"local","default"})
@AllArgsConstructor
public class CacheOtpServiceImpl implements OtpService {

	private final RedisTemplate<String, Object> redisTemplate;
    private final Random random = new Random();

    public String generateOtp(String mobile) {
        String otp = String.format("%06d", random.nextInt(999999));
        redisTemplate.opsForValue().set(mobile, otp, Duration.ofMinutes(1));
        System.out.println("OTP for " + mobile + " -> " + otp);
        return otp;
    }

    public int validateOtp(String mobile, String otp) {
        String cachedOtp = (String) redisTemplate.opsForValue().getAndDelete(mobile);
        if (cachedOtp == null) {
        	return 1;
        } else if (cachedOtp.equals(otp)) {
            return 0;
        } else {
        	return -1;
        }
    }

}
