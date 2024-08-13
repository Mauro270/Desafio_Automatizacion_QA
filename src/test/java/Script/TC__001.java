package Script;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.*;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
 * Descripción: CReación de usuario nuevo
 * 1.- Ingresar a sitio WEB: http://opencart.abstracta.us/index.php?route=common/home
 * 2.- Presionar botón "My acount"
 * 3.- Presionar botón "Register"
 * 4.- Ingresar datos validos
 * 5.- Presionar check de politicas de privacidad
 * 6.- Presionar botón "Continue"
 * 7.- Validar texto "Congratulations! Your new account has been successfully created!"
 * */
 


public class TC__001 {
	
	WebDriver driver;
	private ReadExcelFile readfile;	
	
	By LABEL_MY_ACCOUNT = By.xpath("//span[contains(text(),'My Account')]");
	By BUTTON_REGISTER = By.xpath("//a[contains(text(),'Register')]");
	By FIRST_NAME_INPUT = By.id("input-firstname");
	By LAST_NAME_INPUT = By.id("input-lastname");
	By EMAIL_INPUT = By.id("input-email");
	By CELLPHONE_INPUT = By.id("input-telephone");
	By PASS_INPUT = By.id("input-password");
	By CONFIRM_PASS_INPUT = By.id("input-confirm");
	By CHECKBOX_INPUT = By.xpath("//input[@name='agree']");
	By CONGRATULATION_LABEL = By.xpath("//p[contains(text(),'Congratulations! Your new account has been success')]");
	By BUTTON_CONTINUE = By.xpath("//input[@value = 'Continue']");
	
	
	
	@Before
	public void setUp() throws Exception {
		System.out.println("Execute before each test method");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		readfile = new ReadExcelFile();
		driver.manage().window().maximize();
		driver.get("http://opencart.abstracta.us/index.php?route=common/home");
		System.out.println("Llega acá ");
	}
	
	@Test
	public void test() throws Exception {
				
		String filepath = "C:\\Users\\Mauricio\\Desktop\\data.xlsx";
			
		String userName = readfile.getCellValue(filepath, "data", 1, 0);
		String lastName = readfile.getCellValue(filepath, "data", 1, 1);
		String email = readfile.getCellValue(filepath, "data", 1, 2);
		String phone = readfile.getCellValue(filepath, "data", 1, 3);
		String password = readfile.getCellValue(filepath, "data", 1, 4);
		
		System.out.println("este es el name: " + userName);
		System.out.println("este es el last name: " + lastName);
		System.out.println("este es el mail: " + email);
		System.out.println("este es el phone: " + phone);
		System.out.println("este es el pass: " + password);
					
		driver.findElement(LABEL_MY_ACCOUNT).click();
		driver.findElement(BUTTON_REGISTER).click();
		driver.findElement(FIRST_NAME_INPUT).clear();
		driver.findElement(FIRST_NAME_INPUT).sendKeys(userName);
		
		
		driver.findElement(LAST_NAME_INPUT).clear();
		driver.findElement(LAST_NAME_INPUT).sendKeys(lastName);
		driver.findElement(EMAIL_INPUT).clear();
		driver.findElement(EMAIL_INPUT).sendKeys(email);
		driver.findElement(CELLPHONE_INPUT).clear();
		driver.findElement(CELLPHONE_INPUT).sendKeys(phone);
		driver.findElement(PASS_INPUT).clear();
		driver.findElement(PASS_INPUT).sendKeys(password);
		driver.findElement(CONFIRM_PASS_INPUT).clear();
		driver.findElement(CONFIRM_PASS_INPUT).sendKeys(password);
		
		
		driver.findElement(CHECKBOX_INPUT).click();
		driver.findElement(BUTTON_CONTINUE).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		//Validación de texto para el usuario creado
		String currentTitle = driver.findElement(CONGRATULATION_LABEL).getText();
		System.out.println("Este es el titulo a evaluar" + currentTitle);
		assertEquals("Congratulations! Your new account has been successfully created!", currentTitle);
		
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File("target/Screenshot/create_user.png"));
		System.out.println("Sirve");
		
		
	}
	
	@After
	public void tearDown() {
		System.out.println("Execute after each test method");
		driver.quit();
		
	}
	

}
