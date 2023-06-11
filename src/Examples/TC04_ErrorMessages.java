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
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
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

public class TC04_ErrorMessages {
	Random rand = new Random();
	int upperbound = 2555;
	int int_random1 = rand.nextInt(upperbound);
	int int_random2 = rand.nextInt(upperbound);

	String correo = "testmail" + int_random1 + int_random2 + "@gmail.com";
	String nombre_number = "24797823947298437979";
	String nombre_special = "&%$%$&#%#]и[и]";
	String nombre = "Lilian";
	String a = null;

	WebDriver driver = null;
	final String pagina = "https://automationexercise.com/";
	

	@BeforeTest
	public void S000_BeforeTest() {
		//ChromeOptions options = new ChromeOptions();
       // options.addArguments("load-extension=C:/Users/David Alejandro/AppData/Local/Google/Chrome/User/Guest Profile/Extensions/cjpalhdlnbpafiamejdnhcphjbkeiagm");
        //options.addArguments("--user-data-dir=C:/Users/David Alejandro/AppData/Local/Google/Chrome/User Data/Default");
        //options.addArguments("--profile-directory=Default");
        //C:\Users\DAVIDA~1\AppData\Local\Temp\scoped_dir9928_45061649\Default
        
        driver = new ChromeDriver();
        
        driver.manage().window().maximize();
	}

	@Test(description = "Access the register form ", priority = 1)
	public void S001_AccessForm() throws InterruptedException {
		driver.get(pagina);
		WebElement Sign_in = driver.findElement(By.cssSelector("a[href='/login']"));
		Sign_in.click();
		
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
	
	@Test(description = "Password field Empty", priority = 2)
	public void S008_PasswordEmpty() throws InterruptedException {

		WebElement password = driver.findElement(By.id("password"));
		
		password.clear();
		
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", password));
		if(a.isBlank()) {
			Assert.fail();
		}
		Assert.assertTrue(true);
	}

	@Test(description = "Password field with only 4 characters", priority = 3)
	public void S009_PasswordFourCharacters() throws InterruptedException {

		WebElement password = driver.findElement(By.id("password"));
		
		password.sendKeys("1234");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", password));
		if(a.isBlank()) {
			Assert.fail();
		}
		Assert.assertTrue(true);
	}

	@Test(description = "First name test/Empty", priority = 4)
	public void S002_FirstNameEmpty() throws InterruptedException {
		driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(nombre);
		driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(correo);
		driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();
		WebElement firstname = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.id("first_name")));
		driver.findElement(By.id("password")).sendKeys("12345");
		
		firstname.clear();
		
		
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", firstname));
		if(a.isBlank()) {
			Assert.fail();
		}
		Assert.assertTrue(true);

	}

	@Test(description = "First name test/Numbers", priority = 5)
	public void S003_FirstNameNum() throws InterruptedException {

		WebElement firstname =  driver.findElement(By.id("first_name"));
		firstname.sendKeys("213123141244");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", firstname));
		if(a.isBlank()) {
			Assert.fail();
		}
		Assert.assertTrue(true);


	}

	@Test(description = "First name test/Symbols", priority = 6)
	public void S004_FirstNameSymbols() throws InterruptedException {
		WebElement firstname =  driver.findElement(By.id("first_name"));
		firstname.clear();
		firstname.sendKeys("#$%#$%?б=)=)(=");
		
		WebElement field = driver.findElement(By.id("first_name"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", firstname));
		if(a.isBlank()) {
			Assert.fail();
		}
		Assert.assertTrue(true);

	}

	@Test(description = "Last name validation/Empty", priority = 7)
	public void S005_LastNameEmpty() throws InterruptedException {

		WebElement firstname =  driver.findElement(By.id("first_name"));
		WebElement lastname =  driver.findElement(By.id("last_name"));
		firstname.clear();
		firstname.sendKeys("Eleanor");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", lastname));
		if(a.isBlank()) {
			Assert.fail();
		}else {
			Assert.assertTrue(true);	
		}
		
	}

	@Test(description = "Last name validation/Numbers", priority = 8)
	public void S006_LastNameNum() throws InterruptedException {

		WebElement lastname =  driver.findElement(By.id("last_name"));
		lastname.sendKeys("1424213213");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", lastname));
		if(a.isBlank()) {
			Assert.fail();
		}
		Assert.assertTrue(true);
	}

	@Test(description = "Last name validation/Symbols", priority = 9)
	public void S007_LastNameSymbol() throws InterruptedException {

		WebElement lastname =  driver.findElement(By.id("last_name"));
		
		lastname.clear();
		lastname.sendKeys("&$%$/$%$#$$#%$#%");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", lastname));
		if(a.isBlank()) {
			Assert.fail();
		}
		Assert.assertTrue(true);
	}

	

	@Test(description = "Validation of address field with special symbol : @ ", priority = 10)
	public void S010_AdressSpecialCharacters() throws InterruptedException {

		WebElement password = driver.findElement(By.id("password"));
		WebElement address = driver.findElement(By.id("address1"));
		password.clear();
		password.sendKeys("12345");
		address.sendKeys("@@@@@@@@@");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", address));
		if(a.isBlank()) {
			Assert.fail();
		}
		Assert.assertTrue(true);
	}

	@Test(description = "Validation of address field / Valid data ", priority = 11)
	public void S011_AdressValid() {

		WebElement password = driver.findElement(By.id("password"));
		WebElement address = driver.findElement(By.id("address1"));
		password.clear();
		password.sendKeys("12345");
		address.clear();
		address.sendKeys("New california republic , 5th street chritsmas way");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		address = driver.findElement(By.id("address1"));
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", address));
		if(a.isEmpty()) {
			Assert.assertTrue(true);
		}else {
			Assert.fail();
		}
		

	}
	
	@Test(description = "Validation of State / Empty ", priority = 12)
	public void S024_StateVal() throws InterruptedException {
		WebElement pass = driver.findElement(By.id("password"));
		WebElement state = driver.findElement(By.id("state"));
		pass.sendKeys("12345");
		state.clear();
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", state));
		if(a.isEmpty()) {
			Assert.fail();
		}else {
			Assert.assertTrue(true);
			
		}
	}

	@Test(description = "Validation of State / Selected ", priority = 13)
	public void S025_StateVal2() {

		
		WebElement pass = driver.findElement(By.id("password"));
		WebElement state = driver.findElement(By.id("state"));
		pass.sendKeys("12345");
		state.clear();
		state.sendKeys("Nevada");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", state));
		if(a.isEmpty()) {
			Assert.assertTrue(true);
		}else {
			Assert.fail();
			
		}
	}

	@Test(description = "Validation of city field with special symbols ", priority = 14)
	public void S012_CitySymbol() throws InterruptedException {

		WebElement pass = driver.findElement(By.id("password"));
		WebElement city = driver.findElement(By.id("city"));
		pass.sendKeys("12345");
		city.sendKeys("@##@##?би*[");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", city));
		if(a.isEmpty()) {
			Assert.fail();
			
		}else {
			Assert.assertTrue(true);
		}
	}

	@Test(description = "Validation of city field ", priority = 15)
	public void S013_CityValid() {

		WebElement pass = driver.findElement(By.id("passwd"));
		WebElement submitButton = driver.findElement(By.id("submitAccount"));
		WebElement city = driver.findElement(By.id("city"));
		pass.sendKeys("12345");
		city.clear();
		city.sendKeys("New Reno");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", city));
		if(a.isEmpty()) {
			
			Assert.assertTrue(true);
		}else {
			
			Assert.fail();
		}
	}

	@Test(description = "Validation of Zip code field / Letters and numbers ", priority = 16)
	public void S014_ZipNumLetter() throws InterruptedException {

		WebElement pass = driver.findElement(By.id("password"));
		WebElement zip = driver.findElement(By.id("zipcode"));
		pass.clear();
		pass.sendKeys("12345");
		zip.sendKeys("A6C42");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", zip));
		if(a.isEmpty()) {
			Assert.fail();
			
		}else {
			Assert.assertTrue(true);
			
		}
	}

	@Test(description = "Validation of Zip code field / Letters ", priority = 17)
	public void S015_ZipLetterValid() throws InterruptedException {


		WebElement pass = driver.findElement(By.id("password"));
		WebElement zip = driver.findElement(By.id("zipcode"));
		zip.clear();
		pass.clear();
		pass.sendKeys("12345");
		zip.sendKeys("ABCDE");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", zip));
		if(a.isEmpty()) {
			Assert.fail();
			
		}else {
			Assert.assertTrue(true);
			
		}
	}

	@Test(description = "Validation of Zip code field / Lenght of numbers +5 ", priority = 18)
	public void S016_ZipUp5Char() throws InterruptedException {


		WebElement pass = driver.findElement(By.id("password"));
		WebElement zip = driver.findElement(By.id("zipcode"));
		
		zip.clear();
		pass.clear();
		pass.sendKeys("12345");
		zip.sendKeys("341453534");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", zip));
		if(a.isEmpty()) {
			Assert.fail();
			
		}else {
			Assert.assertTrue(true);
			
		}
	}

	@Test(description = "Validation of Zip code field / Lenght of numbers -5 ", priority = 19)
	public void S017_ZipLess5Char() throws InterruptedException {


		WebElement pass = driver.findElement(By.id("password"));
		WebElement zip = driver.findElement(By.id("zipcode"));
		zip.clear();
		pass.sendKeys("12345");
		zip.sendKeys("3414");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		a = ((String)js.executeScript("return arguments[0].validationMessage;", zip));
		if(a.isEmpty()) {
			Assert.fail();
			
		}else {
			Assert.assertTrue(true);
			
		}
	}

	@Test(description = "Validation of Phone number / Home phone /Letters and numbers", priority = 20)
	public void S018_PhoneLetterNumVal() throws InterruptedException {

		WebElement pass = driver.findElement(By.id("password"));
		WebElement zip = driver.findElement(By.id("zipcode"));
		WebElement number1 = driver.findElement(By.id("mobile_number"));
		
		zip.clear();
		zip.sendKeys("34567");
		pass.clear();
		pass.sendKeys("12345");
		number1.sendKeys("9G6E2E6Y8U");
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		       		  .withTimeout(Duration.ofSeconds(5))
		       		  .pollingEvery(Duration.ofSeconds(1))
		       		  .ignoring(NoSuchElementException.class);
		       	
			Function<WebDriver, Boolean> predicate = new Function<WebDriver, Boolean>()
			{

				public Boolean apply(WebDriver arg0) {
					
					
					 return arg0.findElement(By.xpath("//a[normalize-space()='Continu']")).isDisplayed();
					
				}
			};
	       
			Boolean find =  wait.until(predicate);
			Assert.assertTrue(false);
		}catch (Exception e) {
			/*driver.navigate().back();
			driver.navigate().back();
			WebElement delete = driver.findElement(By.cssSelector("a[href='/delete_account']"));
			delete.click();*/
			Assert.assertTrue(true);
		}
			
				
		
		
	}

	@Test(description = "Validation of Phone number / Home phone /Special characters ", priority = 21)
	public void S010_PhoneSpecialChar() throws InterruptedException {

		driver.get("https://automationexercise.com/");
		Thread.sleep(5000);
		
		
		
		WebElement delete = driver.findElement(By.cssSelector("a[href='/delete_account']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		delete.click();
		delete.click();
		
		driver.get(pagina);
		WebElement login = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		WebElement Sign_in = driver.findElement(By.cssSelector("a[href='/login']"));
		Sign_in.click();
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(10))
	       		  .pollingEvery(Duration.ofSeconds(2))
	       		  .ignoring(NoSuchElementException.class);
	       	
	       	WebElement firstResult = wait.until(new Function<WebDriver, WebElement>() {
	            public WebElement apply(WebDriver driver) {
	                return driver.findElement(By.cssSelector("div[class='signup-form'] h2"));
	              }
	            });
	       	driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(nombre);
			driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(correo);
			driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();
			WebElement firstname = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.id("first_name")));
			driver.findElement(By.id("password")).sendKeys("12345");
			firstname.sendKeys("Eleanor");
			WebElement lastname =  driver.findElement(By.id("last_name"));
			lastname.sendKeys("Rossvelt");
			WebElement address =  driver.findElement(By.id("address1"));
			address.sendKeys("New california republic , 5th street chritsmas way");
			WebElement state =  driver.findElement(By.id("state"));
			state.sendKeys("California");
			WebElement city =  driver.findElement(By.id("city"));
			city.sendKeys("San Diego");
			WebElement number1 = driver.findElement(By.id("mobile_number"));
			number1.sendKeys("123@4567");
			driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
			a = ((String)js.executeScript("return arguments[0].validationMessage;", number1));
			if(a.isEmpty()) {
				Assert.fail();
				
			}else {
				Assert.assertTrue(true);
				
			}
		
		
		
	}

	@Test(description = "Validation of Phone number / Home phone /Valid data ", priority = 22)
	public void S020_PhoneVal() {

		WebElement pass = driver.findElement(By.id("password"));
		WebElement number1 = driver.findElement(By.id("mobile_number"));
		pass.clear();
		pass.sendKeys("12345");
		number1.clear();
		number1.sendKeys("9855475623");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		a = ((String)js.executeScript("return arguments[0].validationMessage;", number1));
		if(a.isEmpty()) {
			Assert.fail();
			
		}else {
			Assert.assertTrue(true);
			
		}
	}

	@Test(description = "Validation of Phone number / Mobile phone/ Letters ", priority = 23)
	public void S021_MobilePhoneLettersVal() throws InterruptedException {

		WebElement pass = driver.findElement(By.id("password"));
		WebElement number1 = driver.findElement(By.id("mobile_number"));
		pass.clear();
		pass.sendKeys("12345");
		number1.clear();
		number1.sendKeys("ABCFTERSRU");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		a = ((String)js.executeScript("return arguments[0].validationMessage;", number1));
		if(a.isEmpty()) {
			Assert.fail();
			
		}else {
			Assert.assertTrue(true);
			
		}
	}

	
	@Test(description = "Validation of Phone number / Mobile phone/ Special symbols ", priority = 24)
	public void S022_MobilePhoneSpecialCharVal() throws InterruptedException {

		WebElement pass = driver.findElement(By.id("password"));
		WebElement number1 = driver.findElement(By.id("mobile_number"));
		pass.clear();
		pass.sendKeys("12345");
		number1.clear();
		number1.sendKeys("%&$#&/$&/");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
		a = ((String)js.executeScript("return arguments[0].validationMessage;", number1));
		if(a.isEmpty()) {
			Assert.fail();
			
		}else {
			Assert.assertTrue(true);
			
		}
	}

	@Test(description = "Validation of Phone number / Mobile phone / Valid data ", priority = 25)
	public void S023_MbilePhoneVal() {

		WebElement pass = driver.findElement(By.id("password"));
		WebElement number1 = driver.findElement(By.id("mobile_number"));
		pass.clear();
		pass.sendKeys("12345");
		WebElement zip = driver.findElement(By.id("zipcode"));
		zip.sendKeys("1234");
		number1.clear();
		number1.sendKeys("9855475623");
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

	
	

	@AfterTest
	public void S026_AfterTest() {
		try {
			Thread.sleep(3000);// Close
			driver.quit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// -----------------------

	public boolean checkError(String Message, String fieldname) throws InterruptedException {
		// Function to check the messages on the register error warning

		Thread.sleep(2000);
		boolean result = false;

		WebElement t = driver.findElement(By.xpath("//*[@id=\"center_column\"]/div/ol"));
		// identify child nodes with ./child::* expression in xpath
		List<WebElement> c = t.findElements(By.xpath("./child::*"));
		for (WebElement i : c) {
			if (i.getText().contains(Message)) {
				result = true;

				System.out.println("Validation of field " + fieldname + " Passed the if , successfull");
				Reporter.log("Validation of field " + fieldname + " Passed the validation IF , successfull <br>");
			}

		}

		return result;
	}

}
