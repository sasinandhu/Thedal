package knila.thedal;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExecuteClass extends BaseClass {

	public String browser,URL,filename;
	public static String filepath = "resource/testcase/";
	public ArrayList TestCases = null;
	ArrayList TestSteps=null;
	
	@BeforeClass
	public void WarmUp() {
		browser = "chrome";
		URL = "";
		filename="Thedal_Automation_Testcase.xlsx";
//		GetDriver(browser);
	}
	
	@Test
	public void StartTest() {
		ReadTestCases();
		ExecuteRunner();
	}
	
	private void ExecuteRunner() {
		for (int testcase = 0; testcase < TestCases.size();testcase ++) {
			TestSteps = (ArrayList) TestCases.get(testcase);
			
		}

		
		
	}

	public void ReadTestCases() {
		try {
		File file = new File(filepath+filename);
		Workbook wb = null;
		if(filename.contains(".xlsx")) {
			FileInputStream inputStream = new FileInputStream(file);
			wb = new XSSFWorkbook(inputStream);
		}
		else {

			FileInputStream inputStream = new FileInputStream(file);
			wb = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = wb.getSheet("TestCase");
		
		int Rowcount = sheet.getLastRowNum();
		Row row = sheet.getRow(0);
		int ColumnCount = row.getLastCellNum();
		System.out.println(Rowcount);
		System.out.println(ColumnCount);

		TestCases = new ArrayList<>();
		for(int Testcase=2; Testcase<= Rowcount;Testcase ++) {
			row = sheet.getRow(Testcase);if(Testcase > 2 ) {
				if(TestSteps.isEmpty()) {				
				}else {

					TestCases.add(TestSteps);
				}
				
			}
			 TestSteps = new ArrayList<>();
			
			
			Cell Cell1 = row.getCell(0);
			Cell Cell2 = row.getCell(1);
			String Cell1value = getCellValueAsString(Cell1);
			String Cell2value = getCellValueAsString(Cell2);
			if( Cell1value== null && Cell2value == null ) {
				
			}
			else if (Cell1value != null && Cell2value == null  ) {
				for(int Step=1;Step <= Rowcount; Step ++) {
					int r = Testcase+Step;
					if(r> Rowcount) {
						break;
					}
					row =  sheet.getRow(r);
					Cell Cells1 = row.getCell(1);
					Cell Cells2 = row.getCell(2);
					Cell Cells3 = row.getCell(3);
					Cell Cells4 = row.getCell(4);
					Cell Cells5 = row.getCell(5);
					String Cell1values = getCellValueAsString(Cells1);
					if(Cell1values ==null){
						break;
					}
					else {
						String[] actions = new String[ColumnCount-1];
						actions[0] = getCellValueAsString(Cells1);
						actions[1] = getCellValueAsString(Cells2);
						actions[2] = getCellValueAsString(Cells3);
						actions[3] = getCellValueAsString(Cells4);
						actions[4] = getCellValueAsString(Cells5);
						TestSteps.add(actions);
					}
				}
			}			
		}
		
		}
		catch(Exception e) {

			System.out.println(e);
		}
	}

	public static String getCellValueAsString(Cell cell) {
        String strCellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                strCellValue = cell.toString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "dd/MM/yyyy");
                    strCellValue = dateFormat.format(cell.getDateCellValue());
                } else {
                    Double value = cell.getNumericCellValue();
                    Long longValue = value.longValue();
                    strCellValue = new String(longValue.toString());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                strCellValue = new String(new Boolean(
                        cell.getBooleanCellValue()).toString());
                break;
            case Cell.CELL_TYPE_BLANK:
                strCellValue = "";
                break;
            }
        }
        return strCellValue;
    }
}
