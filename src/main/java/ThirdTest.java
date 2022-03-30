import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ThirdTest {

    private static AndroidDriver driver;
    private static Helper helper;

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        System.out.println("Setting up the process...");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "10.0");

        capabilities.setCapability(MobileCapabilityType.APP, "src/main/resources/app-debug.apk");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(url, capabilities);
        helper = new Helper(driver);

        System.out.println("Setup process completed");
        System.out.println("Third Test: Checking for duplicate forms started.");
    }

    @Test
    public void checkForDuplicateInput() {
        System.out.println("Test 3 started.");
        helper.fillShortForm();

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        String feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("Your response has been send!", feedbackMessageText);

        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("You have send your response already!", feedbackMessageText);
    }


    @AfterClass
    public static void End() {
        System.out.println("Third Test: Checking for duplicate input finished.");
        driver.quit();
    }

}
