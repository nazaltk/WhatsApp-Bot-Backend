package com.addox.whatsappBot.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class WebUtility {
	
	private static Map<Long, WebDriver> userDriverData = new HashMap<>();

	public static void waitUntilElementIsVisible(WebDriver driver, String xPath) {

		while (driver.findElements(By.xpath(xPath)).size() == 0) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveDriverDataToUser(long userId, WebDriver driver) {
		userDriverData.put(userId, driver);
	}

	public static WebDriver getDriverDataForUser(long userId) {
		 
		return userDriverData.get(userId );
	}

	public static void openNewTabAndSwitch(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
	}

	public static void closeTabAndSwitchToFirst(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.close()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
	}
}
