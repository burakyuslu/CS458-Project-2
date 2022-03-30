import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;


public class FifthTest {

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
        System.out.println("Fifth Test : Checking for hidden fields started.");
    }


    @Test
    public void checkForInvisibleElements() {
        System.out.println("Test 5a: Checking for invisible elements.");

        helper.scroll(Helper.DIRECTION.DOWN);
        Assert.assertEquals(0, driver.findElements(By.id("sideEffects")).size());
        Assert.assertEquals(0, driver.findElements(By.id("history")).size());
    }

    @Test
    public void checkForVisibleSE() {
        System.out.println("Test 5b: Checking for visible side effects section started.");

        helper.fillShortForm();
        helper.scroll(Helper.DIRECTION.DOWN);
        helper.clickVaccRadio(true);

        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);

        Assert.assertEquals(1, driver.findElements(By.id("sideEffects")).size());
        Assert.assertEquals(0, driver.findElements(By.id("history")).size());
    }


    @Test
    public void checkForValidInputWithWrongOrder() {
        System.out.println("Test 5c: Checking for visible history section started.");

        helper.fillShortForm();
        helper.scroll(Helper.DIRECTION.DOWN);
        helper.clickThirdRadio(true);

        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);

        Assert.assertEquals(0, driver.findElements(By.id("sideEffects")).size());
        Assert.assertEquals(1, driver.findElements(By.id("history")).size());
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
