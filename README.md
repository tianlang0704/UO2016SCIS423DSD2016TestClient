#### **Description**:
This is the JavaAPI for accessing DSD2016 login and facial recognition project.
It is currently linked to UNC authentication server hosted at ix.cs.uoregon.edu:3555
This setting can be changed in the DSD2016JAVA.java file, it'll later become a set-able variable.

The structure and description of this module can be found in the image file:
TestClient-JavaAPIModules.png
![alt tag](TestClient-JavaAPIModules_v2.png)
___
#### **TODO**:
- *Add java image to base64 converter function
- *Add wrapper function for using a list of image instead of base64 image string, converted through the converting function.\
___

####  **Change log**:

###### **2016 May 1**
- Added: Implementation of interface functions.
- Added: Tests for all the function methods.
- Added: Jar pack in the bin for GUI use.
- Added: Expected results in tests

###### **2016 April 30**
- Added: Initial creation of project.
- Added: JSONRequest module that helps sending and receiving messages.
- Added: Test entry point for convenience.
- Added: Interface frame for adding function.