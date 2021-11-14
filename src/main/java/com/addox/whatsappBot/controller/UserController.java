package com.addox.whatsappBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.addox.whatsappBot.entity.User;
import com.addox.whatsappBot.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public User createUser(@RequestBody User userDetails) {
		return userService.createUser(userDetails);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/screenshot")
	public byte[] createUser(@RequestParam String userId) {
		return userService.getScreenshot(Long.parseLong(userId));
	}
}
