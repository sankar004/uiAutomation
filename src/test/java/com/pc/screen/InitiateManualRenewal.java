/**
 * @ClassPurpose This Class used for the CustomerCompletion letter in SOR Policy
 * @Scriptor Raj
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/23/2017
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

public class InitiateManualRenewal {
	
	public static String sheetname = "InitiateManualRenewal";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	
	/**
	 * @function Use to perform all the action in the SORCustomerCompltr sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRInitiateManualRenewal() throws Exception
	{

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			return false;
		}
		
	return status;
	}
	public boolean DefaultEffectiveDate1() throws Exception
	{
		boolean status = false;
		String sCurrentDate = SCRCommon.ReturnCurrentDate();
		status = common.SafeAction(Common.o.getObject("edtEffDate"), sCurrentDate, "edt");
		return status;
	}
	
	public boolean DefaultEffectiveDate(String strFuncValue) throws Exception
	{
		boolean status = false;
		String[] strSplitValue = strFuncValue.split(":::");
		String strDate = null;
		switch(strSplitValue[0].toUpperCase())
		{
			case"CURRENTDATE":
				strDate = SCRCommon.ReturnCurrentDate();
				status = common.SafeAction(Common.o.getObject("edtEffDate"), strDate, "edt");
				break;
			case"INCREASEDECREASEDATE":
				strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
				status = common.SafeAction(Common.o.getObject("edtEffDate"), strDate, "edt");
				break;
		}
		return status;
	}
	
	}
