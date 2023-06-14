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

public class TC20_SearchProductsandVerifyCart {

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
		driver.findElement(By.xpath("//a[normalize-space()='Continue']")).click();
		WebElement name = new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li:nth-child(10) a:nth-child(1)")));
		Assert.assertTrue(name.getText().contains("Logged in as "+nombre));
		Reporter.log("The name is showed<br>");
	}
	
	@Test(description = "Logoutfrom create account", priority = 4)

	public void S004_Logout() throws InterruptedException {
		
		driver.findElement(By.cssSelector("a[href='/logout']")).click();
		
		
		WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-qa='login-button']")));
		Assert.assertTrue(logout.isDisplayed());
		Reporter.log("The Login form appears<br>");
	}

	@Test(description = "Click on Products Cases Link", priority = 5)
	public void S005_ProductsClick() {
		
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
	
	
	
	@Test(description = "search the same item on the search bar", priority = 6)
	public void S006_SearchItemDisplayed() throws InterruptedException {
		
		
		WebElement search =  driver.findElement(By.id("search_product"));
		
		
		Assert.assertTrue(search.isDisplayed());
		Reporter.log("Search input was displayed <br>");
	}
	
	@Test(description = "search the same item on the search bar", priority = 7)
	public void S007_SearchItem() throws InterruptedException {
		
		
		driver.findElement(By.id("search_product")).sendKeys("Pure Cotton V-Neck T-Shirt");
		driver.findElement(By.id("submit_search")).click();
		WebElement product = new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='productinfo text-center']//p[contains(text(),'Pure Cotton V-Neck T-Shirt')]")));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,550)");
		
		Assert.assertTrue(product.getText().contains("Cotton"));
		Reporter.log("Item searched matches with the item displayed <br>");
	}
	
	@Test(description = "Add product to the cart", priority = 8)
	public void S008_AddToTheCart() {
		
		WebElement add = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/section/div[@class='container']/div[@class='row']/div[@class='col-sm-9 padding-right']/div[@class='features_items']/div[2]/div[1]/div[1]/div[1]/a[1]")));
		
		add.click();
		try {
			add.click();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		WebElement promt = new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title.w-100")));
		
		Assert.assertTrue(promt.getText().contains("Added!"));
		Reporter.log("Item Added Correctly  <br>");
	}
	
	@Test(description = "View Cart", priority = 9)
	public void S009_ViewCart() {
		
		driver.findElement(By.xpath("//u[normalize-space()='View Cart']")).click();
		WebElement cart = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
		String text4 = driver.findElement(By.cssSelector("a[href='/product_details/28']")).getText();
		Assert.assertTrue(text4.contains("Pure Cotton V-Neck T-Shirt"));
		Reporter.log("Summary page displayed <br>");
	}
	
	@Test(description = "Verify Items", priority = 10)
	public void S010_VerifyItems() throws InterruptedException {
		
		String nombre1 = driver.findElement(By.cssSelector("a[href='/product_details/28']")).getText();
		
		
		Assert.assertEquals("Pure Cotton V-Neck T-Shirt", nombre1);
		
		
		
	}
	
	
	
	@Test(description = "Click login", priority = 11)

	public void S011_GoLogin() throws InterruptedException {
		
		driver.findElement(By.cssSelector("a[href='/login']")).click();
		WebElement login = new WebDriverWait(driver, Duration.ofSeconds(10))
				
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='login-form'] h2")));
		Assert.assertEquals("Login to your account", login.getText());
		Reporter.log("Login correctly<br>");
	}
	
	@Test(description = "Login to proceed to checkout Again", priority = 12)
	public void S012_Summary() throws InterruptedException {
	
	driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(correo);
	driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(password);
	
	driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();
	
	WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/logout']")));
	Assert.assertTrue(logout.isDisplayed());
	Reporter.log("The form appears<br>");
	}
	
	@Test(description = "View Cart", priority = 13)
	public void S013_ViewCartAfterLogin() {
		
		driver.findElement(By.xpath("//body[1]/header[1]/div[1]/div[1]/div[1]/div[2]/div[1]/ul[1]/li[3]/a[1]")).click();
		WebElement cart = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
		String text4 = driver.findElement(By.cssSelector("a[href='/product_details/28']")).getText();
		Assert.assertTrue(text4.contains("Pure Cotton V-Neck T-Shirt"));
		Reporter.log("Summary page displayed <br>");
	}
	
	@Test(description = "Verify Items", priority = 14)
	public void S014_VerifyItemsAfterLogin() throws InterruptedException {
		
		String nombre1 = driver.findElement(By.cssSelector("a[href='/product_details/28']")).getText();
		
		
		Assert.assertEquals("Pure Cotton V-Neck T-Shirt", nombre1);
		
		
	}
	
	
	
	@AfterTest
	public void LastStep() throws InterruptedException {
		Thread.sleep(1000);
		driver.close();
	}
}
