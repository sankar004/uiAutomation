/**
 * @ClassPurpose This Class used for the DocumentInfo usecase
 * @Scriptor Nishantni
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/22/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;

public class Preferences {
	
	public static String sheetname = "Preferences";
	Common common = CommonManager.getInstance().getCommon();
	static Logger logger =Logger.getLogger(sheetname);
	
	/**
	 * @function Use to perform all the objects/elements/functions from the  DocumentInfo sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRPreferences() throws Exception
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