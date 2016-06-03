package dsd2016.api;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

public class DSD2016JAVA 
{	
	//Setting up API URLs, will change to set-able variable later
//	public static final String registerURL = "http://ix.cs.uoregon.edu:1337/register";
//	public static final String loginUserURL = "http://ix.cs.uoregon.edu:1337/login";
	public static final String registerURL = "http://ix.cs.uoregon.edu:8888/JLU_AuthServer/register";
	public static final String loginUserURL = "http://ix.cs.uoregon.edu:8888/JLU_AuthServer/login";
	//Defining error codes
	public static final int ERRORCODE_REGISTER_SUCCESS = 1;
	public static final int ERRORCODE_REGISTER_FAIL = 0;
	public static final int ERRORCODE_REGISTER_IO_ERROR = -1;
	public static final int ERRORCODE_REGISTER_UNKNOWN = -2;
	
	public static final int ERRORCODE_LOGIN_SUCCESS = 1;
	public static final int ERRORCODE_LOGIN_FAIL = 0;
	public static final int ERRORCODE_LOGIN_IO_ERROR = -1;
	public static final int ERRORCODE_LOGIN_UNKNOWN = -2;
	
///////////////////////////////////////////////////////////////////////////////
/////////////////registerNewUser function and its variants/////////////////////
///////////////////////////////////////////////////////////////////////////////
	public static void asyncRsImgRegisterNewUser(
			ArrayList<BufferedImage> inPics, 
			DSD2016JAVACallBack cb
	)
	{
		ArrayList<String> inB64Pics = ImageToBase64(ImageResizeFixedRatio(inPics, 160));
		asyncRegisterNewUser(inB64Pics, cb);
	}
	
	public static int rsImgRegisterNewUser(
			ArrayList<BufferedImage> inPics, 
			ArrayList<String> outB64BadPics, 
			StringBuilder outMsg
	)
	{
		ArrayList<String> inB64Pics = ImageToBase64(ImageResizeFixedRatio(inPics, 160));
		return registerNewUser(inB64Pics, outB64BadPics, outMsg);
	}
	public static void asyncImgRegisterNewUser(
			ArrayList<BufferedImage> inPics, 
			DSD2016JAVACallBack cb
	)
	{
		ArrayList<String> inB64Pics = ImageToBase64(inPics);
		asyncRegisterNewUser(inB64Pics, cb);
	}
	
	public static int imgRegisterNewUser(
			ArrayList<BufferedImage> inPics, 
			ArrayList<String> outB64BadPics, 
			StringBuilder outMsg
	)
	{
		ArrayList<String> inB64Pics = ImageToBase64(inPics);
		return registerNewUser(inB64Pics, outB64BadPics, outMsg);
	}
	
	public static void asyncRegisterNewUser(
			ArrayList<String> inB64Pics, 
			DSD2016JAVACallBack cb
	)
	{
		//Create thread, define call back function and 
		Thread t = new Thread(()->{
			ArrayList<String> outB64BadPics = new ArrayList<String>();
			StringBuilder outMsg = new StringBuilder();
			int outErrorCode = registerNewUser(inB64Pics, outB64BadPics, outMsg);
			cb.call(outErrorCode, outB64BadPics, outMsg);
		});
		//start thread.
		t.start();
	}
	
	public static int registerNewUser(
			ArrayList<String> inB64Pics, 
			ArrayList<String> outB64BadPics, 
			StringBuilder outMsg
	)
	{
		//Convert inputs to JSONObject
		JSONObject content = new JSONObject();
		content.put("pictures", Base64ArrayToPicIdJSONArray(inB64Pics));
//		System.out.println("Sending JSON: " + content.toString());
		
		//Create a JSONRequest for sending request
		JSONRequest req = new JSONRequest(registerURL);
		
		//Get response from the request
		//return -1 for IOException and return a message accordingly
		JSONObject res;
		try{
			res = req.SyncSendJSON(content);
		}catch(org.json.JSONException e){
			outMsg.append("Server did not return a JSON object.");
			return ERRORCODE_REGISTER_IO_ERROR;
		}catch(Exception e){
			outMsg.append(e.getMessage());
			return ERRORCODE_REGISTER_IO_ERROR;
		}
		
		//Deal with the errors
		if(res.has("success") && res.getBoolean("success")){//Success
			outMsg.append(res.getString("userId"));
			return ERRORCODE_REGISTER_SUCCESS;
		}else if(res.has("errors")){//If errors section is found
			String msg = "Registration failed, check picture error message.";
			JSONArray errors = new JSONArray();
			
			try{
				errors = res.getJSONArray("errors");
			}catch(Exception e){
				msg = "Registration failed, unknown error.";
			}
			
			for(Object it : errors){//Go through every error and generate error message
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
			outMsg.append(msg);
			return ERRORCODE_REGISTER_FAIL;
		}else{
			outMsg.append("Unknown error");
			return ERRORCODE_REGISTER_UNKNOWN;
		}
	}
///////////////////////////////////////////////////////////////////////////////
/////////////////////End of registerNewUser section////////////////////////////
///////////////////////////////////////////////////////////////////////////////
	
	
	
///////////////////////////////////////////////////////////////////////////////
/////////////////////validateUser function and its variants////////////////////
///////////////////////////////////////////////////////////////////////////////
	public static void asyncRsImgValidateUser(
			String userID, 
			BufferedImage inPic,
			DSD2016JAVACallBack cb
	)
	{
		String inB64Pic = ImageToBase64(ImageResizeFixedRatio(inPic, 160));
		asyncValidateUser(userID, inB64Pic, cb);
	}
	
	public static int rsImgValidateUser(
			String userID, 
			BufferedImage inPic, 
			StringBuilder outMsg
	)
	{
		String inB64Pic = ImageToBase64(ImageResizeFixedRatio(inPic, 160));
		return validateUser(userID, inB64Pic, outMsg);
	}
	
	public static void asyncImgValidateUser(
			String userID, 
			BufferedImage inPic,
			DSD2016JAVACallBack cb
	)
	{
		String inB64Pic = ImageToBase64(inPic);
		asyncValidateUser(userID, inB64Pic, cb);
	}
	
	public static int imgValidateUser(
			String userID, 
			BufferedImage inPic, 
			StringBuilder outMsg
	)
	{
		String inB64Pic = ImageToBase64(inPic);
		return validateUser(userID, inB64Pic, outMsg);
	}
	
	public static void asyncValidateUser(
			String userID, 
			String inB64Pic,
			DSD2016JAVACallBack cb
	)
	{
		//Create thread, define call back function and 
		Thread t = new Thread(()->{
			StringBuilder outMsg = new StringBuilder();
			int outErrorCode = validateUser(userID, inB64Pic, outMsg);
			cb.call(outErrorCode, new ArrayList<String>(), outMsg);
		});
		//start thread.
		t.start();
	}
	
	public static int validateUser(
			String userID, 
			String inB64Pic, 
			StringBuilder outMsg
	)
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
		if(res.has("success") && res.getBoolean("success")){//Success
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
///////////////////////////////////////////////////////////////////////////////
/////////////////////End of validateUser section///////////////////////////////
///////////////////////////////////////////////////////////////////////////////
	
///////////////////////////////////////////////////////////////////////////////
/////////////////////Helper functions//////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
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
	
	//Helper function for converting an image to base64 format
	public static String ImageToBase64(BufferedImage inPic)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(inPic, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(bytes);
	}
	
	//Helper function for converting an array of images to base64
	public static ArrayList<String> ImageToBase64(ArrayList<BufferedImage> inPics)
	{
		ArrayList<String> res = new ArrayList<String>();
		for(BufferedImage i: inPics)
			res.add(ImageToBase64(i));
		return res;
	}
	
	//Helper function for shrinking an image to specified width
	public static BufferedImage ImageResizeFixedRatio(BufferedImage inPic, int Width)
	{
        int newW = Width;
        int newH = Width * inPic.getHeight() / inPic.getWidth();
        
        //Create buffered image with shrinked size
        Image interImage = inPic.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage image = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(interImage, 0, 0, null);
        g.dispose();
        
        return image;
	}
	
	//Helper function for shrinking an array list of images to specified width
	public static ArrayList<BufferedImage> ImageResizeFixedRatio(
			ArrayList<BufferedImage> inPics,
			int Width
	)
	{
		ArrayList<BufferedImage> res = new ArrayList<BufferedImage>();
		for(BufferedImage i: inPics)
			res.add(ImageResizeFixedRatio(i, Width));
		return res;
	}
///////////////////////////////////////////////////////////////////////////////
/////////////////////End of helper functions section///////////////////////////
///////////////////////////////////////////////////////////////////////////////
}
