package dsd2016.api;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import dsd2016.api.JSONRequest.HTTPMethod;

public class Test {
	public static void main(String args[]) throws JSONException, MalformedURLException, IOException
	{
		JSONRequest req1 = new JSONRequest("http://ix.cs.uoregon.edu:3555/api/verify/");
		JSONObject obj1 = req1.SyncSendJSON(new JSONObject("{\"email\":\"thisisemail@email.com\"}"), HTTPMethod.GET);
		JSONRequest req2 = new JSONRequest("http://ix.cs.uoregon.edu:3555/api/authenticate/");
		String reqStr = "{" +
			"\"email\": \"user_email\"," +
			"\"password\": \"password\"," +
			"\"picture\" : \"eRHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY=\"}";
		JSONObject obj2 = req2.SyncSendJSON(new JSONObject(reqStr)/*, HTTPMethod.POST*/);
		System.out.println(obj1.toString() + "\n" + obj2.toString());
	}
}
