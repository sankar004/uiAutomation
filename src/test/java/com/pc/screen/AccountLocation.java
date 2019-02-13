package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;

public class AccountLocation {

	public static String sheetname = "AccountLocation";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();

	public Boolean SCRAccountLocation() throws Exception{
		
		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			status = false;
		}
		
		return status;
}
	
	public boolean actionOnLocationTable(String strValue)throws Exception{
		boolean status = false;
		
		status = common.ActionOnTable(Common.o.getObject("eleLocationTable"), 6, 0, strValue, "", "img");
				
		return status;
	}
	
	
	
}
