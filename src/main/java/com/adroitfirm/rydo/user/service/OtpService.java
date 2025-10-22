package com.adroitfirm.rydo.user.service;

public interface OtpService {

	public String generateOtp(String mobile);
    public int validateOtp(String mobile, String otp);
}
