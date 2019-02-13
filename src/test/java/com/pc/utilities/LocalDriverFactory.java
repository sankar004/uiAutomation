/**
 * @ClassPurpose This Class is to run the test in the local machine
 * @Scriptor Banu Pradeep
 * @ReviewedBy
 * @ModifiedBy 
 * @LastDateModified 3/17/2017
 */

package com.pc.utilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.pc.constants.PCConstants;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LocalDriverFactory 
{
	   
	   static Logger log = Logger.getLogger(LocalDriverFactory.class);
	
	   private static LocalDriverFactory instance = new LocalDriverFactory();
	
	   public static LocalDriverFactory getInstance()
	   {
	      return instance;
	   }
	   
		public  WebDriver createNewDriver(){
				   	  WebDriver driver = null;
				   	  String driverPath = HTML.properties.getProperty("driverPath");
			  		  String execution = HTML.properties.getProperty("TypeOfAutomation");
			  		  String browser = HTML.properties.getProperty("Browser");
			  		  DesiredCapabilities cap = null;
			  		 if (execution.toUpperCase().equalsIgnoreCase("HEADLESS"))
						{
			  			 	log.info("Webdriver Headless Automation started");
							/*final String phantomJsFilePath = driverPath + "\\phantomjs.exe";
							service = new PhantomJSDriverService.Builder().usingPhantomJSExecutable(new File(phantomJsFilePath)).usingAnyFreePort()
							.build();
							try {
								service.start();
							} catch (IOException e1) {
								e1.printStackTrace();
							} 
							log.info("Headless phantomjs service started");*/
							if (browser.toUpperCase().contains("CH"))
							{	
								 cap = DesiredCapabilities.chrome();
							}
							if (browser.toUpperCase().contains("FF"))
							{
								cap = DesiredCapabilities.firefox();
							}
							if (browser.toUpperCase().contains("IE"))
							{
								cap = DesiredCapabilities.internetExplorer();
							}
							try {
								driver = new RemoteWebDriver(new URL(ManagerPhantomJS.getInstance().getPhantomJSDriverService().getUrl().toString()), cap);
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
							log.info("Headless '" + browser.toUpperCase() + "' DesiredCapabilities initiated");
							log.info("Headless '" + browser.toUpperCase() + "' driver started");	
						}
			  		if (execution.toUpperCase().equalsIgnoreCase("HEAD"))
					{
			  			log.info("Webdriver with Head Automation started");
						if (browser.toUpperCase().contains("CH"))
						{
							//System.setProperty("webdriver.chrome.driver", driverPath + "\\chromedriver.exe");
							 WebDriverManager.chromedriver().setup();
							ChromeOptions options = new ChromeOptions();
							options.addArguments("--headless");
							options.setExperimentalOption("useAutomationExtension", false);
							options.addArguments("no-sandbox");
							options.addArguments("start-maximized");
							options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
							if(HTML.properties.getProperty("Forms").equalsIgnoreCase("YES"))
							{
								createFormDirectory();
								HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
							    chromePrefs.put("profile.default_content_settings.popups", 0);
							    chromePrefs.put("download.default_directory", PCThreadCache.getInstance().getProperty(PCConstants.FormsFolderPath));
							    options.setExperimentalOption("prefs", chromePrefs);
							}
							driver = new ChromeDriver(options);
						}
						if (browser.toUpperCase().contains("FF"))
						{
							driver = new FirefoxDriver();
							log.info("firefox  DesiredCapabilities initiated");	
						}
						if (browser.toUpperCase().contains("IE"))
						{
							//System.setProperty("webdriver.ie.driver", driverPath + "\\IEDriverServer.exe");
							WebDriverManager.iedriver().setup();
							driver = new InternetExplorerDriver();
						}
						log.info("'" + browser.toUpperCase() + "' driver started");
											 
					}
			  		return driver;
			   }
		
		 public static Boolean createFormDirectory()
		 {
			 Date d = new Date();          
			 SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
			 String date = dateFormat.format(d);	
			 String dirAddress = HTML.properties.getProperty("FormsDownloadPath")+"\\"+ date+"_"+ d.getHours() +d.getMinutes()+d.getSeconds() + Thread.currentThread().getId();
			 PCThreadCache.getInstance().setProperty(PCConstants.FormsFolderPath, dirAddress);
			 File dir = new File(dirAddress);
	         if(!dir.exists()) {
	      	   dir.mkdir();
	         }
	         return true;
		 }
		 

}
