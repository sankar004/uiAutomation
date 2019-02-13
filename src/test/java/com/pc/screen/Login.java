package com.pc.screen;


import org.apache.log4j.Logger;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class Login{
	
	public static String sheetname = "Login";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	
	public Boolean SCRLogin() throws Exception
	{		
		Boolean s = common.OpenApp();
		if(!s)
		{
			logger.info("Application not Loggedin Successfully");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Application should login successfully","Application not loggedin successfully", "PASS");
			return false;
		}
		Boolean status = true;
		if(HTML.properties.getProperty("EXECUTIONAPP").contains("ODS"))
		{
			status = common.CSVFile();
		}
		status = common.ClassComponent(sheetname,Common.o);
		if(!status)
		{
			return status;
		}
		if(common.WaitUntilClickable(Common.o.getObject("eleDeskTopAction"),  Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))))
		{
			logger.info("Application Loggedin Successfully");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Application should login successfully","Application loggedin successfully", "PASS");
			status=SCRCommon.funGetUserLoggedName();
			status = true;
		}
		else
		{
			logger.info("Application not Loggedin Successfully");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Application should login successfully","Application not loggedin successfully", "FAIL");
			status = false;
		}
		return status;
	}
	
	public boolean SelectActivity(String sFuncValue) throws Exception
	{
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		status = common.ActionOnTable(Common.o.getObject("eleActivityTable"), 2, 2, sValue[1], sValue[1], "a");
		return status;
	}
	
}