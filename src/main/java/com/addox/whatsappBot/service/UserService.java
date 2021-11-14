package com.addox.whatsappBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addox.whatsappBot.entity.User;
import com.addox.whatsappBot.repository.UserRepository;

@Service
public class UserService {
 
	@Autowired
	UserRepository userRepository;
	
	public User createUser(User userDetails) {
		User user = new User();
		user.setUsername(userDetails.getUsername());
		user.setPassword(userDetails.getPassword());
		user.setMobileNumber(userDetails.getMobileNumber());
		
		return userRepository.save(user);
	}
	
}
