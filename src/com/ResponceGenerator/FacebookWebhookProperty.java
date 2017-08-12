/**
 * 
 */
package com.ResponceGenerator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author VAIBHAV-PC
 *
 */
public class FacebookWebhookProperty {

	public static Properties facebookWebhookProperties = null;

	static {
		facebookWebhookProperties = new Properties();
		try (InputStream fileInputStream = new FileInputStream("FBWebhook.properties")) {
			facebookWebhookProperties.load(fileInputStream);
		} catch (Exception e) {
			System.out.println("Exception while loading property file : " + e);
		}
	}

	public static String getFacebookWebhookProperties(String propertyName) {
		return facebookWebhookProperties.getProperty(propertyName);
	}

}
