package Script;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ReadExcelFile {
	
	public ReadExcelFile() {
		// constructor
	}
	
	
	//Leer hoja de excel fila por fila y cada una de las celdas de la fila 
	public void readExcel(String filepath, String sheetName) throws Exception {
		
		File file = new File(filepath);
		FileInputStream inputStream = new FileInputStream(file);
		
		XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream);
		
		XSSFSheet newSheet = newWorkBook.getSheet(sheetName);
		
		int rowCount = newSheet.getLastRowNum() + newSheet.getFirstRowNum();
		
		for (int i = 0; i < rowCount;  i++  ) {
			XSSFRow row = newSheet.getRow(i);
			
			for( int j = 0; j < row.getLastCellNum(); j++ ) {
				System.out.println(row.getCell(j).getStringCellValue() + "||");
				
			}
			
		}
		
	}
	
	
	public String getCellValue(String filepath, String sheetName, int rowNumber, int cellNumber) throws IOException {
		
		
		File file = new File(filepath);
		
		// Leemos el archivos
		FileInputStream inputStream = new FileInputStream(file);
		
		//Creamos libro de excel 
		XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream);
		
		//Creamos la hoja
		XSSFSheet newSheet = newWorkBook.getSheet(sheetName);
		
		XSSFRow row = newSheet.getRow(rowNumber);
		
		XSSFCell cell = row.getCell(cellNumber);
		
		System.out.println(cell.getStringCellValue());
		
		return cell.getStringCellValue();
		
	} 
	
	

}
