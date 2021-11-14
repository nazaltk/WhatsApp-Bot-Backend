package com.addox.whatsappBot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.addox.whatsappBot.model.MessageData;
import com.addox.whatsappBot.service.WhatsAppService;

@RestController
@RequestMapping(value = "/whatsapp")
public class WhatsAppController {

	@Autowired
	WhatsAppService whatsAppService;

	@RequestMapping(method = RequestMethod.GET, value = "/status")
	public Map<String, String> getLoginStatus(@RequestParam String userId) {
		return whatsAppService.getLoginStatus(Long.parseLong(userId));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/open")
	public String createUser(@RequestParam String userId) {
		return whatsAppService.openWhatsapp(Long.parseLong(userId));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/send")
	public Boolean sendMessage(@RequestParam String userId, @RequestBody MessageData messageData) {
		return whatsAppService.sendMessage(Long.parseLong(userId), messageData);
	}
}
