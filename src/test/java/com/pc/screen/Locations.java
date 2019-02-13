package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;

public class Locations {
	
	public static String sheetname = "Locations";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();

		public Boolean SCRLocations() throws Exception{
			
			Boolean status = true;
			status = common.ClassComponent(sheetname, Common.o);
			if(!status)
			{
				status = false;
			}
			return status;		
	}
		
		/**
		 * @function to check whether we get update or standardize button for account creation
		 * @return true/false
		 */
		public boolean UpdateLocation()
		{
			boolean status = false;
			int sElementSize = common.ElementSize(Common.o.getObject("eleLocationOK"));
			if(sElementSize == 1)
			{
				try {
					status = common.SafeAction(Common.o.getObject("eleLocationOK"), "eleLocationOK", "eleLocationOK");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
			{
				try {
					status = common.SafeAction(Common.o.getObject("eleOverRide"), "eleOverRide", "eleOverRide");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return status;
		}
		
		/**
         * @function use to select the check box of the particular location from Locations screen
         * @param  sReadString
         * @return true/false
         * @throws Exception
         */
         
         public boolean SelectLocations(String sReadString) throws Exception
         {
                boolean status = false;
                status = common.ActionOnTable(Common.o.getObject("eleLocationsTbl"), 6, 0, sReadString, "div");
                return status;
         }
         
}
