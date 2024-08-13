/**
 * 
 */
package Script;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * Descripción: Ingresar producto a carro de compras y realizar validaciones de textos solicitadas
 * 1.- Ingresar a sitio WEB: http://opencart.abstracta.us/index.php?route=common/home
 * 2.- Presionar botón "My acount"
 * 3.- Presionar botón "Login"
 * 4.- En campo de busqueda escribir el producto "PC HP LP3065"
 * 5.- Seleccionar producto
 * 6.- Se realiza validación de capacidad solicitada (Que posea 16GB de memoria)
 * 7.- Se realiza validación de comentario review con caracteres insuficientes
 * 8.- Se realiza validación de comentario review con caracteres correctos. Se deja grabado el review
 * 9.- Se cambia fecha en calendario para que la entrega del producto se realice el día siguiente de ingresar el producto
 * 10.-Se toma evidencia del cambio de fecha
 
*/
public class TC_004 {

	WebDriver driver;
	private ReadExcelFile readfile;
	
	//WebElement Login
	By LABEL_MY_ACCOUNT = By.xpath("//span[contains(text(),'My Account')]");
	By LABEL_LOGIN = By.xpath("//a[contains(text(),'Login')]");
	By INPUT_LOGIN_MAIL = By.xpath("//input[@id='input-email']");
	By INPUT_LOGIN_PASS = By.xpath("//input[@id='input-password']");
	By BUTTON_LOGIN = By.xpath("//input[@value = 'Login']");
	By LABEL_MY_ACOUNT = By.xpath("//h2[contains(text(),'My Account')]");
	By SPAN_CART_TOTAL = By.xpath("//span[contains(text(),'Shopping Cart')]");
	By LABEL_ORDER_HISTORY = By.xpath("//a[@href='https://opencart.abstracta.us:443/index.php?route=account/order']");
	By INPUT_SEARCH = By.xpath("//input[@name='search']");
	By BUTTON_SEARCH = By.xpath("//button[@class='btn btn-default btn-lg']");
	By LABEL_HP_PRODUCT = By.xpath("//a[contains(text(),'HP LP3065')]");
	By LABEL_SPECIFICATIONS = By.xpath("//a[contains(text(),'Specification')]");
	By LABEL_SPECIFICATIONS_GB = By.xpath("//td[contains(text(),'16GB')]");
	By LABEL_REVIEW = By.xpath("//a[contains(text(),'Reviews')]");
	By TEXT_AREA_REVIEW = By.xpath("//textarea[@id='input-review']");
	By BUTTON_CONTINUE_REVIEW = By.xpath("//button[@id='button-review']");
	By LABEL_ERROR_REVIEW = By.xpath("//*[@class='alert alert-danger alert-dismissible'][contains(text(),' Warning: Review Text must be between 25 and 1000 characters!')]");
	By RADIO_BUTTON_RATING_REVIEW = By.xpath("/html/body/div[2]/div/div/div/div[1]/div/div[3]/form/div[4]/div/input[3]");
	By INPUT_CALENDAR = By.xpath("//input[@id='input-option225']");
	By BUTTON_CALENDAR = By.xpath("//*[@id='product']/div[1]/div/span/button");
	By LABEL_APPROVAL_REVIEW = By.xpath("//*[@class='alert alert-success alert-dismissible'][contains(text(),' Thank you for your review. It has been submitted to the webmaster for approval.')]");
	
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Execute before each test method");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		readfile = new ReadExcelFile();
		driver.manage().window().maximize();
		driver.get("http://opencart.abstracta.us/index.php?route=common/home");
	}




	@Test
	public void test() throws Exception {
		
		String filepath = "C:\\Users\\Mauricio\\Desktop\\data.xlsx";
		String email = readfile.getCellValue(filepath, "data", 1, 2);
		String password = readfile.getCellValue(filepath, "data", 1, 4);
		String failreview = readfile.getCellValue(filepath, "data", 1,11);
		String review = readfile.getCellValue(filepath, "data", 1,12);
		
		
		driver.findElement(LABEL_MY_ACCOUNT).click();
		driver.findElement(LABEL_LOGIN).click();
		driver.findElement(INPUT_LOGIN_MAIL).clear();
		driver.findElement(INPUT_LOGIN_MAIL).sendKeys(email);
		driver.findElement(INPUT_LOGIN_PASS).clear();
		driver.findElement(INPUT_LOGIN_PASS).sendKeys(password);
		driver.findElement(BUTTON_LOGIN).click();
		
		String currentTitle = driver.findElement(LABEL_MY_ACOUNT).getText();
		System.out.println("Este es el titulo a evaluar: " + currentTitle);
		assertEquals("My Account", currentTitle);
		
		driver.findElement(INPUT_SEARCH).clear();
		driver.findElement(INPUT_SEARCH).sendKeys("HP LP3065");
		driver.findElement(BUTTON_SEARCH).click();
		driver.findElement(LABEL_HP_PRODUCT).click();
		driver.findElement(LABEL_SPECIFICATIONS).click();
		
		String gbValidation = driver.findElement(LABEL_SPECIFICATIONS_GB).getText();
		System.out.println("Este es el titulo a evaluar: " + gbValidation);
		assertEquals("16GB", gbValidation);
		
		driver.findElement(LABEL_REVIEW).click();
		driver.findElement(TEXT_AREA_REVIEW).sendKeys(failreview);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement radioButtonRating = wait.until(ExpectedConditions.elementToBeClickable(RADIO_BUTTON_RATING_REVIEW));
		radioButtonRating.click();
		driver.findElement(BUTTON_CONTINUE_REVIEW).click();
		
		
		
		String warningReview = driver.findElement(LABEL_ERROR_REVIEW).getText();
		
		System.out.println("Este es el texto a evaluar: " + warningReview);
		
		if (warningReview.contains("Warning: Review Text must be between 25 and 1000 characters!")) {
			
			System.out.println("Pasa la validación del mensaje de error" + warningReview);
			
		} else {
			
			fail("No encontró el texto ");
		}
		
		
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File("target/Screenshot/calendar_current_date.png"));
		System.out.println("Sirve");
		
		
		// Función de calendario: Se toma el valor que trae el campo desde su contrucción, posterior se separa dejando solamente el día actual que muestra calentadario, de forma posterior se suma el día siguiente y se realiza el click solicitado en la prueba
		String inputCalendar = driver.findElement(INPUT_CALENDAR).getAttribute("value");
		System.out.println("la fecha del calendario es : " + inputCalendar);
		
		String dayCalendar = inputCalendar.split("-")[2];
		System.out.println("El día es el siguiente : " + dayCalendar);
		
		int number = Integer.parseInt(dayCalendar);
		int number_1 = 1;
		int result = number + number_1;
		
		driver.findElement(BUTTON_CALENDAR).click();
		
		WebElement calendarClick = driver.findElement(By.xpath("//td[contains(text(),'"+result+"')]"));
		calendarClick.click();
		
		TakesScreenshot screenshotNextDays = (TakesScreenshot)driver;
		File sourceNextDay = screenshotNextDays.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sourceNextDay, new File("target/Screenshot/calendar_next_date.png"));
		System.out.println("Sirve");
		
		
		driver.findElement(TEXT_AREA_REVIEW).clear();
		driver.findElement(TEXT_AREA_REVIEW).sendKeys(review);
		
		driver.findElement(BUTTON_CONTINUE_REVIEW).click();
		
		
		String approvalMsg = driver.findElement(LABEL_APPROVAL_REVIEW).getText();
		
		System.out.println("Este es el texto a evaluar: " + approvalMsg);
		
		if (approvalMsg.contains("Thank you for your review. It has been submitted to the webmaster for approval.")) {
			
			System.out.println("Pasa la validación del mensaje de correcto" + approvalMsg);
			
		} else {
			
			fail("No encontró el texto ");
		}
		
		TakesScreenshot screenshotApproval = (TakesScreenshot)driver;
		File sourceApproval = screenshotApproval.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(sourceApproval, new File("target/Screenshot/approval_evidence.png"));
		System.out.println("Sirve");
		
	}
	
	
	@After
	public void tearDown() throws Exception {
		System.out.println("Execute after each test method");
		driver.quit();
	}

}
