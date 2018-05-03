package knila.thedal;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class ExecuteClass extends BaseClass {

	public String browser,URL,filename;
	public static String filepath = "resource/testcase/";
	public ArrayList TestCases = null;
	ArrayList TestSteps=null;
    private static ScreenRecorder screenRecorder;
    public boolean RecoderSwitch;

	
	@BeforeClass
	public void WarmUp() {
		RecoderSwitch = true;
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		Date date = new Date();
		//Create a instance of GraphicsConfiguration to get the Graphics configuration
        //of the Screen. This is needed for ScreenRecorder class.
        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
        
        try {
			screenRecorder = new ScreenRecorder(gc,
			        new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
			        new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
			                CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
			                DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
			                QualityKey, 1.0f,
			                KeyFrameIntervalKey, (int) (15 * 60)),
			        new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,"black",
			                FrameRateKey, Rational.valueOf(30)),
			        null);
			if(RecoderSwitch == true){
				screenRecorder.start();
			}
		} catch (IOException | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		browser = "Chrome";
		URL = "https://www.google.co.in/?gws_rd=ssl";
		filename="Thedal_Automation_Testcase.xlsx";
		GetDriver(browser);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.MINUTES);
	}
	
	@AfterClass
	public void teardown() {

		 try {
			 if(RecoderSwitch == true){
					screenRecorder.stop();
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        java.util.List<File> createdMovieFiles = screenRecorder.getCreatedMovieFiles();
	        for(File movie : createdMovieFiles){
	            System.out.println("New movie created: " + movie.getAbsolutePath());
	        }
//		driver.close();
	}
	
	@Test
	public void StartTest() {
		ReadTestCases();
		ExecuteRunner();
	}
	
	private void ExecuteRunner() {
		for (int testcase = 0; testcase < TestCases.size();testcase ++) {
			TestSteps = (ArrayList) TestCases.get(testcase);
			
			for (int teststep = 0; teststep < TestSteps.size();teststep ++) {
				String s[] = (String[]) TestSteps.get(teststep);
				if(s[0] == null) {s[0] = "";}
				if(s[1] == null) {s[1] = "";}
				if(s[2] == null) {s[2] = "";}
				if(s[3] == null) {s[3] = "";}
				if(s[4] == null) {s[4] = "";}
				
				
				ActionClass ac = new ActionClass(driver);
				boolean ActionResult = ac.action(s[1].toString(), s[2].toString(), s[3].toString(), s[4].toString());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try{
					Assert.assertEquals(ActionResult, true);
					System.out.println(s[0].toString() + " ---> "+ "Passed");
				}
				catch (Exception e) {
					System.out.println(s[0].toString() + " ---> "+ "Failed");
				}
			}			
		}		
	}
	

	private String nulltoempty(String string) {
		if(string==null) {
			return "";
		}else {
			return string;
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
