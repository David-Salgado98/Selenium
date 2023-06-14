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

public class TC23_VerifyAddress {
	// BEFORE
	WebDriver driver = null;

	Random rand = new Random();
	
	int upperbound2 = 2;
	int y = rand.nextInt(upperbound2) + 2;

	String nombre = "David";
	
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
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		
		Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
		Reporter.log("The webpage loads correctly<br>");

	}

	@Test(description = "Create User", priority = 2)
	public void S002_CreateUser() throws InterruptedException {
		// Women menu
		driver.findElement(By.cssSelector("a[href='/login']")).click();
		WebElement signup = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-qa='signup-button']")));
			driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(nombre);
			driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(correo);
			signup.click();
			WebElement firstname = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.presenceOfElementLocated(By.id("first_name")));
	       
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
	
	@Test(description = "Check the login name", priority = 3)
	public void S003_LoginName() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;	
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		
		driver.findElement(By.xpath("//a[normalize-space()='Continue']")).click();
		try {
		driver.findElement(By.xpath("//a[normalize-space()='Continue']")).click();
		}catch (Exception e) {
			// TODO: handle exception
		}
		WebElement name = new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li:nth-child(10) a:nth-child(1)")));
		Assert.assertTrue(name.getText().contains("Logged in as "+nombre));
		Reporter.log("The name is showed<br>");
	}
	
	@Test(description = "Click on Products Link", priority = 4)
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
	
	@Test(description = "Add product to the cart", priority = 5)
	public void S003_AddToTheCart() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(0,550)");
		WebElement add = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/section/div[@class='container']/div[@class='row']/div[@class='col-sm-9 padding-right']/div[@class='features_items']/div[2]/div[1]/div[1]/div[1]/a[1]")));
		
		add.click();
		
		WebElement promt = new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title.w-100")));
		
		Assert.assertTrue(promt.getText().contains("Added!"));
		Reporter.log("Item Added Correctly  <br>");
	}
	
	@Test(description = "Continue Shopping", priority = 6)
	public void S006_ContinueShopping() {
		
		WebElement conti = driver.findElement(By.cssSelector(".btn.btn-success.close-modal.btn-block"));
		conti.click();
		
		
		Assert.assertTrue(!driver.findElement(By.cssSelector(".modal-content")).isDisplayed());
		Reporter.log("Continue Shopping  <br>");
	}
	
	@Test(description = "Add product to the cart2", priority = 7)
	public void S007_AddToTheCart2() {
		
		WebElement add = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/section/div[@class='container']/div[@class='row']/div[@class='col-sm-9 padding-right']/div[@class='features_items']/div[3]/div[1]/div[1]/div[1]/a[1]")));
		
		add.click();
		
		
		WebElement promt = new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title.w-100")));
		
		Assert.assertTrue(promt.getText().contains("Added!"));
		Reporter.log("Item Added Correctly  <br>");
	}
	
	@Test(description = "View Cart", priority = 8)
	public void S008_ViewCart() {
		
		driver.findElement(By.xpath("//u[normalize-space()='View Cart']")).click();
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
		String text4 = driver.findElement(By.cssSelector("a[href='/product_details/1']")).getText();
		Assert.assertTrue(text4.contains("Blue"));
		Reporter.log("Summary page displayed <br>");
	}
	
	@Test(description = "Verify Items", priority = 9)
	public void S009_VerifyItems() throws InterruptedException {
		
		String nombre1 = driver.findElement(By.xpath("//tbody/tr[1]/td[2]/h4[1]/a[1]")).getText();
		String nombre2 = driver.findElement(By.xpath("//tbody/tr[2]/td[2]/h4[1]/a[1]")).getText();
		
		Assert.assertEquals("Blue Top", nombre1);
		Assert.assertEquals("Men Tshirt", nombre2);
		
		
	}
	
	@Test(description = "Check address checkout", priority = 10)
	public void S010_CheckAddressChekout() throws InterruptedException {
		driver.findElement(By.cssSelector(".btn.btn-default.check_out")).click();
		WebElement address = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[id='address_delivery'] li:nth-child(4)")));
		Assert.assertTrue(address.getText().contains("New california republic , 5th street chritsmas way"));
		Reporter.log("Addres checkout correct <br>");
	}
	
	
	@Test(description = "Check address invoice", priority = 11)
	public void S011_CheckAddressInvoice() throws InterruptedException {
		
		WebElement address = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[id='address_invoice'] li:nth-child(4)")));
		Assert.assertTrue(address.getText().contains("New california republic , 5th street chritsmas way"));
		Reporter.log("Addres Invoice Correct <br>");
	}
	
	
	@Test(description = "Delete Account", priority = 12)
	public void S012_DeleteAccount() throws InterruptedException {
		WebElement delete = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/delete_account']")));
	
	delete.click();
	try {
		delete.click();
	}catch (Exception e) {
		// TODO: handle exception
	}
	WebElement promt = new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h2[class='title text-center'] b")));
	Assert.assertTrue(promt.getText().contains("ACCOUNT DELETED"));
	Reporter.log("The account is deleted<br>");
	
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
