/**
 * @ClassPurpose This Class used for the AccountParticipants usecases
 * @Scriptor Nishantni
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/22/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;	
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.ManagerDriver;

public class AccountParticipants {
	
	public static String sheetname = "AccountParticipants";
	Common common = CommonManager.getInstance().getCommon();
	static Logger logger =Logger.getLogger(sheetname);
	
	/**
	 * @function Use to perform all the objects/elements/functions from the  AccountParticipant sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRAccountParticipants() throws Exception
	{
			Boolean status = true;
			status = common.ClassComponent(sheetname,Common.o);
			if(!status)
			{
				return status;
			}
			return status;
	}	
}