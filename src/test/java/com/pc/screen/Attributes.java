package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class Attributes {
	
	public static String sheetname = "Attributes";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	
	public Boolean SCRAttributes() throws Exception
	{

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			return false;
		}
		
	return status;
	}
	
	public boolean selectAttributeName(String sFuncValue) throws Exception
    {
    	boolean status = false;
    	status = common.ActionOnTable(Common.o.getObject("eleAttributestbl"), 1, 1, sFuncValue, "a");
    	return status;
    }
	
	
	public boolean selectAttributeCheckbox(String sFuncValue) throws Exception
    {
    	boolean status = false;
    	status = common.ActionOnTable(Common.o.getObject("eleAttributestbl"), 1, 0, sFuncValue, "div");
    	return status;
    }
	
	}

