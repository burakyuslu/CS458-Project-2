import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;


public class Helper {
    private AndroidDriver driver;
    private final String[] exampleInput = {"example_name example_surname", "01-01-2000", "Ankara", "No", "Yes"};

    public Helper(AndroidDriver driver) {
        this.driver = driver;
    }

    public enum DIRECTION {
        DOWN, UP;
    }

    public void fillLongForm() {
        fillNameField(exampleInput[0]);
        fillDateField(exampleInput[1]);
        fillCityField(exampleInput[2]);
        clickGenderRadio("male");

        scroll(DIRECTION.DOWN);
        clickVaccRadio(true);

        fillSEField(exampleInput[3]);
        clickThirdRadio(true);
        fillHistoryField(exampleInput[4]);
        driver.manage().timeouts().implicitlyWait(250, TimeUnit.MILLISECONDS);
        scroll(DIRECTION.UP);
    }

    public void fillShortForm() {
        fillNameField(exampleInput[0]);
        fillDateField(exampleInput[1]);
        fillCityField(exampleInput[2]);
        clickGenderRadio("male");
        scroll(DIRECTION.DOWN);

        clickVaccRadio(false);
        clickThirdRadio(false);
        driver.manage().timeouts().implicitlyWait(250,TimeUnit.MILLISECONDS);
        scroll(DIRECTION.UP);
    }

    public void fillNameField(String input) {
        WebElement nameTextField = (WebElement) driver.findElements(By.id("editTextName")).get(0);
        nameTextField.sendKeys(input);
    }

    public void fillDateField(String input) {
        WebElement nameTextField = (WebElement) driver.findElements(By.id("editTextDate")).get(0);
        nameTextField.sendKeys(input);
    }

    public void fillCityField(String input) {
        WebElement nameTextField = (WebElement) driver.findElements(By.id("city")).get(0);
        nameTextField.sendKeys(input);
    }

    public void fillSEField(String input) {
        WebElement nameTextField = (WebElement) driver.findElements(By.id("sideEffects")).get(0);
        nameTextField.sendKeys(input);
    }

    public void fillHistoryField(String input) {
        WebElement nameTextField = (WebElement) driver.findElements(By.id("history")).get(0);
        nameTextField.sendKeys(input);
    }

    public String getErrorMessage() {
        WebElement feedbackMessage = (WebElement) driver.findElements(By.id("errorText")).get(0);

        return feedbackMessage.getText();
    }

    public void clickSend() {
        WebElement sendButton = (WebElement) driver.findElements(By.id("send")).get(0);
        sendButton.click();
    }

    public void clickGenderRadio(String gender) {
        WebElement radio = (WebElement) driver.findElements(By.id(gender)).get(0);
        radio.click();
    }

    public void clickVaccRadio(boolean yes) {
        WebElement radio = (WebElement) driver.findElements(By.id(yes ? "vaccYes" : "vaccNo")).get(0);
        radio.click();
    }

    public void clickThirdRadio(boolean yes) {
        WebElement radio = (WebElement) driver.findElements(By.id(yes ? "thirdYes" : "thirdNo")).get(0);
        radio.click();
    }

    public void scroll( DIRECTION dir) {
        /*
        Point source = scrollView.getLocation();
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);

        sequence.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), source.x, source.y));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(600)));

        sequence.addAction(finger.createPointerMove(ofMillis(600), PointerInput.Origin.viewport(), source.x + x, source.y + y));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));

        driver.perform(singletonList(sequence));

         */
        Dimension size = driver.manage().window().getSize();

        int startX = 0;
        int startY = 0;
        int endY = 0;
        int duration = 3;

        switch (dir) {
            case UP:
                endY = (int) (size.height * 0.70);
                startY = (int) (size.height * 0.30);
                startX = (size.width / 2);
                new TouchAction(driver)
                        .press(startX, startY)
                        .waitAction(duration)
                        .moveTo(startX, endY)
                        .release()
                        .perform();
                break;
            case DOWN:
                startY = (int) (size.height * 0.70);
                endY = (int) (size.height * 0.30);
                startX = (size.width / 2);
                new TouchAction(driver)
                        .press(startX, startY)
                        .waitAction(duration)
                        .moveTo(startX, endY)
                        .release()
                        .perform();
                break;
        }
    }
}
