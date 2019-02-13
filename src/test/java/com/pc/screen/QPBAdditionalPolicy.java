package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;

public class QPBAdditionalPolicy {
	
	public static String sheetname = "QPBAdditionalPolicy";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	
	public Boolean SCRQPBAdditionalPolicy() throws Exception{

		Boolean status = true;
		status = common.ClassComponent(sheetname,Common.o);
		if(!status)
		{
			status = false;
		}	
		return status;
	}
	
	public boolean PolicyEffectiveDate() throws Exception
	{
		boolean status = false;
		status = common.SafeAction(Common.o.getObject("lstQPBPolicyEffectiveDate"),SCRCommon.ReturnCurrentDate(), "lst");
		return status;
	}
}