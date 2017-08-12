/**
 * 
 */
package com.ResponceGenerator;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author VAIBHAV-PC
 *
 */
public class FacebookWebhook {
	// TO DO : modify name of class as per facebook description

	private String pageToken;
	private String fbRequestURL;
	private String responseMSG;
	private String userId;
	private String userMSG;

	public String getPageToken() {
		return pageToken;
	}

	public void setPageToken(String pageToken) {
		this.pageToken = pageToken;
	}

	public String getFbRequestURL() {
		return fbRequestURL;
	}

	public void setFbRequestURL(String fbRequestURL) {
		this.fbRequestURL = fbRequestURL;
	}

	public String getResponseMSG() {
		return responseMSG;
	}

	public void setResponseMSG(String responseMSG) {
		this.responseMSG = responseMSG;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserMSG() {
		return userMSG;
	}

	public void setUserMSG(String userMSG) {
		this.userMSG = userMSG;
	}

	// To Do : use properties file to get all data reagarding facebook (no
	// hardcode any data)

	public void setRequestUrl(String RequestURL, String PageToken) {
		try {
			if (isNotEmpty(RequestURL) && isNotEmpty(PageToken)) {
				this.setPageToken(PageToken);
				StringBuilder fbRequestURL = new StringBuilder();
				fbRequestURL.append(RequestURL.trim());
				fbRequestURL.append(PageToken.trim());
				this.setFbRequestURL(fbRequestURL.toString());
				System.out.println("RequestUrl is :  " + this.fbRequestURL);
			}
		} catch (Exception e) {
			System.out.println("error while woring with setRequset : : " + e);
			e.printStackTrace();
		}

	}

	@SuppressWarnings("null")
	public static boolean isNotEmpty(String value) {
		return (value != null) || (value.length() != 0);
	}

	public String getUserMesssage(String Message) {
		try {

			if (isNotEmpty(Message)) {
				JSONObject jSONObject = new JSONObject(Message);

				JSONArray entry = (JSONArray) jSONObject.get("entry");
				for (int i = 0; i < entry.length(); i++) {
					System.out.println(i);
					JSONObject jsonobj = (JSONObject) entry.get(i);
					JSONArray messaging = (JSONArray) jsonobj.get("messaging");
					JSONObject messagingObject = (JSONObject) messaging.get(0);
					JSONObject sender = (JSONObject) messagingObject.get("sender");
					JSONObject message = (JSONObject) messagingObject.get("message");

					this.userId = String.valueOf(sender.get("id"));
					this.userMSG = (String) message.get("text");
					System.out.println("userId : " + userId + " messaage : " + userMSG);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception while reading use message : ");
			e.printStackTrace();
		}
		return this.userMSG;
	}

	public void responseMSG() {
		CloseableHttpClient client = null;
		HttpPost httpPost = null;
		try {

			if (isNotEmpty(this.responseMSG)) {
				this.responseMSG = this.userMSG;
			}
			System.out.println("response meddage  : " + this.responseMSG);

			client = HttpClients.createDefault();
			httpPost = new HttpPost(this.fbRequestURL);
			JSONObject recipient = new JSONObject();

			recipient.put("id", this.userId);
			JSONObject message = new JSONObject();
			message.put("text", this.responseMSG);

			JSONObject responseJson = new JSONObject();
			responseJson.put("recipient", recipient);
			responseJson.put("message", message);

			String json = responseJson.toString();
			System.out.println("converting response json object to string : " + json);
			StringEntity entity = new StringEntity(json);

			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			CloseableHttpResponse response = client.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());

		} catch (Exception e) {
			System.out.println("Exceptione while sending data to user chat board : " + e);
			e.printStackTrace();
		} finally {
			try {
				if (client != null) {
					client.close();
				}
			} catch (Exception e) {
				System.out.println("Exception whlile closing httpClient : " + e);
				e.printStackTrace();
			}

		}

	}

}
