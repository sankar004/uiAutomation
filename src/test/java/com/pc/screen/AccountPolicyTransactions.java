package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;

public class AccountPolicyTransactions {
	
	public static String sheetname = "AccountPolicyTransactions";
	static Logger logger =Logger.getLogger(sheetname);
	public static String AccountNumber;
	public static String AccountName;
	Common common = CommonManager.getInstance().getCommon();
	public Boolean SCRAccountPolicyTransactions() throws Exception
	{
					Boolean status = true;
					status = common.ClassComponent(sheetname, Common.o);
					if(!status)
					{
						status = false;
					}
					
					return status;
	}
	
	/**
	 * @function This function use to select the specified transaction
	 * @param value(submission number)
	 * @return status
	 * @throws Exception
	 */
	public boolean SelectTransaction(String value) throws Exception 
	{
		boolean status = false;
		String sValue = value;
		try
		{
			status = common.ActionOnTable(Common.o.getObject("eleAPTSearchResultTable"), 3,3, sValue,sValue,"a");
			
			if (!status)
			{
				status = false;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @function This function verify policy transaction
	 * @param value (account name)
	 * @return status
	 * @throws Exception
	 */
	public boolean VerifyPolicyTransactions(String sFuncValue) throws Exception 
	{
	    boolean status = false;
		String sApplnValue = "";
//		String sValue = CreateAccount.AccountName;
		try
		{
			sApplnValue = common.ReadElement(Common.o.getObject("eleAPTPolicyTransactionAccountName"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
			
			if (sApplnValue.equals(sFuncValue))
			{
				status = true;
			}
			else
			{
				status = false;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	
	
}