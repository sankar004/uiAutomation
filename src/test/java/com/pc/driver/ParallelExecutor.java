package com.pc.driver;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.E2ETestCaseUtil;
import com.pc.utilities.HTML;
import com.pc.utilities.LocalDriverFactory;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.ManagerPhantomJS;
import com.pc.utilities.PhantomJSDriverFactory;
import com.pc.utilities.RemoteDriverFactory;

public class ParallelExecutor implements Runnable {
	
	private String strRunMode = null;
	private String strTestCaseName = null;
	private String dataSheetName = null;
	private String region = null;
	static  Logger log =Logger.getLogger(ParallelExecutor.class);
	//added E2E Framework integration start
	private boolean isE2EExecution = false;
	//added E2E Framework integration end
	
	public ParallelExecutor(String strRunMode, String strTestCaseName, String dataSheetName, String region){
		this.strRunMode = strRunMode;
		this.strTestCaseName = strTestCaseName;
		this.dataSheetName = dataSheetName;
		this.region = region;
	}
	
	@Override
	public void run()
	{
		log.info("Starting Thread Id =" +Thread.currentThread().getId()+"Executing testcase = "+strTestCaseName);
		//added E2E Framework integration start
		boolean isTestCasePass = false;
		E2ETestCaseUtil e2eTestCaseUtil = null;
		if(strTestCaseName != null && strTestCaseName.length() > 0 && strTestCaseName.contains("|")){
			isE2EExecution = true;
			e2eTestCaseUtil = new E2ETestCaseUtil();
			strTestCaseName = e2eTestCaseUtil.e2eInitE2ETestCaseExecution(strTestCaseName);
		}
		//added E2E Framework integration end
		
		WebDriver driver = null;
		String execMode = HTML.properties.getProperty("EXECUTIONMODE");
		if(HTML.properties.getProperty("TypeOfAutomation").equalsIgnoreCase("HEADLESS"))
		{
			PhantomJSDriverService service = PhantomJSDriverFactory.getInstance().createPhantomJSDriver();
			ManagerPhantomJS.getInstance().setPhantomJSDriverService(service);
		}
		if(execMode.equalsIgnoreCase(PCConstants.executionModeLocal)){
			driver = LocalDriverFactory.getInstance().createNewDriver();
		}else {
			driver = RemoteDriverFactory.getInstance().createNewDriver();
		}
		Common common = new Common();
		CommonManager.getInstance().setCommon(common);
        ManagerDriver.getInstance().setWebDriver(driver);
        log.info("Thread ID = " + Thread.currentThread().getId() + " common = "+ common);
        try {
        	isTestCasePass = common.RunTest("RunModeNo",strTestCaseName,"",region);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error while executing test case = "+strTestCaseName, e);
		}
        
      //added E2E Framework integration start
        if(isE2EExecution && isTestCasePass){ 
        	e2eTestCaseUtil.updateTestResultsintoDB();
        	e2eTestCaseUtil.upDateE2EStatus("Passed");
        	e2eTestCaseUtil = null;//Mark for Garbage collection
        } else if(isE2EExecution && !isTestCasePass ){
        	e2eTestCaseUtil.upDateE2EStatus("Failed");
        	e2eTestCaseUtil = null;//Mark for Garbage collection
        }
      //added E2E Framework integration end
        
        common = null; //Mark for garbage collection
		
	}
}
