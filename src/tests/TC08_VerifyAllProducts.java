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

public class TC08_VerifyAllProducts {
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
	
	@Test(description = "Review All Products", priority = 3)
	public void S003_ListDisplay() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		WebElement lista = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".features_items"))); 
		
		Assert.assertTrue(lista.isDisplayed());
		Reporter.log("Item Resume Showed Correctly  <br>");
	}
	
	@Test(description = "Click to see more on the product", priority = 4)
	public void S004_SeeMore() {
		
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
	
	@Test(description = "Review Details", priority = 5)
	public void S005_Details() {
		
		
		WebElement productName = driver.findElement(By.cssSelector("div[class='product-information'] h2"));
		WebElement category = driver.findElement(By.xpath("//div[@class='product-details']//p[1]"));
		WebElement price = driver.findElement(By.cssSelector("div[class='product-information'] span span"));
		WebElement avalability = driver.findElement(By.xpath("//div[@class='product-details']//p[2]"));
		WebElement condition = driver.findElement(By.xpath("//div[@class='product-details']//p[3]"));
		WebElement brand = driver.findElement(By.xpath("//div[@class='product-details']//p[4]"));
		
		Assert.assertTrue(productName.getText().contains("Blue Top"));
		Assert.assertTrue(category.getText().contains("Category: Women > Tops"));
		Assert.assertTrue(price.getText().contains("Rs. 500"));
		Assert.assertTrue(avalability.getText().contains("In Stock"));
		Assert.assertTrue(condition.getText().contains("New"));
		Assert.assertTrue(brand.getText().contains("Polo"));
		
		Reporter.log("Details Showed Correctly  <br>");
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
