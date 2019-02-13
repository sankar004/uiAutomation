package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class SearchNewAccount {
	
	public static String sheetname = "SearchNewAccount";
	Common common = CommonManager.getInstance().getCommon();
	static Logger logger =Logger.getLogger(sheetname);
	
	public Boolean SCRSearchNewAccount() throws Exception{
		
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			return false;
		}
		if(common.WaitUntilClickable(Common.o.getObject("edtAddress1"),  Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))))
		{
			logger.info("System displayed Create New Account Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display Create New Account Page","System displayed Create New Account Page", "PASS");
			status = true;
		}
		else
		{
			logger.info("System not displayed Create New Account Page");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display Create New Account Page","System not displayed Create New Account Page", "FAIL");
			status = false;
		}
			return status;
	}
	
}
