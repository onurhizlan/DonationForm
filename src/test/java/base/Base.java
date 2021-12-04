package base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.Screenshot;
import pages.DonationPage;

public class Base {

	public WebDriver driver;
	public WebDriverWait wait;
	public String myBrowser, environment;
	protected DonationPage dp;
	public Screenshot Screenshot;

	
	
	public void initPages() {
		dp = PageFactory.initElements(driver, DonationPage.class);
	}
	
	@SuppressWarnings("deprecation")
	@BeforeMethod
	@Parameters({ "myBrowser", "environment" })
	public void initDriver(String myBrowser, String environment) {

		this.environment = environment;
		if (System.getProperty("os.name").contains("Window")) {
			if (myBrowser.equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

				DesiredCapabilities capability = new DesiredCapabilities();
				capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

				ChromeOptions options = new ChromeOptions();
				Dimension dimension = new Dimension(400, 1000);
								
				Map<String, String> mobileEmulation = new HashMap<String, String>();
				mobileEmulation.put("deviceName", "Galaxy S5");
	
				options.setExperimentalOption("mobileEmulation", mobileEmulation);
				options.addArguments("--start-maximized");
				options.addArguments(new String[] { "disable-infobars" });
				options.setExperimentalOption("useAutomationExtension", false);
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				options.addArguments("--allow-running-insecure-content");
				options.addArguments("ignore-certificate-errors");
				options.addArguments("window-size=1920,1080");
				capability.setCapability(ChromeOptions.CAPABILITY, options);

				driver = new ChromeDriver(capability);
				wait = new WebDriverWait(driver, 90);
				driver.manage().window().maximize();

			}
		}
		initPages();
	}
	
	
	@AfterMethod
	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
		}
	}

	@AfterTest
	public void closeBrowserr() {
		if (driver != null) {
			driver.quit();
		}
	}

	public WebDriver getDriver() {
		return this.driver;
	}
	
}
