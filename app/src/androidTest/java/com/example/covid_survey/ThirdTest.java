package com.example.covid_survey;


import android.widget.EditText;

import java.net.MalformedURLException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ThirdTest {

    WebDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        System.out.println("Setting up the process...");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Pixel_3a_API_29");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
        capabilities.setCapability(CapabilityType.VERSION, "10.0");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.example.covid_survey");
        capabilities.setCapability("appActivity", "com.example.covid_survey.MainActivity");
        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }


    @Test
    public void checkForDuplicateInput() {

        WebElement nameTextField = driver.findElements(By.id("R.id.editTextName")).get(0);
        nameTextField.sendKeys("example_name example_surname");

        WebElement birthdayTextField = driver.findElements(By.id("R.id.editTextDate")).get(0);
        birthdayTextField.sendKeys("01/01/2000");

        WebElement cityTextField = driver.findElements(By.id("R.id.city")).get(0);
        cityTextField.sendKeys("Ankara");

        WebElement male_radio = driver.findElements(By.id("R.id.male")).get(0);
        male_radio.click();

        WebElement vacc_radio = driver.findElements(By.id("R.id.vaccYes")).get(0);
        vacc_radio.click();

        WebElement sideEffectTextField = driver.findElements(By.id("R.id.sideEffects")).get(0);
        sideEffectTextField.sendKeys("No");

        WebElement third_vacc_radio = driver.findElements(By.id("R.id.thirdYes")).get(0);
        third_vacc_radio.click();

        WebElement historyTextField = driver.findElements(By.id("R.id.history")).get(0);
        historyTextField.sendKeys("Yes");

        WebElement sendButton = driver.findElements(By.id("R.id.send")).get(0);
        sendButton.click();

        WebElement feedbackMessage = driver.findElements(By.id("R.id.errorText")).get(0);

        String feedbackMessageText = feedbackMessage.getText();

        Assert.assertEquals("Your response has been send!", feedbackMessageText);

        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);


        // clear previous input from the fields
        nameTextField.clear();
        birthdayTextField.clear();
        cityTextField.clear();
        male_radio.clear();
        vacc_radio.clear();
        sideEffectTextField.clear();
        third_vacc_radio.clear();
        historyTextField.clear();


        nameTextField.sendKeys("example_name example_surname");

        birthdayTextField.sendKeys("01/01/2000");

        cityTextField.sendKeys("Ankara");

        male_radio.click();

        vacc_radio.click();

        sideEffectTextField.sendKeys("No");

        third_vacc_radio.click();

        historyTextField.sendKeys("Yes");

        sendButton.click();


        feedbackMessage = driver.findElements(By.id("R.id.errorText")).get(0);

        feedbackMessageText = feedbackMessage.getText();

        Assert.assertEquals("You have send your response already!", feedbackMessageText);


        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    }



    @After
    public void End() {
        driver.quit();
    }
}
