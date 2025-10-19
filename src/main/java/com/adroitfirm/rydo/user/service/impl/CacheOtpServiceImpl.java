package com.adroitfirm.rydo.user.service.impl;

import java.util.Random;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.adroitfirm.rydo.user.service.OtpService;

import lombok.AllArgsConstructor;

@Service
@Profile(value = {"local","default"})
@AllArgsConstructor
public class CacheOtpServiceImpl implements OtpService {

	private final CacheManager cacheManager;
    private final Random random = new Random();

    public String generateOtp(String mobile) {
        String otp = String.format("%06d", random.nextInt(999999));
        cacheManager.getCache("otpCache").put(mobile, otp);
        System.out.println("OTP for " + mobile + " -> " + otp);
        return otp;
    }

    public boolean validateOtp(String mobile, String otp) {
        var cache = cacheManager.getCache("otpCache");
        String cachedOtp = cache != null ? cache.get(mobile, String.class) : null;
        if (cachedOtp != null && cachedOtp.equals(otp)) {
            cache.evict(mobile);
            return true;
        }
        return false;
    }

}
