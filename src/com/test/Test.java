/**
 * 
 */
package com.test;

import com.ResponceGenerator.FacebookWebhookProperty;

/**
 * @author VAIBHAV-PC
 *
 */
public class Test {
	
	public static void main(String[] args){
		System.out.println(String.format(FacebookWebhookProperty.getFacebookWebhookProperties("facebook.webhook.url"), FacebookWebhookProperty.getFacebookWebhookProperties("facebook.page.token")));
	}

}
