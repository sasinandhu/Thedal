package knila.thedal;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class ActionClass {
	
	private WebDriver driver; 
	
	public ActionClass(WebDriver driver) {
		this.driver = driver;
	}

	public boolean action(String Action,String ElementType,String Location,String Value) {
		boolean result = false;
		try {		
		Robot robot =  new Robot();
		switch(Action.toLowerCase())
		{
		case "click":{
			driver.findElement(Getobject(ElementType, Location)).click();
			result = true;
			break;
		}
		case "enter":{
			driver.findElement(Getobject(ElementType, Location)).sendKeys(Value);
			result = true;
			break;
		}
		case "enterandtab":{
			driver.findElement(Getobject(ElementType, Location)).sendKeys(Value);
			Thread.sleep(700);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(700);
			result = true;
			break;
		}
		case "clearandenter":{
			WebElement element = driver.findElement(Getobject(ElementType, Location));
			element.clear();
			element.sendKeys(Value);
			result = true;
			break;
		}
		case "clearentertab":{
			WebElement element = driver.findElement(Getobject(ElementType, Location));
			element.clear();
			element.sendKeys(Value);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			result = true;
			break;
		}
		case "gotourl":{
			driver.get(Value);
			result = true;
			break;
		}
		case "choose":
		case "select":{
			WebElement element = driver.findElement(Getobject(ElementType, Location));
			Select s = new Select(element);
			s.selectByValue(Value);			
			result = true;
			break;
		}
		case "verifypagetitle":{
			String ActualTitle = null;
			String ExpectedTitle = null;		
			
			ActualTitle= driver.getTitle().toString();
			ExpectedTitle = Value;
			Assert.assertEquals(ActualTitle, ExpectedTitle);
			result = true;
			break;
		}
		case "verifycontent":{
			String Actual = null;
			String Expected = null;
			 			
			Actual= driver.findElement(Getobject(ElementType, Location)).getText();
			Expected = Value;
			Assert.assertEquals(Actual, Expected);
			result = true;
			break;
		}
		case "verifyelementpresent":{
			String Actual = null;
			String Expected = null;
			 			
			try{
			Actual= driver.findElement(Getobject(ElementType, Location)).getText();
			Actual = "true";
			result = true;
			}
			catch(NoSuchElementException e){
				Actual = "false";
				result = false;
			}
			Expected = Value;
			Assert.assertEquals(Actual, Value.toLowerCase());
			result = true;
			break;
		}
		case "imageupload":{
			StringSelection selection = new StringSelection(Value);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			
			
			break;			
		}
		default:{

			result = false;
			break;
		}
		}
		}
		catch (Exception e) {
			result = false;
		}
		
		return result;
	
	}
	
	public By Getobject(String ElementType,String findpath) {
		ElementType = ElementType.toUpperCase();
		ElementType = ElementType.replace(" ", "");
		
		if(ElementType.equalsIgnoreCase("XPATH")){
			
			return By.xpath(findpath);
		}
		//find by class
		else if(ElementType.equalsIgnoreCase("CLASSNAME")){
			
			return By.className(findpath);
			
		}
		//find by name
		else if(ElementType.equalsIgnoreCase("NAME")){
			
			return By.name(findpath);
			
		}
		//Find by css
		else if(ElementType.equalsIgnoreCase("CSS")){
			
			return By.cssSelector(findpath);
			
		}
		//find by link
		else if(ElementType.equalsIgnoreCase("LINK")){
			
			return By.linkText(findpath);
			
		}
		//find by partial link
		else if(ElementType.equalsIgnoreCase("PARTIALLINK")){
			
			return By.partialLinkText(findpath);
			
		}
		//find by partial link
		else if(ElementType.equalsIgnoreCase("ID")){
			
			return By.id(findpath);
			
		}else
		{
			return By.id("");
		}
		
	}
}
