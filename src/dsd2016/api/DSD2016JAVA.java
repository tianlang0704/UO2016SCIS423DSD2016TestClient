package dsd2016.api;

import java.util.ArrayList;
import org.json.JSONObject;

public class DSD2016JAVA 
{	
	public int registerNewUser(
			String name, String email, String password, String gender, 
			ArrayList<String> inB64Pics, ArrayList<String> outB64BadPics, String outMsg)
	{
		outMsg = "Place holder for result message.";
		
		
		
		
		return 1;
	}
	
	public int validateUser(
			String email, String password, ArrayList<String> inB64Pics, String outMsg)
	{
		outMsg = "Place holder for result message.";
		
		
		
		return 1;
	}
	
	public boolean verifyEmail(String email, String outMsg)
	{
		outMsg = "Place holder for result message.";
		
		
		
		return true;
	}
}
