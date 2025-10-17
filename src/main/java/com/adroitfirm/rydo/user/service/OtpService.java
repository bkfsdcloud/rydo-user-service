package com.adroitfirm.rydo.user.service;

public interface OtpService {

	public String generateOtp(String mobile);
    public boolean validateOtp(String mobile, String otp);
}
