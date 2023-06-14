package tests;

import java.time.Duration;
import java.util.Random;
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

public class TC21_AddAReview {
	WebDriver driver = null;
	String nombre = "David";
	Random rand = new Random();

	int upperbound2 = 2;
	int y = rand.nextInt(upperbound2) + 2;
	int upperbound = 2555;
	int int_random1 = rand.nextInt(upperbound);
	int int_random2 = rand.nextInt(upperbound);

	String correo = "david" + int_random1 + int_random2 + "@gmail.com";
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
		try {
		driver.findElement(By.cssSelector("a[href='/products']")).click();
		}catch (Exception e) {
			// TODO: handle exception
		}
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
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		WebElement details = driver.findElement(By.cssSelector("a[href='/product_details/1']"));
		details.click();
		try {
		details.click();
		}catch (Exception e) {
			// TODO: handle exception
		}
		WebElement description = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='product-information'] h2"))); 
		String text3 = description.getText();
		Assert.assertTrue(text3.contains("Blue"));
		Reporter.log("Item Resume Showed Correctly  <br>");
	}
	
	@Test(description = "Check the review form", priority = 4)
	public void S004_CheckReviewForm() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(0,550)");
		
		WebElement description = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='#reviews']"))); 
		
		Assert.assertTrue(description.getText().contains("WRITE YOUR REVIEW"));
		Reporter.log("The Review Form Showed Correctly  <br>");
	}
	
	@Test(description = "Add a review on the product", priority = 5)
	public void S005_AddReview() {
		WebElement name  = driver.findElement(By.cssSelector("#name"));
		name.sendKeys(nombre);
		WebElement mail  = driver.findElement(By.cssSelector("#email"));
		mail.sendKeys(correo);
		WebElement rew  = driver.findElement(By.cssSelector("#review"));
		rew.sendKeys("test Review");
		driver.findElement(By.cssSelector("#button-review")).click();
		WebElement confirm = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='alert-success alert'] span"))); 
		Assert.assertTrue(confirm.getText().contains("Thank you for your review."));
		Reporter.log("The Alert Message Showed Correctly  <br>");
	}
	@AfterTest
	public void LastStep() throws InterruptedException {
		Thread.sleep(1000);
		driver.close();
	}
}
