import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.safari.SafariDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class CustomWebDriver {
	public static void startChromeWithCustomOptions() {
		
//		String browser = RunConfiguration.getProperty("webui.browserType") ?: "Chrome"
//		println("Browser: " + browser)
		
		String rawBrowser = DriverFactory.getExecutedBrowser().getName()
		println(">>> Running with browser: " + rawBrowser)
		
		String browser
		
		switch(rawBrowser) {
			case "CHROME_DRIVER":
				browser = "Chrome"
				break
			case "FIREFOX_DRIVER":
				browser = "Firefox"
				break
			case "EDGE_CHROMIUM_DRIVER":
				browser = "Edge"
				break
			default:
				browser = rawBrowser
		}
		
		WebDriver driver
		
		if(browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions()
			options.addArguments("--disable-save-password-bubble")
			options.addArguments("--disable-notifications")
			options.addArguments("--disable-popup-blocking")
			options.addArguments("--incognito")
			options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"))
			driver = new ChromeDriver(options)
			
		} else if (browser.equalsIgnoreCase("Firefox")) {
            FirefoxOptions options = new FirefoxOptions()
            options.addPreference("dom.webnotifications.enabled", false)
            options.addPreference("signon.rememberSignons", false)
            options.addPreference("browser.privatebrowsing.autostart", true)
            driver = new FirefoxDriver(options)

        } else if (browser.equalsIgnoreCase("Edge")) {
            EdgeOptions options = new EdgeOptions()
            options.addArguments("--inprivate")
            driver = new EdgeDriver(options)
        } else if (browser.equalsIgnoreCase("Safari")) {
            driver = new SafariDriver()

        } else {
            throw new IllegalArgumentException("❌ Browser not supported: " + browser)
        }

		
		
		DriverFactory.changeWebDriver(driver)
	}
}
 