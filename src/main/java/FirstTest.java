import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;


public class FirstTest {

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
        System.out.println("First Test : Checking for valid/invalid inputs started.");
    }


    @Test
    public void checkForValidInput1() {
        System.out.println("Test 1a: Checking for full form valid inputs started.");
        helper.fillLongForm();

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        String feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("Your response has been send!", feedbackMessageText);
    }

    @Test
    public void checkForValidInput2() {
        System.out.println("Test 1b: Checking for short form valid inputs started.");
        helper.fillShortForm();

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        String feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("Your response has been send!", feedbackMessageText);
    }


    @Test
    public void checkForInvalidInputName() {
        System.out.println("Test 1c: Checking for invalid name started.");
        helper.fillShortForm();

        helper.fillNameField("name1 surname23/&");

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        String feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("Name field is invalid.", feedbackMessageText);

        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);

        helper.fillNameField("15686");

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("Name field is invalid.", feedbackMessageText);
    }

    @Test
    public void checkForInvalidInputDate() {
        System.out.println("Test 1d: Checking for invalid date started.");
        helper.fillShortForm();

        helper.fillDateField("1st of june 2000");

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        String feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("Birthday date is invalid.", feedbackMessageText);

        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);

        helper.fillDateField("06-20-2000");

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("Birthday date is invalid.", feedbackMessageText);

        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);

        helper.fillDateField("01-01-2025");

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("Birthday date is invalid.", feedbackMessageText);
    }

    @Test
    public void checkForInvalidInputCity() {
        System.out.println("Test 1e: Checking for invalid city started.");
        helper.fillShortForm();

        helper.fillCityField("0001");

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        String feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("City name is invalid or unavailable.", feedbackMessageText);

        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);

        helper.fillCityField("Aankara");

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("City name is invalid or unavailable.", feedbackMessageText);

        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);

        helper.fillCityField("London");

        helper.clickSend();
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        feedbackMessageText = helper.getErrorMessage();

        Assert.assertEquals("City name is invalid or unavailable.", feedbackMessageText);
    }

    @After
    public void Reset() {
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        driver.resetApp();
    }

    @AfterClass
    public static void End() {
        System.out.println("First Test: Checking for valid/invalid inputs finished.");
        driver.quit();
    }


}
