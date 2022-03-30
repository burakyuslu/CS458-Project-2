# CS458-Project-2 TESTING
Project 2 for CS 458: TESTING PART

This branch (main-MobileTesting) includes the app apk generated from the android branch. One can generate the same apk using Android Studio using the code from the android branch of this repository.

Both the app and the test cases are written in Java using Appium.

Please use Appium Maven to open this project and have your system include Java (minimum 8), Android SDK and set up Appium.

When running, please modify the apk path in each Test file, since the application requests the absolute path to the apk.
This line can be found in the setUp function of Test classes.

This branch does not include a main method. The tests were run using built-in test running tools of IntelliJ.
To run the tests, you may also use an IDE that has built-in test running tool.
In order to run these tests, you may need to configure your environment variables and Appium server settings.
