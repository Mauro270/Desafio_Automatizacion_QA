package Script;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * Descripción: Ingresar a sitio WEB, completar datos para realizar la compra de los productos
 * 1.- Ingresar a sitio WEB: http://opencart.abstracta.us/index.php?route=common/home
 * 2.- Presionar botón "My acount"
 * 3.- Presionar botón "Login"
 * 4.- Presionar botón "shopping car"
 * 5.- Vericicar productos y presionar botón check out
 * 6.- Completar los datos solicitados para realizar compra (nombre, dirección, apellido, datos de dirección para envio de los productos, metodo de pago)
 * 7.- Presionar botón de confirmación de orden
 * 8.- Presionar botón "My acount"
 * 9.- Presionar botón "Order History"
 * 10.- Verificar productos y tomar evidencia (imagen) respectiva  
 */



public class TC_003 {
	
	WebDriver driver;
	private ReadExcelFile readfile;
	
	//WebElement Login
	By LABEL_MY_ACCOUNT = By.xpath("//span[contains(text(),'My Account')]");
	By LABEL_LOGIN = By.xpath("//a[contains(text(),'Login')]");
	By INPUT_LOGIN_MAIL = By.xpath("//input[@id='input-email']");
	By INPUT_LOGIN_PASS = By.xpath("//input[@id='input-password']");
	By BUTTON_LOGIN = By.xpath("//input[@value = 'Login']");
	By SPAN_CART_TOTAL = By.xpath("//span[contains(text(),'Shopping Cart')]");
	By LABEL_ORDER_HISTORY = By.xpath("//a[@href='https://opencart.abstracta.us:443/index.php?route=account/order']");
	
	//WebElement payment
	By BUTTON_CHECKOUT = By.xpath("//a[contains(text(),'Checkout')]");
	By INPUT_PAYMENT_NAME = By.xpath("//*[@id='input-payment-firstname']");
	By INPUT_PAYMENT_LAST_NAME = By.xpath("//input[@id='input-payment-lastname']");
	By INPUT_PAYMENT_COMPANY = By.xpath("//input[@id='input-payment-company']");
	By INPUT_PAYMENT_ADDRESS_1 = By.xpath("//input[@id='input-payment-address-1']");
	By INPUT_PAYMENT_ADDRESS_2 = By.xpath("//input[@id='input-payment-address-2']");
	By INPUT_PAYMENT_CITY = By.xpath("//input[@id='input-payment-city']");
	By INPUT_PAYMENT_CODE = By.xpath("//input[@id='input-payment-postcode']");
	By INPUT_PAYMENT_COUNTRY = By.xpath("//select[@id='input-payment-country']");
	By INPUT_PAYMENT_REGION = By.xpath("//select[@id='input-payment-zone']");
	By INPUT_PAYMENT_REGION_VAL = By.xpath("//*[@id='input-payment-zone']/option[16]");
	By BUTTON_PAYMENT_ADDRESS = By.xpath("//*[@id='button-payment-address']");
	By LABEL_ADDRESS_VALIDATE_TEXT = By.xpath("//option[contains(text(),'user_test last_name_test, address test, city test,')]"); // validación texto entre billing details y delivery detalis 
	By LABEL_COLAPSE_ADDRESS = By.xpath("//a[@href='#collapse-payment-address']");
	By BUTTON_PAYMENT_SHIPPING = By.xpath("//*[@id='button-shipping-address']");
	By LABEL_SHIPPING_RATE = By.xpath("//*[@id='collapse-shipping-method']/div/div[1]/label"); // validación shipping rate
	By TEXTAREA_COMMENT = By.xpath("//textarea[@name='comment']");
	By BUTTON_METHOD_SHIPPING = By.xpath("//*[@id='button-shipping-method']");
	By INPUT_PAYMENT_CHECKBOX = By.xpath("//*[@id='collapse-payment-method']/div/div[3]/div/input[1]");
	By BUTTON_PAYMENT_METHOD = By.xpath("//*[@id='button-payment-method']");
	By BUTTON_ORDER_CONFIRM = By.xpath("//*[@id='button-confirm']");
	By LABEL_ORDER_CONFIRM = By.xpath("//h1[contains(text(),'Your order has been placed!')]");
	
	
	
	
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
		String userName = readfile.getCellValue(filepath, "data", 1, 0);
		String lastName = readfile.getCellValue(filepath, "data", 1, 1);
		String email = readfile.getCellValue(filepath, "data", 1, 2);
		String password = readfile.getCellValue(filepath, "data", 1, 4);
		String company = readfile.getCellValue(filepath, "data", 1, 5);
		String address_1 = readfile.getCellValue(filepath, "data", 1, 6);
		String address_2 = readfile.getCellValue(filepath, "data", 1, 7);
		String city = readfile.getCellValue(filepath, "data", 1, 8);
		String country = readfile.getCellValue(filepath, "data", 1, 10);
		
		System.out.println("Pasa el Test");
		driver.findElement(LABEL_MY_ACCOUNT).click();
		driver.findElement(LABEL_LOGIN).click();
		driver.findElement(INPUT_LOGIN_MAIL).clear();
		driver.findElement(INPUT_LOGIN_MAIL).sendKeys(email);
		driver.findElement(INPUT_LOGIN_PASS).clear();
		driver.findElement(INPUT_LOGIN_PASS).sendKeys(password);
		driver.findElement(BUTTON_LOGIN).click();
		
		driver.findElement(SPAN_CART_TOTAL).click();
		
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		WebElement ScrollButtonCheckout = driver.findElement(BUTTON_CHECKOUT);
		js2.executeScript("arguments[0].scrollIntoView(true);", ScrollButtonCheckout);
		
		driver.findElement(BUTTON_CHECKOUT).click();
		
		
		
		 WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(20));
		 WebElement elementLocated = wait.until(ExpectedConditions.presenceOfElementLocated(INPUT_PAYMENT_NAME));
		 elementLocated.sendKeys(userName);
		
		driver.findElement(INPUT_PAYMENT_LAST_NAME).sendKeys(lastName);
		driver.findElement(INPUT_PAYMENT_COMPANY).sendKeys(company);
		driver.findElement(INPUT_PAYMENT_ADDRESS_1).sendKeys(address_1);
		driver.findElement(INPUT_PAYMENT_ADDRESS_2).sendKeys(address_2);
		driver.findElement(INPUT_PAYMENT_CITY).sendKeys(city);
		driver.findElement(INPUT_PAYMENT_CODE).sendKeys("24000000");
		Select sel = new Select(driver.findElement(INPUT_PAYMENT_COUNTRY));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		sel.selectByVisibleText(country);
		
		
		
		driver.findElement(INPUT_PAYMENT_REGION).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		WebElement regionVal = wait.until(ExpectedConditions.elementToBeClickable(INPUT_PAYMENT_REGION_VAL));
		regionVal.click();
		
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File("target/Screenshot/payment_checkout.png"));
		System.out.println("Sirve");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(BUTTON_PAYMENT_ADDRESS).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement addressExisting = wait.until(ExpectedConditions.presenceOfElementLocated(LABEL_ADDRESS_VALIDATE_TEXT));	
		String addressExisting2 = addressExisting.getText();
		
		System.out.println("Este es el texto a evaluar: " + addressExisting2);
		
		// Validación de texto solicitada en puntos de prueba técnica
		if (addressExisting2.contains(userName)) {	
			System.out.println("Pasa la validación del mensaje de dirección " + addressExisting2);
		} else {
			fail("No encontró el texto ");
		}
				
		TakesScreenshot shippingscreenshot = (TakesScreenshot)driver;
		File shippingsource = shippingscreenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(shippingsource, new File("target/Screenshot/shipping_checkout.png"));
		System.out.println("Sirve");
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		WebElement buttonPaymentShipping = wait.until(ExpectedConditions.elementToBeClickable(BUTTON_PAYMENT_SHIPPING));
		buttonPaymentShipping.click();
		
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String currentTitle = wait.until(ExpectedConditions.elementToBeClickable(LABEL_SHIPPING_RATE)).getText();
		System.out.println("Este es el titulo a evaluar " + currentTitle);
		assertEquals("Flat Shipping Rate - $5.00", currentTitle);
		driver.findElement(TEXTAREA_COMMENT).sendKeys("Comment test");
		
		TakesScreenshot shippingratescreenshot = (TakesScreenshot)driver;
		File shippingratesource = shippingratescreenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(shippingratesource, new File("target/Screenshot/rate_checkout.png"));
		System.out.println("Sirve");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement buttonMethodShipping = wait.until(ExpectedConditions.elementToBeClickable(BUTTON_METHOD_SHIPPING));
		buttonMethodShipping.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		
		WebElement buttonPaymentCheckbox = wait.until(ExpectedConditions.elementToBeClickable(INPUT_PAYMENT_CHECKBOX));
		buttonPaymentCheckbox.click();
		
		TakesScreenshot methodscreenshot = (TakesScreenshot)driver;
		File methodsource = methodscreenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(methodsource, new File("target/Screenshot/method.png"));
		System.out.println("Sirve");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.findElement(BUTTON_PAYMENT_METHOD).click();
		driver.findElement(BUTTON_ORDER_CONFIRM).click();
			
		String currentTitleConfirm = driver.findElement(LABEL_ORDER_CONFIRM).getText();
		System.out.println("Este es el titulo a evaluar 2 :" + currentTitleConfirm);
		assertEquals("Your order has been placed!", currentTitleConfirm);
		driver.findElement(LABEL_MY_ACCOUNT).click();
		driver.findElement(LABEL_ORDER_HISTORY).click();
		
		
		TakesScreenshot confirmscreenshot = (TakesScreenshot)driver;
		File confirmsource = confirmscreenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(confirmsource, new File("target/Screenshot/confirm.png"));
		System.out.println("Sirve");	
	}
	
	
	@After
	public void tearDown() {
		System.out.println("Execute after each test method");
		driver.quit();	
	}

}
