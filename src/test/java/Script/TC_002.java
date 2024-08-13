package Script;

import static org.junit.Assert.*;



import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * Descripción: Realizar Login y buscar productos en tienda
 * 1.- Ingresar a sitio WEB: http://opencart.abstracta.us/index.php?route=common/home
 * 2.- Presionar botón "My acount"
 * 3.- Presionar botón "Login"
 * 4.- En campo de busqued escribir lod productos "Ipod Classic" y "iMac"
 * 5.- Realizar la búsqueda de cada productos y agregar a carro de compra
 * 6.- Presionar botón "Shoppimg card"
 * 7.- Tomar evidencia (imagen) de los productos
 * 8.- Realizar logut de la plataforma
 */




public class TC_002 {
	
	WebDriver driver;
	private ReadExcelFile readfile;
	
	By LABEL_MY_ACCOUNT = By.xpath("//span[contains(text(),'My Account')]");
	By LABEL_LOGIN = By.xpath("//a[contains(text(),'Login')]");
	By INPUT_LOGIN_MAIL = By.xpath("//input[@id='input-email']");
	By INPUT_LOGIN_PASS = By.xpath("//input[@id='input-password']");
	By BUTTON_LOGIN = By.xpath("//input[@value = 'Login']");
	By BUTTON_LOGOUT = By.xpath("//a[contains(text(),'Logout')]");
	By LABEL_MY_ACOUNT = By.xpath("//h2[contains(text(),'My Account')]");
	By INPUT_SEARCH = By.xpath("//input[@name='search']");
	By BUTTON_SEARCH = By.xpath("//button[@class='btn btn-default btn-lg']");
	By LABEL_IPOD_CLASSIC = By.xpath("//a[contains(text(),'iPod Classic')]");
	By BUTTON_ADD_CARD = By.xpath("//span[contains(text(),'Add to Cart')]");
	By LABEL_IMAC = By.xpath("//a[contains(text(),'iMac')]");
	By SPAN_CART_TOTAL = By.xpath("//span[contains(text(),'Shopping Cart')]");
	
	
	
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
	public void test() throws Exception{
		System.out.println("Pasa el Test");
		String filepath = "C:\\Users\\Mauricio\\Desktop\\data.xlsx";
		String email = readfile.getCellValue(filepath, "data", 1, 2);
		String password = readfile.getCellValue(filepath, "data", 1, 4);
		
		driver.findElement(LABEL_MY_ACCOUNT).click();
		driver.findElement(LABEL_LOGIN).click();
		driver.findElement(INPUT_LOGIN_MAIL).clear();
		driver.findElement(INPUT_LOGIN_MAIL).sendKeys(email);
		driver.findElement(INPUT_LOGIN_PASS).clear();
		driver.findElement(INPUT_LOGIN_PASS).sendKeys(password);
		driver.findElement(BUTTON_LOGIN).click();
		
		String currentTitle = driver.findElement(LABEL_MY_ACOUNT).getText();
		System.out.println("Este es el titulo a evaluar" + currentTitle);
		assertEquals("My Account", currentTitle);
		
		driver.findElement(INPUT_SEARCH).clear();
		driver.findElement(INPUT_SEARCH).sendKeys("Ipod Classic");
		driver.findElement(BUTTON_SEARCH).click();
		
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Scroll = driver.findElement(LABEL_IPOD_CLASSIC);
		js.executeScript("arguments[0].scrollIntoView(true);", Scroll);
		
		String ipodCLassicTitle = driver.findElement(LABEL_IPOD_CLASSIC).getText();
		System.out.println("Este es el titulo a evaluar " + ipodCLassicTitle);
		assertEquals("iPod Classic", ipodCLassicTitle); //Validación Ipod Classic
		driver.findElement(BUTTON_ADD_CARD).click();
		
		
		
		driver.findElement(INPUT_SEARCH).clear();
		driver.findElement(INPUT_SEARCH).sendKeys("iMac");
		driver.findElement(BUTTON_SEARCH).click();
		
		String iMacTitle = driver.findElement(LABEL_IMAC).getText();
		System.out.println("Este es el titulo a evaluar " + iMacTitle);
		assertEquals("iMac", iMacTitle); // Validación iMac
		driver.findElement(BUTTON_ADD_CARD).click();
		

		driver.findElement(SPAN_CART_TOTAL).click();
		Thread.sleep(10);
		
		
		
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File("target/Screenshot/cart_products.png"));
		System.out.println("Sirve");
		
		driver.findElement(LABEL_MY_ACCOUNT).click();
		driver.findElement(BUTTON_LOGOUT).click();	
	}
	
	@After
	public void tearDown() {
		System.out.println("Execute after each test method");
		driver.quit();
		
	}

}
