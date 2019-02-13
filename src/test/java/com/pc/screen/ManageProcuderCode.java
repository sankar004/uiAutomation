package com.pc.screen;

import org.apache.log4j.Logger;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;

public class ManageProcuderCode {
	
	public static String sheetname = "ManageProcuderCode";
	Common common = CommonManager.getInstance().getCommon();
	static Logger logger =Logger.getLogger(sheetname);
	public static String AccountNumber;

	public Boolean SCRManageProcuderCode() throws Exception{

					Boolean status = true;
					status = common.ClassComponent(sheetname, Common.o);
					if(!status)
					{
						status = false;
					}
					return status;
	
	}
}