/**
 * @ClassPurpose This Class used for the Additional Named Insured in SOR Policy
 * @Scriptor Nisha
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 09/22/2017
 */
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

public class AdditionalNamedInsured {
	
	public static String sheetname = "AdditionalNamedInsured";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	
	/**
	 * @function Use to perform all the action in the AdditionalNamedInsured sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRAdditionalNamedInsured() throws Exception
	{

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			return false;
		}
		
	return status;
	}
	
	
	
	}
