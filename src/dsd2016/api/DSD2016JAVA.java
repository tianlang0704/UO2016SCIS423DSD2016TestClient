package dsd2016.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import dsd2016.api.JSONRequest.HTTPMethod;

public class DSD2016JAVA 
{	
	private static final String registerNewUserURL = "http://ix.cs.uoregon.edu:3555/api/register";
	private static final String validateUserURL = "http://ix.cs.uoregon.edu:3555/api/authenticate";
	private static final String verifyEmailURL = "http://ix.cs.uoregon.edu:3555/api/verify";
	
	public int registerNewUser(
			String name, String email, String password, String gender, 
			ArrayList<String> inB64Pics, ArrayList<String> outB64BadPics, StringBuilder outMsg)
	{
		JSONObject content = new JSONObject();
		content.put("name", name);
		content.put("email", email);
		content.put("password", password);
		content.put("gender", gender);
		content.put("picture", Base64ArrayToPicIdJSONArray(inB64Pics));
		System.out.println("Sending JSON: " + content.toString());
		
		JSONRequest req = new JSONRequest(registerNewUserURL);
		JSONObject res;
		try{
			res = req.SyncSendJSON(content);
		}catch(Exception e){
			outMsg.append(e.getMessage());
			return 0;
		}
		
		outMsg.append(res.getString("message"));
		return res.getBoolean("success") ? 1 : 0;
	}
	
	
	
	public int validateUser(
			String email, String password, String inB64Pic, StringBuilder outMsg)
	{
		JSONObject content = new JSONObject();
		content.put("email", email);
		content.put("password", password);
		content.put("picture", inB64Pic);
		System.out.println("Sending JSON: " + content.toString());
		
		JSONRequest req = new JSONRequest(validateUserURL);
		JSONObject res;
		try{
			res = req.SyncSendJSON(content);
		}catch(Exception e){
			outMsg.append(e.getMessage());
			return 0;
		}
		
		outMsg.append(res.getString("message"));
		return res.getBoolean("success") ? 1 : 0;
	}
	
	public boolean verifyEmail(String email, StringBuilder outMsg)
	{
		JSONObject content = new JSONObject();
		content.put("email", email);
		System.out.println("Sending JSON: " + content.toString());
		
		JSONRequest req = new JSONRequest(verifyEmailURL);
		JSONObject res;
		try{
			res = req.SyncSendJSON(content, HTTPMethod.GET);
		}catch(Exception e){
			outMsg.append(e.getMessage());
			return false;
		}
		
		outMsg.append(res.getString("message"));
		return res.getBoolean("success");
	}
	
	private JSONArray Base64ArrayToPicIdJSONArray(ArrayList<String> inB64Pics)
	{
		JSONArray pics = new JSONArray(); 
		for(Integer i = 0; i < inB64Pics.size(); i++){
			JSONObject picObj = new JSONObject();
			picObj.put("id", i.toString());
			picObj.put("base64",inB64Pics.get(i));
			pics.put(picObj);
		}
		
		return pics;
	}
}
