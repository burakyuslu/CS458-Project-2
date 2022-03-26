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
        // Created object of DesiredCapabilities class.
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Set android deviceName desired capability. Set your device name.
        capabilities.setCapability("deviceName", "XT1562");

        // Set BROWSER_NAME desired capability. It's Android in our case here.
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");

        // Set android VERSION desired capability. Set your mobile device's OS version.
        capabilities.setCapability(CapabilityType.VERSION, "6.0.1");

        // Set android platformName desired capability. It's Android in our case here.
        capabilities.setCapability("platformName", "Android");

        // Set android appPackage desired capability. It is
        // com.android.calculator2 for calculator application.
        // Set your application's appPackage if you are using any other app.
        capabilities.setCapability("appPackage", "com.android.calculator2");

        // Set android appActivity desired capability. It is
        // com.android.calculator2.Calculator for calculator application.
        // Set your application's appPackage if you are using any other app.
        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");

        // Created object of RemoteWebDriver will all set capabilities.
        // Set appium server address and port number in URL string.
        // It will launch calculator app in android device.
        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);


    }


    @Test
    public void checkForValidInput() {


        // Click on DELETE/CLR button to clear result text box before running test.
        WebElement nameTextField = driver.findElements(By.id("R.id.editTextName")).get(0);
        nameTextField.sendKeys("sample_name_1");

        WebElement birthdayTextField = driver.findElements(By.id("R.id.editTextDate")).get(0);
        birthdayTextField.sendKeys("01/01/2022");

        WebElement cityTextField = driver.findElements(By.id("R.id.city")).get(0);
        cityTextField.sendKeys("test_city_1");

        WebElement sideEffectTextField = driver.findElements(By.id("R.id.sideEffects")).get(0);
        sideEffectTextField.sendKeys("cancer_1");

        WebElement historyTextField = driver.findElements(By.id("R.id.history")).get(0);
        historyTextField.sendKeys("history_1");

        WebElement sendButton = driver.findElements(By.id("R.id.send")).get(0);
        sendButton.click();


        // todo add message for acceptance of input
        WebElement feedbackMessage = driver.findElements(By.id("R.id.message")).get(0);

        String feedbackMessageText = feedbackMessage.getText();

        Assert.assertEquals("Accept", feedbackMessageText);


        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);


        // clear previous input from the fields
        nameTextField.clear();
        birthdayTextField.clear();
        cityTextField.clear();
        sideEffectTextField.clear();
        historyTextField.clear();


        // Click on DELETE/CLR button to clear result text box before running test.
        nameTextField.sendKeys("sample_name_1");

        birthdayTextField.sendKeys("01/01/2022");

        cityTextField.sendKeys("test_city_1");

        sideEffectTextField.sendKeys("cancer_1");

        historyTextField.sendKeys("history_1");

        sendButton.click();


        // todo add message for acceptance of input
        feedbackMessage = driver.findElements(By.id("R.id.message")).get(0);

        feedbackMessageText = feedbackMessage.getText();

        Assert.assertEquals("Fail", feedbackMessageText);


        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
    }



    @After
    public void End() {
        driver.quit();
    }
}