package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;

public class Users {
	public static String sheetname = "Users";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	
	/**
	 * @function Use to perform all the objects/elements/functions from the Users excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRUsers() throws Exception{

					Boolean status = true;
					status = common.ClassComponent(sheetname, Common.o);
					if(!status)
					{
						status = false;
					}
					return status;
	}	
	
	public Boolean fillUserName()
	{
		Boolean blnStatus = true;
		try 
		{
			String strLoginUserName = System.getProperty("user.name");
			blnStatus = common.SafeAction(Common.o.getObject("edtUserSearch_UserName"), strLoginUserName, "edtUserSearch_UserName");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blnStatus;
	}
	
	
	public Boolean skillSegementTrack(String strFuncValue)
	{
		Boolean blnStatus = true;
		blnStatus = SCRCommon.skillAndTrack(strFuncValue);
		return blnStatus;
	}
	

	 public boolean selectSearchedUser(String sFuncValue) throws Exception
	    {
	    	boolean status = false;
	    	status = common.ActionOnTable(Common.o.getObject("eleSearchUsertbl"), 1, 1, sFuncValue, "a");
	    	return status;
	    }
	 
	 public boolean selectGroupName(String sFuncValue) throws Exception
	    {
	    	boolean status = false;
	    	status = common.ActionOnTable(Common.o.getObject("eleAccessGrouptbl"), 1, 1, sFuncValue, "a");
	    	return status;
	    }
	 
}