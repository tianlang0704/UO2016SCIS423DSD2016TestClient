package dsd2016.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.JSONObject;

public class Tests {
	public static void main(String args[]) throws JSONException, MalformedURLException, IOException
	{
//		//Temporary test
//		BufferedImage pic0 = ImageIO.read(new File("D:\\[C]Temp\\test1.png"));
//		DSD2016JAVA.asyncRsImgValidateUser(
//				"4e45642a-231b-11e6-b9fd-525400028af0",
//				pic0,
//				(int outErrorCode, 
//				 ArrayList<String> outB64BadPics, 
//				 StringBuilder outMsg)->{
//			System.out.println("Message: " + outMsg.toString() + "\n" + 
//					"Returned: " + outErrorCode + "\n");
//		});
//		
//		try {
//			Thread.sleep(100000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//Temporary test
		ArrayList<BufferedImage> pics0 = new ArrayList<BufferedImage>();
		for(int i = 1; i <= 8; i++)
		    pics0.add(ImageIO.read(new File("D:\\[C]Temp\\test" + Integer.toString(i) + ".png")));
		DSD2016JAVA.asyncRsImgRegisterNewUser(
				pics0, 
		        new DSD2016JAVACallBack(){
					public void call(
						int outErrorCode, 
					    ArrayList<String> outB64BadPics, 
					    StringBuilder outMsg){
		    System.out.println("Message: " + outMsg.toString() + "\n" + 
		            "Returned: " + outErrorCode + "\n");
		}});

		try {
		    Thread.sleep(100000);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		//JSONRequest Test 1
		JSONRequest req1 = new JSONRequest(DSD2016JAVA.registerURL);
		JSONObject content1 = new JSONObject();
		ArrayList<String> inB64Pics = new ArrayList<String>();
		inB64Pics.add("asdnasljdbjasbsdkajbflksbfkasbfhfa");
		inB64Pics.add("Baseojdasfjbsjodsjabfkjbsadkñfjh");
		content1.put("pictures", DSD2016JAVA.Base64ArrayToPicIdJSONArray(inB64Pics));
		System.out.println(content1.toString());
		
		JSONObject res1 = req1.SyncSendJSON(content1);
		System.out.println("JSONRequest Test 1 response: " + res1.toString() + "\n");
//		Expected output:
//		JSONRequest Test 1 response: {"facilitatorIds":null,"success":false,"errors":[{"pictureId":0,"errorMessage":"There is something unknown wrong with the image.","errorCode":3},{"pictureId":1,"errorMessage":"There is something unknown wrong with the image.","errorCode":3}]}
		
		
		//JSONRequest Test 2
		JSONRequest req2 = new JSONRequest(DSD2016JAVA.loginUserURL);
		JSONObject content2 = new JSONObject();
		content2.put("userId", "96cb7a81a34dd0888ea4fedbb42745e7893179b1");
		content2.put("picture", "asdnasljdbjasbsdkajbflksbfkasbfhfa");
		System.out.println(content2.toString());
		
		JSONObject res2 = req2.SyncSendJSON(content2);
		System.out.println("JSONRequest Test 2 response: " + res2.toString() + "\n");
//		Expected output:
//		JSONRequest Test 2 response: {"success":false,"errors":[{"errorMessage":"User does not exist.","errorCode":10}]}
		
		
		//registerNewUser test 1
		System.out.println("registerNewUser test 1:");
		ArrayList<String> pics1 = new ArrayList<String>();
		pics1.add("eRHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY");
		pics1.add("aHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY");
		ArrayList<String> errorPics1 = new ArrayList<String>();
		StringBuilder outMsg1 = new StringBuilder();
		
		int ret1 = DSD2016JAVA.registerNewUser(pics1, errorPics1, outMsg1);
		
		System.out.println("Message: " + outMsg1.toString() + "\n" + 
				"Returned: " + ret1 + "\n" +
				"Error pic 0: " + errorPics1.get(0) + "\n" + 
				"Error pic 1: " + errorPics1.get(1) + "\n");
//		Expected output:
//		registerNewUser test 1:
//		Sending JSON: {"pictures":[{"pictureId":"0","base64":"eRHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY="},{"pictureId":"1","base64":"aHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY="}]}
//		Message: Registration failed, check picture error message.
//		Returned: 0
//		Error pic 0: 0,3,There is something unknown wrong with the image.
//		Error pic 1: 0,3,There is something unknown wrong with the image.
		
		
		//validateUser test 1
		System.out.println("validateUser test 1:");
		StringBuilder outMsg2 = new StringBuilder();
		
		int ret2 = DSD2016JAVA.validateUser(
						"96cb7a81a34dd0888ea4fedbb42745e7893179b1", 
						"asdnasljdbjasbsdkajbflksbfkasbfhfa",
						outMsg2);
		
		System.out.println("Message: " +  outMsg2.toString() + "\n" + 
				"Returned: " + ret2 + "\n");
//		Expected output:
//		validateUser test 1:
//		Sending JSON: {"userId":"96cb7a81a34dd0888ea4fedbb42745e7893179b1","picture":"asdnasljdbjasbsdkajbflksbfkasbfhfa"}
//		Message: Login failed,10,User does not exist.
//		Returned: 0
		
		
		//asyncRegisterNewUser test 1 with lambda expression
		//This function returns immediately, and when finish it's going to call
		//the defined lambda function.
		//This is the same as the interface implementation method in the next example.
		System.out.println("asyncRegisterNewUser test 1:");
		ArrayList<String> pics3 = new ArrayList<String>();
		pics3.add("eRHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY");
		pics3.add("aHR0cDovL3NhZHNhZnNhZnNmc2ZzYWY");
		
		DSD2016JAVA.asyncRegisterNewUser(pics3, 
				(	int outErrorCode, 
					ArrayList<String> outB64BadPics, 
					StringBuilder outMsg		
				)->
		{
			System.out.println("Message: " + outMsg.toString() + "\n" + 
					"Returned: " + outErrorCode + "\n" +
					"Error pic 0: " + outB64BadPics.get(0) + "\n" + 
					"Error pic 1: " + outB64BadPics.get(1) + "\n");
		});
//		Expected output:
//		registerNewUser test 1:
//		Message: Registration failed, check picture error message.
//		Returned: 0
//		Error pic 0: 0,3,There is something unknown wrong with the image.
//		Error pic 1: 0,3,There is something unknown wrong with the image.
		
		
		//asyncValidateUser test 1 with interface implementation
		//
		System.out.println("asyncValidateUser test 1:");
		DSD2016JAVACallBack cb = new DSD2016JAVACallBack(){
			public void call(int outErrorCode, ArrayList<String> outB64BadPics, StringBuilder outMsg){
				System.out.println("Message: " +  outMsg.toString() + "\n" + 
						"Returned: " + outErrorCode + "\n");
			}
		};
		
		DSD2016JAVA.asyncValidateUser(
						"96cb7a81a34dd0888ea4fedbb42745e7893179b", 
						"3123124ASDHFHGGTJ52342ASDG",
						cb);
//		Expected output:
//		asyncValidateUser test 1:
//		Sending JSON: {"userId":"96cb7a81a34dd0888ea4fedbb42745e7893179b","picture":"3123124ASDHFHGGTJ52342ASDG"}
//		Message: Login failed,10,User does not exist.
//		Returned: 0
	}
}
