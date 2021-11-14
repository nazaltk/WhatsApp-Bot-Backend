package com.addox.whatsappBot.service;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addox.whatsappBot.model.MessageData;
import com.addox.whatsappBot.repository.UserRepository;
import com.addox.whatsappBot.utility.WebUtility;

@Service
public class WhatsAppService {

	private String WHATSAPP_WEB_URL = "https://web.whatsapp.com/";

	private String GOOGLE_URL = "https://google.com/";

	private String STATUS_ATTR = "Status";

	private String QR_CODE_ATTR = "qrCode";

	private String SUCCESS_VALUE = "Success";

	private String CONNTECTING_VALUE = "Connecting";

	private String PENDING_VALUE = "Pending";

	private String QR_CODE_PATH = "//div[@class='_2UwZ_']";

	private String QR_CODE_WITH_RELOAD_PATH = "//div[@class='_2UwZ_ n4LVt']";

	private String PROFILE_PICTURE_XPATH = "//span[@data-icon='default-user']";

	private String SEND_BUTTON_XPATH = "//span[@data-icon='send']";

	@Autowired
	UserRepository userRepository;

	public String openWhatsapp(long userId) {
		System.setProperty("webdriver.chrome.driver", "/app/.chromedriver/bin/chromedriver");
		//System.setProperty("webdriver.chrome.driver", "N:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		WebUtility.saveDriverDataToUser(userId, driver);

		driver.get(WHATSAPP_WEB_URL);

		//WebUtility.waitUntilElementIsVisible(driver, QR_CODE_PATH);

		return "Success";

	}

	public Boolean sendMessage(long userId, MessageData messageData) {

		WebDriver driver = WebUtility.getDriverDataForUser(userId);

		// WebUtility.openNewTabAndSwitch(driver);

		driver.get("https://web.whatsapp.com/send?text=hello&phone=918547510128&app_absent=1");

		WebUtility.waitUntilElementIsVisible(driver, SEND_BUTTON_XPATH);

		driver.findElement(By.xpath(SEND_BUTTON_XPATH)).click();

		WebUtility.sleep(500);
		// WebUtility.closeTabAndSwitchToFirst(driver);

		return true;
	}

	public Map<String, String> getLoginStatus(long userId) {
		Map<String, String> response = new HashMap<>();

		WebDriver driver = WebUtility.getDriverDataForUser(userId);

		WebUtility.sleep(1000);

		if (driver.findElements(By.xpath(PROFILE_PICTURE_XPATH)).size() > 0) {
			response.put(STATUS_ATTR, SUCCESS_VALUE);
		} else if (driver.findElements(By.xpath(QR_CODE_PATH)).size() > 0) {
			response.put(STATUS_ATTR, PENDING_VALUE);
			response.put(QR_CODE_ATTR, findQRCOdeValue(driver));
		} else if (driver.findElements(By.xpath(QR_CODE_WITH_RELOAD_PATH)).size() > 0) {
			response.put(STATUS_ATTR, PENDING_VALUE);

			driver.findElement(By.xpath(QR_CODE_WITH_RELOAD_PATH)).click();

			WebUtility.sleep(500);

			response.put(QR_CODE_ATTR, findQRCOdeValue(driver));
		} else {
			response.put(STATUS_ATTR, CONNTECTING_VALUE);
		}

		return response;
	}

	private String findQRCOdeValue(WebDriver driver) {

		WebUtility.waitUntilElementIsVisible(driver, QR_CODE_PATH);

		WebElement qrCodeElement = driver.findElement(By.xpath(QR_CODE_PATH));

		return qrCodeElement.getAttribute("data-ref");
	}

}
