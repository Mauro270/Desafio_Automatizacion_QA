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
 * Descripción: Realizar comparación de produtos
 * 1.- Ingresar a sitio WEB: http://opencart.abstracta.us/index.php?route=common/home
 * 2.- Presionar botón "My acount"
 * 3.- Presionar botón "Login"
 * 4.- Presionar botón "Components"
 * 5.- Presionar botón "Monitors"
 * 6.- Presionar botón "Compare this product" de cada producto mostrado en pantalla (2 productos son mostrados)
 * 7.- Presionar botón "Compare product"
 * 8.- Verificar comparación y tomar evidencia en imagen de lo solicitado  
*/

public class TC_005 {
	
	WebDriver driver;
	private ReadExcelFile readfile;
	
	//WebElement Login
	By LABEL_MY_ACCOUNT = By.xpath("//span[contains(text(),'My Account')]");
	By LABEL_LOGIN = By.xpath("//a[contains(text(),'Login')]");
	By INPUT_LOGIN_MAIL = By.xpath("//input[@id='input-email']");
	By INPUT_LOGIN_PASS = By.xpath("//input[@id='input-password']");
	By BUTTON_LOGIN = By.xpath("//input[@value = 'Login']");
	By LABEL_MY_ACOUNT = By.xpath("//h2[contains(text(),'My Account')]");
	By LABEL_COMPONENT = By.xpath("//*[@id='menu']/div[2]/ul/li[3]/a");
	By LABEL_MONITORS = By.xpath("//a[contains(text(),'Monitors')]");
	By BUTTON_COMPARE_APPLE = By.xpath("//*[@id='content']/div[3]/div[1]/div/div[2]/div[2]/button[3]");
	By BUTTON_COMPARE_SAMSUNG = By.xpath("//*[@id='content']/div[3]/div[2]/div/div[2]/div[2]/button[3]");
	By LABEL_COMPARE_TOTAL = By.xpath("//a[@id='compare-total']");

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
		
		driver.findElement(LABEL_COMPONENT).click();
		driver.findElement(LABEL_MONITORS).click();
		driver.findElement(BUTTON_COMPARE_APPLE).click();
		driver.findElement(BUTTON_COMPARE_SAMSUNG).click();
		driver.findElement(LABEL_COMPARE_TOTAL).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");
		
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File("target/Screenshot/comparative_table.png"));
		System.out.println("Sirve");
	}
	
	
	@After
	public void tearDown() throws Exception {
		
		System.out.println("Execute after each test method");
		driver.quit();
	}

}
