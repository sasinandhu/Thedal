package knila.thedal;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExecuteClass extends BaseClass {

	public String browser,URL,filename;
	public static String filepath = "resource/testcase/";
	
	@BeforeClass
	public void WarmUp() {
		browser = "chrome";
		URL = "";
		filename="Thedal_Automation_Testcase.xlsx";
		GetDriver(browser);
	}
	
	@Test
	public void StartTest() {
		ReadTestCases();
	}
	
	public void ReadTestCases() {
		try {
		File file = new File(filepath+filename);
		Workbook wb = null;
		if(filename.contains(".xlsx")) {
			wb = new XSSFWorkbook(file);
		}
		else {

			FileInputStream inputStream = new FileInputStream(file);
			wb = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = wb.getSheet("TestCase");
		
		int Columncount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		
		}
		catch(Exception e) {
			
		}
	}

}
