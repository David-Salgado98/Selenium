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

public class TC06_BuyProduct {

	String nombre = "David";
	Random rand = new Random();
	int upperbound = 2555;
	int int_random1 = rand.nextInt(upperbound);
	int int_random2 = rand.nextInt(upperbound);

	String correo = "david" + int_random1 + int_random2 + "@gmail.com";
	String password = "12345";
	WebDriver driver = null;
	String title = null;
	String title2 = null;
	String text3 = null;
	String text4 = null;
	String text5 = null;
	String text6 = null;
	
	@BeforeTest
	public void S000_BeforeClass() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test(description = "Go to the webpage", priority = 1)
	public void S001_OpenWebPage() {
		driver.get("https://automationexercise.com/");
		WebElement firstname = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		
		Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
		Reporter.log("The webpage loads correctly<br>");
	}

	@Test(description = "click on Sign in", priority = 2)

	public void S002_SignIn() throws InterruptedException {
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
			Assert.assertTrue(asserResult.isDisplayed());
			Assert.assertTrue(UserName.contains("ACCOUNT CREATED!"));
			Reporter.log("The account is created<br>");
	}

	@Test(description = "type username and password", priority = 3)

	public void S003_TypeEmail() throws InterruptedException {
		driver.get("https://automationexercise.com/");
		driver.findElement(By.cssSelector("a[href='/logout']")).click();
		driver.findElement(By.cssSelector("a[href='/login']")).click();
		driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(correo);
		driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(password);
		
		driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();
		
		WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/logout']")));
		Assert.assertTrue(logout.isDisplayed());
		Reporter.log("The form appears<br>");
	}

	@Test(description = "GO to Women section", priority = 4)
	public void S004_ClickWomen() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,250)");
		
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		
		driver.findElement(By.cssSelector("body > section:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > h4:nth-child(1) > a:nth-child(1)")).click();
		//js.executeScript("javascript:window.scrollBy(250,250)");
		
		WebElement tops = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/category_products/2']"))); 
		tops.click();
		tops.click();
			
			WebElement top = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body > section:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(4)"))); 
			//
			WebElement section = driver.findElement(By.cssSelector("body > section:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > p:nth-child(3)")); 
			Assert.assertEquals("Blue Top", section.getText());
	}
		

	@Test(description = "Select More", priority = 5)
	public void S005_MoreOption() throws InterruptedException {
		
		WebElement details = driver
				.findElement(By.cssSelector("a[href='/product_details/1']"));
		details.click();
		Thread.sleep(2000);
		text3 = driver.findElement(By.xpath("//body//section//p[4]")).getText();
		Assert.assertTrue(text3.contains("Polo"));
		Reporter.log("More section displayed correctly  <br>");
	}

	@Test(description = "Select quantity and size", priority = 6)
	public void S006_SelectTshirt() throws InterruptedException {
		
		WebElement size = driver.findElement(By.id("quantity"));
		size.clear();
		size.sendKeys("3");
		driver.findElement(By.cssSelector("button[type='button']")).click();
		
		WebElement cart = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title.w-100")));
		Assert.assertEquals("Added!",cart.getText());
		Reporter.log("Item added to cart <br>");
	}

	@Test(description = "Click proceed to checkout", priority = 7)
	public void S007_Proceed() throws InterruptedException {
		
		driver.findElement(By.xpath("//u[normalize-space()='View Cart']")).click();
		WebElement cart = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-default.check_out")));
		text4 = driver.findElement(By.cssSelector("a[href='/product_details/1']")).getText();
		Assert.assertTrue(text4.contains("Blue"));
		Reporter.log("Summary page displayed <br>");
	}

	@Test(description = "Click proceed to checkout Again", priority = 8)
	public void S008_Summary() throws InterruptedException {
		driver.findElement(By.cssSelector(".btn.btn-default.check_out")).click();
		WebElement resume = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='Your delivery address']")));
		Assert.assertTrue(resume.getText().contains("DELIVERY"));
		Reporter.log("address page displayed <br>");
	}

	@Test(description = "Proceed to shipping", priority = 9)
	public void S009_Shipping() throws InterruptedException {
		driver.findElement(By.cssSelector(".btn.btn-default.check_out")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,250)");
		WebElement submit = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#submit")));
		Assert.assertTrue(submit.isDisplayed());
		Reporter.log("shipping page displayed and terms accepted <br>");
	}

	

	@Test(description = "Proceed to complete order", priority = 11)
	public void S011_ProcedToOrderComplete() throws InterruptedException {
		
		driver.findElement(By.name("name_on_card")).sendKeys("Eleanor Rossvelt");
		driver.findElement(By.name("card_number")).sendKeys("1111222233334444");
		driver.findElement(By.name("cvc")).sendKeys("123");
		driver.findElement(By.name("expiry_month")).sendKeys("06");
		driver.findElement(By.name("expiry_year")).sendKeys("2023");
		driver.findElement(By.cssSelector("#submit")).click();
		WebElement order = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2[class='title text-center'] b")));
		
		Assert.assertTrue(order.getText().contains("ORDER"));
		Reporter.log("payment added correctly <br>");
	}

	

	
	@AfterTest
	public void LastStep() throws InterruptedException {
		System.out.println("END");
		// Script Ends and closes browser
		Thread.sleep(3000);
		//driver.close();
	}

	
}
