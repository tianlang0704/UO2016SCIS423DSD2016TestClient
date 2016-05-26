#### **How To Use**:
Method A) Add Precompiled JAR to the Project(Eclipse): <br/>

1. Download the precompiled jar file: bin/dsd2016api-java.jar <br/>
2. Open project property -> JAVA Build Path <br/>
3. Click Add JARs or Add External JARs depending on where you placed the downloaded file <br/>
4. Click OK to apply the change. <br/>

Method B) Use git or download source directly: <br/>

1. Copy and paste src folder into your project and use it directly.


Example: 
test.java
```Java
//Import package
import dsd2016.api.DSD2016JAVA;

public class test {
	static public void main(String[] args)
	{
		//Create a StringBuilder to receive message
		StringBuilder outMsg = new StringBuilder();
		
		//Call api to validate a user with a base64 picture and a ID
		int ret = DSD2016JAVA.validateUser(
						"96cb7a81a34dd0888ea4fedbb42745e7893179b", 
						"3123124ASDHFHGGTJ52342ASDG",
						outMsg);
		
		//Print out the results
		System.out.println("Message: " +  outMsg.toString() + "\n" + 
				"Returned: " + ret + "\n");
	}
}
```

#### **Description**:
Current snapshot of the companion specification:
(https://docs.google.com/document/d/101paqEpshmsjjWuJHrcSxw1_QuSP1nzljuBxdeXQQ48/edit?usp=sharing)

This is the JavaAPI for accessing DSD2016 login and facial recognition project.
It is currently linked to UNC authentication server hosted at ix.cs.uoregon.edu:3555
This setting can be changed in the DSD2016JAVA.java file, it'll later become a set-able variable.

The structure and description of this module can be found in the image file:
TestClient-JavaAPIModules.png
<br/>
![alt tag](TestClient-JavaAPIModules_v3.png)
___
#### **TODO**:
None for now.
<br/>
___

####  **Change log**:

###### **2016 May 26(specification ver 2.0, implementation ver 2.1)**
- Added: Async variants of access methods.
- Added: Variants to accept Java BufferedImage objects directly.
- Added: Variants that shrink images for performance.
- Added: Helper functions to convert and shrink images.
- Added: tests for asynchronous calling and lambda function.

###### **2016 May 14(specification ver 1.0, implementation ver 2.0)**
- Modified: Updated to the new integration standard.

###### **2016 May 9(specification ver 0.4, implementation ver 1.2)**
- Modified: Changed all the access methods to static for programmatic correctness and ease of use. (Bryon)

###### **2016 May 4(specification ver 0.2, implementation ver 1.1)**
- Modified: Included org.json package into the project, producing only one JAR file.

###### **2016 May 1(specification ver 0.1, implementation ver 1.0)**
- Added: Implementation of interface functions.
- Added: Tests for all the function methods.
- Added: Jar pack in the bin for GUI use.
- Added: Expected results in tests.

###### **2016 April 30 (specification ver 0.1, implementation ver 0.1)**
- Added: Initial creation of project.
- Added: JSONRequest module that helps sending and receiving messages.
- Added: Test entry point for convenience.
- Added: Interface frame for adding function.
