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

public class TC03_RequiredFields {
	
	Random rand = new Random();
	int upperbound = 2555;
	int int_random1 = rand.nextInt(upperbound);
	int int_random2 = rand.nextInt(upperbound);

	String correo = "testmail" + int_random1 + int_random2 + "@gmail.com";
	String nombre = "Lilian";
	String apellido = "Coronado";
	String pass = "123456";
	String addr = "House";
	String city = "Acapulco";
	String z = "12345";
	String phone = "1234567890";
	String state = "estado";
	Boolean b = false;
	WebDriver driver = null;
	String a = null;
	//List<String> inputs= Collections.emptyList();  
	//String [] messages= {"password","first_name","last_name","address1","country","state","city","zipcode","mobile_number"};

	@BeforeTest(description = "Setup the driver")
	public void S000_BeforeTest() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test(description = "Go to the webpage", priority = 1)
	public void S001_MainPage() throws InterruptedException {
		// Set browser
		driver.get("https://automationexercise.com/");
		Thread.sleep(2000);
		Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
		Reporter.log("The webpage loads correctly<br>");
	}

	@Test(description = "Click on login button", priority = 2)
	public void S002_LogInClick() {
		driver.findElement(By.cssSelector("a[href='/login']")).click();
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(10))
	       		  .pollingEvery(Duration.ofSeconds(2))
	       		  .ignoring(NoSuchElementException.class);
	       	
	       	WebElement firstResult = wait.until(new Function<WebDriver, WebElement>() {
	            public WebElement apply(WebDriver driver) {
	                return driver.findElement(By.cssSelector("div[class='signup-form'] h2"));
	              }
	            });
		Assert.assertEquals("Automation Exercise - Signup / Login", driver.getTitle());
		Reporter.log("The login page loads correctly<br>");
	}

	@Test(description = "Type an email in the 'Email' box and click on 'create account' button", priority = 3)
	public void S003_EnterEmailAdress() throws InterruptedException {
		driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(nombre);
		driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(correo);
		String corr = driver.findElement(By.cssSelector("input[data-qa='signup-email']")).getAttribute("value");
		System.out.println(corr);
		Assert.assertEquals(correo,corr);
		driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();
		
		Wait<WebDriver> waitForm = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(5))
	       		  .pollingEvery(Duration.ofSeconds(1))
	       		  .ignoring(NoSuchElementException.class);
	       	
	       	WebElement nameDisplayed = waitForm.until(new Function<WebDriver, WebElement>() {
	            public WebElement apply(WebDriver driver) {
	                return driver.findElement(By.xpath("//div[@class='login-form']"));
	              }
	            });
		Assert.assertTrue(nameDisplayed.isDisplayed());
		Reporter.log("The form appears<br>");
	}

	@Test(description = "Click on Register button with empty format", priority = 4)
	public void S004_EmptyRegister() throws InterruptedException {
		
		clears();
		Thread.sleep(5000);
		
       
		driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
		
		
		
		
		WebElement field = driver.findElement(By.id("password"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", field));
		System.out.println((String)js.executeScript("return arguments[0].validationMessage;", field));
		errores();
		Assert.assertTrue(b);
	}
	
	@Test(description = "Add password and submit", priority = 5)
	public void S008_AddPassword() throws InterruptedException {
		Thread.sleep(2000);
		
		driver.findElement(By.id("password")).sendKeys(pass);

		driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
		

		WebElement field = driver.findElement(By.id("first_name"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", field));
		System.out.println((String)js.executeScript("return arguments[0].validationMessage;", field));
		errores();
		Assert.assertTrue(b);
	}

	@Test(description = "Only first name and submit", priority = 6)
	public void S005_AddFirstName() throws InterruptedException {
		Thread.sleep(2000);
		
		driver.findElement(By.id("first_name")).sendKeys(nombre);

		driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
		

		WebElement field = driver.findElement(By.id("last_name"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", field));
		System.out.println((String)js.executeScript("return arguments[0].validationMessage;", field));
		errores();
		Assert.assertTrue(b);
	}

	@Test(description = "Add last name and submit", priority = 7)
	public void S006_AddLastName() throws InterruptedException {
		Thread.sleep(2000);
		
		driver.findElement(By.id("last_name")).sendKeys(apellido);

		driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
		

		WebElement field = driver.findElement(By.id("address1"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", field));
		System.out.println((String)js.executeScript("return arguments[0].validationMessage;", field));
		errores();
		Assert.assertTrue(b);
	}
	
	@Test(description = "Add addres and submit", priority = 8)
	public void S009_AddAddress() throws InterruptedException {
		Thread.sleep(2000);
		
		
		
		driver.findElement(By.id("address1")).sendKeys(addr);

		driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
		WebElement field = driver.findElement(By.id("state"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", field));
		System.out.println((String)js.executeScript("return arguments[0].validationMessage;", field));
		errores();
		Assert.assertTrue(b);
	}
	
	@Test(description = "Add State and submit", priority = 9)
	public void S013_AddAlias() throws InterruptedException {
		Thread.sleep(2000);
		
	
		driver.findElement(By.id("state")).sendKeys(state);
		driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
		WebElement field = driver.findElement(By.id("city"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", field));
		errores();
		Assert.assertTrue(b);
	}
	
	@Test(description = "Add city and submit", priority = 10)
	public void S010_AddCity() throws InterruptedException {
		Thread.sleep(2000);
		
		
		
		driver.findElement(By.id("city")).sendKeys(city);

		
		driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
		WebElement field = driver.findElement(By.id("zipcode"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", field));
		errores();
		Assert.assertTrue(b);
	}

	

	@Test(description = "Add ZIP and submit", priority = 11)
	public void S011_AddZIP() throws InterruptedException {
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("zipcode")).sendKeys(z);

		driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
		WebElement field = driver.findElement(By.id("mobile_number"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", field));
		errores();
		Assert.assertTrue(b);
	}

	@Test(description = "Add Phone and submit", priority = 12)
	public void S012_AddPhone() throws InterruptedException {
		Thread.sleep(2000);
		
		
		driver.findElement(By.id("mobile_number")).sendKeys(phone);

		driver.findElement(By.xpath("//button[normalize-space()='Create Account']")).click();
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

	

	

	@AfterTest
	public void LastStep() throws InterruptedException {
		System.out.println("END");
		// Script Ends and closes browser
		Thread.sleep(3000);
		driver.close();
	}

	public void clears() {
		System.out.println("CLEARS");

		driver.findElement(By.id("password")).clear();
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:window.scrollBy(250,550)");
        
        Select day = new Select(driver.findElement(By.id("days")));
		day.selectByVisibleText("Day");
		
		Select months = new Select(driver.findElement(By.id("months")));
		months.selectByVisibleText("Month");
		
		Select years = new Select(driver.findElement(By.id("years")));
		years.selectByVisibleText("Year");
		
		
		
		driver.findElement(By.id("first_name")).clear();
		driver.findElement(By.id("last_name")).clear();
		driver.findElement(By.id("company")).clear();
		
		driver.findElement(By.id("address1")).clear();
		js.executeScript("javascript:window.scrollBy(250,550)");
		driver.findElement(By.id("state")).clear();
		driver.findElement(By.id("city")).clear();
		driver.findElement(By.id("zipcode")).clear();
		driver.findElement(By.id("mobile_number")).clear();
		
        

		Select country = new Select(driver.findElement(By.id("country")));
		country.selectByVisibleText("India");
		

		System.out.println("CLEARS FINISH");

	}

	public void errores() {
		
		
		System.out.println("ERRORES");
		
		b = false;
		
			
			
			
			if (a.contains("number")) {
				Reporter.log("One phone number requiered <br>");
				b = true;
			}else if (a.contains("Completa")) {
				System.out.println("Completa este campo");
				Reporter.log("requiered field <br>");
				b = true;
			} 
			else if (a.contains("lastname")) {
				Reporter.log("Last name requiered <br>");
				b = true;
			} else if (a.contains("firstname ")) {
				Reporter.log("First name requiered <br>");
				b = true;
			} else if (a.contains("email ")) {
				Reporter.log("Email requiered <br>");
				b = true;
			} else if (a.contains("passwd ")) {
				Reporter.log("Password requiered <br>");
				b = true;
			} else if (a.contains("alias ")) {
				Reporter.log("Alias requiered <br>");
				b = true;
			} else if (a.contains("address1 ")) {
				Reporter.log("Address requiered <br>");
				b = true;
			} else if (a.contains("city ")) {
				Reporter.log("City requiered <br>");
				b = true;
			} else if (a.contains("This country requires")) {
				Reporter.log("State not selected <br>");
				b = true;
			} else if (a.contains("The Zip/Postal code")) {
				Reporter.log("Invalid ZIP code <br>");
				b = true;
			} else {
				b = false;
			}

		
		
	}

}
