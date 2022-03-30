import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;


public class FourthTest {

    private static AndroidDriver driver;
    private static Helper helper;

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        System.out.println("Setting up the process...");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.0");

        capabilities.setCapability(MobileCapabilityType.APP, "src/main/resources/app-debug.apk");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(url, capabilities);
        helper = new Helper(driver);

        System.out.println("Setup process completed");
        System.out.println("Forth Test : Checking for different platform version started.");
    }


    @Test
    public void checkForValidInputForDifferentAndroidVersion() {
        helper.fillLongForm();

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        String feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("Your response has been send!", feedbackMessageText);
    }


    @AfterClass
    public static void End() {
        System.out.println("Forth Test: Checking for different platform version finished.");
        driver.quit();
    }

}
