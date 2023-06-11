package Examples;

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

public class TC09_ProductQuantity {

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
		

		@Test(description = "Go to login to the page", priority = 3)
		public void S003_AccesToPage() throws InterruptedException {
			//Login and Logout
			driver.get("https://automationexercise.com/");
			WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/logout']")));
			logout.click();
			driver.findElement(By.cssSelector("a[href='/login']")).click();
			driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(correo);
			driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(password);
			
			driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();
			
			WebElement login = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/logout']")));
			Assert.assertTrue(login.isDisplayed()); 
			Reporter.log("The account logout<br>");
		}

		@Test(description = "Hover over 'Women' yab and clicks on 'T-shirts'", priority = 4)
		public void S004_HoverClickTshirt() throws InterruptedException {
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

		@Test(description = "Hover the first product and click on 'more' button", priority = 5)
		public void S005_HoverFirstProductMore() throws InterruptedException {
			// Women menu
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("javascript:window.scrollBy(250,250)");
			
			js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
			driver.findElement(By.cssSelector("body")).click();
			
						
			WebElement product = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/product_details/3']"))); 
			product.click();
			
				
				WebElement top = new WebDriverWait(driver, Duration.ofSeconds(10))
						.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='product-information'] h2"))); 
				
				 
				Assert.assertTrue( top.getText().contains("Sleeveless"));
		}

		@Test(description = "Verify cuantity = 1", priority = 6)
		public void S006_VerifyCuantity() throws InterruptedException {
			
			WebElement cantidad = driver.findElement(By.id("quantity"));
			String numeroInicial = cantidad.getAttribute("value");
			
			cantidad.clear();
			cantidad.sendKeys("3");
			Assert.assertEquals("1", numeroInicial);
			Assert.assertEquals("3", cantidad.getAttribute("value"));
			Reporter.log("Quantity of the product=3 <br>");
		}

		@Test(description = "S009_Add to cart", priority = 7)
		public void S009_AddCart() throws InterruptedException {
			driver.findElement(By.cssSelector("button[type='button']")).click();
			new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title.w-100")));
			String text3 = driver.findElement(By.cssSelector(".modal-title.w-100")).getText();
			Assert.assertTrue(text3.contains("Added!"));
			Reporter.log("Item Added Correctly  <br>");
		}

		@Test(description = "Proceed to checkout", priority = 8)
		public void S010_CheckOutClick() throws InterruptedException {
			WebElement description =driver.findElement(By.xpath("//u[normalize-space()='View Cart']"));
			description.click();
			WebElement cart = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
			String text4 = driver.findElement(By.cssSelector("a[href='/product_details/3']")).getText();
			Assert.assertTrue(text4.contains("Sleeveless"));
			Reporter.log("Summary page displayed <br>");
		}

		

		@Test(description = "Verify price", priority = 9)
		public void S009_VerifyNewPrice() throws InterruptedException {
			
			String unitario = driver.findElement(By.cssSelector("td[class='cart_price'] p")).getText();
			String cantidad = driver.findElement(By.cssSelector(".disabled")).getText();
			String total = driver.findElement(By.cssSelector(".cart_total_price")).getText();

			String unitariosn = unitario.replace("Rs. ", "");
			String totalsn = total.replace("Rs. ", "");
			
			System.out.println(unitariosn);
			System.out.println(totalsn);
			
			
		}
		
		/*@Test(description = "Checkout", priority = 9)
		public void S011_ChangeCuantity() throws InterruptedException {
			System.out.println("y: " + y);
			for (int i = 0; i < (y - 1); i++) {
				driver.findElement(By.id("cart_quantity_up_1_4_0_695076")).click();
				Thread.sleep(1000);
			}
			Thread.sleep(5000);
			String Y = "" + y;
			Assert.assertEquals(Y,
					driver.findElement(By.xpath("//*[@id=\"product_1_4_0_695076\"]/td[5]/input[1]")).getAttribute("value"));
			Reporter.log("Proceed to check out <br>");
		}*/

		// AFTER
		@AfterTest
		public void after() throws InterruptedException {
			System.out.println("END");
			// Script Ends and closes browser
			Thread.sleep(2000);
			//driver.close();
		}

	
}
