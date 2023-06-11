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

public class TC13_VerifyProductQuantity {
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

	@Test(description = "Click on Products Link", priority = 2)
	public void S002_ProductsClick() {
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		driver.findElement(By.cssSelector("a[href='/products']")).click();
		driver.findElement(By.cssSelector("a[href='/products']")).click();
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(5))
	       		  .pollingEvery(Duration.ofSeconds(1))
	       		  .ignoring(NoSuchElementException.class);
	       	
	       	WebElement test = wait.until(new Function<WebDriver, WebElement>() {
	            public WebElement apply(WebDriver driver) {
	                return driver.findElement(By.cssSelector(".title.text-center"));
	              }
	            });
		Assert.assertEquals("ALL PRODUCTS", test.getText());
		Reporter.log("The Products page loads correctly<br>");
	}
	
	@Test(description = "Click to see more on the product", priority = 3)
	public void S003_SeeMore() {
		
		WebElement details = driver.findElement(By.cssSelector("a[href='/product_details/1']"));
		details.click();
		WebElement description = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='product-information'] h2"))); 
		String text3 = description.getText();
		Assert.assertTrue(text3.contains("Blue"));
		Reporter.log("Item Resume Showed Correctly  <br>");
	}
	
	@Test(description = "Select quantity and size", priority = 4)
	public void S004_SelectTshirt() throws InterruptedException {
		WebElement size = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.id("quantity"))); 
		
		size.clear();
		size.sendKeys("4");
		driver.findElement(By.cssSelector("button[type='button']")).click();
		
		WebElement cart = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title.w-100")));
		Assert.assertEquals("Added!",cart.getText());
		Reporter.log("Item added to cart <br>");
	}

	
	@Test(description = "View Cart", priority = 5)
	public void S005_ViewCart() {
		
		driver.findElement(By.xpath("//u[normalize-space()='View Cart']")).click();
		WebElement cart = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
		
		String text4 = driver.findElement(By.cssSelector("a[href='/product_details/1']")).getText();
		Assert.assertTrue(text4.contains("Blue"));
		Reporter.log("Summary page displayed <br>");
	}
	
	@Test(description = "Verify Items", priority = 6)
	public void S006_VerifyItems() throws InterruptedException {
		
		String nombre1 = driver.findElement(By.cssSelector("a[href='/product_details/1']")).getText();
		
		
		Assert.assertEquals("Blue Top", nombre1);
		
		
		
		
		
	}
	
	@Test(description = "Verify quantity", priority = 6)
	public void S006_VerifyQuantity() throws InterruptedException {
		
		
		String cantidad1 = driver.findElement(By.cssSelector("tr[id='product-1'] button[class='disabled']")).getText();
		
		int intCantidad1 =   Integer.parseInt( cantidad1);

		Assert.assertEquals(4, intCantidad1);
		
		
		
		
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
