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

public class TC22_RecomendenItems {
	WebDriver driver = null;
	String producto = null;
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
	
	@Test(description = "Review Recommenden Products", priority = 2)
	public void S002_ListDisplay() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		js.executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-800)");
		WebElement recommended = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='recommended_items'] h2[class='title text-center']"))); 
		
		Assert.assertTrue(recommended.getText().contains("RECOMMENDED ITEMS"));
		Reporter.log("Item Recommended Items Showed Correctly  <br>");
	}
	
	@Test(description = "Add product to the cart", priority = 3)
	public void S003_AddToTheCart() {
		
		WebElement add = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > section:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(4)")));
		producto = driver.findElement(By.cssSelector("body > section:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > p:nth-child(3)")).getText();
		add.click();
		
		WebElement promt = new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title.w-100")));
		
		Assert.assertTrue(promt.getText().contains("Added!"));
		Reporter.log("Item Added Correctly  <br>");
	}
	@Test(description = "View Cart", priority = 4)
	public void S004_ViewCart() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		driver.findElement(By.xpath("//u[normalize-space()='View Cart']")).click();
		try {
		driver.findElement(By.xpath("//u[normalize-space()='View Cart']")).click();
		}catch (Exception e) {
			// TODO: handle exception
		}
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
		String text4 = driver.findElement(By.xpath("//td[@class='cart_description']//h4//a")).getText();
		Assert.assertTrue(text4.contains(producto));
		Reporter.log("Summary page displayed <br>");
	}
	
	@Test(description = "Verify Items", priority = 5)
	public void S005_VerifyItems() throws InterruptedException {
		
		String nombre1 = driver.findElement(By.xpath("//td[@class='cart_description']//h4//a")).getText();
		
		Assert.assertEquals(producto, nombre1);
		
		
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
