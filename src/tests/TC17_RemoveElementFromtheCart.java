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

public class TC17_RemoveElementFromtheCart {
	
	WebDriver driver = null;

	

	@BeforeTest(description = "Setup the driver")
	public void S000_FirstStep() {
		// Setup driver
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test(description = "Go to URL 'http://automationpractice.com/index.php'", priority = 1)
	public void S001_GoWebpage() {
		// Go to web page
		driver.get("https://automationexercise.com/");
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		
		Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
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
	
	@Test(description = "Add product to the cart", priority = 3)
	public void S003_AddToTheCart() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		js.executeScript("javascript:window.scrollBy(0,550)");
		WebElement add = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/section/div[@class='container']/div[@class='row']/div[@class='col-sm-9 padding-right']/div[@class='features_items']/div[2]/div[1]/div[1]/div[1]/a[1]")));
		
		add.click();
		
		WebElement promt = new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title.w-100")));
		
		Assert.assertTrue(promt.getText().contains("Added!"));
		Reporter.log("Item Added Correctly  <br>");
	}
	
	@Test(description = "Continue Shopping", priority = 4)
	public void S004_ContinueShopping() {
		
		WebElement conti = driver.findElement(By.cssSelector(".btn.btn-success.close-modal.btn-block"));
		conti.click();
		
		
		Assert.assertTrue(!driver.findElement(By.cssSelector(".modal-content")).isDisplayed());
		Reporter.log("Continue Shopping  <br>");
	}
	
	@Test(description = "Add product to the cart2", priority = 5)
	public void S005_AddToTheCart2() {
		
		WebElement add = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/section/div[@class='container']/div[@class='row']/div[@class='col-sm-9 padding-right']/div[@class='features_items']/div[3]/div[1]/div[1]/div[1]/a[1]")));
		
		add.click();
		
		
		WebElement promt = new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title.w-100")));
		
		Assert.assertTrue(promt.getText().contains("Added!"));
		Reporter.log("Item Added Correctly  <br>");
	}
	
	@Test(description = "View Cart", priority = 6)
	public void S006_ViewCart() {
		
		driver.findElement(By.xpath("//u[normalize-space()='View Cart']")).click();
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
		String text4 = driver.findElement(By.cssSelector("a[href='/product_details/1']")).getText();
		Assert.assertTrue(text4.contains("Blue"));
		Reporter.log("Summary page displayed <br>");
	}
	
	@Test(description = "Verify Items", priority = 7)
	public void S007_VerifyItems() throws InterruptedException {
		
		String nombre1 = driver.findElement(By.cssSelector("a[href='/product_details/1']")).getText();
		String nombre2 = driver.findElement(By.cssSelector("a[href='/product_details/2']")).getText();
		
		Assert.assertEquals("Blue Top", nombre1);
		Assert.assertEquals("Men Tshirt", nombre2);
		
		
	}
	
	@Test(description = "Verify Items", priority = 8)
	public void S008_VerifyItems() throws InterruptedException {
		
		WebElement product1 = driver.findElement(By.cssSelector("tr[id='product-2'] i[class='fa fa-times']"));
		product1.click();
		Thread.sleep(2000);
		try {
		 driver.findElement(By.cssSelector("a[href='/product_details/2']")).getText();
		Assert.fail();
		}catch (Exception e) {
			Assert.assertTrue(true);
			
		}
		
		
		
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
