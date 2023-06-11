package Examples;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.time.Duration;
import java.util.Random;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC01_CreateAccount {
	WebDriver driver = null;
	String nombre = "David";
	String apellido = "Salgado";
	Random rand = new Random();
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

	@Test(description = "select gender", priority = 4)
	public void S004_GenderSelection() {
		// Gender
		WebElement gender = driver.findElement(By.id("id_gender1"));
		gender.click();
		String genderselection = gender.getAttribute("checked");
		Assert.assertEquals("true", genderselection);
		Reporter.log("The gender selected is checked <br>");
	}
	
	@Test(description = "check the mail", priority = 5)
	public void S005_EmailValidation() {
		Assert.assertEquals(correo, driver.findElement(By.name("email")).getAttribute("value"));
		Reporter.log("The email typed at the beginning is shown<br>");
	}
	
	@Test(description = "type the password un the 'password' box", priority = 6)
	public void S006_Password() {
		// password
		driver.findElement(By.id("password")).sendKeys("12345");
		Assert.assertEquals("12345", driver.findElement(By.name("password")).getAttribute("value"));
		Reporter.log("The password typed is shown<br>");
	}

	@Test(description = "Select the date of birth", priority = 7)
	public void S007_BirthDateSelection() {
		// Date of birth
		Select day = new Select(driver.findElement(By.id("days")));
		day.selectByValue("12");
		Select month = new Select(driver.findElement(By.id("months")));
		month.selectByValue("4");
		Select year = new Select(driver.findElement(By.id("years")));
		year.selectByValue("1998");
		Assert.assertEquals("12", driver.findElement(By.id("days")).getAttribute("value"));
		Assert.assertEquals("4", driver.findElement(By.id("months")).getAttribute("value"));
		Assert.assertEquals("1998", driver.findElement(By.id("years")).getAttribute("value"));
		Reporter.log("The birth date selected is shown<br>");
	}

	@Test(description = "type the name un the 'name' box", priority = 8)
	public void S008_NameValidation() {
		// Name
		 JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("javascript:window.scrollBy(250,350)");
		driver.findElement(By.name("first_name")).sendKeys(nombre);
		Assert.assertEquals(nombre, driver.findElement(By.name("first_name")).getAttribute("value"));
		Reporter.log("The name typed is shown<br>");
	}

	@Test(description = "type the  last name un the ' last name' box", priority = 9)
	public void S009_LastNameVaidation() {
		// Last Name
		driver.findElement(By.name("last_name")).sendKeys(apellido);
		Assert.assertEquals(apellido, driver.findElement(By.name("last_name")).getAttribute("value"));
		Reporter.log("The lastname typed is shown<br>");
	}
	

	@Test(description = "verify that the name and the last name is the same in the 'first name' and 'last name' box in the address section", priority = 10)
	public void S010_NameAndLastnameValidation() {
		Assert.assertEquals(nombre, driver.findElement(By.id("first_name")).getAttribute("value"));
		Assert.assertEquals(apellido, driver.findElement(By.id("last_name")).getAttribute("value"));
		Reporter.log("The name and the last name typed at the beginning of the form is shown<br>");
	}

	@Test(description = "Enter the company name in the 'company' box", priority = 11)
	public void S011_CompanyValidation() {
		// company
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:window.scrollBy(250,550)");
		driver.findElement(By.id("company")).sendKeys("Prueba");
		Assert.assertEquals("Prueba", driver.findElement(By.id("company")).getAttribute("value"));
		Reporter.log("The company typed is shown<br>");
	}

	@Test(description = "Enter the address in the 'address' box", priority = 12)
	public void S012_AdrressValidation() {
		// address
		driver.findElement(By.id("address1")).sendKeys("calle Test");
		Assert.assertEquals("calle Test", driver.findElement(By.id("address1")).getAttribute("value"));
		Reporter.log("The first address typed is shown<br>");
	}

	@Test(description = "enter a second address in the 'second address' box", priority = 13)
	public void S013_SecondAddressValidation() {
		// address2
		driver.findElement(By.id("address2")).sendKeys("calle Test2");
		Assert.assertEquals("calle Test2", driver.findElement(By.id("address2")).getAttribute("value"));
		Reporter.log("The second address typed is shown<br>");
	}
	
	@Test(description = "select a country in the 'country' selector", priority = 14)
	public void S014_CountrySelection() {
		// Select Country
		WebElement country = driver.findElement(By.id("country"));
		Select select1 = new Select(country);
		select1.selectByVisibleText("United States");
		Assert.assertEquals("United States", driver.findElement(By.id("country")).getAttribute("value"));
		Reporter.log("The country selected is shown<br>");
	}
	
	@Test(description = "select a state from the 'state' selector", priority = 15)
	public void S015_StateSelection() {
		// State
		driver.findElement(By.id("state")).sendKeys("Estado Prueba");
		
		Assert.assertEquals("Estado Prueba", driver.findElement(By.id("state")).getAttribute("value"));
		Reporter.log("The state selected is shown<br>");
	}

	@Test(description = "enter a city in the 'city' box", priority = 16)
	public void S016_CityValidation() {
		// city
		driver.findElement(By.id("city")).sendKeys("Ciudad de Prueba");
		Assert.assertEquals("Ciudad de Prueba", driver.findElement(By.id("city")).getAttribute("value"));
		Reporter.log("The city typed is shown<br>");
	}

	

	@Test(description = "enter the ZIP code in the 'zip' box", priority = 17)
	public void S017_ZIPValidation() {
		// ZIP
		driver.findElement(By.name("zipcode")).sendKeys("54321");
		Assert.assertEquals("54321", driver.findElement(By.id("zipcode")).getAttribute("value"));
		Reporter.log("The ZIP code typed is shown<br>");
	}

	

	@Test(description = "enter numbers in the 'mobile number' box", priority = 18)
	public void S018_MobileNumberValidation() {
		// Mobile Number
		driver.findElement(By.cssSelector("#mobile_number")).sendKeys("0987654321");
		Assert.assertEquals("0987654321", driver.findElement(By.cssSelector("#mobile_number")).getAttribute("value"));
		Reporter.log("The mobile number typed is shown<br>");
	}

	

	@Test(description = "Click on Register button", priority = 20)
	public void S019_ClickOnRegister() throws InterruptedException {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(5))
	       		  .pollingEvery(Duration.ofSeconds(1))
	       		  .ignoring(NoSuchElementException.class);
	       	
		Function<WebDriver, Boolean> predicate = new Function<WebDriver, Boolean>()
		{

			public Boolean apply(WebDriver arg0) {
				WebElement element = arg0.findElement(By.cssSelector("#mobile_number"));
				
				
					String mobile = element.getAttribute("value");
					System.out.println(mobile);
					
					if(mobile.equals("0987654321"))
					{
						return true;
					}
				
				
				return false;
			}
		};
		wait.until(predicate);
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

	// AFTER
	@AfterTest
	public void LastStep() throws InterruptedException {
		System.out.println("END");
		// Script Ends and closes browser
		Thread.sleep(5000);
		driver.close();
	}

}
