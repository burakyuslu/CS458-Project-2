import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SecondTest {

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
        System.out.println("Second Test : Checking for send click on empty fields started.");
    }

    @Test
    public void checkForSend() {
        System.out.println("Test 2a: Checking for complete empty form started.");
        helper.clickSend();

        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        String feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("All fields need to be filled.", feedbackMessageText);
    }

    @Test
    public void checkForSendSingleEmpty() {
        System.out.println("Test 2b: Checking for partial empty form started.");
        helper.fillShortForm();

        helper.fillNameField("");

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);

        String feedbackMessageText = helper.getErrorMessage();
        Assert.assertEquals("All fields need to be filled.", feedbackMessageText);
    }

    @After
    public void Reset() {
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        driver.resetApp();
    }

    @AfterClass
    public static void End() {
        System.out.println("Second Test: Checking for send click finished.");
        driver.quit();
    }

}
