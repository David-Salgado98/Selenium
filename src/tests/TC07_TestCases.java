package tests;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC07_TestCases {
	WebDriver driver = null;
	@BeforeTest(description = "Setup the driver")
	public void S000_BeforeTest() throws InterruptedException {
		// set driver
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}

	@Test(description = "Go to the webpage", priority = 1)
	public void S001_MainPage() throws InterruptedException {
		// Set browser
		driver.get("https://automationexercise.com/");
		WebElement inicio = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='features_items'] h2[class='title text-center']")));
				
				Assert.assertEquals("FEATURES ITEMS", inicio.getText());
		Reporter.log("The webpage loads correctly<br>");
	}

	@Test(description = "Click on Test Cases Link", priority = 2)
	public void S002_LogInClick() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		driver.findElement(By.cssSelector("header[id='header'] li:nth-child(5) a:nth-child(1)")).click();
		try {
		driver.findElement(By.cssSelector("header[id='header'] li:nth-child(5) a:nth-child(1)")).click();
		}catch (Exception e) {
			// TODO: handle exception
		}
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(5))
	       		  .pollingEvery(Duration.ofSeconds(1))
	       		  .ignoring(NoSuchElementException.class);
	       	
	       	WebElement test = wait.until(new Function<WebDriver, WebElement>() {
	            public WebElement apply(WebDriver driver) {
	                return driver.findElement(By.cssSelector("h2[class='title text-center'] b"));
	              }
	            });
		Assert.assertEquals("TEST CASES", test.getText());
		Reporter.log("The Test Case page loads correctly<br>");
	}
	@AfterTest
	public void LastStep() throws InterruptedException {
		System.out.println("END");
		// Script Ends and closes browser
		Thread.sleep(3000);
		driver.close();
	}
}
