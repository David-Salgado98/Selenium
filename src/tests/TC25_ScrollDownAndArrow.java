package tests;

import java.time.Duration;

import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC25_ScrollDownAndArrow {
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
	
	@Test(description = "Review Subscription Input", priority = 2)
	public void S002_SuscriptionDisplay() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		js.executeScript("javascript:window.scrollBy(0,document.body.scrollHeight)");
		WebElement subscription = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='single-widget'] h2"))); 
		
		Assert.assertTrue(subscription.getText().contains("SUBSCRIPTION"));
		Reporter.log("Subscription Input Showed Correctly  <br>");
	}
	
	@Test(description = "Go to the Top Page", priority = 3)
	public void S003_UpPage() throws InterruptedException {
		
		
		WebElement arrow = driver.findElement(By.xpath("//a[@id='scrollUp']"));
		
		arrow.click();
		
		WebElement top = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='item active'] div[class='col-sm-6'] h2")));
				
				Assert.assertEquals("Full-Fledged practice website for Automation Engineers", top.getText());
		Reporter.log("The Top Page Shows correctly<br>");
	}
	
	@AfterTest
	public void AfterClass() {
		System.out.println("END");
		// Script Ends and closes browser
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
	
	}
}
