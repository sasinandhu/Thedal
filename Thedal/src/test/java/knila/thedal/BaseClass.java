package knila.thedal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseClass {
	public WebDriver driver;

	public void GetDriver(String browser) {
		switch (browser.toLowerCase()) {
		case "mozilla":
		case "firefox":
		{
			System.setProperty("webdriver.gecko.driver", "resource/driver/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		}
		case "chrome":{
			System.setProperty("webdriver.chrome.driver", "resource/driver/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		default:
			break;
		}
	}

}
