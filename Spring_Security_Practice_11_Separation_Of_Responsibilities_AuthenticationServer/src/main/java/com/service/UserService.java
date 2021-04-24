package com.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entities.Otp;
import com.entities.User;
import com.repositorys.OtpRepository;
import com.repositorys.UserRepository;
import com.util.GenerateCodeUtil;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OtpRepository otpRepository;
	
	public void addUser(User user) {
		
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public void auth(User user) {
		
		Optional <User> o = userRepository.findUserByUsername(user.getUsername());
		
		if(o.isPresent()) {
			User u = o.get();
			
			if(encoder.matches(user.getPassword(), u.getPassword())) {
				renewOtp(u);
			} else {
				throw new BadCredentialsException("Bad Credentials");
			}
		} else {
			throw new BadCredentialsException("Bad Credentials");
		}
	}
	
	public boolean check(Otp otpToValidate) {
		
		Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());
		
		if(userOtp.isPresent()) {
			Otp otp = userOtp.get();
			if(otpToValidate.getCode() .equals(otp.getCode())) {
				return true;
			}
		}
		return false;
	}
	
	private void renewOtp(User user) {
		
		String code = GenerateCodeUtil.generateCode();
		Optional<Otp> userOtp = otpRepository.findOtpByUsername(user.getUsername());
		
		if(userOtp.isPresent()) {
			Otp otp = userOtp.get();
			otp.setCode(code);
		} else {
			Otp otp = new Otp();
			otp.setUsername(user.getUsername());
			otp.setCode(code);
			otpRepository.save(otp);
		}
	}
}
