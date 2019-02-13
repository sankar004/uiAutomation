/**
 * @ClassPurpose This Class used for the CustomerCompletion letter in SOR Policy
 * @Scriptor Raj
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/23/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.ManagerDriver;

public class PreRenewalDirection {
	
	public static String sheetname = "PreRenewalDirection";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	
	/**
	 * @function Use to perform all the action in the SORCustomerCompltr sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRPreRenewalDirection() throws Exception
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
