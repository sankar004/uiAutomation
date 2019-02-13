/**
 * @ClassPurpose This Class used for the Create Accounts
 * @Scriptor Sojan David
 * @ReviewedBy
 * @ModifiedBy 
 * @LastDateModified 3/24/2017
 */
package com.pc.screen;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.FlatFile;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class CreateAccount {
	
	public static String sheetname = "CreateAccount";
	static Logger logger =Logger.getLogger(sheetname);
	public String accountNumber;
	public String accountName;
	Common common = CommonManager.getInstance().getCommon();
	
	/**
	 * @function Use to perform all the objects/elements/functions from the Create Account excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRCreateAccount() throws Exception{

					Boolean status = true;
					status = common.ClassComponent(sheetname, Common.o);
					if(!status)
					{
						status = false;
					}
					return status;
	}
	
	/**
	 * @function Use to update the account number
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean UpdateAccountNumber() throws Exception{
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
		try{
			Boolean status = true;
			if(common.WaitUntilClickable(Common.o.getObject("eleCreateNewAccountNumber"),  Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))))
			{
				XlsxReader sXL = XlsxReader.getInstance();
				accountNumber = common.ReadElement(Common.o.getObject("eleCreateNewAccountNumber"), 30);
				PCThreadCache.getInstance().setProperty(PCConstants.CACHE_ACCOUNT_NUMBER, accountNumber);
				updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
				status = sXL.executeUpdateQuery(PCConstants.SHEET_CREATEACCOUNT, updateColumnNameValues, whereConstraint);
				if(HTML.properties.getProperty("EXECUTIONAPP").contains("ODS"))
				{
					FlatFile sReadWrite = FlatFile.getInstance();
					SCRCommon scrCommon = new SCRCommon();
					String FlatFileName = scrCommon.FlatFileName();
					status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "AccountNumber", accountNumber, "OUTPUT", FlatFileName);
				}
				
				/*updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.PolicyNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
				status = sXL.executeUpdateQuery(PCConstants.SHEET_POLICYMOVE,  updateColumnNameValues,  whereConstraint);
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint);*/
				
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update CreateAccount Set AccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				logger.info("System displayed Account Summary Page with Account Number: " + accountNumber);  
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display Account Summary Page with Account Number","System displayed Account Summary Page with Account Number: '" + accountNumber + "'", "PASS");
				status = true;
			}
			else
			{
				logger.info("System not displayed Account Summary Page");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display Account Summary Page with Account Number","System not displayed Account Summary Page", "FAIL");
				status = false;
			}
			return status;
		}finally
		{
			updateColumnNameValues = null;
			whereConstraint = null;
		}
		
	}
	
	/**
	 * @function to check whether we get update or standardize button for account creation
	 * @return true/false
	 */
	public boolean update()
	{
		boolean status = false;
		int sElementSize = common.ElementSize(Common.o.getObject("eleUpdate"));
		if(sElementSize == 1)
		{
			try {
				status = common.SafeAction(Common.o.getObject("eleUpdate"), "eleUpdate", "eleUpdate");
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
}