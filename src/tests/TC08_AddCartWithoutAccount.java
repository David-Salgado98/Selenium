package tests;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC08_AddCartWithoutAccount {
	
	WebDriver driver = null;
	String nombre = "David";
	Random rand = new Random();
	int upperbound = 2555;
	int int_random1 = rand.nextInt(upperbound);
	int int_random2 = rand.nextInt(upperbound);

	String correo = "david" + int_random1 + int_random2 + "@gmail.com";
	String password = "12345";
	
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
		WebElement firstname = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		
		Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
		Reporter.log("The webpage loads correctly<br>");

	}

	@Test(description = "Create User", priority = 2)
	public void S002_CreateUser() throws InterruptedException {
		// Women menu
		driver.findElement(By.cssSelector("a[href='/login']")).click();
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(10))
	       		  .pollingEvery(Duration.ofSeconds(2))
	       		  .ignoring(NoSuchElementException.class);
			driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(nombre);
			driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(correo);
			driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();
			WebElement firstname = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.id("first_name")));
	       
			driver.findElement(By.id("password")).sendKeys(password);
			firstname.sendKeys("Eleanor");
			WebElement lastname =  driver.findElement(By.id("last_name"));
			lastname.sendKeys("Rossvelt");
			WebElement address =  driver.findElement(By.id("address1"));
			address.sendKeys("New california republic , 5th street chritsmas way");
			WebElement state =  driver.findElement(By.id("state"));
			state.sendKeys("California");
			WebElement city =  driver.findElement(By.id("city"));
			city.sendKeys("San Diego");
			WebElement zip = driver.findElement(By.id("zipcode"));
			zip.sendKeys("1234");
			WebElement number1 = driver.findElement(By.id("mobile_number"));
			number1.sendKeys("1234567899");
			driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
			Wait<WebDriver> waitAssert = new FluentWait<WebDriver>(driver)
		       		  .withTimeout(Duration.ofSeconds(5))
		       		  .pollingEvery(Duration.ofSeconds(1))
		       		  .ignoring(NoSuchElementException.class);
		       	
		       	WebElement asserResult = waitAssert.until(new Function<WebDriver, WebElement>() {
		            public WebElement apply(WebDriver driver) {
		                return driver.findElement(By.xpath("//a[normalize-space()='Continue']"));
		              }
		            });
			String UserName = driver.findElement(By.xpath("//b[normalize-space()='Account Created!']")).getText();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
			Assert.assertTrue(asserResult.isDisplayed());
			Assert.assertTrue(UserName.contains("ACCOUNT CREATED!"));
			Reporter.log("The account is created<br>");

	}
	
	@Test(description = "Logout User", priority = 3)
	public void S003_Logout() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.get("https://automationexercise.com/");
		WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/logout']")));
		logout.click();
		WebElement login = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-qa='login-button']")));
		Assert.assertTrue(login.isDisplayed());
		Reporter.log("The account logout<br>");
		
	}
	
	@Test(description = "Move to Products", priority = 4)
	public void S004_GoProdcuts() throws InterruptedException {
		// Products menu
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		WebElement products = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/products']")));
		products.click();
		Thread.sleep(1000);
		products.click();
		
		
		
		
		//search_product
		WebElement search = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_product")));
		Assert.assertTrue(search.isDisplayed());

	}
	
	@Test(description = "Move your cursor over Women's link and click on sub menu 'T-shirts'", priority = 5)
	public void S005_GoTshirtPage() throws InterruptedException {
		// Women menu
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		
		driver.findElement(By.cssSelector("body > section:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > h4:nth-child(1) > a:nth-child(1)")).click();
		js.executeScript("javascript:window.scrollBy(250,250)");
		WebElement tops = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/category_products/2']"))); 
		tops.click();
		
		try {
			WebElement description =  driver.findElement(By.xpath("//div[@class='productinfo text-center']//p[contains(text(),'Winter Top')]"));
			System.out.println(description.getText());
			Assert.assertTrue(description.getText().contains("Top"));
		}catch (Exception e) {
			Assert.fail();
		}

	}

	@Test(description = "Click to see more on the product", priority = 6)
	public void S006_SeeMore() {
		WebElement details = driver.findElement(By.cssSelector("a[href='/product_details/5']"));
		details.click();
		WebElement description = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//body//section//p[4]"))); 
		String text3 = description.getText();
		Assert.assertTrue(text3.contains("Mast"));
		Reporter.log("Item Resume Showed Correctly  <br>");
	}

	@Test(description = "Add the product to wishlist", priority = 7)
	public void S007_AddWishList() throws InterruptedException {
		
		driver.findElement(By.cssSelector("button[type='button']")).click();
		WebElement description = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//u[normalize-space()='View Cart']")));
		description.click();
		WebElement cart = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
		String text4 = driver.findElement(By.cssSelector("a[href='/product_details/5']")).getText();
		Assert.assertTrue(text4.contains("Winter"));
		Reporter.log("Summary page displayed <br>");
		
		
	}
	
	@Test(description = "Click proceed to checkout Again", priority = 8)
	public void S008_Summary() throws InterruptedException {
		driver.findElement(By.cssSelector(".btn.btn-default.check_out")).click();
		WebElement resume = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//u[normalize-space()='Register / Login']")));
		resume.click();
		WebElement login = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-qa='login-button']")));
		Assert.assertTrue(login.isDisplayed());
		Reporter.log("Redirect to the account login <br>");
	}
	
	@Test(description = "Login to proceed to checkout Again", priority = 9)
	public void S009_Summary() throws InterruptedException {
	
	driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(correo);
	driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(password);
	
	driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();
	
	WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/logout']")));
	Assert.assertTrue(logout.isDisplayed());
	Reporter.log("The form appears<br>");
	}
	
	@Test(description = "Show the cart", priority = 10)
	public void S010_CartWithLogin() throws InterruptedException {
	
	
	
	driver.findElement(By.xpath("//body[1]/header[1]/div[1]/div[1]/div[1]/div[2]/div[1]/ul[1]/li[3]/a[1]")).click();
	
	WebElement cart = new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
	String text4 = driver.findElement(By.cssSelector("a[href='/product_details/5']")).getText();
	Assert.assertTrue(text4.contains("Winter"));
	Reporter.log("Summary page displayed <br>");
	}

	@AfterTest
	public void LastStep() throws InterruptedException {
		Thread.sleep(1000);
		driver.close();
	}

}
