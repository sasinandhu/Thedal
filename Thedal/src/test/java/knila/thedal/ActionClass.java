package knila.thedal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ActionClass {
	
	private WebDriver driver; 
	
	public ActionClass(WebDriver driver) {
		this.driver = driver;
	}

	public void action(String Action,String ElementType,String Location,String Value) {
		switch(Action.toLowerCase())
		{
		case "click":{
			driver.findElement(Getobject(ElementType, Location)).click();;
			break;
		}
		case "enter":{
			driver.findElement(Getobject(ElementType, Location)).sendKeys(Value);
			break;
		}
		case "gotourl":{
			driver.get(Value);
			break;
		}
		case "choose":
		case "select":{
			WebElement element = driver.findElement(Getobject(ElementType, Location));
			Select s = new Select(element);
			s.selectByValue(Value);			
			break;
		}
		default:{
			break;
		}
		}
	}
	
	public By Getobject(String ElementType,String findpath) {
		
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
			
		}else
		{
			return By.id("");
		}
		
	}
}
