#### **How To Use**:
Method A) Add Precompiled JAR to the Project(Eclipse): <br/>

1. Download the precompiled jar file in bin/dsd2016api-java.jar <br/>
2. Open project property -> JAVA Build Path <br/>
3. Click Add JARs or Add External JARs depending on where you placed the download file <br/>
4. Click OK to apply the change. <br/>

Method B) Use git or download source directly: <br/>

	1. Copy and paste src folder into your project and use it directly.
	
Example: 
test.java
```
//Import package
import dsd2016.api.DSD2016JAVA;

public class test {
	static public void main(String[] args)
	{
		//Create an object to access DSD2016 server
		DSD2016JAVA api = new DSD2016JAVA();
		
		//Create a StringBuilder to receive message
		StringBuilder outMsg = new StringBuilder();
		
		//Using verifyEmail access method to query about availability of the email address
		//and print out the results
		if(api.verifyEmail("example@email.com", outMsg) == true)
			System.out.println("Success: " + outMsg.toString());
		else
			System.out.println("Fail: " + outMsg.toString());
	}
}
```

#### **Description**:
This is the JavaAPI for accessing DSD2016 login and facial recognition project.
It is currently linked to UNC authentication server hosted at ix.cs.uoregon.edu:3555
This setting can be changed in the DSD2016JAVA.java file, it'll later become a set-able variable.

The structure and description of this module can be found in the image file:
TestClient-JavaAPIModules.png
<br/>
![alt tag](TestClient-JavaAPIModules_v2.png)
___
#### **TODO**:
- *Add java image to base64 converter function
- *Add wrapper function for using a list of image instead of base64 image string, converted through the converting function.
<br/>
___

####  **Change log**:

###### **2016 May 1(specification ver 0.2, implementation ver 1.1)**
- Modified: included org.json package into the project, producing only one JAR file.

###### **2016 May 1(specification ver 0.1, implementation ver 1.0)**
- Added: Implementation of interface functions.
- Added: Tests for all the function methods.
- Added: Jar pack in the bin for GUI use.
- Added: Expected results in tests

###### **2016 April 30** (specification ver 0.1, implementation ver 0.1)**
- Added: Initial creation of project.
- Added: JSONRequest module that helps sending and receiving messages.
- Added: Test entry point for convenience.
- Added: Interface frame for adding function.
