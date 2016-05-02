package dsd2016.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import dsd2016.api.JSONRequest.HTTPMethod;

public class Tests {
	public static void main(String args[]) throws JSONException, MalformedURLException, IOException
	{
		//JSONRequest Test 1
		JSONRequest req1 = new JSONRequest("http://ix.cs.uoregon.edu:3555/api/verify/");
		JSONObject res1 = req1.SyncSendJSON(new JSONObject("{\"email\":\"thisisemail@email.com\"}"), HTTPMethod.GET);
		System.out.println("JSONRequest Test 1 response: " + res1.toString() + "\n");
		
		//JSONRequest Test 2
		JSONRequest req2 = new JSONRequest("http://ix.cs.uoregon.edu:3555/api/authenticate/");
		JSONObject content = new JSONObject();
		content.put("email", "user_email");
		content.put("password", "password");
		content.put("picture", "eRHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY=");
		JSONObject res2 = req2.SyncSendJSON(new JSONObject(content)/*, HTTPMethod.POST*/);
		System.out.println("JSONRequest Test 2 response: " + res2.toString() + "\n");
		
		//registerNewUser test 1
		System.out.println("registerNewUser test 1:");
		DSD2016JAVA japi1 = new DSD2016JAVA();
		ArrayList<String> pics1 = new ArrayList<String>();
		pics1.add("eRHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY=");
		pics1.add("aHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY=");
		StringBuilder outMsg1 = new StringBuilder();
		
		int ret1 = japi1.registerNewUser(
						"Jeison Andres Hurtado", 
						"yeison_andres94@hotmail.com", 
						"123456789", 
						"Male", 
						pics1, 
						null, 
						outMsg1);
		
		System.out.println("Message: " + outMsg1.toString() + "\n" + 
				"Returned: " + ret1 + "\n");
		
		//validateUser test 1
		System.out.println("validateUser test 1:");
		DSD2016JAVA japi2 = new DSD2016JAVA();
		StringBuilder outMsg2 = new StringBuilder();
		
		int ret2 = japi2.validateUser(
						"yeison_andres94@hotmail.com", 
						"123456789", 
						"eRHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY=",
						outMsg2);
		
		System.out.println("Message: " +  outMsg2.toString() + "\n" + 
				"Returned: " + ret2 + "\n");
		
		//validateUser test 2
		System.out.println("validateUser test 2:");
		DSD2016JAVA japi3 = new DSD2016JAVA();
		StringBuilder outMsg3 = new StringBuilder();
		
		int ret3 = japi3.validateUser(
						"2222222", 
						"1111111", 
						"3123124ASDHFHGGTJ52342ASDG",
						outMsg3);
		
		System.out.println("Message: " +  outMsg3.toString() + "\n" + 
				"Returned: " + ret3 + "\n");
		
		//verifyEmail test 1
		System.out.println("verifyEmail test 1:");
		DSD2016JAVA japi4 = new DSD2016JAVA();
		StringBuilder outMsg4 = new StringBuilder();
		
		boolean ret4 = japi4.verifyEmail("yeison_andres94@hotmail.com", outMsg4);
		System.out.println("Message: " +  outMsg4.toString() + "\n" + 
				"Returned: " + ret4 + "\n");
		
		//verifyEmail test 2
		System.out.println("verifyEmail test 2:");
		DSD2016JAVA japi5 = new DSD2016JAVA();
		StringBuilder outMsg5 = new StringBuilder();
		
		boolean ret5 = japi5.verifyEmail("1112412123@123.com", outMsg5);
		System.out.println("Message: " +  outMsg5.toString() + "\n" + 
				"Returned: " + ret5 + "\n");
		
		//verifyEmail test 3
		System.out.println("verifyEmail test 3:");
		DSD2016JAVA japi6 = new DSD2016JAVA();
		StringBuilder outMsg6 = new StringBuilder();
		
		boolean ret6 = japi6.verifyEmail("11124121231.com", outMsg6);
		System.out.println("Message: " +  outMsg6.toString() + "\n" + 
				"Returned: " + ret6 + "\n");
	}
}
