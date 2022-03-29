import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;

public class FourthTest {

    AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        System.out.println("Setting up the process...");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "10.0");



        capabilities.setCapability(MobileCapabilityType.APP, "C:\\Users\\cankirsallioba\\Desktop\\MobileTesting\\src\\main\\resources\\app-debug.apk");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(url, capabilities);

        System.out.println("Setup process completed");

    }


    @Test
    public void checkForValidInputForDifferentAndroidVersions() {

        WebElement nameTextField = driver.findElements(By.id("editTextName")).get(0);
        nameTextField.sendKeys("example_name example_surname");

        WebElement birthdayTextField = driver.findElements(By.id("editTextDate")).get(0);
        birthdayTextField.sendKeys("01/01/2000");

        WebElement cityTextField = driver.findElements(By.id("city")).get(0);
        cityTextField.sendKeys("Ankara");

        WebElement male_radio = driver.findElements(By.id("male")).get(0);
        male_radio.click();

        WebElement vacc_radio = driver.findElements(By.id("vaccYes")).get(0);
        vacc_radio.click();

        scroll(0,-500);

        WebElement sideEffectTextField = driver.findElements(By.id("sideEffects")).get(0);
        sideEffectTextField.sendKeys("No");

        WebElement third_vacc_radio = driver.findElements(By.id("thirdYes")).get(0);
        third_vacc_radio.click();

        scroll(0,-500);

        WebElement historyTextField = driver.findElements(By.id("history")).get(0);
        historyTextField.sendKeys("Yes");

        WebElement sendButton = driver.findElements(By.id("send")).get(0);
        sendButton.click();

        scroll(0,500);

        WebElement feedbackMessage = driver.findElements(By.id("errorText")).get(0);

        String feedbackMessageText = feedbackMessage.getText();

        Assert.assertEquals("Your response has been send!", feedbackMessageText);

        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

    }


    @After
    public void End() {
        driver.quit();
    }

    public void scroll(int x, int y) {
        WebElement scrollView = driver.findElements(By.id("scrollView2")).get(0);


        Point source = scrollView.getLocation();
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0),
                PointerInput.Origin.viewport(), source.x, source.y));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(600)));
        sequence.addAction(finger.createPointerMove(ofMillis(600),
                PointerInput.Origin.viewport(), source.x + x, source.y + y));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));

        driver.perform(singletonList(sequence));
    }
}
