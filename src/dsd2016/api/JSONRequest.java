package dsd2016.api;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONRequest 
{
	
	private String m_URLstr;
	
	public enum HTTPMethod {POST, GET};
	
	public JSONRequest(String urlStr)
	{
		m_URLstr = urlStr;
	}
	
	public void SetURLStr(String urlStr)
	{
		m_URLstr = urlStr;
	}
	
	//////////////////wrappers for send method//////////////////////////
	public JSONObject SyncSendJSON() 
			throws JSONException, MalformedURLException, IOException
	{
		return SyncSendJSON(new JSONObject("{}"));
	}
	
	public JSONObject SyncSendJSON(JSONObject inObj) 
			throws JSONException, MalformedURLException, IOException
	{
		return SyncSendJSON(inObj, HTTPMethod.POST);
	}
	//////////////////END wrappers for send method///////////////////////
	
	public JSONObject SyncSendJSON(JSONObject inObj, HTTPMethod meth) 
			throws JSONException, IOException
	{	
		URL urlObj;
		HttpURLConnection con;
		if(meth == HTTPMethod.POST){
			//sending JSON object with post
			urlObj = new URL(m_URLstr);
			con = (HttpURLConnection)urlObj.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStream os = con.getOutputStream();
			os.write(inObj.toString().getBytes("UTF-8"));
			os.close();
		}else{
			//sending JSON object with GET
			String paramStr = inObj.toString().replaceAll("[{}\"]", "").replace(":", "=");
			urlObj = new URL(m_URLstr + "?" + paramStr);
			con = (HttpURLConnection)urlObj.openConnection();
		}
		
		//receiving response
		int httpCode = con.getResponseCode();
		switch(httpCode){
			case 404: throw new IOException("HTTP 404 not found");
			case 500: throw new IOException("HTTP 500 server internal error");
		}	
		JSONTokener tokener = new JSONTokener(httpCode / 100 == 2 ? con.getInputStream() : con.getErrorStream());
		return new JSONObject(tokener);
	}
}
