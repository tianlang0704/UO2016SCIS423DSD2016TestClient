package dsd2016.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class DSD2016JAVA 
{	
	//Setting up API URLs, will change to set-able variable later
	public static final String registerURL = "http://ix.cs.uoregon.edu:1337/register";
	public static final String loginUserURL = "http://ix.cs.uoregon.edu:1337/login";
	//private static final String verifyEmailURL = "http://ix.cs.uoregon.edu:1337/verify";
	
	//Defining error codes
	public static final int ERRORCODE_REGISTER_SUCCESS = 1;
	public static final int ERRORCODE_REGISTER_FAIL = 0;
	public static final int ERRORCODE_REGISTER_IO_ERROR = -1;
	public static final int ERRORCODE_REGISTER_UNKNOWN = -2;
	
	public static final int ERRORCODE_LOGIN_SUCCESS = 1;
	public static final int ERRORCODE_LOGIN_FAIL = 0;
	public static final int ERRORCODE_LOGIN_IO_ERROR = -1;
	public static final int ERRORCODE_LOGIN_UNKNOWN = -2;
	
	
	public static int registerNewUser(
			ArrayList<String> inB64Pics, ArrayList<String> outB64BadPics, StringBuilder outMsg)
	{
		//Convert inputs to JSONObject
		JSONObject content = new JSONObject();
		content.put("pictures", Base64ArrayToPicIdJSONArray(inB64Pics));
		System.out.println("Sending JSON: " + content.toString());
		
		//Create a JSONRequest for sending request
		JSONRequest req = new JSONRequest(registerURL);
		
		//Get response from the request
		//return -1 for IOException and return a message accordingly
		JSONObject res;
		try{
			res = req.SyncSendJSON(content);
		}catch(Exception e){
			outMsg.append(e.getMessage());
			return ERRORCODE_REGISTER_IO_ERROR;
		}
		
		//Deal with the errors
		if(res.getBoolean("success")){//Success
			outMsg.append(res.getString("userId"));
			return ERRORCODE_REGISTER_SUCCESS;
		}else if(res.has("errors")){//If errors section is found
			JSONArray errors = res.getJSONArray("errors");
			for(Object it : errors)//Go through every error and generate error message
			{
				JSONObject err = (JSONObject)it;
				int picID = 0, errCode = 0;
				String errMsg = new String();

				if(err.has("pictureID"))
					picID = err.getInt("pictureId");
				if(err.has("errorCode"))
					errCode = err.getInt("errorCode");
				if(err.has("errorMessage"))
					errMsg = err.getString("errorMessage");
				
				//error message format:
				//<Picture ID(input order)>,<Error code>,<Error message>
				outB64BadPics.add(Integer.toString(picID) + "," + Integer.toString(errCode) + "," + errMsg);
			}
			outMsg.append("Registration failed, check picture error message.");
			return ERRORCODE_REGISTER_FAIL;
		}else{
			outMsg.append("Unknown error");
			return ERRORCODE_REGISTER_UNKNOWN;
		}
	}
	
	
	
	public static int validateUser(String userID, String inB64Pic, StringBuilder outMsg)
	{
		//Convert inputs to JSONObject
		JSONObject content = new JSONObject();
		content.put("userId", userID);
		content.put("picture", inB64Pic);
		System.out.println("Sending JSON: " + content.toString());
		
		//Create a JSONRequest for sending request
		JSONRequest req = new JSONRequest(loginUserURL);
		
		//Get response from the request, fill return messages, and return
		JSONObject res;
		try{
			res = req.SyncSendJSON(content);
		}catch(Exception e){
			outMsg.append(e.getMessage());
			return ERRORCODE_LOGIN_IO_ERROR;
		}
		
		
		//Deal with the errors
		if(res.getBoolean("success")){//Success
			outMsg.append("Login success");
			return ERRORCODE_LOGIN_SUCCESS;
		}else if(res.has("errors")){//If errors section is found
			outMsg.append("Login failed");
			JSONArray errors = res.getJSONArray("errors");
			for(Object it : errors)//Go through every error and generate error message
			{
				outMsg.append(",");
				JSONObject err = (JSONObject)it;

				if(err.has("errorCode"))
					outMsg.append(Integer.toString(err.getInt("errorCode")));
				outMsg.append(",");
				if(err.has("errorMessage"))
					outMsg.append(err.getString("errorMessage"));
			}
			return ERRORCODE_LOGIN_FAIL;
		}else{
			outMsg.append("Unknown error");
			return ERRORCODE_LOGIN_UNKNOWN;
		}
	}
	
//	public static boolean verifyEmail(String email, StringBuilder outMsg)
//	{
//		//Convert inputs to JSONObject
//		JSONObject content = new JSONObject();
//		content.put("email", email);
//		System.out.println("Sending JSON: " + content.toString());
//		
//		//Create a JSONRequest for sending request
//		JSONRequest req = new JSONRequest(verifyEmailURL);
//		
//		//Get response from the request, fill return messages, and return
//		JSONObject res;
//		try{
//			res = req.SyncSendJSON(content, HTTPMethod.GET);
//		}catch(Exception e){
//			outMsg.append(e.getMessage());
//			return false;
//		}
//		
//		outMsg.append(res.getString("message"));
//		return res.getBoolean("success");
//	}
	
	//Helper function for converting a base64 array to JSON id and picture array
	public static JSONArray Base64ArrayToPicIdJSONArray(ArrayList<String> inB64Pics)
	{
		JSONArray pics = new JSONArray(); 
		for(Integer i = 0; i < inB64Pics.size(); i++){
			JSONObject picObj = new JSONObject();
			picObj.put("pictureId", i.toString());
			picObj.put("base64",inB64Pics.get(i));
			pics.put(picObj);
		}
		
		return pics;
	}
}
