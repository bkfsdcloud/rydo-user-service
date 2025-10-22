package com.adroitfirm.rydo.user.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.adroitfirm.rydo.user.service.OtpService;

@Component
@Profile("cloud")
public class SMSOtpServiceImpl implements OtpService {

	@Override
	public String generateOtp(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int validateOtp(String mobile, String otp) {
		// TODO Auto-generated method stub
		return 0;
	}

}
