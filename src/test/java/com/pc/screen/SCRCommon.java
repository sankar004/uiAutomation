package com.pc.screen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.DBConnectionManager;
import com.pc.utilities.E2ETestCaseUtil;
import com.pc.utilities.FlatFile;
import com.pc.utilities.HTML;
import com.pc.utilities.IconReader;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class SCRCommon {

	public static String sheetname = "SCRCommon";
	static Logger logger =Logger.getLogger(sheetname);
	public WebDriver driver = ManagerDriver.getInstance().getWebDriver();
//	public static String Path;

	public boolean Wait() throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		boolean status = common.WaitForElementExist(Common.o.getObject("eleAddSuccessMsg"), 20);		
		return status;
	}
	
	/**
	 * @function it helps to handle the create account new button for dynamic	 
	 * @return 
	 * @throws Exception 
	 */
	public boolean CheckPopUpAlert() throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		boolean status = false;
		boolean popUpExist = common.ElementSize(Common.o.getObject("eleAlertPopUp"))!=0;
		if(popUpExist == true)
		{
			common.SafeAction(Common.o.getObject("eleAlertPopUp"), "eleAlertPopUp", "ele");
			status = true;
		}
		return status;
	}
	
	/**
	 * @function This function is used to fill the data's in all the webtable
	 * @param id, sRow, sCol
	 * @param strReadString
	 * @return status
	 * @throws Exception
	 */
	/*public static boolean DataWebTable(By id, int sRow, int sCol, String strReadString, String ActionClick) throws Exception 
	{	 
		Common common = CommonManager.getInstance().getCommon();
		boolean SearchString= false;
		boolean Status = false;
		Actions objActions = new Actions(ManagerDriver.getInstance().getWebDriver());
		WebElement  mytable = common.returnObject(id);
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
		sRow--;
		
		List<WebElement> Columns_row = rows_table.get(sRow).findElements(By.tagName("td"));
		String celtext = Columns_row.get(sCol).getText();
		if (celtext.contains(strReadString))
		{
  			  SearchString = true;
  			  List<WebElement> CellElements = Columns_row.get(sCol).findElements(By.tagName("div"));
  	          for(WebElement element: CellElements)
  	          {
  	        		  	switch(ActionClick)
  	        		  	{
  	        		  		case "single":
  	        		  			element.click();
  	        		  			Status = true;
  	        		  		break;	
  	        		  		case "double":
  	        		  			objActions.doubleClick(element).build().perform();
  	        		  			Status = true;
  	        		  		break;
  	        		  	}
  	        		  	break;
  	          }
  	     }
		 if(SearchString)
		 {
			  	logger.info("Search String available in the table. '" + strReadString + "'");  
			  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and search string is  '" + strReadString + "'", "PASS");
		 }
		 else
		 {
			  	logger.info("Search String not available in the table. '" + strReadString + "'");  
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System not searched string in table and search string is  '" + strReadString + "'", "FAIL");
				Status = false;
		 }
		 return Status;
	}*/
	
	public static boolean DataWebTable(By id, int sRow, int sCol, String strReadString, String ActionClick , String sTagName) throws Exception 
    {      
           Common common = CommonManager.getInstance().getCommon();
           boolean SearchString= false;
           boolean Status = false;
           Actions objActions = new Actions(ManagerDriver.getInstance().getWebDriver());
           WebElement  mytable = common.returnObject(id);
           List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
           sRow--;
           List<WebElement> Columns_row = rows_table.get(sRow).findElements(By.tagName("td"));
           String celtext = Columns_row.get(sCol).getText();
           if (celtext.contains(strReadString))
           {
                SearchString = true;
                List<WebElement> CellElements = Columns_row.get(sCol).findElements(By.tagName(sTagName));
	              for(WebElement element: CellElements)
	              {
                            switch(ActionClick)
                            {
                                   case "single":
                                          element.click();
                                          Status = true;
                                   break; 
                                   case "double":
                                          objActions.doubleClick(element).build().perform();
                                          Status = true;
                                   break;
                            }
                            break;
	              }
         }
           if(SearchString)
           {
                         logger.info("Search String available in the table. '" + strReadString + "'");  
                         HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and search string is  '" + strReadString + "'", "PASS");
           }
           else
           {
                         logger.info("Search String not available in the table. '" + strReadString + "'");  
                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System not searched string in table and search string is  '" + strReadString + "'", "FAIL");
                        Status = false;
           }
           return Status;
    }

	
	/**
	 * @function This function use to fill the dependent test data in corresponding component
	 * @param sValue
	 * @return
	 * @throws Exception
	 */
	public boolean TestDataFill(String sValue) throws Exception
	{
		System.out.println("SCRCommon.testDataFill log start = " +Runtime.getRuntime().totalMemory());
		CommonManager.getInstance().getCommon();
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
		String accountNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER);
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		String policyNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER);
		String policyNumberSearch = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH);
		String submissionNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_SUBMISSION_NUMBER);
		String PolicyeffDate = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_EFF_DATE);
		String PolicyexpDate = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_EXP_DATE);
		String strTCID =  PCThreadCache.getInstance().getProperty("TCID");
		System.out.println(Runtime.getRuntime().freeMemory()); 
		boolean status = false;
		String[] sfunValue = sValue.split(":::");
//		int funValueSize = sfunValue.length;
		XlsxReader sXL = XlsxReader.getInstance();
//		int rowcount = sXL.getRowCount(sfunValue[1]);
		int rowcount = sXL.getRowCount(sfunValue[0]);
		for(int i=1;i<=rowcount;i++)
		{
//			String value = sXL.getCellData(sfunValue[1], 0, i);
			String value = sXL.getCellData(sfunValue[0], 0, i);
//			String sheetName = sXL.getCellData("TestData", 1, i);
			if(!value.isEmpty())
			{
//					if(PCThreadCache.getInstance().getProperty("TCID").equals(value))
					if(strTCID.equals(value))
					{		
							switch (sfunValue[1].toUpperCase())
							{
								case "ACCOUNTNUMBER":
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint );
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set AccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchAccount Set edtaccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");					
									status = true;
									break;
								case "POLICYNUMBER":
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set AccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchAccount Set edtaccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");	
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set PolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									status = true;
									break;
								case "SUBMISSIONNUMBER":
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
																		
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									

									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.SubmissionNumber, submissionNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set AccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchAccount Set edtaccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");	
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set PolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set SubmissionNumber = '"+Payment.SubmissionNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									status = true;
									break;	
								case "POLICYNUMBERONLY":	
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
									
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set PolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									status = true;
									break;
								case "CLAPOLICYNUMBERONLY":
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.CLAPolicyNumber, policyNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
									
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set CLAPolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									status = true;
									break;
								case "ACCOUNTCLAPOLICYNUMBER":  
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.CLAPolicyNumber, policyNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
									
									updateColumnNameValues.clear();
									whereConstraint.clear();
									updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
									whereConstraint.put(PCConstants.ID, tcID);
									status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint );
									
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set AccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set CLAPolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchAccount Set edtaccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
									status = true;
									break;
									
								case "POLICYEFFDATE": 
                                    updateColumnNameValues.clear();
                                    whereConstraint.clear();
                                    updateColumnNameValues.put(PCConstants.CLAPolicyEffDate,PolicyeffDate);
                                    whereConstraint.put(PCConstants.ID, tcID);
                                    status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );

                                    updateColumnNameValues.clear();
                                    whereConstraint.clear();
                                    updateColumnNameValues.put(PCConstants.CLAPolicyExpDate, PolicyexpDate);
                                    whereConstraint.put(PCConstants.ID, tcID);
                                    status = sXL.executeUpdateQuery(sfunValue[2],  updateColumnNameValues,  whereConstraint );
                                    
                                    //status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set PolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
                                    //status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
                                    status = true;
                                    break;	
							}	
					}
			}
		}	
//		System.out.println("SCRCommon.testDataFill log End = " +Runtime.getRuntime().totalMemory());
//		System.out.println(Runtime.getRuntime().freeMemory()); 
		return status;
	}
	
	/**
	 * @function This function use to fill the dependent test data in corresponding component
	 * @param sValue
	 * @return
	 * @throws Exception
	 */
	public static boolean testData(String sValue) throws Exception
	{
		System.out.println("SCRCommon.testDataFill log start = " +Runtime.getRuntime().totalMemory());
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
		String accountNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER);
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		String policyNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER);
		String policyNumberSearch = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH);
		String submissionNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_SUBMISSION_NUMBER);
		String PolicyeffDate = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_EFF_DATE);
		String PolicyexpDate = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_POLICY_EXP_DATE);
		System.out.println(Runtime.getRuntime().freeMemory()); 
		boolean status = false;
		String[] sfunValue = sValue.split(":::");
		XlsxReader sXL = XlsxReader.getInstance();
		switch (sfunValue[0].toUpperCase())
		{
			case "ACCOUNTNUMBER":
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint );
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set AccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchAccount Set edtaccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");					
				status = true;
				break;
			case "POLICYNUMBER":
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set AccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchAccount Set edtaccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");	
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set PolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				status = true;
				break;
				
			case "SUBMISSIONNUMBER":
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
													
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				

				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.SubmissionNumber, submissionNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set AccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchAccount Set edtaccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");	
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set PolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set SubmissionNumber = '"+Payment.SubmissionNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				status = true;
				break;	
			case "POLICYNUMBERONLY":	
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
				
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set PolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				status = true;
				break;
			case "CLAPOLICYNUMBERONLY":
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.CLAPolicyNumber, policyNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
				
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set CLAPolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				status = true;
				break;
			case "ACCOUNTCLAPOLICYNUMBER":  
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.CLAPolicyNumber, policyNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtPolicyNumber, policyNumberSearch);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraint );
				
				updateColumnNameValues.clear();
				whereConstraint.clear();
				updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
				whereConstraint.put(PCConstants.ID, tcID);
				status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint );
				
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set AccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set CLAPolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchAccount Set edtaccountNumber = '"+accountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
				status = true;
				break;
				
			case "POLICYEFFDATE": 
                updateColumnNameValues.clear();
                whereConstraint.clear();
                updateColumnNameValues.put(PCConstants.CLAPolicyEffDate,PolicyeffDate);
                whereConstraint.put(PCConstants.ID, tcID);
                status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );

                updateColumnNameValues.clear();
                whereConstraint.clear();
                updateColumnNameValues.put(PCConstants.CLAPolicyExpDate, PolicyexpDate);
                whereConstraint.put(PCConstants.ID, tcID);
                status = sXL.executeUpdateQuery(sfunValue[1],  updateColumnNameValues,  whereConstraint );
                
                //status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sfunValue[2]+" Set PolicyNumber = '"+Payment.PolicyNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
                //status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchPolicy Set edtPolicyNumber = '"+Payment.PolicyNumberSearch+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
                status = true;
                break;	
		}	
//		System.out.println("SCRCommon.testDataFill log End = " +Runtime.getRuntime().totalMemory());
//		System.out.println(Runtime.getRuntime().freeMemory()); 
		return status;
	}
	
	/**
     * @function This will use to take the current date
     * @return Current Effective Date
     */
     public static String ReturnCurrentDate() 
     {
            String status = null;
            try
            {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = sdf.format(date);
                status = strDate;
            }
            catch(Exception e)
            {
                  e.printStackTrace();   
                  logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
            }
            return status;
     }

 	 /**
      * @function This will use to take the current date
      * @return Current Effective Date
      */
      public static boolean ReturnCurrentDate(String sFuncValue) 
      {
    	  	Common common = CommonManager.getInstance().getCommon();
             boolean status = false;
             String edt = "edt" + sFuncValue;
             try
             {
                 Date date = new Date();
                 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                 String strDate = sdf.format(date);
                 status = common.SafeAction(Common.o.getObject(edt), strDate, "edt");
             }
             catch(Exception e)
             {
                   e.printStackTrace();      
                   logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
             }
             return status;
      }
      
      /**
       * @function This will use to take the current date
       * @return Current Effective Date
       */
       public static boolean ReturnOneYearFutureDate(String sFuncValue) 
       {
              boolean status = false;
              Common common = CommonManager.getInstance().getCommon();
              String edt = "edt" + sFuncValue;
              try
              {
            	  Calendar Date = Calendar.getInstance();  
                  Date.add(Calendar.YEAR,1);
                  String End_Date =  (Date.get(Calendar.MONTH)+1)+"/"+(Date.get(Calendar.DATE)+"/"+(Date.get(Calendar.YEAR)));
                  status = common.SafeAction(Common.o.getObject(edt), End_Date, "edt");
              }
              catch(Exception e)
              {
                    e.printStackTrace();  
                    logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
              }
              return status;
       }

     /**
      * @function used to count the number of row count in the table
      * @param locator
      * @return sRowCount(row count)
      */
     public static int TableRowCount(By locator)
     {
    	Common common = CommonManager.getInstance().getCommon();
    	WebElement  table = common.returnObject(locator);
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
        int sRowCount = rows_table.size();
		return sRowCount; 
     }
     
     /**
      * @function use to return the 1 year date from current date
      * @return 1 year from Effective Date
      */
      public static String ReturnOneYearFromDate() 
      {
             String status = null;
             try
             {
                 Calendar Date = Calendar.getInstance();  
                 Date.add(Calendar.YEAR,1);
                 String End_Date =  (Date.get(Calendar.MONTH)+1)+"/"+(Date.get(Calendar.DATE)+"/"+(Date.get(Calendar.YEAR)));
                 status = End_Date;
             }
             catch(Exception e)
             {
                   e.printStackTrace();  
                   logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
             }
             return status;
      }
      
	  public static boolean CommonWait(String sFuncValue) throws Exception
	  {
		  boolean status = true;
		  Common common = CommonManager.getInstance().getCommon();
		  String[] sValue = sFuncValue.split(":::");
		  common.WaitForPageToBeReady();
		  status = common.WaitUntilClickable(Common.o.getObject(""+sValue[0]+""),  Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
		  if(status == false)
		  {
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Object "+sValue[0]+" is not ready to click","Object "+sValue[0]+" should ready to click", "FAIL");
		  }
		  return status;	
	  }
	  
	  /**
       * @function used to wait for specified time before executing ODS Component
       * @return True
       */
     public static Boolean ODSWait(int odswait1) throws Exception
     {
            Thread.sleep(odswait1);
            return true;
     }
         
	  /**
       * @function used to wait for specified time before executing MDM Component
       * @return True
       */
	  public static Boolean MDMWait() throws Exception
	  {
		  Thread.sleep(Integer.valueOf(HTML.properties.getProperty("MDMWAIT")));              
		  return true;
	  }
	  
	 /**
	   * @Function to verify whether banner message is displayed or not
	   * @param strValue
	   * @return
	   * @throws Exception
	  */
     public static boolean VerifyBannerMsg(String strValue) throws Exception
     {
    	 	Common common = CommonManager.getInstance().getCommon();
            String strObjName = "";
            String[] arrValue = strValue.split(":::");
            switch (arrValue[0].toUpperCase())
            {
                   case "SUBMISSIONMANAGER" :
                         strObjName = "eleASMBannerMessage";
                         break;    
                   case "LOCATIONS":
                	   strObjName = "eleALBannerMessage";
                	   break;
                   case "SEARCHACC":
                	   strObjName = "eleSearchErrorMessage";
                	   break;
                   case "ACCOUNTRECEPIENT":
                	   strObjName = "eleARErrortext";
                  break;
                  
                   case "ACCPOLICYTRANSACTION" :
                	   strObjName = "eleAPTBannerMessage";
                   break;
            
             }
            boolean status = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject(strObjName)).size()!=0;
            String strErrorMsg = common.ReadElement(Common.o.getObject(strObjName), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
            if(status == true)
            {
                   
                    if (strErrorMsg.toUpperCase().equalsIgnoreCase(arrValue[1]))
                   {
                         HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether banner message is displayed as expected","Expected banner message '"+arrValue[1]+"'  is matching with Actual : '"+strErrorMsg+"'", "PASS");
                         status = true;                      
                    }
                   else
                   {
                         HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether banner message is displayed as expected","Expected banner message '"+arrValue[1]+"' is not matching with Actual : '"+strErrorMsg+"'", "FAIL");
                         status = false;
                   }
                   }
            else{
                   HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify whether banner message is displayed or not","Banner message is not displayed : '"+strErrorMsg+"'", "FAIL");
                   status = false;                   
            }
            
             return status;
     }
     
     
     /**
      * @function This function use to fill the dependent test data in corresponding component
      * @param sValue
      * @return
      * @throws IOException 
       * @throws Exception
      */
      public static boolean VerifyErrorMessages(String sValue) throws IOException
      {
            boolean Status = true;
            String expectedText = null;
            String[] sfunVal = sValue.split(":::::");
            String[] sfunValue = sfunVal[1].split(":::");
            String ele = "ele" + sfunVal[0];
            List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject(ele));
	         for(WebElement element: CellElements)
	         {
	             boolean matchStatus = false;
	             String errorText = element.getText();
	             for(String expText: sfunValue)
	                {
	                    expectedText = expText.toString();
	                    if (errorText.contentEquals(expectedText))
	                    {
	                           matchStatus = true;
	                           logger.info("Expected error text is matching with actual text '" + expectedText + "'");  
	                           HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'","Expected error text is matching with actual text '" + expectedText + "'", "PASS");
	                           break;
	                    }
	                }
	             if(matchStatus == false)
	             {
	                    logger.info("Expected error text is not matching with actual text '" + expectedText + "'");  
	                    HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected error text should matching with actual text '" + expectedText + "'","Expected error text is not matching with actual text '" + expectedText + "'", "FAIL");
	                     //Status = false;
	             }
	         }
           return Status;
       }
      
      /**
       * @function Verify Page Assert
       * @param strPageName
       * @return boolean
       * @throws Exception
       */
      public static Boolean PageVerify(String strPageName) throws Exception
      {    
    	  Common common = CommonManager.getInstance().getCommon();
          String ele = "ele" + strPageName;
          Boolean status = true;
          if(common.WaitUntilClickable(Common.o.getObject(ele),  Integer.valueOf(HTML.properties.getProperty("LONGESTWAIT"))))
          {
                logger.info("System displayed '" + strPageName + "'");  
                HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + strPageName + "'","System displayed '" + strPageName + "'", "PASS");
                status = true;
          }
          else
          {
                logger.info("System not displayed '" + strPageName + "'");  
                HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + strPageName + "'","System not displayed '" + strPageName + "'", "FAIL");
                status = false;
          }
          return status;
      }
   

	  /**
	   * @function Verify Exist or not
	   * @param strPageName
	   * @return boolean
	   * @throws Exception
	   */
	   public static Boolean VerifyElements(String strPageName) throws Exception{
		   Common common = CommonManager.getInstance().getCommon();
	      boolean Status = true;
	      String[] sfunVal = strPageName.split(":::::");
	      for(String element: sfunVal)
	      {
	                   String[] sfunValue = element.split(":::");
	                   String ele = "ele" + sfunValue[1];
	                   switch (sfunValue[0].toUpperCase())
	                   {
	                          case "VISIBLETRUE":
	                                 boolean popUpExist = common.ElementSize(Common.o.getObject(ele))!=0;
	                                 if(popUpExist == true)
	                                 {
	                                 //if(Common.WaitUntilClickable(Common.o.getObject(ele),  Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))))
	                                 //{
	                                        logger.info("System displayed '" + strPageName + "'");  
	                                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + strPageName + "'","System displayed '" + strPageName + "'", "PASS");
	                                        //Status = true;
	                                 }
	                                 else
	                                 {
	                                        logger.info("System not displayed '" + strPageName + "'");  
	                                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + strPageName + "'","System not displayed '" + strPageName + "'", "FAIL");
	                                        //Status = false;
	                                 }
	                          break;
	                          case "VISIBLEFALSE":
	                                 boolean popUpExist1 = common.ElementSize(Common.o.getObject(ele))!=0;
	                                 if(popUpExist1 == true)
	                                 {
	                                        logger.info("System not displayed '" + strPageName + "'");  
	                                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should not display '" + strPageName + "'","System displayed '" + strPageName + "'", "FAIL");
	                                        //Status = true;
	                                 }
	                                 else
	                                 {
	                                        logger.info("System displayed '" + strPageName + "'");  
	                                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should not display '" + strPageName + "'","System not displayed '" + strPageName + "'", "PASS");
	                                        //Status = false;
	                                 }
	                   }
	      }
	      return Status;
	   }
	   
	   /**
	   * @function Verify Exist or not
	   * @param strPageName
	   * @return boolean
	   * @throws Exception
	   */
	   public static Boolean VerifyElement(String strPageName) throws Exception{
		   Common common = CommonManager.getInstance().getCommon();
	      boolean Status = true;
	      String[] sfunVal = strPageName.split(":::::");
	      for(String element: sfunVal)
	      {
	                   String[] sfunValue = element.split(":::");
//		                   String ele = "ele" + sfunValue[1];
	                   switch (sfunValue[0].toUpperCase())
	                   {
	                          case "VISIBLETRUE":
	                                 boolean popUpExist = common.ElementSize(Common.o.getObject(sfunValue[1]))!=0;
	                                 if(popUpExist == true)
	                                 {
	                                 //if(Common.WaitUntilClickable(Common.o.getObject(ele),  Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT"))))
	                                 //{
	                                        logger.info("System displayed '" + strPageName + "'");  
	                                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + strPageName + "'","System displayed '" + strPageName + "'", "PASS");
	                                        //Status = true;
	                                 }
	                                 else
	                                 {
	                                        logger.info("System not displayed '" + strPageName + "'");  
	                                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + strPageName + "'","System not displayed '" + strPageName + "'", "FAIL");
	                                        //Status = false;
	                                 }
	                          break;
	                          case "VISIBLEFALSE":
	                                 boolean popUpExist1 = common.ElementSize(Common.o.getObject(sfunValue[1]))!=0;
	                                 if(popUpExist1 == true)
	                                 {
	                                        logger.info("System not displayed '" + strPageName + "'");  
	                                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should not display '" + strPageName + "'","System displayed '" + strPageName + "'", "FAIL");
	                                        //Status = true;
	                                 }
	                                 else
	                                 {
	                                        logger.info("System displayed '" + strPageName + "'");  
	                                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should not display '" + strPageName + "'","System not displayed '" + strPageName + "'", "PASS");
	                                        //Status = false;
	                                 }
	                   }
	      }
	      return Status;
	   }
   
   /**
    * @function Use to click the 
    * @param strPageName
    * @return
    * @throws Exception
    */
   public Boolean PageLeftPane1(String strPageName) throws Exception
   {    
	   Common common = CommonManager.getInstance().getCommon();
       String strReadString="";
       String actionObjetName="";
       String LeftPanelActionsLinksLevel = "";

       boolean SearchString=false;
       boolean ActionObject=false;
         
       String LeftPanelLinks="";
       String LeftPanelActions = "";
       int readCol = 0;
       boolean Status = true;
       String[] sfunVal = strPageName.split("::");
       
       switch (sfunVal[0].toUpperCase())
       {
              case "DESKTOP":
                    LeftPanelLinks = "eleDeskTopLeftPanelLinks";
                    LeftPanelActions = "eleDeskTopLeftPanelActions";
              break;
              case "ACCOUNT":
                    LeftPanelLinks = "eleAccountLeftPanelLinks";
                    LeftPanelActions = "eleAccountLeftPanelActions";
              break;
              case "POLICY":
                    LeftPanelLinks = "elePolicyLeftPanelLinks";
                    LeftPanelActions = "elePolicyLeftPanelActions";
              break;
       }
       
       String[] sLnkAct = sfunVal[1].split("--");
       switch (sLnkAct[0].toUpperCase())
       {
              case "LINKS":
                    WebElement  mytable = common.returnObject(Common.o.getObject(LeftPanelLinks));
                    List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
                      for(int i = 0; i <= allrows.size()-1; i++)
                      {
                             List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
                             String readText = Cells.get(readCol).getText();
                             strReadString = sLnkAct[1];
                             actionObjetName = sLnkAct[1];
                             if (readText.contains(sLnkAct[1]))
                             {
                                    SearchString = true;
                                    List<WebElement> CellElements = Cells.get(readCol).findElements(By.tagName("div"));
                              for(WebElement element: CellElements)
                              {
                                    String objName = element.getText();
                                    if(objName.contains(sLnkAct[1]))
                                    {
                                                Status = true;
                                                ActionObject = true;
                                                element.click();
                                                break;
                                    }
                              }
                     	}
                             if(ActionObject == true)
                                  {
                                         break;
                                  }
                      }
                      if(SearchString)
                      {
                                  logger.info("Search String available in the table. '" + strReadString + "'");  
                                  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "PASS");
                                  if(ActionObject)
                                    {
                                                logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
                                                  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "PASS");
                                                Status = true;                                    
                                    }
                                    else
                                    {
                                                logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
                                                  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "FAIL");
                                                Status = false;
                                    }
                      }
                      else
                      {
                                  logger.info("Search String not available in the table. '" + strReadString + "'");  
                                  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "FAIL");
                                  Status = false;
                      }
              break;
              case "ACTIONS":
                    Status = common.SafeAction(Common.o.getObject(LeftPanelActions), "", LeftPanelActions);
                    String[] sActs = sLnkAct[1].split("&&");
                    for(String str: sActs)
                 {
                           LeftPanelActionsLinksLevel = "x-menu-item-text";
                           //String[] sLinkAction  = str.split(":&");
                           List<WebElement> allrows1 = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("span"));
                           for(WebElement ele: allrows1)
                        {
                                  ActionObject = false;
                                  Status = false;
                                  String readAttri = ele.getAttribute("class");
                                  
                                    strReadString = str;
                                    actionObjetName = str;
                                    if (readAttri.contains(LeftPanelActionsLinksLevel))
                                    {
                                           //logger.info(readAttri);
                                           String readText = ele.getText();
                                           if (readText.contains(str))
                                           {
                                                  SearchString = true;
                                                  String readAttriID = ele.getAttribute("id");
                                                  WebElement  elem = common.returnObject(By.id(readAttriID));
                                                 Status = true;
                                                ActionObject = true;
                                                common.highlightElement(By.id(readAttriID));
                                                elem.click();
                                                common.WaitForPageToBeReady();
                                         break;
                                           }
                                }
                                    if(ActionObject == true)
                                    {
                                                break;
                                    }
                             }
                           if(SearchString)
                             {
                                         logger.info("Search String available in the table. '" + strReadString + "'");  
                                           HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "PASS");
                                         if(ActionObject)
                                           {
                                                       logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
                                                         HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "PASS");
                                                       Status = true;                                    
                                           }
                                           else
                                           {
                                                       logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
                                                         HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "FAIL");
                                                       Status = false;
                                           }
                             }
                             else
                              {
                                         logger.info("Search String not available in the table. '" + strReadString + "'");  
                                         HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "FAIL");
                                         Status = false;
                             }
                      }
       }
       return Status;
   }
   
   /**
    * @function Use to select the policy number in the account summary
    * @param sFuncValue
    * @return
    * @throws Exception
    */
   public boolean SelectPolicyNumber(String sFuncValue) throws Exception
   {
		boolean status = false;
		Common common = CommonManager.getInstance().getCommon();
		String[] sValue = sFuncValue.split(":::");
		status = common.ActionOnTable(Common.o.getObject("eleAccountSummary_PolicyTermsTbl"), 1, 1, sValue[0], sValue[0], "a");
		return status;
   }
   
   /**
    * @function This function use to verify the CLA policy info status
    * @param sValue
    * @return
    * @throws Exception
    */
   public static boolean VerifyPolicyStatus(String sValue) throws Exception
  	{
  		boolean status = false;
  		Common common = CommonManager.getInstance().getCommon();
//  	Thread.sleep(8000);
  		String strPolicyStatus = common.ReadElement(Common.o.getObject("eleCLAPolicyStatus"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
  		if(strPolicyStatus.equalsIgnoreCase(sValue))
  		{
  			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify the policy status","Application policy status is matching with expected status: '"+strPolicyStatus+"'", "PASS");
  			status = true;
  		}
  		else
  		{
  			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify the policy status","Application policy status is not matching with expected status: '"+strPolicyStatus+"'", "FAIL");
  			status = false;			
  		}
  		return status;
  	}
   
   /**
	 * @function This function verify Messages
	 * @param value (Success Messages)
	 * @return status
	 * @throws Exception
	 */
	public boolean VerifyStandardizationMessages(String strFunValue) throws Exception 
	{
	    boolean status = false;
	    Common common = CommonManager.getInstance().getCommon();
		String sApplnValue = "";
		String[] sValue = strFunValue.split(":::");
		try
		{
			switch (sValue[0])
			{
			case "AccountLocation":
				sApplnValue =common.ReadElement(Common.o.getObject("eleLocationInfoMsg"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
				break;
			case "AccountContact":
				sApplnValue =common.ReadElement(Common.o.getObject("eleAccount_Message"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
				break;
			case "CreateAccount":
				sApplnValue =common.ReadElement(Common.o.getObject("eleAddSuccessMsg"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
				break;
			}
			if (sApplnValue.equals(sValue[1]))
			{
				status = true;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display expected standardized message","System displayed" +sApplnValue+ "message as expected", "PASS");
			}
			else
			{
				status = false;
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display expected standardized message","System did not display" +sApplnValue+ "message as expected", "FAIL");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
		}
		return status;
	}
	
	/**
	 * @function it helps to handle the create account new button for dynamic	 
	 * @return 
	 * @throws Exception 
	 */
	public boolean CreateAccountOK() throws Exception
	{
		boolean status = true;
		Common common = CommonManager.getInstance().getCommon();
//		boolean popUpExist = common.ElementSize(Common.o.getObject("eleCreateAccountOK"))!=0;
		int popUpExist = common.ElementSize(Common.o.getObject("eleCreateAccountOK"));
		if(popUpExist == 1)
		{
			common.SafeAction(Common.o.getObject("eleCreateAccountOK"), "eleCreateAccountOK", "eleCreateAccountOK");
			status = true;
		}
		return status;
	}
	
	/**
	 * @function it helps to handle the create account new button for dynamic	 
	 * @return 
	 * @throws Exception 
	 */
	public boolean CreateAccountCancel() throws Exception
	{
		boolean status = true;
		Common common = CommonManager.getInstance().getCommon();
		boolean popUpExist = common.ElementSize(Common.o.getObject("eleCreateAccountCancel"))!=0;
		if(popUpExist == true)
		{
			common.SafeAction(Common.o.getObject("eleCreateAccountCancel"), "eleCreateAccountCancel", "eleCreateAccountCancel");
			status = true;
		}
		return status;
	}
	
	public static Boolean VerifyElementText(String strPageName) throws Exception{
		
		boolean Status = true;
		Common common = CommonManager.getInstance().getCommon();
		String[] sfunVal = strPageName.split(":::::");
		
          for(String element: sfunVal)
          {
				String[] sfunValue = element.split(":::");
				String ele = "ele" + sfunValue[0];

				//String txt = Common.ReadElement(Common.o.getObject(ele), 30);
				
				common.WaitUntilClickable(Common.o.getObject(ele), 30);		
				WebElement element1 = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(ele));
				String txt =  element1.getText();
				//String txt =  element1.getAttribute("value");
				if (txt.contentEquals(sfunValue[1].toUpperCase()))
						{
							logger.info("System displayed '" + sfunValue[1] + "'");  
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + sfunValue[1] + "'","System displayed '" + sfunValue[1] + "'", "PASS");
							//Status = true;
						}
						else
						{
							logger.info("System not displayed '" + sfunValue[1] + "'");  
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + sfunValue[1] + "'","System not displayed '" + sfunValue[1] + "'", "FAIL");
							//Status = false;
						}
          }
		return Status;
	}
	
	  /**
     * @function This function use to fill the dependent test data in corresponding component
     * @param sValue
     * @return
     * @throws IOException 
      * @throws Exception
     */
     public static boolean VerifyText(String sValue) throws IOException
     {
           boolean Status = true;
           String expectedText = null;
           String[] sfunVal = sValue.split(":::::");
           String[] sfunValue = sfunVal[1].split(":::");
//           String ele = "ele" + sfunVal[0];
           List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject(sfunVal[0]));
           if(CellElements.size() > 0){
  	         for(WebElement element: CellElements)
  	         {
  	           boolean matchStatus = false;
  	           String errorText = element.getText();
  	           for(String expText: sfunValue)
  	                {
  	                    expectedText = expText.toString();
  	                    //if (errorText.toUpperCase().contentEquals(expectedText.toUpperCase()))
  	                  if (errorText.toUpperCase().contains(expectedText.toUpperCase()) || (expectedText.toUpperCase().contains(errorText.toUpperCase())))
  	                    {
  	                           matchStatus = true;
  	                           logger.info("Expected text '"+expectedText+"' is matching/ contains with actual text '" + errorText + "'");  
  	                           HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected text '"+expectedText+"' should matching with actual text '" + errorText + "'","Expected text '"+expectedText+"' is matching with actual text '" + errorText + "'", "PASS");
  	                           break;
  	                    }
  	                }
  	             if(matchStatus == false)
  	             {
  	                    logger.info("Expected text '"+expectedText+"' is not matching with actual text '" + errorText + "'");  
  	                    HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Expected text '"+expectedText+"' should matching with actual text '" + errorText + "'","Expected text '"+expectedText+"' is not matching with actual text '" + errorText + "'", "FAIL");
  	                     //Status = false;
  	             }
  	         }
           }else {
        	   HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sfunVal[0]+" element should be found", ""+sfunVal[0]+" not found", "FAIL");
           }
          return Status;
      }
	
	public static Boolean VerifyEditBoxText(String strPageName) throws Exception{
		
		boolean Status = true;
		Common common = CommonManager.getInstance().getCommon();
		String[] sfunVal = strPageName.split(":::::");
		
          for(String element: sfunVal)
          {
				String[] sfunValue = element.split(":::");
				String ele = "edt" + sfunValue[0];

				//String txt = Common.ReadElement(Common.o.getObject(ele), 30);
				
				common.WaitUntilClickable(Common.o.getObject(ele), 30);		
				WebElement element1 = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(ele));
				//String txt =  element1.getText();
				String txt =  element1.getAttribute("value");
				if (txt.equalsIgnoreCase(sfunValue[1]))
						{
							logger.info("System displayed '" + sfunValue[1] + "'");  
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + sfunValue[1] + "'","System displayed '" + sfunValue[1] + "'", "PASS");
							//Status = true;
						}
						else
						{
							logger.info("System not displayed '" + sfunValue[1] + "'");  
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + sfunValue[1] + "'","System not displayed '" + sfunValue[1] + "'", "FAIL");
							//Status = false;
						}
          }
		return Status;
	}
	
	public static Boolean verifyEditBoxTextValue(String strPageName) throws Exception{
			
			boolean Status = true;
			CommonManager.getInstance().getCommon();
			String[] sfunVal = strPageName.split(":::::");
			
	          for(String element: sfunVal)
	          {
					String[] sfunValue = element.split(":::");
					//String txt = Common.ReadElement(Common.o.getObject(ele), 30);
					
	//				common.WaitUntilClickable(Common.o.getObject(ele), 30);		
					WebElement element1 = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(sfunValue[0]));
					//String txt =  element1.getText();
					String txt =  element1.getAttribute("value");
					//if (txt.toUpperCase().contains(sfunValue[1].toUpperCase()) || sfunValue[1].toUpperCase().contains(txt.toUpperCase()))
					if (txt.equalsIgnoreCase(sfunValue[1]))
							{
								logger.info("System displayed '" + sfunValue[1] + "'");  
								HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + sfunValue[1] + "'","System displayed '" + sfunValue[1] + "'", "PASS");
								//Status = true;
							}
							else
							{
								logger.info("System not displayed '" + sfunValue[1] + "'");  
								HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should display '" + sfunValue[1] + "'","System not displayed '" + sfunValue[1] + "'", "FAIL");
								//Status = false;
							}
	          }
			return Status;
		}
	
	public Boolean VerifyElementNotExistence(String strElement) throws Exception{
		
		String[] sfunVal = strElement.split(":::");
          for(String element: sfunVal)
          {
        	  	boolean Status = true;
				List<WebElement> ele = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("a"));
		          for(WebElement e: ele)
		          {
		        	  String txt = e.getText();
		        	  if (txt.contains(element))
					  {
		        		  Status = false;
					  }
		        	  
		          }
		          if(Status)
				  {
						logger.info("'" + element + "' " + "Element should not available");  
					  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Element should not available '" + element + "'","'" + element + "' " + "Element not available", "PASS");
						//Status = true;					  
				  }
				  else
				  {
					  logger.info("Element should not available '" + element + "'");  
					  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'" + element + "' " + "Element should not available","'" + element + "' " + "Element available", "FAIL");
						//Status = false;
				  }
			          
          }
		return true;
	}
	
	/**
	    * @function Use to click the left pane of the PC and also click menus
	    * @param strPageName
	    * @return true/false
	    * @throws Exception
	    */
	public Boolean PageLeftPane(String strPageName) throws Exception{
		
		Common common = CommonManager.getInstance().getCommon();
		String strReadString="";
		String actionObjetName="";
		String LeftPanelActionsLinksLevel = "";
		boolean SearchString=false;
		boolean ActionObject=false;
		String LeftPanelLinks="";
		String LeftPanelActions = "";
		int readCol = 0;
		boolean Status = true;
		String[] sfunVal = strPageName.split("::");
		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
		switch (sfunVal[0].toUpperCase())
		{
				case "DESKTOP":
					LeftPanelLinks = "eleDeskTopLeftPanelLinks";
					LeftPanelActions = "eleDeskTopLeftPanelActions";
					LeftPanelActionsLinksLevel = "x-menu-item-text";
				break;
				case "ACCOUNT":
					LeftPanelLinks = "eleAccountLeftPanelLinks";
					LeftPanelActions = "eleAccountLeftPanelActions";
					LeftPanelActionsLinksLevel = "x-tree-node-text ";
				break;
				case "POLICY":
					LeftPanelLinks = "elePolicyLeftPanelLinks";
					LeftPanelActions = "elePolicyLeftPanelActions";
					LeftPanelActionsLinksLevel = "x-tree-node-text ";
				break;
				case "ADMIN":
					LeftPanelLinks = "eleAdminLeftPanelLinks";
					//LeftPanelActions = "elePolicyLeftPanelActions";
					LeftPanelActionsLinksLevel = "x-tree-node-text ";
				break;
			
		}
		
		String[] sLnkAct = sfunVal[1].split("--");
		switch (sLnkAct[0].toUpperCase())
		{
			case "LINKS":
				
				WebElement  mytable = common.returnObject(Common.o.getObject(LeftPanelLinks));
				List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
				  for(int i = 0; i <= allrows.size()-1; i++)
				  {
					  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
					  String readText = Cells.get(readCol).getText();
					  strReadString = sLnkAct[1];
					  actionObjetName = sLnkAct[1];
					  if (readText.contains(sLnkAct[1]))
					  {
						  SearchString = true;
						  List<WebElement> CellElements = Cells.get(readCol).findElements(By.tagName("div"));
				          for(WebElement element: CellElements)
				          {
				        	  String objName = element.getText();
				        	  if(objName.contains(sLnkAct[1]))
			        	  		{
			            			element.click();
			            			ActionObject = true;
			            			Status = JavaScript(js);
			            			break;
				            	}
				          }
				     }
					  if(ActionObject == true)
						 {
							 break;
						 }
				  }
				  if(SearchString)
				  {
					  	logger.info("Search String available in the table. '" + strReadString + "'");  
					  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "PASS");
						if(ActionObject)
						  {
								logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
							  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "PASS");
								Status = true;					  
						  }
						  else
						  {
								logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
							  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "FAIL");
								Status = false;
						  }
				  }
				  else
				  {
					  	logger.info("Search String not available in the table. '" + strReadString + "'");  
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "FAIL");
						Status = false;
				  }
			break;
			case "ACTIONS":
				Status = common.SafeAction(Common.o.getObject(LeftPanelActions), "", LeftPanelActions);
				String[] sActs = sLnkAct[1].split("&&");
				for(String str: sActs)
		          {
					LeftPanelActionsLinksLevel = "x-menu-item-text";
					//String[] sLinkAction = str.split(":&");
					List<WebElement> allrows1 = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("span"));
					for(WebElement ele: allrows1)
			          {
						ActionObject = false;
						Status = false;
						String readAttri = ele.getAttribute("class");
						
						  strReadString = str;
						  actionObjetName = str;
						  if (readAttri.contains(LeftPanelActionsLinksLevel))
						  {
							  //logger.info(readAttri);
							  String readText = ele.getText();
							  if (readText.contains(str))
							  {
								  	SearchString = true;
								  	String readAttriID = ele.getAttribute("id");
								  	WebElement  elem = common.returnObject(By.id(readAttriID));
				        		  	ActionObject = true;
				        		  	common.highlightElement(By.id(readAttriID));
				        		  	elem.click();
				        		  	Status = JavaScript(js);
//				        		  	common.WaitForPageToBeReady();
			            			break;
							  }
					     }
						  if(ActionObject == true)
						  {
								 break;
						  }
					  }
					if(SearchString)
					  {
						  	logger.info("Search String available in the table. '" + strReadString + "'");  
						  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "PASS");
							if(ActionObject)
							  {
									logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
								  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "PASS");
									Status = true;					  
							  }
							  else
							  {
									logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
								  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "FAIL");
									Status = false;
							  }
					  }
					  else
					  {
						  	logger.info("Search String not available in the table. '" + strReadString + "'");  
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "FAIL");
							Status = false;
					  }
				  }
		}
		return Status;
	}
	
	/**
	 * @function Wait until the browser state is ready
	 * @return status
	 */
	public boolean WaitToPageToReady()
	{
		Common common = CommonManager.getInstance().getCommon();
		boolean status = true;
		common.WaitForPageToBeReady();
		return status;
	}
	
	/**
	 * @function This function used to take the SS where its required
	 * @throws IOException
	 */
	/*public static Boolean PageScreenShot(String strSSName) throws IOException
    {       
//		   File directory = new File (".");
		   Boolean Status = false;
           File reportFile;          
           Date d = new Date();          
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
           String date = dateFormat.format(d);
//           File dir = new File("C:\\Selenium\\WorkSpace\\PC\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date);
//           File dir = new File("C:\\Selenium\\WorkSpace2\\PC_E2E\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date);
           File dir = new File("C:\\Selenium\\WorkSpace2\\PC_E2E\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date +"_" + Thread.currentThread().getId());
//           String FolderName = "C:\\Selenium\\WorkSpace\\PC\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date;
           if (!dir.exists()) {
        	   dir.mkdir();
           }		
       	     do {
        	   //reportFile = new File(directory.getCanonicalPath() +"\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date+"_"+ d.getHours() +d.getMinutes()+d.getSeconds()+ ".png");
//        	   reportFile = new File(dir.getCanonicalPath()+"\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date+"_"+ d.getHours() +d.getMinutes()+d.getSeconds()+ ".png");
        	   reportFile = new File(dir.getCanonicalPath()+"\\"+strSSName+"_"+ date+"_"+ d.getHours() +d.getMinutes()+d.getSeconds()+ ".png");
        	   String Path = dir.getCanonicalPath();
        	   PCThreadCache.getInstance().setProperty(PCConstants.ALMScreenshotPath, Path);
        	   Status = true;
              } 
                  while (reportFile.exists());
                  File screenshot = ((TakesScreenshot) ManagerDriver.getInstance().getWebDriver()).getScreenshotAs(OutputType.FILE);
                  FileUtils.copyFile(screenshot, reportFile);
          return Status;
    }*/
	
	/**
	 * @throws Exception 
	 * @function This function used to take the SS where its required
	 */
	public static Boolean PageScreenShot(String strSSName) throws Exception
    {       
//		   File directory = new File (".");
		   Boolean Status = true;
		   if(HTML.properties.getProperty("TestCaseScreenShot").contains("YES"))
		   {
	           File reportFile;          
	           Date d = new Date();          
	           SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
	           String date = dateFormat.format(d);
	           String ssFolder = HTML.properties.getProperty("ResultsFolderPath");
	//           File dir = new File("C:\\Selenium\\WorkSpace\\PC\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date);
	           File dir = new File("C:\\Selenium\\WorkSpace2\\PC_E2E\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date);
	           if (!dir.exists()) {
	        	   dir.mkdir();
	           }		
	       	     do {
	        	   reportFile = new File(dir.getCanonicalPath()+"\\"+strSSName+"_"+ date+"_"+ d.getHours() +d.getMinutes()+d.getSeconds()+ ".png");
	        	   String Path = dir.getCanonicalPath();
	        	   PCThreadCache.getInstance().setProperty(PCConstants.ALMScreenshotPath, Path);
	        	   Status = true;
	              } 
	                  while (reportFile.exists());
	                  File screenshot = ((TakesScreenshot) ManagerDriver.getInstance().getWebDriver()).getScreenshotAs(OutputType.FILE);
	                  FileUtils.copyFile(screenshot, reportFile);
//	                  Status = ZipFolder();
		   }
          return Status;
    }

	public static Boolean PageScreenShot() throws Exception
    {       
//		   File directory = new File (".");
		   Boolean Status = false;
           File reportFile;          
           Date d = new Date();          
           SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
           String date = dateFormat.format(d);
//           File dir = new File("C:\\Selenium\\WorkSpace\\PC\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date);
           File dir = new File("C:\\Selenium\\WorkSpace2\\PC_E2E\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date +"_" + Thread.currentThread().getId());
//           String FolderName = "C:\\Selenium\\WorkSpace\\PC\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date;
           if (!dir.exists()) {
        	   dir.mkdir();
           }		
       	     do {
        	   //reportFile = new File(directory.getCanonicalPath() +"\\Reports\\HTMLReports\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date+"_"+ d.getHours() +d.getMinutes()+d.getSeconds()+ ".png");
        	   reportFile = new File(dir.getCanonicalPath()+"\\"+PCThreadCache.getInstance().getProperty("testcasename")+"_"+ date+"_"+ d.getHours() +d.getMinutes()+d.getSeconds()+ ".png");
        	   String Path = dir.getCanonicalPath();
        	   PCThreadCache.getInstance().setProperty(PCConstants.ALMScreenshotPath, Path);
        	   Status = true;
              } 
                  while (reportFile.exists());
                  File screenshot = ((TakesScreenshot) ManagerDriver.getInstance().getWebDriver()).getScreenshotAs(OutputType.FILE);
                  FileUtils.copyFile(screenshot, reportFile);
                  
          return Status;
    }

	public static boolean ZipFolder() throws Exception {
		
//	    zipFolder(SCRCommon.Path, SCRCommon.Path+".zip");
		if(HTML.properties.getProperty("TestCaseScreenShot").contains("YES"))
		{
			  zipFolder(PCThreadCache.getInstance().getProperty(PCConstants.ALMScreenshotPath), PCThreadCache.getInstance().getProperty(PCConstants.ALMScreenshotPath)+".zip");
		}
	    return true;
	    
	  }
	  static public void zipFolder(String srcFolder, String destZipFile) throws Exception {
		    ZipOutputStream zip = null;
		    FileOutputStream fileWriter = null;

		    fileWriter = new FileOutputStream(destZipFile);
		    zip = new ZipOutputStream(fileWriter);

		    addFolderToZip("", srcFolder, zip);
		    zip.flush();
		    zip.close();
		  }

		  static private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
		      throws Exception {

		    File folder = new File(srcFile);
		    if (folder.isDirectory()) {
		      addFolderToZip(path, srcFile, zip);
		    } else {
		      byte[] buf = new byte[1024];
		      int len;
		      FileInputStream in = new FileInputStream(srcFile);
		      zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
		      while ((len = in.read(buf)) > 0) {
		        zip.write(buf, 0, len);
		      }
		    }
		  }

		  static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
		      throws Exception {
		    File folder = new File(srcFolder);

		    for (String fileName : folder.list()) {
		      if (path.equals("")) {
		        addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
		      } else {
		        addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
		      }
		    }
		  }
	
	public Boolean WaitForObjectToBeReady(String sFuncValue) throws Exception, Exception
	{
		Boolean status = false;
		Common common = CommonManager.getInstance().getCommon();
		status = common.WaitUntilClickable(Common.o.getObject("ele"+sFuncValue+""), Integer.valueOf(HTML.properties.getProperty("LONGESTWAIT")));
		return status;
	}
	
	public Boolean QPBOtherPremiumEdit(String strParam) throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		Boolean status = true;
		String[] sHeader = strParam.split("###");
		String[] strParams = sHeader[1].split(":::::");
		for(int i=0; i<(strParams.length);i++)
		{
			String strParams1[] = strParams[i].split(":::");
			String cla = "x-form-item-label x-unselectable x-form-item-label-top";
			List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
			  for(WebElement element: CellElements)
	          {
	        	  String objName = element.getText();
	        	  String clas = element.getAttribute("class");
		        	  if(objName.contains(strParams1[0]) )
		        	  {
			        		  if(cla.contains(clas))
			        		  {
					        		String readAttriID1 = element.getAttribute("id");
					        		String[] sID = readAttriID1.split("DV:");
					        		String[] sID1 = sID[1].split(":");
					        		int value2 = Integer.parseInt(sID1[0])+1;
					        		String SD3 = sID[0].concat("DV:")+value2+":QPBModalQuestion_ExtInputSet:IntegerFieldInput-inputEl";					        		
				        			WebElement  elem = common.returnObject(By.id(SD3));
				        		  	elem.click();
				        		  	elem.sendKeys(strParams1[1]);
				        		  	elem.sendKeys(Keys.TAB);
//				        		  	Common.WaitForPageToBeReady();
				        		  	Thread.sleep(4000);
				        		  	 break;
		        	  			}	
	        	  		}
        	  }
	          if(status)
			  {
				  logger.info(""+strParams1[0]+" TestArea has been Filled");  
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams1[0]+" TestArea should filled",""+strParams1[0]+" TestArea is filled","PASS");
			  }
			  else
			  {
				  logger.info(""+strParams1[0]+" TestArea has not been Filled");
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams1[0]+" TestArea should filled",""+strParams1[0]+" TestArea is not filled","FAIL");
				  status = false;
			  }
		}
		return status;
	}
	
	public Boolean QPBEdit(String strParam) throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		Boolean status = false;
		String[] sHeader = strParam.split("###");
		String[] strParams = sHeader[1].split(":::::");
//		String[] strParams = strParam.split(":::::");
		for(int i=0; i<(strParams.length);i++)
		{
			String strParams1[] = strParams[i].split(":::");
//		         for(String str: strParams)
//		         {
		        	 	  String cla = "x-form-item-label x-unselectable x-form-item-label-top";
						  List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
					          for(WebElement element: CellElements)
					          {
					        	  String objName = element.getText();
					        	  String clas = element.getAttribute("class");
						        	  if(objName.contains(strParams1[0]) )
						        	  {
							        		  if(cla.contains(clas))
							        		  {
									        		String readAttriID1 = element.getAttribute("id");
									        		String[] sID = readAttriID1.split("-");
								        			String sID1 = sID[0].concat("-inputEl");
								        			WebElement  elem = common.returnObject(By.id(sID1));
								        		  	elem.click();
								        		  	common.WaitForPageToBeReady();
								        		  	elem.sendKeys(strParams1[1]);
								        		  	status = false;
								        		  	break;
						        	  			}	
					        	  		}
						        	  
					        	  }
//		         }
	          if(status)
			  {
				  logger.info(""+strParams1[0]+" TestArea has been Filled");  
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams1[0]+" TestArea should filled",""+strParams1[0]+" TestArea is filled","PASS");
			  }
			  else
			  {
				  logger.info(""+strParams1[0]+" TestArea has not been Filled");
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams1[0]+" TestArea should filled",""+strParams1[0]+" TestArea is not filled","FAIL");
				  status = false;
			  }
		}
		return status;
	}
	
	public Boolean QPBEditWait(String strParam) throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		Boolean status = false;
		String[] sHeader = strParam.split("###");
		String[] strParams = sHeader[1].split(":::::");
//		String[] strParams = strParam.split(":::::");
		for(int i=0; i<(strParams.length);i++)
		{
			String strParams1[] = strParams[i].split(":::");
//		         for(String str: strParams)
//		         {
        	 	  String cla = "x-form-item-label x-unselectable x-form-item-label-left";
				  List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
			          for(WebElement element: CellElements)
			          {
			        	  String objName = element.getText();
			        	  String clas = element.getAttribute("class");
				        	  if(objName.contains(strParams1[0]) )
				        	  {
					        		  if(cla.contains(clas))
					        		  {
							        		String readAttriID1 = element.getAttribute("id");
							        		String[] sID = readAttriID1.split("-");
						        			String sID1 = sID[0].concat("-inputEl");
						        			WebElement  elem = common.returnObject(By.id(sID1));
						        		  	elem.click();
						        		  	elem.sendKeys(strParams1[1]);
						        		  	elem.sendKeys(Keys.TAB);
						        		  	Thread.sleep(4000);
						        		  	status = true;
						        		  	break;
				        	  			}	
			        	  		}  
		        	  }
//		         }
	          if(status)
			  {
				  logger.info(""+strParams1[0]+" TestArea has been Filled");  
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams1[0]+" TestArea should filled",""+strParams1[0]+" TestArea is filled","PASS");
			  }
			  else
			  {
				  logger.info(""+strParams1[0]+" TestArea has not been Filled");
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams1[0]+" TestArea should filled",""+strParams1[0]+" TestArea is not filled","FAIL");
				  status = false;
			  }
		}
		return status;
	}
	
	public Boolean QPBCoverageMarketingFlyers(String strParam) throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		Boolean status = false;
		String[] sHeader = strParam.split("###");
		String[] strParams = sHeader[1].split(":::::");
//		String[] strParams = strParam.split(":::::");
		for(int i=0; i<(strParams.length);i++)
		{
			String strParams1[] = strParams[i].split(":::");
//				for(String str: strParams1)
//		         {
				  List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("a"));
		          for(WebElement element: CellElements)
		          {
		        	  String objName = element.getText();
		        	  		{
			        		  if(objName.contains(strParams1[0]))
			        		  {
					        		String readAttriID1 = element.getAttribute("id");
					        		String[] sID = readAttriID1.split("_ExtInputSet");
				        			String sID1 = sID[0].concat("_ExtInputSet:choicea:choicea_Arg-inputEl");
				        			WebElement  sElement = common.returnObject(By.id(sID1));
				        			sElement.click();
				        			sElement.clear();
				        			sElement.sendKeys(strParams1[1]);
				        			sElement.sendKeys(Keys.TAB);
				        		  	common.WaitForPageToBeReady();
				        		  	Thread.sleep(3000);
				        		  	status = true;
				        		  	break;
		        	  			}	
		        	  		}
		        	  }
//		         }
			if(status)
			  {
				  logger.info(""+strParams1[0]+" Flyer has been Filled");  
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams1[0]+" should filled",""+strParams1[0]+" is filled","PASS");
			  }
			  else
			  {
				  logger.info(""+strParams1[0]+" Flyer has not been Filled");
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams1[0]+" should filled",""+strParams1[0]+" is not filled","FAIL");
				  status = false;
			  }
		}
		return status;
	}
	
	public Boolean QPBCheckBox(String strParam) throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		Boolean status = false;
		String[] sHeader = strParam.split("###");
		String[] strParams = sHeader[1].split(":::");
//		String[] strParams = strParam.split(":::");
		for(String str: strParams)
         {
    	 		  String cla = "x-form-cb-label x-form-cb-label-after";
				  List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
		          for(WebElement element: CellElements)
		          {
		        	  String objName = element.getText();
		        	  String clas = element.getAttribute("class");
		        	  if(objName.contains(str) )
        	  			{
		        		  if(cla.contains(clas))
		        		  {
				        		  String readAttriID1 = element.getAttribute("id");
								  WebElement  elem = common.returnObject(By.id(readAttriID1));
				        		  elem.click();
//				        		  Common.WaitForPageToBeReady();
//				        		  waitForAjax();
//				        		  Common.getResponseCode();
//				        		  Common.checkHttpResponseCode();
//				        		  Common.getStatusCode();
//				        		  Common.linkExists();
//				        		  Common.getStatusCode();
				        		  status = true;
				        		  break;
	        	  			}	
	        	  		}
		          }
		          if(status)
				  {
					  logger.info(""+str+" Check Box has been Selected");  
					  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+str+" should filled",""+str+" is Selected","PASS");					  
				  }
				  else
				  {
					  logger.info(""+str+" Check Box has not been Selected");
					  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+str+" should filled",""+str+" is not Selected","FAIL");
					  status = false;
				  }
         }
		return status;
	}
	
	public Boolean QPBVerifyElement(String strParam) throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		Boolean status = false;
		Boolean sVerifyElementAssert = true;
		String[] sHeader = strParam.split("###");
		String[] strParams = sHeader[1].split(":::");
//		String[] strParams = strParam.split(":::");
		for(String str: strParams)
         {
    	 		  String cla = "x-form-cb-label x-form-cb-label-after";
				  List<WebElement> CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
		          for(WebElement element: CellElements)
		          {
		        	  String objName = element.getText();
		        	  String clas = element.getAttribute("class");
		        	  if(objName.contains(str))
        	  			{
		        		  if(cla.contains(clas))
		        		  {
				        		  String readAttriID1 = element.getAttribute("id");
								  WebElement  elem = common.returnObject(By.id(readAttriID1));
				        		  	//Common.highlightElement(By.id(readAttriID1));
				        		  elem.click();
				        		  status = true;
				        		  sVerifyElementAssert = SCRCommon.PageVerify(strParams[1]);
				        		  break;
//				        		  Common.WaitForPageToBeReady();
	        	  			}	
	        	  		}
		          }
		          if(status)
				  {
					  logger.info(""+str+" Check Box has been Selected and Verified the Element");  
					  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+str+" should filled",""+str+" is Selected","PASS");
					  if(sVerifyElementAssert)
					  {
						  logger.info(""+strParams[1]+" Verified the Element");  
						  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams[1]+" should filled",""+strParams[1]+" is Selected","PASS");
					  }
					  else
					  {
						  logger.info(""+str+" Element does not exist");
						  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strParams[1]+" should filled",""+strParams[1]+" is not Selected","FAIL");
						  status = false;
					  }
				  }
				  else
				  {
					  logger.info(""+str+" Check Box has not been Selected");
					  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+str+" should filled",""+str+" is not Selected","FAIL");
					  status = false;
				  }
         }
		return status;
	}
	
	public static String ReturnSixtyDaysFromDate() 
    {
           String status = null;
           try
           {
               Calendar Date = Calendar.getInstance();  
               Date.add(Calendar.DATE,60);
               String End_Date =  (Date.get(Calendar.MONTH)+1)+"/"+(Date.get(Calendar.DATE)+"/"+(Date.get(Calendar.YEAR)));
               status = End_Date;
           }
           catch(Exception e)
           {
                 e.printStackTrace();  
                 logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
           }
           return status;
    }
	
	public static String ReturnDate(int dateValue) 
    {
           String sDate = null;
           try
           {   
	        	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	       		Calendar c = Calendar.getInstance();
	       		c.add(Calendar.DATE, dateValue);
	       		sDate = sdf.format(c.getTime());
           }
           catch(Exception e)
           {
                 e.printStackTrace();  
                 logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
           }
           return sDate;
    }
	/**
	 * 
	 * @param dateValue
	 * @return
	 */
	public static String ReturnDate(Calendar cal,int dateValue) 
    {
		
		String strReturnDate = null; 
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		System.out.println(cal.getTime());
	   	cal.add(Calendar.DATE, dateValue);
	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    System.out.println(formatter.format(cal.getTime()));
	    System.out.println(cal.getTime());
	    strReturnDate = formatter.format(cal.getTime());
	    return strReturnDate;
	 
    }
	
	
	public static String ReturnHundredAndEightyDaysFromDate() 
    {
           String status = null;
           try
           {
               Calendar Date = Calendar.getInstance();  
               Date.add(Calendar.DATE,180);
               String End_Date =  (Date.get(Calendar.MONTH)+1)+"/"+(Date.get(Calendar.DATE)+"/"+(Date.get(Calendar.YEAR)));
               status = End_Date;
           }
           catch(Exception e)
           {
                 e.printStackTrace();      
                 logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
           }
           return status;
    } 
	
	/**
	 * @function it helps to Mouseover the Object	 
	 * @return 
	 * @throws Exception 
	 */
	public boolean MouseOverActionMainAndSubMenu(String strValue) throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		boolean status = false;		
		String[] strOject = strValue.split(":::");
		status = common.MouseHoverAction(Common.o.getObject(strOject[0]), Common.o.getObject(strOject[1]));		
		return status;
	}
	
	
	/**
	 * @function it helps to Mouseover the Object	 
	 * @return true/false
	 * @throws Exception 
	 */
	public boolean VerifySubMenu(String strObject) throws Exception
	{
		boolean status = false;	
		boolean blnMenu = false;
		
		 String strElement = strObject.split(":::")[0];
		String[] strMenu = strObject.split(":::")[1].split("::");
		String[] strActMenu = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(strElement)).getText().split("\n");
		
		for(int i=0;i<=strMenu.length-1;i++){
			for(int j=0;j<=strActMenu.length-1;j++){
				if(strMenu[i].equalsIgnoreCase(strActMenu[j])){
					blnMenu = true;
				}
			}
			if(blnMenu){
				logger.info("Search String available in the Menu. '" + strMenu[i] + "'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in Menu and Search string is '" + strMenu[i] + "'","System searched string in Menu and search string is  '" + strMenu[i] + "'", "PASS");
				blnMenu = false;
				status = true;
			}
			else{
				logger.info("Search String not available in the Menu. '" + strMenu[i] + "'");  
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in Menu and Search string is '" + strMenu[i] + "'","System not searched string in Menu and search string is  '" + strMenu[i] + "'", "FAIL");
				status = false;
			}	
		}
//		System.out.println(driver.findElement(By.cssSelector(".x-panel.x-layer.x-panel-default.x-menu.x-border-box:not([style*='hidden'])")).getText());		
		return status;
	}
	
	public static Boolean QPBCommon(String strParam) throws Exception 
	{
		Common common = CommonManager.getInstance().getCommon();
        Boolean status = false;
        String sClassName = null;
        List<WebElement> CellElements = null;
        JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
        String[] sHeader= strParam.split("#####");
        logger.info("Starting the Additional Coverage"  + sHeader[0]);
        		for(int i=1; i<=(sHeader.length)-1;i++)
				{
        			status = false;
		        	 	String[] sFields = sHeader[i].split("~~");
		        	 	String sSameLabelIdentity = sFields[1].substring(0, 3);
				        	 		switch (sFields[2].toUpperCase())
				        			{
				        				case "CHK":
			        							if(sFields[3].equals("YES"))
			        							{
			        								 sClassName = "x-form-cb-label x-form-cb-label-after";
										               //added log statement
										               logger.info("str 1 =" + sFields[1]);
										               CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
										               for (WebElement element : CellElements) 
										               {
										                     String objName = element.getText();
										                     String sFieldClass = element.getAttribute("class");
										                     if (objName.contains(sFields[1]))
										                     {
										                            //added log statement
										                            logger.info("str 2 =" + sFields[1]);
										                            if (sClassName.contains(sFieldClass)) 
										                            {
										                                   String readAttriID1 = element.getAttribute("id");
										                                   WebElement sElement = common.returnObject(By.id(readAttriID1));
										                                   sElement.click();
										                                   status = common.SafeAction(By.id(readAttriID1), "ele", "ele");
										                                   status = true;
										                                   break;
										                            }
										                     }
										               }
			        							}else
			        							{
			        								status = true;
			        							}
							                   break; 
							               case "EDT":
							            	   if(sFields[3].equals("YES"))
							            	   {
										               sClassName = "x-form-item-label x-unselectable x-form-item-label-left";
										               //added log statement
										               logger.info("str 1 =" + sFields[1]);
										               CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
											               for(WebElement element: CellElements)
													          {
													        	  String objName = element.getText();
													        	  String sFieldClass = element.getAttribute("class");
														        	  if(objName.contains(sFields[1]) )
														        	  {
															        		  if(sClassName.contains(sFieldClass))
															        		  {
																	        		String readAttriID1 = element.getAttribute("id");
																	        		String[] sID = readAttriID1.split("-");
																        			String sID1 = sID[0].concat("-inputEl");
																        			WebElement  sElement = common.returnObject(By.id(sID1));
//																        			sElement.click();
																        			sElement.clear();
																        			sElement.sendKeys(sFields[4]);
																        		  	sElement.sendKeys(Keys.TAB);
//																        		  	status = Common.JavaScriptWait(sElement, js);
																        		  	status = true;
																        		  	break;
														        	  			}	
													        	  		}  
												        	  }
			        							}else{
			        								status = true;
			        							}
								               break;
							               case "TXA":
								            	   if(sFields[3].equals("YES"))
								            	   {
									            		   if(!sSameLabelIdentity.equals("OC1") && !sSameLabelIdentity.equals("OC2") && !sSameLabelIdentity.equals("OC3") &&
									            				!sSameLabelIdentity.equals("ALD") && !sSameLabelIdentity.equals("GMS") && !sSameLabelIdentity.equals("NFP") && !sSameLabelIdentity.equals("APP")
										            				   && !sSameLabelIdentity.equals("OC3"))
										            	   		{
													               sClassName = "x-form-item-label x-unselectable x-form-item-label-top";
													               //added log statement
													               logger.info("str 1 =" + sFields[1]);
													               CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
														               for(WebElement element: CellElements)
																          {
																        	  String objName = element.getText();
																        	  String sFieldClass = element.getAttribute("class");
																	        	  if(objName.contains(sFields[1]) )
																	        	  {
																		        		  if(sClassName.contains(sFieldClass))
																		        		  {
																				        		String readAttriID1 = element.getAttribute("id");
																				        		String[] sID = readAttriID1.split("-");
																			        			String sID1 = sID[0].concat("-inputEl");
																					        	WebElement  sElement = common.returnObject(By.id(sID1));
//																			        			sElement.click();
//																			        			sElement.clear();
//																			        			sElement.sendKeys(sFields[4]);
																					        	status = common.SafeAction(By.id(sID1), sFields[4], "edt");
																			        		  	sElement.sendKeys(Keys.TAB);
				//															        		  	status = Common.JavaScriptWait(sElement, js);
																			        		  	status = true;
																			        		  	break;
																	        	  			}	
																        	  		}  
															        	  }
										            	   }
										            	   else
						        							{
						        								String[] sEdtLabelValue = sFields[1].split("---");
						        								String cla = null;
						        								 logger.info("str 1 =" + sFields[1]);
						        								//This is to check whether the concatenate object is an edt label or txtarea label or chk label
						        									if(sSameLabelIdentity.equals("ALD") || sSameLabelIdentity.equals("GMS"))
						        									{
								        								cla = "x-form-item-label x-unselectable x-form-item-label-left";
						        									}
						        									else if(sSameLabelIdentity.equals("OC1") || sSameLabelIdentity.equals("OC2") ||sSameLabelIdentity.equals("OC3") )
						        									{
						        										cla = "x-form-item-label x-unselectable x-form-item-label-top";
						        									}
						        									/*else if(sSameLabelIdentity.equals("CDS"))
						        									{
						        										cla = "x-component x-component-default";
						        									}*/
						        									else
						        									{
						        										cla = "x-form-cb-label x-form-cb-label-after";
						        									}
						        									CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
						        								  for(WebElement element: CellElements)
						        						          {
						        						        	  String objName = element.getText();
						        						        	  String clas = element.getAttribute("class");
						        							        	  if(objName.contains(sEdtLabelValue[1]) )
						        							        	  {
						        								        		  if(cla.contains(clas))
						        								        		  {
						        										        		String readAttriID1 = element.getAttribute("id");
						        										        		String[] sID = readAttriID1.split("DV:");
						        										        		String[] sID1 = sID[1].split(":");
						        										        		int value = 0;
							        										        		if(sSameLabelIdentity.equals("APP") || sSameLabelIdentity.equals("NFP") || sSameLabelIdentity.equals("CDS"))
							        										        		{
								        										        		value = Integer.parseInt(sID1[0])+1;
							        										        		}else
							        										        		{
							        										        			value = Integer.parseInt(sID1[0])+2;
							        										        		}
						        										        		String SD3 = sID[0].concat("DV:")+value+":QPBModalQuestion_ExtInputSet:Stringrtextarea1-inputEl";
						        									        			WebElement  sElement = common.returnObject(By.id(SD3));
//						        									        			sElement.click();
//						        									        			sElement.sendKeys(sFields[4]);
						        									        			status = common.SafeAction(By.id(SD3), sFields[4], "edt");
						        									        			sElement.sendKeys(Keys.TAB);
//						        									        		  	status = Common.JavaScriptWait(sElement, js);
						        									        		  	status = true;
						        									        		  	break;
						        							        	  			}	
						        						        	  		}
						        					        	  }
						        							}
								            	   }
				        	 						else
								            	   {
								            		   status = true;
								            	   }
									               break;
								               case "CHKJ":
				        							if(sFields[3].equals("YES"))
				        							{
				        								if(!sFields[1].equals("ADSOther") && !sFields[1].equals("SUBOther")  &&!sFields[1].equals("OPFOther"))
				        								{
				        									sClassName = "x-form-cb-label x-form-cb-label-after";
												               //added log statement
												               logger.info("str 1 =" + sFields[1]);
												               CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
												               for (WebElement element : CellElements) 
												               {
												                     String objName = element.getText();
												                     String sFieldClass = element.getAttribute("class");
												                     if (objName.contains(sFields[1]))
												                     {
												                            //added log statement
												                            logger.info("str 2 =" + sFields[1]);
												                            if (sClassName.contains(sFieldClass)) 
												                            {
												                                   String readAttriID1 = element.getAttribute("id");
//												                                   WebDriver driver = Common.dr();
//												                                   JavascriptExecutor js1 = (JavascriptExecutor) driver;
												                                   WebElement sElement = common.returnObject(By.id(readAttriID1));
//												                                   status = Common.SafeAction(By.id(readAttriID1), "scl", "scl");
												                                   status = common.SafeAction(By.id(readAttriID1), "ele", "ele");
//												                                   sElement.click(); //here we are clicking the checkbox which is making the ajax call
												                                   status = SCRCommon.JavaScriptWait(sElement, js);
												                                   status = true;
												                                   break;
												                            }
												                     }
												               }
					        							}else
					        							{
					        								String sObject = "eleQPB"+sFields[1]+"";
					        								status = common.SafeAction(Common.o.getObject(sObject), "", sObject);
					        								///status = SCRCommon.JavaScriptWait(Common.ele, js); Krishna Commented this line
					        							}
			        								}else
			        								{
					        							status = true;
			        								}
								                   break; 
								               case "EDTJ":
								            	   if(sFields[3].equals("YES"))
								            	   {
								            			   if(!sSameLabelIdentity.equals("OC1") && !sSameLabelIdentity.equals("OC2") && !sSameLabelIdentity.equals("OC3"))
										            	   {
													               sClassName = "x-form-item-label x-unselectable x-form-item-label-left";
													               //added log statement
													               logger.info("str 1 =" + sFields[1]);
													               CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
														               for(WebElement element: CellElements)
																          {
																        	  String objName = element.getText();
																        	  String sFieldClass = element.getAttribute("class");
																	        	  if(objName.contains(sFields[1]) )
																	        	  {
																		        		  if(sClassName.contains(sFieldClass))
																		        		  {
																				        		String readAttriID1 = element.getAttribute("id");
																				        		String[] sID = readAttriID1.split("-");
																			        			String sID1 = sID[0].concat("-inputEl");
																			        			WebElement  sElement = common.returnObject(By.id(sID1));
//																			        			sElement.click();
//																			        			sElement.clear();
//																			        			sElement.sendKeys(sFields[4]);
																			        			status = common.SafeAction(By.id(sID1), sFields[4], "edt");
																			        		  	sElement.sendKeys(Keys.TAB);
																			        		  	status = SCRCommon.JavaScriptWait(sElement, js);
																			        		  	status = true;
																			        		  	break;
																	        	  			}	
																        	  		}  
															        	  }
						        							}
										            	   else
						        							{
										            		   logger.info("str 1 =" + sFields[1]);
						        								String[] sEdtLabelValue = sFields[1].split("---");
						        								String cla = "x-form-item-label x-unselectable x-form-item-label-top";
						        								CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("label"));
						        								  for(WebElement element: CellElements)
						        						          {
						        						        	  String objName = element.getText();
						        						        	  String clas = element.getAttribute("class");
						        							        	  if(objName.contains(sEdtLabelValue[1]) )
						        							        	  {
						        								        		  if(cla.contains(clas))
						        								        		  {
						        										        		String readAttriID1 = element.getAttribute("id");
						        										        		String[] sID = readAttriID1.split("DV:");
						        										        		String[] sID1 = sID[1].split(":");
						        										        		int value2 = Integer.parseInt(sID1[0])+1;
						        										        		String SD3 = sID[0].concat("DV:")+value2+":QPBModalQuestion_ExtInputSet:IntegerFieldInput-inputEl";					        		
						        									        			WebElement  sElement = common.returnObject(By.id(SD3));
//						        									        			sElement.click();
//						        									        			sElement.sendKeys(sFields[4]);
						        									        			status = common.SafeAction(By.id(SD3), sFields[4], "edt");
						        									        			sElement.sendKeys(Keys.TAB);
						        									        		  	status = SCRCommon.JavaScriptWait(sElement, js);
						        									        		  	status = true;
						        									        		  	break;
						        							        	  			}	
						        						        	  		}
						        					        	  }
						        							}
								            	   }
									               break;
								               case"LSTJ":
								            	   if(sFields[3].equals("YES"))
								            	   {
										            	   CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("a"));
												 		          for(WebElement element: CellElements)
												 		          {
												 		        	  String objName = element.getText();
											 		        	  		{
												 			        		  if(objName.contains(sFields[1]))
												 			        		  {
												 					        		String readAttriID1 = element.getAttribute("id");
												 					        		String[] sID = readAttriID1.split("_ExtInputSet");
												 				        			String sID1 = sID[0].concat("_ExtInputSet:choicea:choicea_Arg-inputEl");
												 				        			WebElement  sElement = common.returnObject(By.id(sID1));
//												 				        			sElement.click();
//												 				        			sElement.clear();
//												 				        			sElement.sendKeys(sFields[4]);
//												 				        			sElement.sendKeys(Keys.TAB);
												 				        			status = common.SafeAction(By.id(sID1), sFields[4], "lst");
												 				        		  	status = SCRCommon.JavaScriptWait(sElement, js);
												 				        		  	break;
												 		        	  			}	
											 		        	  		}
											 		        	  }
								            	   }
								            	   break;
								               case"LSSJ":
								            	   if(sFields[3].equals("YES"))
								            	   {
										            	   CellElements = ManagerDriver.getInstance().getWebDriver().findElements(By.tagName("span"));
												 		          for(WebElement element: CellElements)
												 		          {
												 		        	  String objName = element.getText();
											 		        	  		{
												 			        		  if(objName.contains(sFields[1]))
												 			        		  {
												 					        		String readAttriID1 = element.getAttribute("id");
												 					        		String[] sID = readAttriID1.split("_ExtInputSet");
												 				        			String sID1 = sID[0].concat("_ExtInputSet:choicea:choicea_Arg-inputEl");
												 				        			WebElement  sElement = common.returnObject(By.id(sID1));
//												 				        			sElement.click();
//												 				        			sElement.clear();
//												 				        			sElement.sendKeys(sFields[4]);
//												 				        			sElement.sendKeys(Keys.TAB);
												 				        			status = common.SafeAction(By.id(sID1), sFields[4], "lst");
												 				        		  	status = SCRCommon.JavaScriptWait(sElement, js);
												 				        		  	break;
												 		        	  			}	
											 		        	  		}
											 		        	  }
								            	   }
								            	   break;
	        	 		}
	        	 		if(status)
	        	 		{
	        	 			if(sFields[3].contains("YES"))
	        	 			{
		        	 			if(sFields[2].contains("CHK") || sFields[2].contains("CHKJ"))
		        	 			{
		        	 				 logger.info("System clicked "+sFields[1]+" Check Box");  
		  						  	   HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should click '"+sFields[1]+"' Check box", "System clicked '"+sFields[1]+"' check box","PASS");				
		        	 			}
		        	 			else if(sFields[2].contains("EDT") || sFields[2].contains("EDTJ"))
		        	 			{
		        	 				logger.info("System entered '"+sFields[4]+"' value for "+sFields[1]+" Edit box");  
		  						  	   HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should enter value '"+sFields[4]+"' for '"+sFields[1]+"' Edit Box", "System entered value for '"+sFields[4]+"' '"+sFields[1]+"' Edit Box","PASS");
		        	 			}
		        	 			else if(sFields[2].contains("TXT"))
		        	 			{
		        	 				logger.info("System entered '"+sFields[4]+"' value for "+sFields[1]+" TexArea Box"); 
		  						  	   HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should enter value '"+sFields[4]+"' for '"+sFields[1]+"' TextArea Box", "System entered value for '"+sFields[4]+"' '"+sFields[1]+"' TextArea Box","PASS");	        	 				
		        	 			}
		        	 			else
		        	 			{
		        	 				logger.info("System entered '"+sFields[4]+"' value for "+sFields[1]+" List Box");   
		  						  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should enter value '"+sFields[4]+"' for '"+sFields[1]+"' List Box", "System entered value for '"+sFields[4]+"' '"+sFields[1]+"' List Box","PASS");	        	 				
		        	 			}

	        	 			}else
	        	 			{
	        	 				status = true;	
	        	 			}
	        	 		}else
	        	 		{
	        	 			if(sFields[3].contains("YES"))
	        	 			{
			        	 			if(sFields[2].contains("CHK") || sFields[2].contains("CHKJ"))
			        	 			{
			        	 				logger.info(""+sFields[1]+" check box is not clicked "); 
			  	  						  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should click '"+sFields[1]+"' check box","System not clicked '"+sFields[1]+"' check box","FAIL");
			  	  						  status = false; 
			        	 			}
			        	 			else if(sFields[2].contains("EDT") || sFields[2].contains("EDTJ"))
			        	 			{
			        	 				logger.info("System not entered '"+sFields[4]+"' value for "+sFields[1]+" Edit box"); 
			  	  						  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should enter value '"+sFields[4]+"' for '"+sFields[1]+"' Edit Box", "System not entered value for '"+sFields[4]+"' '"+sFields[1]+"' Edit Box", "FAIL");
			  	  						  status = false;
			        	 			}
			        	 			else if(sFields[2].contains("TXA"))
			        	 			{
			        	 				logger.info("System not entered '"+sFields[4]+"' value for "+sFields[1]+" TexArea Box"); 
			  	  						  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should enter value '"+sFields[4]+"' for '"+sFields[1]+"' TextArea Box", "System not entered value for '"+sFields[4]+"' '"+sFields[1]+"' TextArea Box","FAIL");
			  	  						  status = false;
			        	 			}
			        	 			else{
			        	 				logger.info("System not entered '"+sFields[4]+"' value for "+sFields[1]+" List Box");  
			  	  						  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should enter value '"+sFields[4]+"' for '"+sFields[1]+"' List Box", "System not entered value for '"+sFields[4]+"' '"+sFields[1]+"' List Box", "FAIL");
			  	  						  status = false;
			        	 			}
	        	 			}else
	        	 			{
	        	 				status = false;
	        	 			}
	        	 		}	        	 			
        	 		}
		        				
		return status;
	}
	
	public  static boolean JavaScriptWait1(WebElement element, JavascriptExecutor js) throws Exception
	{
		boolean status = false;
		 for (int i = 1; i <= Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")); i++) 
		 {
             logger.info("Document Ajax State = "
                           + js.executeScript(
                                        "return Ext.Ajax.isLoading();")
                                        .toString());
             Boolean isAjaxRunning = Boolean.valueOf(js
                           .executeScript(
                                        "return Ext.Ajax.isLoading();") //returns true if ajax call is currently in progress
                           .toString());
             if (!isAjaxRunning.booleanValue()) 
             {
            	 status = true;
            	 break;
             }
             Thread.sleep(1000);//wait for one secnod then check if ajax is completed
		 }
	      //only remove this print statements after this has been tested
	      logger.info("Entering Wait....");
	      logger.info("Document window.getComputedStyle(document.body).cursor = "
	                                  + js.executeScript(
	                                               "return window.getComputedStyle(document.body).cursor;")
	                                               .toString());
	      //After ajax call is over, page is not displaying fields(elements) in UI so we are calling below code to display fields in UI
	      //status = Common.SafeAction(Common.o.getObject("eleAccountNumber"), "","eleQPBAccountNo");
	      //only remove this print statements after this has been tested
	      logger.info("End Wait....1");
	      //we are waiting to load the elements into page
	      WebDriverWait wait = new WebDriverWait(ManagerDriver.getInstance().getWebDriver(),
	                    Integer.parseInt(HTML.properties
	                                  .getProperty("VERYLONGWAIT")));
	      wait.until(ExpectedConditions.stalenessOf(element));// (By.id(readAttriID1)));
	      logger.info("End Wait....2");	
	      status = true;
	      return status; 
	}
	
	public static boolean JavaScriptWait(WebElement element, JavascriptExecutor js) throws Exception
	{
		boolean status = false;
		Common common = CommonManager.getInstance().getCommon();
//		Actions clickTriangle= new Actions(driver);
			 for (int i = 1; i <= Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")); i++) 
			 {
	             logger.info("Document Ajax State = "+ js.executeScript("return Ext.Ajax.isLoading();").toString());
	             Boolean isAjaxRunning = Boolean.valueOf(js.executeScript("return Ext.Ajax.isLoading();").toString()); //returns true if ajax call is currently in progress
//	             Boolean isAjaxRun = Boolean .valueOf(js.executeScript("return((window.jQuery != null) && (jQuery.active === 0))").toString());
	             if (!isAjaxRunning.booleanValue()) 
	             {
	            	 status = true;
	            	 break;
	             }
	             Thread.sleep(1000);//wait for one secnod then check if ajax is completed
			 }
	      //only remove this print statements after this has been tested
	      logger.info("Entering Wait....");
	      logger.info("Document window.getComputedStyle(document.body).cursor = "+ js.executeScript("return window.getComputedStyle(document.body).cursor;").toString());
	      //After ajax call is over, page is not displaying fields(elements) in UI so we are calling below code to display fields in UI
	      status = common.SafeAction(Common.o.getObject("eleQPBAccountNo"), "eleQPBAccountNo","eleQPBAccountNo");
	      //only remove this print statements after this has been tested
	      logger.info("End Wait....1");
//	      //we are waiting to load the elements into page
	      WebDriverWait wait = new WebDriverWait(ManagerDriver.getInstance().getWebDriver(),Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")));
	      wait.until(ExpectedConditions.stalenessOf(element));// (By.id(readAttriID1)));
//	      js.executeScript("arguments[0].scrollIntoView();",element);
//	      clickTriangle.moveToElement(element);
	      logger.info("End Wait....2");		
       return status; 
	}
	
	public static boolean JavaScriptDynamicWait(WebElement sElement, JavascriptExecutor js) throws Exception
	{
		boolean status = false;
	        for (int i = 1; i <= Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")); i++) {
	            logger.info("Document Ajax State = "
	                          + js.executeScript(
	                                       "return Ext.Ajax.isLoading();")
	                                       .toString());
	            Boolean isAjaxRunning = Boolean.valueOf(js
	                          .executeScript(
	                                       "return Ext.Ajax.isLoading();") //returns true if ajax call is currently in progress
	                          .toString());
	            if (!isAjaxRunning.booleanValue()) {
	            	status = true;
	                   break;
	            }
	            Thread.sleep(1000);//wait for one secnod then check if ajax is completed
	        }
	     WebDriverWait wait = new WebDriverWait(ManagerDriver.getInstance().getWebDriver(),
	                   Integer.parseInt(HTML.properties
	                                 .getProperty("VERYLONGWAIT")));
	     wait.until(ExpectedConditions.stalenessOf(sElement));// (By.id(readAttriID1)));
//	     logger.info("End Wait....2");
     return status;
	}
	
	public static boolean JavaScript(JavascriptExecutor js) throws Exception
	{
		boolean status = false;
	        for (int i = 1; i <= Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")); i++) {
	            logger.info("Document Ajax State = "
	                          + js.executeScript(
	                                       "return Ext.Ajax.isLoading();")
	                                       .toString());
	            Boolean isAjaxRunning = Boolean.valueOf(js
	                          .executeScript(
	                                       "return Ext.Ajax.isLoading();") //returns true if ajax call is currently in progress
	                          .toString());
	            if (!isAjaxRunning.booleanValue()) {
	            	status = true;
	                   break;
	            }
	            Thread.sleep(1000);//wait for one secnod then check if ajax is completed
	        }
        return status;
	}
	
	public static boolean waitForJQueryProcessing(int timeOutInSeconds) {
 	    boolean jQcondition = false;
 	    try {
 	        new WebDriverWait(ManagerDriver.getInstance().getWebDriver(), timeOutInSeconds) {
 	        }.until(new ExpectedCondition<Boolean>() {

 	            @Override
 	            public Boolean apply(WebDriver driverObject) {
 	                return (Boolean) ((JavascriptExecutor) driverObject)
 	                        .executeScript("return !!window.jQuery && window.jQuery.active == 0");
 	            }
 	        });
 	        jQcondition = (Boolean) ((JavascriptExecutor) ManagerDriver.getInstance().getWebDriver())
 	                .executeScript("return window.jQuery != undefined && jQuery.active === 0");
 	        return jQcondition;
 	    } catch (Exception e) {
 	        e.printStackTrace();
 	       logger.error("Thread ID = " + Thread.currentThread().getId() + " Error Occured =" +e.getMessage(), e);
 	    }
 	    return jQcondition;
 	}
	
	/**
	 * @function VerifyCalendarControlExistence 
	 * @param ObjDatepicker
	 * @return
	 * @throws Exception
	 */
	public static Boolean VerifyCalendarControlExistence(String ObjDatepicker) throws Exception
	{
		Common common = CommonManager.getInstance().getCommon();
		boolean status =false;
		String sObject = "ele"+ObjDatepicker+"";
		status=common.SafeAction(Common.o.getObject(sObject),"ele","ele"); //eleMailDateDtepicker
		List<WebElement> sTable = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleDatepickrControl"));
		for(WebElement ele : sTable)
		{
			logger.info(sTable.size());
			String roleValue=ele.getAttribute("role");
//			String readText = ele.getText();
			String sLabelId = ele.getAttribute("id");
				if (sLabelId.contains("datepicker") && roleValue.contains("grid") ) 
				{
					logger.info(sLabelId);
					int i=common.ElementSize(By.id(sLabelId));
					status=common.ElementDisplayed(By.id(sLabelId));
					if(status && i==1)
					{
						logger.info("Calendar control is displayed in UI - " + sLabelId);
						HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "Calendar Control should be displayed in UI","Calendar control dispalyed", "PASS");
						status=common.SafeAction(Common.o.getObject("eleTodayDtepickerBtn"),"ele","ele");
						//String ActValue=Common.ReadElementGetAttribute(Common.o.getObject("edtMailDate"),"value");
						String ActValue=common.ReadElementFromListEditBox(Common.o.getObject("edtMailDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
						status = common.CompareStringResult("Mail Date  - Canadian Policy", SCRCommon.ReturnCurrentDate(), ActValue);
					}
					else
					{
						logger.info("NO Calendar control is displayed in UI");
						HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "Calendar Control should be displayed in UI","No calendar control dispalyed", "FAIL");
					}
				}
				else
				{
						logger.info("No element matches with Datepicker criteria in UI");
						HTML.fnInsertResult(HTML.properties.getProperty("testcasename"), HTML.properties.getProperty("methodName"), "Calendar Control should be displayed in UI","No elements foudn with Datepicker properties", "FAIL");
				}
		}
		return status;		
	}
	
	/**
	 * /**
	 * @function use to click the radio button according the text given in the application
	 * @param obj
	 * @param readCol
	 * @param actionCol
	 * @param strReadString
	 * @param actionObjetName(true/false)
	 * @param sTagName
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean radioButtonClickAction(By obj, int readCol, int actionCol, String strReadString, String actionObjetName, String sTagName) throws Exception
	{	
	  boolean Status=false;
	  boolean SearchString=false;
	  boolean ActionObject=false;
	  JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();
	  WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
	  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
	  for(int i = 0; i <= allrows.size()-1; i++)
	  {
		  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
		  String readText = Cells.get(readCol).getText();
		  if (readText.contains(strReadString))
		  {
			  SearchString = true;
			  List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
	          for(WebElement element: CellElements)
	          {
	        	  String objName = element.getAttribute("inputvalue");
	        	  if(objName.contains(actionObjetName))
        	  		{
	        		  	Status = true;
	        		  	ActionObject = true;
            			element.click();
            			JavaScriptDynamicWait(element, js);
            			break;
	            	}
	          }
	     }
		 if(ActionObject == true)
		 {
			 break;
		 }
	  }
	  if(SearchString)
	  {
		  	logger.info("Search String available in the table. '" + strReadString + "'");  
		  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "PASS");
			if(ActionObject)
			  {
					logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
				  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "PASS");
					Status = true;					  
			  }
			  else
			  {
					logger.info("Search and click on object in the table cell and object name is '" + actionObjetName + "'");  
				  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + actionObjetName + "'","System searched object in the table and clicked on object. object name is '" + actionObjetName + "'", "FAIL");
					Status = false;
			  }
	  }
	  else
	  {
		  	logger.info("Search String not available in the table. '" + strReadString + "'");  
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + actionObjetName + "'", "FAIL");
			Status = false;
	  }
	  return Status;
	}
	
	/**
	 * This function to verify billing method in policy/ acc summary page under pending transaction table
	 * In data table first parameter is table element, 2nd is billing method to verify, third one is whether its anchor tag or div/span 
	 * @param funValue
	 * @return
	 */
		public Boolean VerifyPendingTransTable(String funValue)
		{
			
			Common common = CommonManager.getInstance().getCommon();
			Boolean status = false;
			String value = null;
			String[] sValue = funValue.split(":::");
			logger.info(sValue[0]);
			
			try
			{
			
				value = common.GetTextFromTable(Common.o.getObject(sValue[0]), 5,5,sValue[1]);
				status = common.CompareStringResult("verify display of biling Number", sValue[1], value);
				if (sValue[2].toUpperCase().equalsIgnoreCase("SPAN") || sValue[2].toUpperCase().equalsIgnoreCase("DIV"))
				{
					status = common.ActionOnTable(Common.o.getObject(sValue[0]), 5, 5, sValue[1], sValue[2].toLowerCase());
					if (status)
					{
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Billing method '"+sValue[1]+"' should display without hyperlink","'"+sValue[1]+"' displayed without hyperlink", "PASS");
					}
					else
					{
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Billing method '"+sValue[1]+"' should display without hyperlink","'"+sValue[1]+"' NOT displayed without hyperlink", "FAIL");
					}
				}
				else if (sValue[2].toUpperCase().equalsIgnoreCase("LINK"))
				{
					status = common.ActionOnTable(Common.o.getObject(sValue[0]), 5,5, sValue[1], "a");
					if (status)
					{
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Billing method '"+sValue[1]+"' should display with hyperlink","'"+sValue[1]+"' displayed with hyperlink", "PASS");
					}
					else
					{
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Billing method '"+sValue[1]+"' should display with hyperlink","'"+sValue[1]+"' NOT displayed with hyperlink", "FAIL");
					}
				}
				else
				{
					status = false;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return status;
			
		}
		/**
		 * This function to verify billing method in policy/ acc summary page under pending transaction table
		 * first parameter is table element, 2nd is billing method to verify, third one is whether its anchor tag or div/span 
		 * @param funValue
		 * @return
		 */
			public Boolean VerifyBillMethodInPendingTransTable(String funValue)
			{
				
				Common common = CommonManager.getInstance().getCommon();
				Boolean status = false;
				String value = null;
				String[] sValue = funValue.split(":::");
				logger.info(sValue[0]);
				
				try
				{
				
					value = common.GetTextFromTable(Common.o.getObject(sValue[0]), 5,5,sValue[1]).trim();
					status = common.CompareStringResult("verify display of biling Number", sValue[1], value);
					if (sValue[2].toUpperCase().equalsIgnoreCase("SPAN") || sValue[2].toUpperCase().equalsIgnoreCase("DIV"))
					{
						status = common.ActionOnTable(Common.o.getObject(sValue[0]), 5, 5, sValue[1], sValue[2].toLowerCase());
						if (status)
						{
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Billing method '"+sValue[1]+"' should display without hyperlink","'"+sValue[1]+"' displayed without hyperlink", "PASS");
						}
						else
						{
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Billing method '"+sValue[1]+"' should display without hyperlink","'"+sValue[1]+"' NOT displayed without hyperlink", "FAIL");
						}
					}
					else if (sValue[2].toUpperCase().equalsIgnoreCase("LINK"))
					{
						status = common.ActionOnTable(Common.o.getObject(sValue[0]), 5,5, sValue[1], "a");
						if (status)
						{
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Billing method '"+sValue[1]+"' should display with hyperlink","'"+sValue[1]+"' displayed with hyperlink", "PASS");
						}
						else
						{
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Billing method '"+sValue[1]+"' should display with hyperlink","'"+sValue[1]+"' NOT displayed with hyperlink", "FAIL");
						}
					}
					else
					{
						status = false;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return status;	
			}
			
	/**
	* used to validate fields value for ICON polices
	* @return status
	 * @throws NumberFormatException 
	 * @throws Exception 
	*/
	public boolean IconValidation(String valEleName, String strExpected) throws NumberFormatException, Exception
	{
          boolean status = false;
//          boolean pageExist = false;
          int iElementCount;
          String strTxt;
          String vElement = valEleName.substring(3);
          String vType = vElement.substring(0, 3).toUpperCase();
          Common common = CommonManager.getInstance().getCommon();
//        pageExist = common.WaitUntilClickable(Common.o.getObject(vElement), 30);
          iElementCount = common.ElementSize(Common.o.getObject(vElement));
          try {
              if (iElementCount == 1)
              {
                 WebElement element1 = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(vElement));
                 switch (vType)
                 {
                 	case "ELE":	                           
                       switch(valEleName)
                       {
                                 case "valeleAccountSummary_Address":
                                 case "valelePolicyInfo_Address":
                                        strTxt =  element1.getText();
                                        status=common.CompareStringResult("Address value" ,strExpected,strTxt);
                                        break;
                                 case "valeleAccountSummary_Phone":
                                 case "valeleAccountContacts_Phone":
                                 case "valelePolicyInfo_PhoneNo":
                                 case "valeleAccountContacts_PhoneAccountHolder":
                                        String pattern = "(\\D)";
                                        strExpected=strExpected.replaceAll(pattern, "");
                                        strTxt =  element1.getText().toUpperCase();
                                        strTxt=strTxt.replaceAll(pattern, "");
                                        status = verifyAndUpdateResults(strTxt,strExpected,vElement);
                                        break;
                                 case "valelePolicyInfo_BOR_EndDate":
                                        strExpected = SCRCommon.returnDate(Integer.parseInt(strExpected));
                                        break;
                                 case "valelePolicyInfo_NPNNumber":
                                	 	String[] number = strExpected.split(":");
                         	  			String NPNNumber = number[1].trim();
                						String NpnValue = common.ReadElement(Common.o.getObject("elePolicyInfo_NPNNumber"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
                         	  			status=common.CompareStringResult("NPN Number" , NpnValue ,NPNNumber);
                                     break;
                               case "valelePolicyInfo_NPNFirstName":
                            	   String[] CombinedValue = strExpected.split(":");
                             		String[] FName = CombinedValue[0].split(",");
                    	  			String FirstName = FName[1].trim();
                      	  			String Fname = common.ReadElement(Common.o.getObject("elePolicyInfo_NPNFirstName"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
                     	  			status=common.CompareStringResult("First Name" , Fname ,FirstName);
                     	  			
                      	  	  break;
                                 case "valelePolicyInfo_NPNLastName":
                                	 String[] CombinedValue1 = strExpected.split(":");
                              		String[] LName = CombinedValue1[0].split(",");
                     	  			String LastName = LName[0].trim();
                       	  			String Lname = common.ReadElement(Common.o.getObject("elePolicyInfo_NPNLastName"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
                     	  			status=common.CompareStringResult("Last Name" , Lname ,LastName);
                                  break;
                                 case "valelePolicyInfo_NPNMiddleName":
                             		String[] CombinedValue2 = strExpected.split(":");
                             		String[] MName = CombinedValue2[0].split(",");
                    	  			String MiddleName = MName[2].trim();
                    	  			String Mname = common.ReadElement(Common.o.getObject("elePolicyInfo_NPNMiddleName"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
                     	  			status=common.CompareStringResult("Middle Name" , Mname ,MiddleName);
                               break;
                                 default:
                                        strTxt =  element1.getText().toUpperCase();
                                        status = verifyAndUpdateResults(strTxt,strExpected,vElement);
                                        break;
                       } 
                       break;
                 	case "EDT":
                       strTxt = common.ReadElementGetAttribute(Common.o.getObject(vElement), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
                       status = verifyAndUpdateResults(strTxt,strExpected,vElement);
                       break;
                 }
              }
              else
              {
                     HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verifying Element '"+vElement+"'","Element not displayed on the screen.", "FAIL");
              }
              if(!status)
              {
            	  status = true;
              } 
   			} catch (IOException e) {
				e.printStackTrace();
				status = true;
			}
          return status;
       }

	/*public boolean IconValidation(String valEleName, String strExpected) throws Exception
	{
		boolean status = false;
		boolean pageExist = false;
		String strTxt;
		String vElement = valEleName.substring(3);
		String vType = vElement.substring(0, 3).toUpperCase();
		
		Common common = CommonManager.getInstance().getCommon();
		pageExist = common.WaitUntilClickable(Common.o.getObject(vElement), 30);	
		
		if (pageExist)
		{
			WebElement element1 = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(vElement));
			switch (vType)
			{
			case "ELE":
				 
				if(vElement.toUpperCase().contains("ADDRESS"))
				{
					strTxt =  element1.getText();
					status=common.CompareStringResult("Address value" ,strExpected,strTxt);
					
				}
				else if(vElement.toUpperCase().contains("PHONE"))
				{
					String pattern = "(\\D)";
					strExpected=strExpected.replaceAll(pattern, "");
					strTxt =  element1.getText().toUpperCase();
					strTxt=strTxt.replaceAll(pattern, "");
					status = verifyAndUpdateResults(strTxt,strExpected,vElement);
				}
				else
				{
				strTxt =  element1.getText().toUpperCase();
				status = verifyAndUpdateResults(strTxt,strExpected,vElement);
				}
				break;
			case "EDT":
				strTxt = common.ReadElementGetAttribute(Common.o.getObject(vElement), "value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
				status = verifyAndUpdateResults(strTxt,strExpected,vElement);
				break;
			}
		}
		else
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verifying Element '"+vElement+"'","Element not displayed on the screen.", "FAIL");
		}
		
		return status;
	}*/
	
	public boolean verifyAndUpdateResults(String strAct , String strExp, String ele) throws IOException
	{
		boolean status = false;
		ele =ele.substring(3); 
		if (strAct.toUpperCase().contentEquals(strExp.toUpperCase()))
		{
			logger.info("Value for the element '" +ele + "' matches with expected value '" + strExp );
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify value for element '"+ ele +"'. Expected value (from ICON): '" + strExp + "'","'"+ele +"' - Actual value in PC '"+strAct+"' matches with the expected value", "PASS");
			status = true;
		}
		else
			
		{
			logger.info("Value for the element '" +ele + "' matches with expected value '" + strExp );
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify value for element '"+ ele +"'. Expected value (from ICON): '" + strExp + "'","'"+ele +"' - Actual value in PC '"+strAct+"' is not matching with expected value", "FAIL");
		}
		
		return status;
	}
	
	public Boolean importIconValue()
	{
		boolean status = false;
		XlsxReader sXL = XlsxReader.getInstance();
		IconReader iCN = IconReader.getInstance();
		HashMap<String,Object> whereConstraintSelect = new HashMap<String,Object>();	
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		ArrayList<HashMap<String,Object>> resultListRows= new ArrayList<HashMap<String,Object>>();
		ArrayList<HashMap<String,Object>> resultListRows1= new ArrayList<HashMap<String,Object>>();
		ArrayList<HashMap<String,Object>> resultListRows2= new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> whereConstraintUpdate = new HashMap<String,Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		String sValueToReadPCDB = null;
		String sPCMappingValue = null;
		String sPCIconValue = null;
		try
		{
			whereConstraintSelect.clear();
			whereConstraintSelect.put(PCConstants.ID, tcID);
			resultListRows = sXL.executeSelectQuery(PCConstants.SHEET_Read, whereConstraintSelect);
			for(HashMap<String,Object> processRow: resultListRows)
			{
				sValueToReadPCDB = (String)processRow.get(PCConstants.ValueToReadPCDB);
				//System.out.println("ValueToReadPCDB "+sValueToReadPCDB);
				whereConstraintSelect.clear();
				whereConstraintSelect.put(PCConstants.PC, sValueToReadPCDB);
				resultListRows1 = iCN.executeSelectQuery(PCConstants.SHEET_Name_Mapping, whereConstraintSelect);
				if(!(resultListRows.size() ==0))
				{
					for(HashMap<String,Object> processRow1: resultListRows1)
					{
						sPCMappingValue = (String)processRow1.get(PCConstants.E2E_Fields);
						//System.out.println("E2E_Fields "+sPCMappingValue);
						break;
					}

					whereConstraintSelect.clear();
					whereConstraintSelect.put(PCConstants.E2E_Scenario_Name, tcID);
					whereConstraintSelect.put(PCConstants.FieldName, sPCMappingValue);
					resultListRows2 = iCN.executeSelectQuery(PCConstants.SHEET_E2E_Testdata, whereConstraintSelect);
					if(!(resultListRows.size() ==0))
					{
						for(HashMap<String,Object> processRow2: resultListRows2)
						{	
							sPCIconValue = (String)processRow2.get(PCConstants.FieldValues);
							//System.out.println("FieldValues "+sPCIconValue);
							String sColumnValue = "val"+sValueToReadPCDB+"";
							updateColumnNameValues.clear();
							whereConstraintUpdate.clear();
							updateColumnNameValues.put(sColumnValue, sPCIconValue);
							whereConstraintUpdate.put(PCConstants.ID, tcID);
							status = sXL.executeUpdateQuery(PCConstants.SHEET_ICONValidation,  updateColumnNameValues,  whereConstraintUpdate );
							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sPCIconValue+ " Value should update in the ICONValidation Sheet",""+sPCIconValue+ " Value updated in the ICONValidation Sheet","PASS");
							if(!status)
							{
								HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sPCIconValue+ " Value should update in the ICONValidation Sheet",""+sPCIconValue+ " Value is not updated in the ICONValidation Sheet","FAIL");
							}
							break;
						}
						
					}else
					{
						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sPCMappingValue+" Value should present in the E2E_Testdata Sheet",""+sPCMappingValue+ " is not present in the E2E_Testdata Sheet","FAIL");
					}
				}else
				{
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sValueToReadPCDB+" should present in the Name_Mapping Sheet",""+sValueToReadPCDB+" is not present in the Name_Mapping Sheet","FAIL");
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	
	/**
	 * function - to get the icon data from ICON mapping sheet for ODS
	 * @return true/false
	 */
	public Boolean importICONODSValue()
	{
		Boolean status = true;
		if(HTML.properties.getProperty("EXECUTIONAPP").contains("ODS"))
		{
			logger.info("Starting to import the ICON Values to Flat File");
			IconReader iCN = IconReader.getInstance();
			FlatFile write = FlatFile.getInstance();
			HashMap<String,Object> whereConstraint = new HashMap<String,Object>();	
			ArrayList<HashMap<String,Object>> resultListRows = new ArrayList<HashMap<String,Object>>();
			ArrayList<HashMap<String,Object>> resultListRows1 = new ArrayList<HashMap<String,Object>>();
			whereConstraint.clear();
			whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
			resultListRows = iCN.executeSelectQuery(PCConstants.SHEET_Trans, whereConstraint);
			for(HashMap<String,Object> processRow: resultListRows)
			{
				String sFieldName = (String)processRow.get("Field_Name");
				String sFieldValue = (String)processRow.get("Field_Value");
				whereConstraint.clear();
				whereConstraint.put("Field_Name", sFieldName);
				resultListRows1 = iCN.executeSelectQuery("Mapping", whereConstraint);
				for(HashMap<String,Object> processRow1: resultListRows1)
				{
					String sODSFieldName = (String)processRow1.get("ODS");
					status = write.write(PCThreadCache.getInstance().getProperty("TCID"), "IconValidation", sODSFieldName, sFieldValue, "Input", FlatFileName());
				}
			}
			logger.info("Completed the ICON Values Import");	
		}
		return status;
	}
	
	/**
	 * function - to get the icon data from ICON mapping sheet for PC
	 * @return true/false
	 */
	public Boolean importICONPCValue()
	{
		logger.info("Starting to import the ICON Values to Flat File");
		boolean status = true;
		XlsxReader sXL = XlsxReader.getInstance();
		IconReader iCN = IconReader.getInstance();
		HashMap<String,Object> whereConstraintSelect = new HashMap<String,Object>();	
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		ArrayList<HashMap<String,Object>> resultListRows= new ArrayList<HashMap<String,Object>>();
		ArrayList<HashMap<String,Object>> resultListRows1= new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> whereConstraintUpdate = new HashMap<String,Object>();
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		String transFieldName = null;
		String transFieldValue = null;
		String sPCMappingField = null;
		try
		{
			whereConstraintSelect.clear();
			whereConstraintSelect.put(PCConstants.ID, tcID);
			resultListRows = iCN.executeSelectQuery("Trans", whereConstraintSelect);
			if(resultListRows.size() > 0)
			{
				for(HashMap<String,Object> processRow: resultListRows)
				{
					transFieldName = (String)processRow.get("Field_Name");
					transFieldValue = (String)processRow.get("Field_Value");
					whereConstraintSelect.clear();
					whereConstraintSelect.put("Field_Name", transFieldName);
					resultListRows1 = iCN.executeSelectQuery("Mapping", whereConstraintSelect);
					if(resultListRows.size() > 0)
					{
							for(HashMap<String,Object> processRow1: resultListRows1)
							{
									sPCMappingField = (String)processRow1.get("PC");
									if(!sPCMappingField.equals(""))
									{
										updateColumnNameValues.clear();
										whereConstraintUpdate.clear();
										whereConstraintUpdate.put(PCConstants.ID, tcID);
										updateColumnNameValues.put("val"+sPCMappingField, transFieldValue);
										status = sXL.executeUpdateQuery(PCConstants.SHEET_ICONValidation,  updateColumnNameValues,  whereConstraintUpdate);
									}
									if(transFieldName.equalsIgnoreCase("ACCOUNTNUMBER") || transFieldName.equalsIgnoreCase("POLICYNUMBER"))
									{
										status = updateAccountPolicyNumberforIcon(transFieldName, transFieldValue);
									}
							}
					}
					else
					{
						logger.info("Trans field ID "+transFieldName+" is not available in the Mapping Sheet");
					}
				}
			}else{
				logger.info("TestCase ID "+tcID+" is not available in the Trans Sheet");
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
	 * @function this will update in the account & policy number in the excel sheet
	 * @param strField
	 * @param strValue
	 * @return true/false
	 */
	public static Boolean updateAccountPolicyNumberforIcon(String strField, String strValue)
	{
		Boolean blnStatus = false;
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		XlsxReader sXL = XlsxReader.getInstance();
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		HashMap<String,Object> whereConstraintUpdate = new HashMap<String,Object>();
		switch(strField.toUpperCase())
		{
			case "ACCOUNTNUMBER":
				updateColumnNameValues.clear();
				whereConstraintUpdate.clear();
				whereConstraintUpdate.put(PCConstants.ID, tcID);
				updateColumnNameValues.put("edtaccountNumber", strValue);
				blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraintUpdate);
				
				updateColumnNameValues.clear();
				whereConstraintUpdate.clear();
				whereConstraintUpdate.put(PCConstants.ID, tcID);
				updateColumnNameValues.put("AccountNumber", strValue);
				blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_ICONValidation,  updateColumnNameValues,  whereConstraintUpdate);
				break;
			case "POLICYNUMBER":
				String policyNumberSearch = strValue.substring(strValue.length()-6);
				updateColumnNameValues.clear();
				whereConstraintUpdate.clear();
				whereConstraintUpdate.put(PCConstants.ID, tcID);
				updateColumnNameValues.put("edtPolicyNumber", policyNumberSearch);
				blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHPOLICY,  updateColumnNameValues,  whereConstraintUpdate);
				
				updateColumnNameValues.clear();
				whereConstraintUpdate.clear();
				whereConstraintUpdate.put(PCConstants.ID, tcID);
				updateColumnNameValues.put("PolicyNumber", strValue);
				blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_ICONValidation,  updateColumnNameValues,  whereConstraintUpdate);
				break;
		}
		return blnStatus;
	}
  
	
	public boolean OutputforODS(String colName) throws Exception
	{
		logger.info("Started Output for ODS Function");
		Boolean Status = false;
		Boolean ElementExist = false;
		String sFileName = FlatFileName();
		String[] sSplitColValue = colName.split(":::");
		Common common = CommonManager.getInstance().getCommon();
//		FlatFileWriter write = FlatFileWriter.getInstance(sFileName);
		FlatFile sReadWrite= FlatFile.getInstance();
		String sGetPCValue = null;
		for(String element : sSplitColValue)
		{
			String elementType = element.substring(0, 3);
			String elementName = element.substring(3);
			switch(elementType.toUpperCase())
			{
				case "EDT":
					ElementExist = common.ElementExist(Common.o.getObject(element)); 
					if(ElementExist)
					{
						sGetPCValue = common.ReadElementGetAttribute(Common.o.getObject(element), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), elementName, sGetPCValue, "OUTPUT", sFileName);
					}
					break;
				case "ELE":
					ElementExist = common.ElementExist(Common.o.getObject(element)); 
					if(ElementExist)
					{
						sGetPCValue = common.ReadElement(Common.o.getObject(element), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						/*if(elementName.toUpperCase().contains("ACCOUNTNUMBER"))
						{*/
							Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), elementName, sGetPCValue, "OUTPUT", sFileName);
						/*}else if(elementName.toUpperCase().contains("POLICYNUMBER"))
						{
							Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "PolicyNumber", sGetPCValue, "OUTPUT", sFileName);
						}else
						{
							Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), elementName, sGetPCValue, "OUTPUT", sFileName);
						}*/
					}
					break;
				case "DAT":
					sGetPCValue = ReturnCurrentDate();
					Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), elementName, sGetPCValue, "OUTPUT", sFileName);
					break;
			}
		}
		return Status;
	}
	
	public String FlatFileName()
	{
		 XlsxReader xls = XlsxReader.getInstance();
		 String tcID = PCThreadCache.getInstance().getProperty("TCID");
		 String[] splittcID = tcID.split("_");
		 String sFileName = null;
		 HashMap<String,Object> whereConstraint = new HashMap<String,Object>();	
		 ArrayList<HashMap<String,Object>> resultListRows= new ArrayList<HashMap<String,Object>>();	 		 
		 whereConstraint.clear();
		 whereConstraint.put(PCConstants.ID, splittcID[0]);
		 resultListRows = xls.executeSelectQuery(PCConstants.SHEET_FLATFILE, whereConstraint);
		 for (HashMap<String,Object> processRow: resultListRows)
		 {
			 sFileName = (String)processRow.get("FlatFileName");
			 break;
		 }
		return sFileName;
	}
	
	/**
	 * function use to get the data for ODS Queries
	 * @param SheetName
	 * @return boolean
	 * @throws Exception
	 */
   public boolean InputforODS(String SheetName) throws Exception
   {
	   boolean Status = true;
	   	if(HTML.properties.getProperty("EXECUTIONAPP").contains("ODS"))
	   	{
		   	logger.info("Started Input for ODS Function");
	    	 XlsxReader xls = XlsxReader.getInstance();
	    	 String tcID = PCThreadCache.getInstance().getProperty("TCID");
	    	 String sFileName = FlatFileName();
//			 FlatFileWriter write = FlatFileWriter.getInstance(sFileName);
	    	 FlatFile sReadWrite= FlatFile.getInstance();
	    	 int rowcount = xls.getRowCount(SheetName);
		     for(int i = 2; i <= rowcount; i++)
		     {
		    	  if(xls.getCellData(SheetName, "ID", i).equals(PCThreadCache.getInstance().getProperty("TCID")))  
		    	  {
		    		  	int colcount = xls.getColumnCount(SheetName);
						for(int j = 2; j <= colcount; j++)
						{
								String ColName = xls.getCellData(SheetName, j, 1);
								String value = xls.getCellData(SheetName, j, i);
								if (!ColName.isEmpty() && !value.isEmpty())
				  				{
						  				String element = ColName.substring(0, 3);
						  				String elementName = ColName.substring(3);
						  				if(!value.isEmpty())
						  				{
						  					if(element.toUpperCase().contains("ODS") || element.toUpperCase().contains("EDT") || element.toUpperCase().contains("EDJ") || element.toUpperCase().contains("LST") || element.toUpperCase().contains("LSJ"))
						  					{
						  						Status = sReadWrite.write(tcID, PCThreadCache.getInstance().getProperty("methodName"), elementName, value, "input", sFileName);
						  					}
						  					else if(element.toUpperCase().contains("VAL"))
						  					{
						  						String strIconElement[] = elementName.split("_");
					  							Status = sReadWrite.write(tcID, PCThreadCache.getInstance().getProperty("methodName"), strIconElement[1], value, "input", sFileName);	
					  						}
						  				}
				  				}		
						}
		    	  }
		     }
	   	}
    	return Status;
   }
   
	public boolean TableHandling(String sFuncValue)
	{
		Common common = CommonManager.getInstance().getCommon();
		Boolean status = true;
		Boolean tcAvailable = false;
		String[] funcValue = sFuncValue.split(":::");
		int sRowCount = 0;
		XlsxReader sXL = XlsxReader.getInstance();// new XlsxReader(   PCThreadCache.getInstance().getProperty("DataSheetName)
		try
		{
			int rowcount = sXL.getRowCount(funcValue[0]);
			  for(int i = 2; i <= rowcount; i++)
			  {
				  String TCID = PCThreadCache.getInstance().getProperty("TCID");
				  String Iteration = PCThreadCache.getInstance().getProperty("Iteration");
				  String TCIDAdd = sXL.getCellData(funcValue[0], "ID", i);
				  String TCIDIteration = sXL.getCellData(funcValue[0], "Iteration", i);
				  if(TCIDAdd.equals(TCID) && TCIDIteration.equals(Iteration))  
//				  if(sXL.getCellData(funcValue[0], "ID", i).equals(PCThreadCache.getInstance().getProperty("TCID")) && sXL.getCellData(funcValue[0], "Iteration", i).contains(PCConstants.Iteration))  
				  {
					  tcAvailable = true;
					  int colcount = sXL.getColumnCount(funcValue[0]);
						for(int j = 2; j <= colcount; j++)
						{
								String ColName = sXL.getCellData(funcValue[0], j, 1);
								if (!ColName.isEmpty())
				  				{
									String value = sXL.getCellData(funcValue[0], j, i);
						  				if(!value.equals(""))
						  				{
						  					String[] valueSplit = value.split(":::");
							  				String elementType = ColName.substring(0, 3);
							  				String elementName = ColName.substring(3);
							  				String concatName = null;
							  				if(elementType.toUpperCase().equals("TED") || elementType.toUpperCase().equals("TBL"))
							  				{
								  				sRowCount = TableRowCount(Common.o.getObject(funcValue[1]));
							  				}	
							  				switch(elementType.toUpperCase())
							  				{
							  					case "TED":
							  						status = SCRCommon.TableCellValueClick(Common.o.getObject(funcValue[1]), sRowCount, Integer.parseInt(valueSplit[1]), "single", "div");
							  			    		concatName =  "edt"+elementName;
							  						status = common.SafeAction(Common.o.getObject(concatName), valueSplit[0], concatName);
							  			    		break;
							  					case "TLS":
							  						status = SCRCommon.TableCellValueClick(Common.o.getObject(funcValue[1]), sRowCount, Integer.parseInt(valueSplit[1]), "single", "div");
							  						concatName =  "lst"+elementName;
							  						status = common.SafeAction(Common.o.getObject(concatName), valueSplit[0], concatName);
							  						break;
							  					case "TLJ":
							  						status = SCRCommon.TableCellValueClick(Common.o.getObject(funcValue[1]), sRowCount, Integer.parseInt(valueSplit[1]), "single", "div");
							  						concatName =  "lsj"+elementName;
							  						status = common.SafeAction(Common.o.getObject(concatName), valueSplit[0], concatName);
							  						break;
							  					case "TEJ":
							  						status = SCRCommon.TableCellValueClick(Common.o.getObject(funcValue[1]), sRowCount, Integer.parseInt(valueSplit[1]), "single", "div");
							  			    		concatName =  "edj"+elementName;
							  						status = common.SafeAction(Common.o.getObject(concatName), valueSplit[0], concatName);
							  			    		break;
							  					case "TSJ":
							  						status = SCRCommon.TableCellValueClick(Common.o.getObject(funcValue[1]), sRowCount, Integer.parseInt(valueSplit[1]), "single", "div");
							  			    		concatName =  "slj"+elementName;
							  						status = common.SafeAction(Common.o.getObject(concatName), valueSplit[0], concatName);
							  			    		break;
							  					case "TBL":
							  						status = SCRCommon.TableCellValueClick(Common.o.getObject(funcValue[1]), sRowCount, Integer.parseInt(valueSplit[1]), "single", "div");
							  						break;
							  					case "ELE":
							  			    		status = common.SafeAction(Common.o.getObject(ColName), "", ColName);
							  						break;
							  					case "ELJ":
							  			    		status = common.SafeAction(Common.o.getObject(ColName), "", ColName);
							  						break;
							  					case "EDT":
							  			    		status = common.SafeAction(Common.o.getObject(ColName), valueSplit[0], ColName);
							  						break;
							  					case "EDJ":
							  			    		status = common.SafeAction(Common.o.getObject(ColName), valueSplit[0], ColName);
							  						break;
							  					case "LSJ":
							  			    		status = common.SafeAction(Common.o.getObject(ColName), valueSplit[0], ColName);
							  						break;
							  					case "LST":
							  			    		status = common.SafeAction(Common.o.getObject(ColName), valueSplit[0], ColName);
							  						break;
							  				}
						  				}
				  				}
						}
						if(!tcAvailable)
						{
							logger.info(""+PCThreadCache.getInstance().getProperty("TCID")+" is not avilable in the "+funcValue[0]+"");
						}
				  }
			  }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return status;
	}
	
	public static boolean TableCellValueClick(By id, int sRow, int sCol, String ActionClick , String sTagName) throws Exception 
    {      
           Common common = CommonManager.getInstance().getCommon();
           boolean Status = false;
           Actions objActions = new Actions(ManagerDriver.getInstance().getWebDriver());
           WebElement  mytable = common.returnObject(id);
           List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
           sRow--;
           List<WebElement> Columns_row = rows_table.get(sRow).findElements(By.tagName("td"));
           Columns_row.get(sCol).getText();
List<WebElement> CellElements = Columns_row.get(sCol).findElements(By.tagName(sTagName));
              for(WebElement element: CellElements)
              {
                                switch(ActionClick)
                                {
                                       case "single":
                                              element.click();
                                              Status = true;
                                       break; 
                                       case "double":
                                              objActions.doubleClick(element).build().perform();
                                              Status = true;
                                       break;
                                }
                                break;
              }
//         }
         /*  if(SearchString)
           {
                         logger.info("Search String available in the table. '" + strReadString + "'");  
                         HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and search string is  '" + strReadString + "'", "PASS");
           }
           else
           {
                         logger.info("Search String not available in the table. '" + strReadString + "'");  
                        HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System not searched string in table and search string is  '" + strReadString + "'", "FAIL");
                        Status = false;
           }*/
           return Status;
    }
	
	public static Boolean coverageSheet(String strSheetName)
	{
		logger.info("Entering into coverage details function");
		Common common = CommonManager.getInstance().getCommon();
		Boolean status = true;
		Boolean checkBoxFound = false;
		String sChooseCoverage = null;
		String sCoverageName = null;
		String sFieldName = null;
		String sFieldValue = null;
		String strFieldType = null;
		String strTablePage = null;
		String strCoverageType = null;
		Boolean blnTableHeaderBody = false;
		int loop;
		XlsxReader xls = XlsxReader.getInstance();
        String tcID = PCThreadCache.getInstance().getProperty("TCID");
        HashMap<String,Object> whereConstraint = new HashMap<String,Object>();    
        ArrayList<HashMap<String,Object>> resultListRows= new ArrayList<HashMap<String,Object>>();                   
        whereConstraint.clear();
        whereConstraint.put(PCConstants.ID, tcID);
        whereConstraint.put(PCConstants.Iteration, PCThreadCache.getInstance().getProperty("Iteration"));
        resultListRows = xls.executeSelectQuery(strSheetName, whereConstraint);
        try{
        	 if(resultListRows.size()>0)
             {
           	  		Outer:
	           	  	for(HashMap<String,Object> processRow: resultListRows)
	     	        {
	           	  			loop = 0;
		           	  		sChooseCoverage = (String)processRow.get("ChooseCoverage");
		           	  		sCoverageName = (String)processRow.get("Coverages");
		           	  		strTablePage = (String)processRow.get("TablePage");
		           	  		strCoverageType = (String)processRow.get("CoverageType");
			           	  	if(sChooseCoverage.toUpperCase().equals("YES") && !sChooseCoverage.isEmpty())
							  {
								  checkBoxFound = selectCheckBoxUsingLabelName(sCoverageName);
								  if(!checkBoxFound){
									  logger.info(""+sCoverageName+" is not present in the screen");
									  status = false;
									  break Outer;
								  }
							  }
		           	  		for(int i=1; i<=10; i++)
		           	  		{
		               	  		sFieldName = (String)processRow.get("Field"+i);
		               	  		sFieldValue  = (String)processRow.get("FieldValue"+i);
 			               	  		if (!(sFieldName == null)  && !(sFieldValue == null) && !sFieldName.isEmpty() && !sFieldValue.isEmpty())
			               	  		{
				     						if(!(strCoverageType == null)  && !(sCoverageName == null) && !strCoverageType.isEmpty() && !sCoverageName.isEmpty() && loop == 0)
				     						{										
				     							if(status){
				     								blnTableHeaderBody = getCoverageTableHeader(sCoverageName, strCoverageType, PCThreadCache.getInstance().getProperty(PCConstants.CoverageID));
				     								loop = 1;
				     							}else{
				     								logger.info(""+sCoverageName+" is not present in the screen for identifying the table");
				     								break Outer;
				     							}
				     						}
				     						strFieldType = sFieldName.substring(sFieldName.length() -3);
				     						switch(strFieldType.toUpperCase())
											{
												case "TBL":
												case "TBD":
												case "TBA":
												case "TBP":
												case "RMV":
													if(blnTableHeaderBody){
														status = coverageTableHandling(sFieldName, sFieldValue, sCoverageName, strTablePage);
													}
													break;
												case "DEF":
													sFieldName = sFieldName.substring(0, sFieldName.length()-3);
													status = common.SafeAction(Common.o.getObject(sFieldName), sFieldValue, sFieldName);
													break;
												default:
													status = fillCoverage(sCoverageName, sFieldName, sFieldValue);
													break;
											}
				     						if(!status){
				     							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), 
				     									PCThreadCache.getInstance().getProperty("methodName"), 
				     									"Coverage "+sCoverageName+" should be filled for the "+sFieldName+" field", 
				     									"Error in the "+sCoverageName+" Coverage for the "+sFieldName+" field","FAIL");
				     							break Outer;
				     						}
			               	  		}
		           	  		}
	     	        }
             }else{
            	 logger.info("TestCaseID "+tcID+" or Iteration "+PCThreadCache.getInstance().getProperty("Iteration")+" is not available in the "+strSheetName+" Sheet");
            	 HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "TestCaseID "+tcID+" and Iteration "+PCThreadCache.getInstance().getProperty("Iteration")+" should available in the "+strSheetName+" Sheet", "TestCaseID "+tcID+" or Iteration "+PCThreadCache.getInstance().getProperty("Iteration")+" is not available in the "+strSheetName+" Sheet", "FAIL");
            	 status = false;
             }
        }catch(Exception e){
        	e.printStackTrace();
        }
		return status;
	}
	
	
	
	
	/**
	 * @function This will use to fill the fields of the particular coverage
	 * @param CoverageName
	 * @param sFieldName
	 * @param sFieldValue
	 * @return true/false
	 * @throws IOException
	 */
	public static Boolean fillCoverage(String CoverageName, String sFieldName, String sFieldValue) throws IOException
	{
		logger.info("Entering into fill the "+CoverageName+" fields");
		Boolean status = false;
		Boolean sParenetLabel = false;
		Boolean sChildLabel = false;
		String sPCLabelValue = null; 
		String sLabelID = null;
		String sLabelConcatListID = null;
		String sSplitFieldName = (String)sFieldName.substring(3);
		String sElementType = sFieldName.substring(0, 3);
		String sID = null;
		String[] sLabelIDSplit = null;
		String[] sSplit = null;
		String interanlID = null;
		try{
    		List <WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleCoverages"));
    		Outer:
    		for(WebElement sText : abcd)
			{
				String sElementText = sText.getText();
				System.out.println(sElementText);
					if(sElementText.toUpperCase().equals(CoverageName.toUpperCase()))
					{
						sParenetLabel = true;
						sID = sText.getAttribute("id");
						sSplit = sID.split("-");
						By sTargetID = By.id(sSplit[0]);
						WebElement sTargetLegend = CommonManager.getInstance().getCommon().returnObject(sTargetID);
						List <WebElement> sTargetLegendDiv = sTargetLegend.findElements(By.tagName("label"));
							for(WebElement sLabel : sTargetLegendDiv)
							{
								sPCLabelValue= (String)sLabel.getText();
//								System.out.println("Excel Value is "+sSplitFieldName+" & length is "+sSplitFieldName.length());
//								System.out.println("PC Value is "+sPCLabelValue+" & length is "+sPCLabelValue.length());
								if(sSplitFieldName.toUpperCase().equals(sPCLabelValue.toUpperCase()))
								{
										sChildLabel = true;
										sLabelID = sLabel.getAttribute("id");
										sLabelIDSplit =sLabelID.split("-");
										sLabelConcatListID = sLabelIDSplit[0].concat("-inputEl");
							        	try 
							        	{
							        		switch (sElementType.toUpperCase()) 
							        		{
												case "LSJ":
													status = CommonManager.getInstance().getCommon().SafeAction(By.id(sLabelConcatListID), sFieldValue, sFieldName);
													break;
												case "EDJ":
													status = CommonManager.getInstance().getCommon().SafeAction(By.id(sLabelConcatListID), sFieldValue, sFieldName);
													break;
												case "LST":
													status = CommonManager.getInstance().getCommon().SafeAction(By.id(sLabelConcatListID), sFieldValue, sFieldName);
													break;
												case "EDT":
													status = CommonManager.getInstance().getCommon().SafeAction(By.id(sLabelConcatListID), sFieldValue, sFieldName);
													break;
												case "CLR":
													status = CommonManager.getInstance().getCommon().SafeAction(By.id(sLabelConcatListID), sFieldValue, sFieldName);
													break;
												case "RDO":
													String getRdoID = getRadioButtonID(sLabelID, sFieldValue);
													status = CommonManager.getInstance().getCommon().SafeAction(By.id(getRdoID), sFieldValue, sFieldName);
													break;
												case "BTN":
													interanlID = sSplit[0].concat(":CoverageSchedule:BP7ScheduleInputSet:BP7ScheduledItemsLV_tb:ToolbarButton-btnInnerEl");
													status = CommonManager.getInstance().getCommon().SafeAction(By.id(interanlID), sFieldValue, sFieldName);
													break;
											}
										} catch (Exception e) 
										{
											e.printStackTrace();
										}
								}
								if(sChildLabel)
								{
									break Outer;
								}
							}
							if(!sChildLabel){
								logger.info("Unable to find the coverage field '"+sFieldName+"'");
								HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'"+sFieldName+"' Field should available in the '"+CoverageName+"' Coverage", "'"+sFieldName+"' Field is not available in the '"+CoverageName+"' Coverage", "FAIL");
							}
					}
				/*if(sParenetLabel == true)
				{
					break;
				}*/
			}
			if(!sParenetLabel)
			{
				logger.info("Unable to find the coverage '"+sFieldName+"'");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'"+CoverageName+"' Coverage should available in the system", "'"+CoverageName+"' Coverage is not available in the system", "FAIL");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			sSplitFieldName = null;
    		sLabelID = null;
    		sLabelConcatListID = null;
    		sElementType = null;
    		sID = null;
    		sLabelIDSplit = null;
    		sSplit = null;
		}
		return status;
	}
	
	/**
	 * @function Convert the label id attribute into radio button id attribute
	 * @param sId
	 * @param option
	 * @return String
	 */
	public static String getRadioButtonID(String sId, String option)
	{
		String radioID = null;
		String[] splitID = sId.split("-");
		switch (option.toUpperCase()) 
		{
			case "YES":
				radioID = splitID[0].concat("_true-boxLabelEl");
				break;
			case "NO":
				radioID = splitID[0].concat("_false-boxLabelEl");
				break;
		}
		return radioID;
	}
	
	/**
	 * @function This function will select the check box by using the label name
	 * @param sFuncValue
	 * @return true/false
	 * @throws IOException
	 */
	public static Boolean selectCheckBoxUsingLabelName(String sFuncValue) throws IOException
	{
		Boolean status = false;
		Boolean sLabelFound = false;
		String getIDofLabel = null;
		String[] sSplitID = null;
		String actualID = null;
		List <WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleCoverages"));
		try{
    		sLabelLoop:
    		for(WebElement element : abcd)
			{
				String sElementText = element.getText();
					if(sElementText.equals(sFuncValue))
					{
						logger.info(""+sFuncValue+" label found in the Screen");
						sLabelFound = true;
						getIDofLabel = element.getAttribute("id");
						PCThreadCache.getInstance().setProperty(PCConstants.CoverageID, getIDofLabel);
						sSplitID = getIDofLabel.split("-");
						actualID = sSplitID[0].concat(":_checkbox");
						try {	
							status = CommonManager.getInstance().getCommon().SafeAction(By.id(actualID), "elj"+sFuncValue+"", "elj"+sFuncValue+"");
							logger.info("Clicked the checkbox of "+sFuncValue+"");
							
						} catch (Exception e) {
//							 TODO Auto-generated catch block
							logger.info("Unable to click the elj"+sFuncValue+" checkbox");
							e.printStackTrace();
						}
					}
					if(sLabelFound)
					{
						break sLabelLoop;
					}
			}
			if(!sLabelFound){
				logger.info(""+sFuncValue+" is not present in the Screen");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'"+sFuncValue+"' CheckBox should available in the system", "'"+sFuncValue+"' CheckBox is not available in the system", "FAIL");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			getIDofLabel = null;
			sSplitID = null;
			actualID = null;
		}
		return status;
	}
	
//	code to iterate all the columns from the excelsheet using excel query
	/*HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
	ArrayList<HashMap<String,Object>> resultListRows= new ArrayList<HashMap<String,Object>>();
	whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
	whereConstraint.put("Coverage", sFuncValue);
	resultListRows = sXL.executeSelectQuery("Coverages", whereConstraint);
	
	for (HashMap<String, Object> sProcessRow : resultListRows) 
	{
	psProcessRow
		
	    for (String key : sProcessRow.keySet()) 
	    {
	    	String getKeyValue = (String)sProcessRow.get(key);
	    	System.out.println(key);
	    	System.out.println(getKeyValue);	
	    }
	}
	*/
	
	/**
	 * @function Use to iterate scheduleRating DateSheet 
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean scheduleRating(String sheetName)
	{
		logger.info("Entering into Schedule Rating ");
		Common common = CommonManager.getInstance().getCommon();
		XlsxReader sXL = XlsxReader.getInstance();
		Boolean status = false;
		Boolean tcAvailable = false;
		String TCID = null;
		String TCIDAdd = null;
		String sCategory = null;
		String sFieldName = null;
		String sFieldValue = null;
		String sIteration = null;
		String ratingTable = null;
		int rowcount;
		int colcount;
		try
		{
		  rowcount = sXL.getRowCount(sheetName);
		  Outer:
		  for(int i = 2; i <= rowcount; i++)
		  {
			  TCID = PCThreadCache.getInstance().getProperty("TCID");
			  TCIDAdd = sXL.getCellData(sheetName, "ID", i);
			  sIteration = sXL.getCellData(sheetName, "Iteration", i);
			  if(TCIDAdd.equals(TCID) && sIteration.equalsIgnoreCase(PCThreadCache.getInstance().getProperty(PCConstants.Iteration)))
			  {
				  tcAvailable = true;
				  colcount = sXL.getColumnCount(sheetName);
				  sCategory = sXL.getCellData(sheetName, "Category", i);
					for(int j = 2; j <= colcount; j++)
					{
						sFieldValue = sXL.getCellData(sheetName, j, i);
						sFieldName = sXL.getCellData(sheetName, j, 1);
						if (!sFieldName.isEmpty() && !sFieldValue.isEmpty() && !sFieldName.equalsIgnoreCase("Category"))
		  				{
							ratingTable = sFieldName.substring(3);
								switch(ratingTable.toUpperCase())
				    			{
					    				case "CREDIT(-)/DEBIT(+)":
					    				case "JUSTIFICATION":
					    					status = fillScheduleRating(sCategory, sFieldName, sFieldValue);
					    					break;
					    				default:
					    					status = common.SafeAction(Common.o.getObject(sFieldName), sFieldValue, sFieldName);
					    					break;
					    					
				    			}
								if(sFieldName.equalsIgnoreCase("eljCommonOk"))	
    							{
		    						System.out.println(sFieldName);
    							}
		  				}
					}
					if(!status){
						break Outer;
					}
			  }
		  }
			if(!tcAvailable)
			{
			  logger.info(""+TCID+" is not available in the "+sheetName+" Sheet");
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+TCID+" should avilable in the "+sheetName+"", ""+TCID+" is not avilable in the "+sheetName+"", "FAIL");
			}else if(!status)
			{
			  logger.info("Problem in filling the "+sCategory+" Coverage");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			TCID = null;
    		TCIDAdd = null;
    		sCategory = null;
    		sFieldName = null;
			sFieldValue = null;
			ratingTable =null;
		}
		return status;
	}
	
	
	
	/**
	 * This function use to fill the data for Schedule Mod Justification table
	 * @param sCateGoryName
	 * @param sFieldName
	 * @param sFieldValue
	 * @return
	 * @throws Exception
	 */
	public static Boolean fillScheduleRating(String sCateGoryName, String sFieldName, String sFieldValue)
	{
		logger.info("Entering into filling the schedule rating");
		Common common = CommonManager.getInstance().getCommon();
        boolean SearchString= false;
        boolean Status = false;
        WebElement  mytable =null;
        List<WebElement> rows_table = null;
        List<WebElement> CellElements = null;
        String sElement = null;
        List<WebElement> Columns_row = null;
        String getCategoryName = null;
        try{
	        mytable = common.returnObject(Common.o.getObject("eleScheduleRateTbl"));
	        rows_table = mytable.findElements(By.tagName("tr"));
	        sElement = sFieldName.substring(3);
	        Outer:
	        for(int i=1;i<rows_table.size();i++)
	        {
	        	Columns_row = rows_table.get(i).findElements(By.tagName("td"));
	        	getCategoryName = Columns_row.get(0).getText();
	        	if(getCategoryName.equalsIgnoreCase(sCateGoryName))
	    		{
	        		logger.info(""+sCateGoryName+" is available in the rating table");
	        		SearchString= true;
	    			switch(sElement.toUpperCase())
	    			{
	    				case "CREDIT(-)/DEBIT(+)":
	    					CellElements = Columns_row.get(3).findElements(By.tagName("div"));
	    					 for(WebElement element: CellElements)
	                         {
	                        	 element.click();
	                        	 Status = common.SafeAction(Common.o.getObject(sFieldName), sFieldValue, sFieldName);
	                        	 Status = common.SafeAction(Common.o.getObject("eleScheduleRatingTitle"), "", "eleScheduleRatingTitle");
	                         }
	    					break;
	    				case "JUSTIFICATION":
	    					CellElements = Columns_row.get(4).findElements(By.tagName("div"));
	   					 	for(WebElement element: CellElements)
	                            {
	   					 			element.click();
	   					 			Status = common.SafeAction(Common.o.getObject(sFieldName), sFieldValue, sFieldName);
	   					 			Status = common.SafeAction(Common.o.getObject("eleScheduleRatingTitle"), "", "eleScheduleRatingTitle");
	                            }
	    					break;
	    			}
	    		}
	        	if(SearchString)
	        	{
	        		break Outer;
	        	}
	        }
	        if(!SearchString || !Status)
	        {
	        	logger.info(""+sCateGoryName+" is not available in the rating table");
	        	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'"+sCateGoryName+"' should available in the Schedule Mod Justification", "'"+sCateGoryName+"' is not available in the Schedule Mod Justification", "FAIL");
	        }
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally
        {
        	mytable =null;
            rows_table = null;
            CellElements = null;
            sElement = null;
            Columns_row = null;
            getCategoryName = null;
        }
		return Status;
	}
	
	/**
	 * @function Use to iterate scheduleRating DateSheet 
	 * @return true/false
	 * @throws Exception
	 * @author Sojan
	 */
	public static boolean tableHandling(String sheetName, String tablePathLocator, String eleColumnSearchLocator, String pageElement)
	{
		logger.info("Entering into Table Function");
		Common common = CommonManager.getInstance().getCommon();
		XlsxReader sXL = XlsxReader.getInstance();
		Boolean blnStatus = false;
		Boolean blnTcAvailable = false;
		String strTCID = null;
		String strTCIDAdd = null;
		String strCategory = null;
		String strFieldName = null;
		String strFieldValue = null;
		String strIteration = null;
		String strTableObject = null;
		String strFunctionName = null;
		int intRowCount;
		int intColCount;
		int intTblColCount;
		int intTblRowCount;
		try
		{
			intRowCount = sXL.getRowCount(sheetName);
		  Outer:
		  for(int i = 2; i <= intRowCount; i++)
		  {
			  strTCID = PCThreadCache.getInstance().getProperty("TCID");
			  strTCIDAdd = sXL.getCellData(sheetName, "ID", i);
			  strIteration = sXL.getCellData(sheetName, "Iteration", i);
			  if(strTCIDAdd.equals(strTCID) && strIteration.equalsIgnoreCase(PCThreadCache.getInstance().getProperty(PCConstants.Iteration)))
			  {
				  blnTcAvailable = true;
				  intColCount = sXL.getColumnCount(sheetName);
					for(int j = 2; j <= intColCount; j++)
					{
						
						strFieldValue = sXL.getCellData(sheetName, j, i);
						strFieldName = sXL.getCellData(sheetName, j, 1);
						if (!strFieldName.isEmpty() && !strFieldValue.isEmpty())
		  				{
//							strRatingTable = strFieldName.substring(3);
							strTableObject = strFieldName.substring(strFieldName.length() - 3);
							switch(strTableObject.toUpperCase())
							{
									case "TBD":
										intTblColCount = getTableHeaderColumn(strFieldName, eleColumnSearchLocator);
										intTblRowCount = getTableRowCount(tablePathLocator);
										if(intTblColCount>=1 && intTblRowCount >=1){
											
											blnStatus = clickTableCell(tablePathLocator, intTblRowCount, intTblColCount, "Single", "div");
											blnStatus = common.SafeAction(Common.o.getObject(strFieldName), strFieldValue, strFieldName);
											blnStatus = common.SafeAction(Common.o.getObject(pageElement), pageElement, pageElement);
										}
										break;
									case "TBA":
										intTblColCount = getTableHeaderColumn(strFieldName, eleColumnSearchLocator);
										intTblRowCount = getTableRowCount(tablePathLocator);
										if(intTblColCount>=1 && intTblRowCount >=1){
											blnStatus = clickTableCell(tablePathLocator, intTblRowCount, intTblColCount, "Single", "a");
										}
										break;
									case "TBP":
										intTblColCount = getTableHeaderColumn(strFieldName, eleColumnSearchLocator);
										intTblRowCount = getTableRowCount(tablePathLocator);
										if(intTblColCount>=1 && intTblRowCount >=1){
											blnStatus = clickTableCell(tablePathLocator, intTblRowCount, intTblColCount, "Single", "div");
											blnStatus = common.SafeAction(Common.o.getObject(strFieldName), strFieldValue, strFieldName);
	//										blnStatus = common.SafeAction(Common.o.getObject(pageElement), pageElement, pageElement);
										}
										break;
									case "RMV":
										intTblColCount = getTableHeaderColumn(strFieldName, eleColumnSearchLocator);
										blnStatus = SCRCommon.selectTableRowToRemove(intTblColCount, tablePathLocator, strFieldValue);
//										blnStatus = common.SafeAction(Common.o.getObject(strFieldName), strFieldValue, strFieldName);
										break;
									case "TBI":
										intTblColCount = getTableHeaderColumn(strFieldName, eleColumnSearchLocator);
										intTblRowCount = getTableRowCount(tablePathLocator);
										if(intTblColCount>=1 && intTblRowCount >=1){
											
											blnStatus = clickTableCell(tablePathLocator, intTblRowCount, intTblColCount, "Single", "img");
											blnStatus = common.SafeAction(Common.o.getObject(pageElement), pageElement, pageElement);
										}
										break;
									case "TBF":
										strFunctionName = strFieldName.substring(0, strFieldName.length() - 3);
										blnStatus = common.coverageFunctionCall(strFunctionName, strFieldValue);
										break;
									default:
										blnStatus = common.SafeAction(Common.o.getObject(strFieldName), strFieldValue, strFieldName);
										break;		
							}
		  				}
					}
					if(!blnStatus){
						break Outer;
					}
			  }
		  }
			if(!blnTcAvailable)
			{
			  logger.info("'"+strTCID+"' TCID or '"+PCThreadCache.getInstance().getProperty(PCConstants.Iteration)+"' IterationNo is not available in the "+sheetName+" Sheet");
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'"+strTCID+"' TCID and '"+PCThreadCache.getInstance().getProperty(PCConstants.Iteration)+"' IterationNo should avilable in the "+sheetName+"", "'"+strTCID+"' TCID or '"+PCThreadCache.getInstance().getProperty(PCConstants.Iteration)+"' IterationNo is not avilable in the "+sheetName+"", "FAIL");
			}else if(!blnStatus)
			{
			  logger.info("Problem in filling the "+strCategory+" Coverage");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			strTCID = null;
    		strTCIDAdd = null;
    		strCategory = null;
    		strFieldName = null;
			strFieldValue = null;
//			strRatingTable =null;
			strTableObject = null;
		}
		return blnStatus;
	}
	
	/**
	 * This function use to select the row from the table
	 * @param intColumnNumber
	 * @param intTotalNoOfRowNumber
	 * @return true/false
	 */
	public static Boolean selectTableRowToRemove(int intColumnNumber, String tblBodyLocator, String strSearchString)
	{
		Boolean blnStatus = false;
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(tblBodyLocator));
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
        for(int i = 0; i <= allrows.size()-1; i++)
        {
                List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
                String readText = Cells.get(intColumnNumber).getText();
                if (readText.equals(strSearchString))
                {  
                	blnStatus = true;
                	Cells.get(0).click();  
                	break;
                }        
		}
        if(!blnStatus)
        {
        	logger.info("Remove table value "+strSearchString+" is not available in the table");
        }
		return blnStatus;
	}
	
	/**
	 * This function use to select the row from the table
	 * @param intColumnNumber
	 * @param intTotalNoOfRowNumber
	 * @return true/false
	 */
	public static Boolean selectCoverageTableRowToRemove(int intColumnNumber, String tblBodyLocator, String strSearchString)
	{
		Boolean blnStatus = false;
		WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(By.id(tblBodyLocator));
		List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
        for(int i = 0; i <= allrows.size()-1; i++)
        {
                List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
                String readText = Cells.get(intColumnNumber).getText();
                if (readText.equals(strSearchString))
                {  
                	blnStatus = true;
                	Cells.get(0).click();  
                	break;
                }        
		}
        if(!blnStatus)
        {
        	logger.info("Remove table value "+strSearchString+" is not available in the table");
        }
		return blnStatus;
	}
	
	/**
	 * @function This will use to fill the fields of the particular coverage
	 * @param CoverageName
	 * @param sFieldName
	 * @param sFieldValue
	 * @return true/false
	 * @throws IOException
	 */
	public static int getTableHeaderColumn(String sHeaderName, String strObjectName) throws IOException
	{
		Common common = CommonManager.getInstance().getCommon();
        boolean blnSearchString= false;
        String strTableHeader = null;
        String splitHeader = null;
        String exactHeader = null;
        String strHeaderName[] = null;
        WebElement  mytable = common.returnObject(Common.o.getObject(strObjectName));
        List<WebElement> rows_table = mytable.findElements(By.tagName("span"));
        int intHeaderColumn = 0;
        int intEmptyColumn = 0;
        splitHeader = sHeaderName.substring(3);
        exactHeader = splitHeader.substring(0, splitHeader.length() - 3);
        strHeaderName = exactHeader.split("_");
        for(int i=1;i<=rows_table.size();i++)
        {
        	strTableHeader = rows_table.get(i).getText();
//        	System.out.println(strTableHeader);
        	if(strTableHeader.equalsIgnoreCase(strHeaderName[0])){
        		logger.info("Table Header "+strHeaderName[0]+" found");
        		intHeaderColumn = i - intEmptyColumn ;
        		blnSearchString = true;
        		break;
        	}else if(strTableHeader.equals("")){
        		intEmptyColumn = intEmptyColumn + 1;
        	}

        }
        if(!blnSearchString){
        	logger.info("Table Header "+strHeaderName[0]+" is not found in the Add Class Table");
        }
		return intHeaderColumn;
	}
	
	/**
	 * @function This will use to fill the fields of the particular coverage
	 * @param CoverageName
	 * @param sFieldName
	 * @param sFieldValue
	 * @return true/false
	 * @throws IOException
	 */
	public static int getCoverageTableHeaderColumn(String sHeaderName, String strObjectName) throws IOException
	{
		Common common = CommonManager.getInstance().getCommon();
        boolean blnSearchString= false;
        String strTableHeader = null;
        String splitHeader = null;
        String exactHeader = null;
        String strHeaderName[] = null;
        WebElement  mytable = common.returnObject(By.xpath(strObjectName));
        List<WebElement> rows_table = mytable.findElements(By.tagName("span"));
        int intHeaderColumn = 0;
//      int intEmptyColumn = 1;
        int intEmptyColumn = 0;
        splitHeader = sHeaderName.substring(3);
        exactHeader = splitHeader.substring(0, splitHeader.length() - 3);
        strHeaderName = exactHeader.split("_");
        for(int i=1;i<rows_table.size();i++)
        {
        	strTableHeader = rows_table.get(i).getText();
//        	System.out.println(strTableHeader);
        	if(strTableHeader.equalsIgnoreCase(strHeaderName[1])){
        		logger.info("Table Header "+strHeaderName[1]+" found");
        		intHeaderColumn = i - intEmptyColumn ;
        		blnSearchString = true;
        		break;
        	}else if(strTableHeader.equals("")){
        		intEmptyColumn = intEmptyColumn + 1;
        	}

        }
        if(!blnSearchString){
        	logger.info("Table Header "+strHeaderName[1]+" is not found in the Add Class Table");
        }
		return intHeaderColumn;
	}
	
	/**
	 * function use to click the table blank cell to make the table field to be visible
	 * @param strTblLocator
	 * @param sRow
	 * @param sCol
	 * @param ActionClick
	 * @param sTagName
	 * @author CPG7081
	 * @modified
	 * @date
	 * @return true/false
	 */
	public static boolean clickTableCell(String strTblLocator, int sRow, int sCol, String ActionClick , String sTagName)
    {      
           Common common = CommonManager.getInstance().getCommon();
           boolean Status = false;
           Actions objActions = new Actions(ManagerDriver.getInstance().getWebDriver());
           WebElement  mytable = common.returnObject(Common.o.getObject(strTblLocator));
           List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
           sRow--;
           List<WebElement> Columns_row = rows_table.get(sRow).findElements(By.tagName("td"));
           List<WebElement> CellElements = Columns_row.get(sCol).findElements(By.tagName(sTagName));
           for(WebElement element: CellElements)
           {
                 switch(ActionClick.toUpperCase())
                 {
                        case "SINGLE":
                               element.click();
                               logger.info("Clicked on the table cell");
                               Status = true;
                        break; 
                        case "DOUBLE":
                               objActions.doubleClick(element).build().perform();
                               Status = true;
                        break;
                 }
                 break;
           }
           return Status;
    }
	
	/**
	 * function use to click the table blank cell to make the table field to be visible(specific to handle the coverages table)
	 * @param strTblLocator
	 * @param sRow
	 * @param sCol
	 * @param ActionClick
	 * @param sTagName
	 * @author CPG7081 
	 * @return true/false
	 */
	public static boolean clickCoverageTableCell(String strTblLocator, int sRow, int sCol, String ActionClick , String sTagName) 
    {      
           Common common = CommonManager.getInstance().getCommon();
           boolean Status = false;
           Actions objActions = new Actions(ManagerDriver.getInstance().getWebDriver());
           WebElement  mytable = common.returnObject(By.id(strTblLocator));
           List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
           sRow--;
           List<WebElement> Columns_row = rows_table.get(sRow).findElements(By.tagName("td"));
           List<WebElement> CellElements = Columns_row.get(sCol).findElements(By.tagName(sTagName));
           for(WebElement element: CellElements)
           {
                 switch(ActionClick.toUpperCase())
                 {
                        case "SINGLE":
                               element.click();
                               logger.info("Clicked on the table cell");
                               Status = true;
                        break; 
                        case "DOUBLE":
                               objActions.doubleClick(element).build().perform();
                               Status = true;
                        break;
                 }
                 break;
           }
           return Status;
    }
	
	/**
     * @function used to count the number of row count in the table
     * @param locator
     * @return sRowCount(row count)
     */
    public static int getTableRowCount(String strLocator)
    {
    	Common common = CommonManager.getInstance().getCommon();
    	WebElement  table = common.returnObject(Common.o.getObject(strLocator));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int sRowCount = rows_table.size();
		return sRowCount; 
    }
    
    /**
     * @function used to count the number of row count in the table
     * @param locator
     * @return sRowCount(row count)
     */
    public static int getCoverageTableRowCount(String strLocator)
    {
    	Common common = CommonManager.getInstance().getCommon();
    	WebElement  table = common.returnObject(By.id(strLocator));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int sRowCount = rows_table.size();
		return sRowCount; 
    }
    
	public static boolean getCoverageNameID(String strCoverageName)
	{
		Boolean blnStatus = false;
		String getIDofLabel = null;
		try
		{
			List <WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleCoverages"));
    		for(WebElement element : abcd)
			{
				String sElementText = element.getText();
				if(sElementText.equals(strCoverageName))
				{
					logger.info(""+strCoverageName+" coverage found in the in the page for finding the button ID");
					blnStatus = true;
					getIDofLabel = element.getAttribute("id");
					PCThreadCache.getInstance().setProperty(PCConstants.CoverageID, getIDofLabel);
				}
				if(blnStatus){
					break;
				}
			}
    		if(!blnStatus)
    		{
    			logger.info("Coverage '"+strCoverageName+"' is not present in the screen");
    		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return blnStatus;
	}
	
	public static Boolean getCoverageTableHeader(String strCoverageName, String strTableName, String strCoverageID) throws IOException
	{
		Boolean blnStatus = false;
		String[] sSplitID = null;
		String strTableHeaderID = null;
		String strTableHeader = null;
		String strTableBody = null;
		String strAddRemoveID = null;
		String strHeaderBodyID = null;
		String strCoverageButtonID  =null;
		try{
				sSplitID = strCoverageID.split("-");
				switch(strTableName.toUpperCase())
				{
					case "ADD":
						strAddRemoveID  = (String)PCConstants.class.getDeclaredField(strTableName.toUpperCase()+"_AddRemove").get(null);
						strTableHeaderID = sSplitID[0].concat(":ScheduleInputSet:0");
						strCoverageButtonID = sSplitID[0].concat(strAddRemoveID);
						strTableHeader = ".//*[@id='"+strTableHeaderID+"']/div[2]/div/div";
						strTableBody = strTableHeaderID.concat("-body");
						PCThreadCache.getInstance().setProperty(PCConstants.TableHeader, strTableHeader);
						PCThreadCache.getInstance().setProperty(PCConstants.TableBody, strTableBody);
						PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
						blnStatus = true;
						break;
					case "ADDSOLEPROPRIETORS":
						strAddRemoveID  = (String)PCConstants.class.getDeclaredField(strTableName.toUpperCase()+"_AddRemove").get(null);
						strCoverageButtonID = sSplitID[0].concat(strAddRemoveID);
						PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
						blnStatus = true;
						break;
					default:
						strAddRemoveID  = (String)PCConstants.class.getDeclaredField(strTableName.toUpperCase()+"_AddRemove").get(null);
						strHeaderBodyID = (String)PCConstants.class.getDeclaredField(strTableName.toUpperCase()).get(null);
						strTableHeaderID = sSplitID[0].concat(strHeaderBodyID);
						strCoverageButtonID = sSplitID[0].concat(strAddRemoveID);
						strTableHeader = ".//*[@id='"+strTableHeaderID+"']/div[2]/div/div";
						strTableBody = strTableHeaderID.concat("-body");
						PCThreadCache.getInstance().setProperty(PCConstants.TableHeader, strTableHeader);
						PCThreadCache.getInstance().setProperty(PCConstants.TableBody, strTableBody);
						PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
						blnStatus = true;
						break;
				}
			if(!blnStatus)
			{
				logger.info("Problem in identify the "+strCoverageName+" table header and body ID");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should find the "+strCoverageName+" table header and body ID", "Problem in identify the "+strCoverageName+" table header and body ID", "FAIL");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			sSplitID = null;
			strTableName = null;
		}
		return blnStatus;
	}
	
	/**
	 * @function Use to iterate scheduleRating DateSheet 
	 * @return true/false
	 * @throws Exception
	 * @author Sojan
	 */
	public static boolean coverageTableHandling(String strFieldName, String strFieldValue, String strCoverageName, String strPageName)
	{
		logger.info("Entering into coverage table details");
		Common common = CommonManager.getInstance().getCommon();
		Boolean blnStatus = false;
		String strElementType = null;
		String strTableObject = null;
		String strAddRemoveButtonID = null;
		int intTblColCount;
		int intTblRowCount;
		try
		{		
			strElementType = strFieldName.substring(0,3);
			if(strElementType.contentEquals("ele") || strElementType.contentEquals("elj") || strElementType.contentEquals("lst") || strElementType.contentEquals("lsj") || strElementType.contentEquals("sel") || strElementType.contentEquals("edj")  || strElementType.contentEquals("edt"))
			{
				strTableObject = strFieldName.substring(strFieldName.length() - 3);
					switch(strTableObject.toUpperCase())
					{
						case "TBD":
							intTblColCount = getCoverageTableHeaderColumn(strFieldName, PCThreadCache.getInstance().getProperty(PCConstants.TableHeader));
							intTblRowCount = getCoverageTableRowCount(PCThreadCache.getInstance().getProperty(PCConstants.TableBody));
							if(intTblColCount>=1 && intTblRowCount >=1){
								blnStatus = clickCoverageTableCell(PCThreadCache.getInstance().getProperty(PCConstants.TableBody), intTblRowCount, intTblColCount, "Single", "div");
								blnStatus = common.SafeAction(Common.o.getObject(strFieldName), strFieldValue, strFieldName);
								blnStatus = common.SafeAction(Common.o.getObject(strPageName), strPageName, strPageName);
							}
							break;
						case "TBA":
							intTblColCount = getCoverageTableHeaderColumn(strFieldName, PCThreadCache.getInstance().getProperty(PCConstants.TableHeader));
							intTblRowCount = getCoverageTableRowCount(PCThreadCache.getInstance().getProperty(PCConstants.TableBody));
							if(intTblColCount>=1 && intTblRowCount >=1){
								blnStatus = clickCoverageTableCell(PCThreadCache.getInstance().getProperty(PCConstants.TableBody), intTblRowCount, intTblColCount, "Single", "div");
								blnStatus = common.SafeAction(Common.o.getObject(strFieldName), strFieldValue, strFieldName);
								blnStatus = common.SafeAction(Common.o.getObject(strPageName), strPageName, strPageName);
							}
							break;
						case "TBP":
							intTblColCount = getCoverageTableHeaderColumn(strFieldName, PCThreadCache.getInstance().getProperty(PCConstants.TableHeader));
							intTblRowCount = getCoverageTableRowCount(PCThreadCache.getInstance().getProperty(PCConstants.TableBody));
							if(intTblColCount>=1 && intTblRowCount >=1){
								
								blnStatus = clickCoverageTableCell(PCThreadCache.getInstance().getProperty(PCConstants.TableBody), intTblRowCount, intTblColCount, "Single", "div");
								blnStatus = common.SafeAction(Common.o.getObject(strFieldName), strFieldValue, strFieldName);
//										blnStatus = common.SafeAction(Common.o.getObject(pageElement), pageElement, pageElement);
							}
							break;
						case "TBL":
							/*if(strElementName.toUpperCase().equals("ADD") || strElementName.toUpperCase().contains("ADDBLANKETWAIVER") || strElementName.toUpperCase().contains("REMOVE"))
							{*/
								strAddRemoveButtonID = strFieldName.substring(0,strFieldName.length()-3);
//								blnStatus = getAddRemoveCoverageButton(strCoverageName, strAddRemoveButtonID, PCThreadCache.getInstance().getProperty(PCConstants.CoverageID));
								blnStatus = common.SafeAction(By.id(PCThreadCache.getInstance().getProperty(PCConstants.CoverageButtonID)), strFieldValue, strFieldName);
/*//									}
									else{
										blnStatus = common.SafeAction(Common.o.getObject(strFieldName), strFieldValue, strFieldName);
									}*/
							break;	
						case "RMV":
							String[] strRemoveValues = strFieldValue.split(":::");
							intTblColCount = getCoverageTableHeaderColumn(strRemoveValues[0], PCThreadCache.getInstance().getProperty(PCConstants.TableHeader));
							blnStatus = SCRCommon.selectCoverageTableRowToRemove(intTblColCount, PCThreadCache.getInstance().getProperty(PCConstants.TableBody), strRemoveValues[1]);
							strAddRemoveButtonID = strFieldName.substring(0,strFieldName.length()-3);
							blnStatus = getAddRemoveCoverageButton(strCoverageName, strAddRemoveButtonID, PCThreadCache.getInstance().getProperty(PCConstants.CoverageID));
							blnStatus = common.SafeAction(By.id(PCThreadCache.getInstance().getProperty(PCConstants.CoverageButtonID)), strFieldName, strFieldName);
							break;
						default:
							blnStatus = common.SafeAction(Common.o.getObject(strFieldName), strFieldValue, strFieldName);
							break;
					}
			}
			if(!blnStatus){
				logger.info("Failed in filling the '"+strFieldName+"' table object");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Should not fail in filling the '"+strFieldName+"' table object", "Failed in filling the '"+strFieldName+"' table object", "FAIL");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			strElementType = null;
//			String strElementName = null;
			strTableObject = null;
			strAddRemoveButtonID = null;
		}
		return blnStatus;
	}
	
	public static Boolean getAddRemoveCoverageButton(String strCoverageName, String strButtonType, String strCoverageNameID)
	{
		Boolean blnStatus = false;
		String[] sSplitID = null;
		String strCoverageButtonID = null;
		//		List <WebElement> abcd = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleCoverages"));
		try
		{
    		Outer:
    		/*for(WebElement element : abcd)
			{
				String sElementText = element.getText();
				if(sElementText.equals(strCoverageName))
				{
					logger.info(""+strCoverageName+" coverage found in the in the page for finding the button ID");
					sLabelFound = true;
					getIDofLabel = element.getAttribute("id");*/
					sSplitID = strCoverageNameID.split("-");
//					strButtonName = strButtonType.substring(3);
					switch(strButtonType.toUpperCase())
					{
						case "ADDBLANKETWAIVER":
							strCoverageButtonID = sSplitID[0].concat(":WC7BlanketWaiverOfSubroLV_tb:Add-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "EDITADDBLANKETWAIVER":
							strCoverageButtonID = sSplitID[0].concat(":WC7BlanketWaiverLV_tb:Add-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "EDITADDSPECIFICWAIVER":
							strCoverageButtonID = sSplitID[0].concat(":WC7SpecWOSubroForPolCchangeLV_tb:Add-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "ADD":
							strCoverageButtonID = sSplitID[0].concat(":ScheduleInputSet:Add-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "ADDFOREIGNVOLUNTARY":
							strCoverageButtonID = sSplitID[0].concat(":WC7ForeignVolunCovLV_tb:Add-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "ADDSOLEPROPRIETORS":
							strCoverageButtonID = sSplitID[0].concat(":ContactLV_tb:AddContactsButton-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "ADDEMPLOYEELEASING":
							strCoverageButtonID = sSplitID[0].concat(":WC7PolicyLaborContractorDetailsLV_tb:AddWC7PolicyLaborContractor-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "REMOVE":
							strCoverageButtonID = sSplitID[0].concat(":ScheduleInputSet:Remove-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "REMOVEBLANKET":
							strCoverageButtonID = sSplitID[0].concat(":WC7BlanketWaiverOfSubroLV_tb:Remove-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "REMOVESPECIFIC":
							strCoverageButtonID = sSplitID[0].concat(":WC7SpecificWaiverOfSubroLV_tb:Remove-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
							
						case "ADDFEDERALEMPLOYEE":
							strCoverageButtonID = sSplitID[0].concat(":WC7FedCovEmpLV_tb:Add-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
						case "ADDEMPLOYEELEASINGCLIENT":
							strCoverageButtonID = sSplitID[0].concat(":WC7PolicyLaborClientDetailsCondLV_tb:AddWC7PolicyLaborClientCond-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
									
						case "ADDEMPLOYEELEASINGCLIENTEXCLUSION":
							strCoverageButtonID = sSplitID[0].concat(":WC7PolicyLaborClientDetailsExclLV_tb:AddWC7PolicyLaborClientExcl-btnInnerEl");
							PCThreadCache.getInstance().setProperty(PCConstants.CoverageButtonID, strCoverageButtonID);
							blnStatus = true;
							break;
							
					}
			if(!blnStatus){
				logger.info(""+strCoverageName+" is not present in the page for finding the Add/Remove button ID");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "'"+strCoverageName+"' coverage should available in the page for finding the Add/Remove button ID", "'"+strCoverageName+"' CheckBox is not available in the page for finding the Add/Remove button ID", "FAIL");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sSplitID = null;
			strCoverageButtonID = null;
		}
		return blnStatus;
	}

	/**
	 * Function to wait for renewal process to start
	 * @return
	 * @throws Exception
	 */
	public static boolean renewalWorkFlowTimeOut1() throws Exception
    {
		logger.info("Waiting for the policy to be ready for renewal");
	   	 boolean status = false;
	   	 Common common = CommonManager.getInstance().getCommon();
	   	 status = common.WaitForElementExist(Common.o.getObject("eljPolicyInfo_RenewalCheck"), Integer.valueOf(PCConstants.RenewalWait));
	   	 if(!status)
	   	 {
	   		 logger.info("Renewal Flow is not ready to use hence try later");
	   	 }
	   	 return status;
    }
	
	/**
	 * Function to wait for renewal process to start
	 * @return
	 * @throws Exception
	 */
	public static boolean renewalWorkFlowTimeOut() throws Exception
    {
		logger.info("Waiting for the policy to be ready for renewal");
	   	 boolean status = false;
	   	 Common common = CommonManager.getInstance().getCommon();
	   	 for(int i=0;i<=10;i++)
	   	 {
//	   		status = common.WaitForElementExist(Common.o.getObject("eljPolicyInfo_RenewalCheck"), 2);
//	   		int size = common.ElementSize(Common.o.getObject("eljPolicyInfo_RenewalCheck"));
	   		String strStatusCheck = common.ReadElement(Common.o.getObject("eleSORPolicyStatus"), 10);
	   		 if(strStatusCheck.equals("Workflow (Wait Timeout/Manual)"))
	   		 {
	   			 logger.info("Renewal ready to go");
	   			 break;
	   		 }else
	   		 {
	   			 status = common.SafeAction(Common.o.getObject("eleCommonNext"), "", "eleCommonNext");
	   			 status = common.SafeAction(Common.o.getObject("eleCommonBack"), "", "eleCommonBack");
	   		 }
	   	 }
//	   	 status = common.WaitForElementExist(Common.o.getObject("eljPolicyInfo_RenewalCheck"), Integer.valueOf(PCConstants.RenewalWait));
	   	 if(!status)
	   	 {
	   		 logger.info("Renewal Flow is not ready to use hence try later");
	   		 status = true;
	   	 }
	   	 return status;
    }
	
	
	/**
	 * Function to wait for renewal process to start
	 * @return
	 * @throws Exception
	 */
	public static boolean renewalWorkFlowEditTransaction() throws Exception
    {
		 logger.info("Waiting for the policy to be ready for renewal");
	   	 boolean blnRenewalStatus = false;
	   	 Common common = CommonManager.getInstance().getCommon();
	   	 Outer:
	   	 for(int i=0;i<=10;i++)
	   	 {
	   		 for(int j=1;j<=2;j++)
	   		 {
	   			blnRenewalStatus = common.SafeAction(Common.o.getObject("eleCommonNext"), "", "eleCommonNext");
	   			blnRenewalStatus = common.SafeAction(Common.o.getObject("eleCommonBack"), "", "eleCommonBack");
	   			blnRenewalStatus = false;
	   		 }
	   		 int iEditTransactionSize = common.ElementSize(Common.o.getObject("eleCommonWorkFlowEditTransaction"));
	   		 if(iEditTransactionSize > 0)
	   		 {
	   			 blnRenewalStatus = true;
	   			 logger.info("Renewal Flow is ready to go");
	   			 break Outer;
	   		 }
	   	 }
	   	 if(!blnRenewalStatus)
	   	 {
	   		 logger.info("Renewal Flow is not ready to use hence try later");
	   		 HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Renewal Transaction should get ready", "Renewal Transaction should get ready", "FAIL");
	   	 }
	   	 return blnRenewalStatus;
    }
	
	/**
	 * Function to wait for renewal process to start
	 * @return
	 * @throws Exception
	 */
	public static boolean renewalFlowToBeReady() throws Exception
    {
		 logger.info("Waiting for the policy to be ready for renewal");
	   	 boolean blnRenewalStatus = false;
	   	 Common common = CommonManager.getInstance().getCommon();
	   	 Outer:
	   	 for(int i=0;i<=10;i++)
	   	 {
	   		 for(int j=1;j<=2;j++)
	   		 {
	   			blnRenewalStatus = common.SafeAction(Common.o.getObject("eleCommonNext"), "", "eleCommonNext");
	   			blnRenewalStatus = common.SafeAction(Common.o.getObject("eleCommonBack"), "", "eleCommonBack");
	   			blnRenewalStatus = false;
	   		 }
	   		 int iTimeOutCheck = common.ElementSize(Common.o.getObject("eleCommonEditTransaction"));
   			 if(iTimeOutCheck >0)
	   		 {
	   			 blnRenewalStatus = true;
	   			 blnRenewalStatus = common.SafeAction(Common.o.getObject("eleCommonEditTransaction"), "", "eleCommonEditTransaction");
	   			 logger.info("Renewal Flow is ready to go");
	   			 break Outer;
	   		 }else
   			 {
	   			int iEditTransactionSize = common.ElementSize(Common.o.getObject("eleCommonWorkFlowEditTransaction"));
	   			if(iEditTransactionSize>0)
	   			{
		   			 blnRenewalStatus = common.SafeAction(Common.o.getObject("eleCommonWorkFlowEditTransaction"), "", "eleCommonWorkFlowEditTransaction");
		   			 int iEditTransactionOkSize = common.ElementSize(Common.o.getObject("eleCommonWorkFlowEditTransaction"));
		   			 if(iEditTransactionOkSize > 0)
		   			 {
			   			 blnRenewalStatus = common.SafeAction(Common.o.getObject("eleEditTransOk"), "", "eleEditTransOk");
		   			 }
		   			 logger.info("Renewal Flow is ready to go");
		   			 break Outer;
	   			}
   			 }
	   	 }
	   	 if(!blnRenewalStatus)
	   	 {
	   		 logger.info("Renewal Flow is not ready to use hence try later");
	   		 HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Renewal Transaction should get ready", "Renewal Transaction is not ready to go", "FAIL");
	   	 }
	   	 return blnRenewalStatus;
    }
	
	/**
	 * Iteration the validation result page
	 * @param strFuncValue
	 * @return true/false
	 * @throws IOException 
	 */
	public static Boolean validate(String strFuncValue) throws IOException
	{
		Boolean blnStatus = false;
		XlsxReader sXL = XlsxReader.getInstance();
		HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
		ArrayList<HashMap<String,Object>> resultListRows= new ArrayList<HashMap<String,Object>>();
		whereConstraint.put("Iteration", PCThreadCache.getInstance().getProperty(PCConstants.Iteration));
		whereConstraint.put("TCValidationCase", strFuncValue);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		resultListRows = sXL.executeSelectQuery("Validation", whereConstraint);
		logger.info("Entered in to Validate function");
		if(!resultListRows.isEmpty())
		{
			for(HashMap<String,Object> processRow: resultListRows)
			{
				String strElement = (String)processRow.get("Element");
				String strExpectedResult = (String)processRow.get("ExpectedResult");
				String strVerificationType = (String)processRow.get("VerificationType");
				String strTableColumnNumber = (String)processRow.get("TableColumnNumber");
				String strCaseName = (String)processRow.get("TCValidationCase");
				/*if(!strTableColumnNumber.isEmpty())
				{
					iTblColNum=Integer.parseInt(strTableColumnNumber);
				}*/
				
				blnStatus = verifyResults(strElement, strExpectedResult, strVerificationType,strTableColumnNumber , strCaseName);
			}
		}else{
			logger.info("No Result Found in the Validation sheet");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Test Case '"+PCThreadCache.getInstance().getProperty("TCID")+"' and Iteration '"+PCThreadCache.getInstance().getProperty(PCConstants.Iteration)+"' should available in the validation sheet",
					  "Test Case '"+PCThreadCache.getInstance().getProperty("TCID")+"' or Iteration '"+PCThreadCache.getInstance().getProperty(PCConstants.Iteration)+"' is not present in the validation sheet", "FAIL");
			blnStatus = false;	
		}
		
		return blnStatus;
	}
	
	/**
	 * common function to validate the results
	 * @param strElement
	 * @param strExpectedResult
	 * @param strVerificationType
	 * @param strTableColumnNumber
	 * @param strCaseName
	 * @return
	 */
	public static Boolean verifyResults(String strElement, String strExpectedResult, String strVerificationType, String strTableColumnNumber, String strCaseName)
	{
		Boolean blnStatus = false;
		String[] splitColumnNumber = null;		String[] splitExpectedResult = null;		try
		{
			switch(strVerificationType.toUpperCase())
			{
				case "VERIFYELEMENTTEXT":
//					blnStatus = VerifyErrorMessages(""+strElement+":::::"+strExpectedResult+"");
					blnStatus = VerifyText(""+strElement+":::::"+strExpectedResult+"");
					break;
				case "VALIDATE_ERR_WARNING_MSGS":
//					blnStatus=captureErrorMsg(strExpectedResult);
					break;
				case "VERIFYELEMENTEXISTENCEFALSE":
					blnStatus = VerifyElement("VISIBLEFALSE:::"+strElement+"");
					break;
				case "VERIFYELEMENTEXISTENCETRUE":
					blnStatus = VerifyElement("VISIBLETRUE:::"+strElement+"");
					break;
				case "TABLEVALIDATION":
					//blnStatus = verifyTextFromTable(strElement, Integer.parseInt(strTableColumnNumber), strExpectedResult, strCaseName);
					blnStatus = verifyTextFromTablePages(strElement, Integer.parseInt(strTableColumnNumber), strExpectedResult, strCaseName);
					break;
				case "TABLEVALIDATIONELEEXISTENCEFALSE":
					blnStatus = verifyTextFromTableFalse(strElement, Integer.parseInt(strTableColumnNumber), strExpectedResult, strCaseName);
					break;
				case "VERIFYEDITBOXTEXT":
					blnStatus = verifyEditBoxTextValue(""+strElement+":::"+strExpectedResult+"");
					break;
				case "READGETTABLE":
					splitColumnNumber = strTableColumnNumber.split(":::");
					splitExpectedResult = strExpectedResult.split(":::");
					blnStatus = verifyTextFromTable(strElement, Integer.parseInt(splitColumnNumber[0]), Integer.parseInt(splitColumnNumber[1]), splitExpectedResult[0], splitExpectedResult[1], strCaseName);
					break;
				case "FORMSVALIDATION":
					splitColumnNumber = strTableColumnNumber.split(":::");
					splitExpectedResult = strExpectedResult.split(":::");
					
					//blnStatus = verifyFormsDetail(Common.o.getObject(strElement), Integer.parseInt(splitColumnNumber[0]), Integer.parseInt(splitColumnNumber[1]), splitExpectedResult[0], splitExpectedResult[1]);
					blnStatus = formsDataValidation(strElement, Integer.parseInt(splitColumnNumber[0]), Integer.parseInt(splitColumnNumber[1]), splitExpectedResult[0], splitExpectedResult[1]);
					break;
				case "RADIOBUTTONEXIST":
					blnStatus = verifyRadioExist(strElement, strExpectedResult);
					break;
							
			}
			if(!blnStatus)
			{
				blnStatus = true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			
		}
		return blnStatus;
	}
	
	/**
	 * @function This function use to get the text from the table according to the column
	 * @param obj
	 * @param readTextCol
	 * @param strReadString
	 * @return String
	 * @throws Exception
	 */
	public  static Boolean verifyTextFromTable(String obj, int readTextCol, String strReadString, String strTableName) throws Exception
	{	
		  boolean SearchString = false;
		  String readText = null;
		  WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(obj));
		  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		  for(int i = 0; i <= allrows.size()-1; i++)
		  {
			  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			  readText = Cells.get(readTextCol).getText(); 
			  if(readText.contains(strReadString))
			  {
				  SearchString = true;
				  break;
			  }
		   }
		  if(SearchString)
		  {
			  	//logger.info("Search String available in the table. '" + strReadString + "'");  
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Search string '" + strReadString + "' should avilable in the "+strTableName+" table",
					  "Search string '" + strReadString + "' is avilable in the "+strTableName+" table", "PASS");
		  }
		  else
		  {
			  	//logger.info("Search String not available in the table. '" + strReadString + "'");  
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Search string '" + strReadString + "' should avilable in the "+strTableName+" table",
					  "Search string '" + strReadString + "' is not avilable in the "+strTableName+" table", "PASS");
				SearchString = false;
		  }
		  return SearchString;
	}
	
	/**
	 * Function to check the radio button validation
	 * @param strElementName
	 * @param strExpected
	 * @return
	 * @throws Exception
	 */
	public static Boolean verifyRadioExist(String strElementName, String strExpected) throws Exception
	{
		Boolean blnStatus = false;
		WebElement radioButton = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(strElementName));
		blnStatus = radioButton.isDisplayed();
		if(blnStatus)
		{
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  ""+strElementName+" element should be selected as "+strExpected+"",
					  ""+strElementName+" element is selected as "+strExpected+"", "PASS");
		}else
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  ""+strElementName+" element should be selected as "+strExpected+"",
					  ""+strElementName+" element is not selected as "+strExpected+"", "FAIL");
			blnStatus = true;
		}
		return blnStatus;
	}
	/**
	 * This function is to verify the display of documents in document section.
	 * @param obj
	 * @param readTextCol
	 * @param strReadString
	 * @param strTableName
	 * @return
	 * @throws Exception
	 */
	public  static Boolean verifyTextFromTablePages(String obj, int readTextCol, String strReadString, String strTableName) throws Exception
	{	
		  boolean blnSearchString = false;
		  boolean blnStatus = false;
		  int icheckNextPageButton = 0;
		  String strPageCount = null;
		  Common common = CommonManager.getInstance().getCommon();
		  try
		  {
			  blnSearchString = verifyTextInTable(obj, readTextCol, strReadString, strTableName);
		  	  if(blnSearchString)
		  	  {
		  		  blnStatus = true;
		  	  }
		  	if(!blnSearchString)
 		   	{
 		   		icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
 		   		if(icheckNextPageButton > 0)
 		   		{
 		   			strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
 		   			strPageCount = SCRCommon.getIntFromString(strPageCount);
 		   			for(int i =1; i <Integer.parseInt(strPageCount); i++)
 		   			{
 		   				blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
 		   				blnStatus = common.SafeAction(Common.o.getObject("eleDocumentTitle"), "", "eleDocumentTitle");
 		   				blnSearchString = verifyTextInTable(obj, readTextCol, strReadString, strTableName);
 		   				if(blnSearchString)
 		   					blnStatus = true;
 		   			}
 		   		}
 		   	}
		  	if(blnSearchString)
		  	{
		  		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should validate the display of document '" +strReadString+ "' ","Document '" + strReadString + "' is displayed in the document section", "PASS");
		  	}
		  	else
		  	{
		  		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should validate the display of document '" +strReadString+ "' ","Document '" + strReadString + "' is NOT displayed in the document section", "FAIL");
		  	}
		  	if(icheckNextPageButton > 0)
 			{
 				blnStatus = common.SafeAction(Common.o.getObject("eleFirstPage"), "", "eleFirstPage");
 			}
		  		
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		
		  return blnStatus;
	}
	public  static Boolean verifyTextInTable(String obj, int readTextCol, String strReadString, String strTableName) throws Exception
	{	
		  boolean searchString = false;
		  String readText = null;
		  WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(obj));
		  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		  for(int i = 0; i <= allrows.size()-1; i++)
		  {
			  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			  readText = Cells.get(readTextCol).getText(); 
			  if(readText.contains(strReadString))
			  {
				  searchString = true;
				  break;
			  }
		   }
		  return searchString;
	}

	
	 /**
     * @function this function use to get the text from the table according to the input and the column
     * @param obj,readTextCol,getTextCol,strReadString
     * @return String
     * @throws Exception
     */
     public static boolean verifyFormsDetail(By obj, int formNumCol, int descCol, String strformName, String formDesc) throws Exception
     {      
              String text = null;
              boolean SearchString = false;
              boolean firstpage = true;
              boolean blnForm = false;
              boolean blnStatus = false;
              boolean blnformDesc = false;
              String strPageCount;
              Common common = CommonManager.getInstance().getCommon();
              WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
              List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
              
              int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
		   		if(icheckNextPageButton > 0)
		   		{
		   			strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		   			strPageCount = SCRCommon.getIntFromString(strPageCount);
		   			for(int i =0; i <Integer.parseInt(strPageCount); i++)
		   			{
		   				if(!firstpage)
		   				{
		   					blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "eleNextPage", "eleNextPage");     
		   					blnStatus = common.SafeAction(Common.o.getObject("eleFormsTitle"), "eleFormsTitle", "eleFormsTitle");
		   				}
		   				firstpage = false;
		   				for(int j = 0; j <= allrows.size()-1; j++)
		   				{
		   					List<WebElement> Cells = allrows.get(j).findElements(By.tagName("td"));
		   					String readText = Cells.get(formNumCol).getText();
		   					if (readText.contains(strformName))
		   					{
		   						blnForm = true;
		   						text = Cells.get(descCol).getText();
		   						if(text.contains(formDesc))
		   						{
		   							blnformDesc = true;  
		   							SearchString = true;
		   						}
		   						break;
		   					} 
		   				}
		   			if(blnformDesc)
		   					break;
		   			}
		   		}
		   		else
		   		{
		   			for(int j = 0; j <= allrows.size()-1; j++)
	   				{
	   					List<WebElement> Cells = allrows.get(j).findElements(By.tagName("td"));
	   					String readText = Cells.get(formNumCol).getText();
	   					if (readText.contains(strformName))
	   					{
	   						blnForm = true;
	   						text = Cells.get(descCol).getText();
	   						if(text.contains(formDesc))
	   						{
	   							blnformDesc = true;  
	   							SearchString = true;
	   						}
	   						break;
	   					}        
	   				}
		   		}
              if(blnForm && blnformDesc)
              {
                  logger.info("Form is available in forms table. '" + strformName + "'");  
                  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strformName + "'","Form '" + strformName + "' and its description '"+formDesc+"' is available in forms table", "PASS");
              }
              else if (blnForm && !blnformDesc)
              {
                  logger.info("Search String not available in the table. '" + strformName + "'");  
                  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Form '" + strformName+"' and its description '"+formDesc + "' should be available in forms table","Form '" + strformName+"' is available. But description'"+formDesc+"' is not availalbe", "FAIL");
                  SearchString = false;
              }
              return SearchString;
     }
     
	
 	/**
 	 * @function used to select the specific xml doc from the table
 	 * @param xmlDocName
 	 * @return true/false
 	 * @throws Exception
 	 */
 	public static boolean formsDataValidation(String eleName, int readCol, int actCol, String readStr, String actStr) throws Exception
 	{
 		boolean blnStatus=false;
 		int valCount;
 		int icheckNextPageButton = 0;
 		Common common = CommonManager.getInstance().getCommon();
 		String strPageCount;
 		try
    	{
 		
 			valCount = ActionOnTableForms(Common.o.getObject(eleName), readCol, actCol, readStr, actStr);
 			if (valCount >0){
 		   		blnStatus = true;
 		   	}
 		  	Outer:
 		  	if(valCount ==0)
 		   	{
 		   		icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
 		   		if(icheckNextPageButton > 0)
 		   		{
 		   			strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
 		   			strPageCount = SCRCommon.getIntFromString(strPageCount);
 		   			for(int i =1; i <Integer.parseInt(strPageCount); i++)
 		   			{
 		   				blnStatus = common.SafeAction(Common.o.getObject("eleNextPage"), "", "eleNextPage");
 		   				blnStatus = common.SafeAction(Common.o.getObject("eleFormsTitle"), "", "eleFormsTitle");
 		   				valCount = ActionOnTableForms(Common.o.getObject(eleName), readCol, actCol, readStr, actStr);
 		   				if(valCount >0)
 		   				{
 		   					blnStatus = true;
 		   					break Outer;
 		   				}else{
 		 		   			logger.info("Document with name '"+readStr+"' does not exist in un attached document Table");
 		 		   		}
 		   			}
 		   		}
 		   	}
 		   	switch(valCount)
 		   	{
 		   		case 0:
 		   			logger.info(""+readStr+" does not exist in un attqched document Table");
 		   			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+readStr+" should available in the forms Table",""+readStr+" is not available in forms Table", "FAIL");
 		   			break;
 		   		case 1:
 		   			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should validate the display of form '" +readStr+ "' and description '"+actStr+"'","Form '" + readStr + "' is available but description '"+actStr+"' is not matching as expected", "FAIL");
 		   			break;
 		   		case 2:
 		   			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should validate the display of form '" +readStr+ "' and description '"+actStr+"'","Form '" + readStr + "' and description '"+actStr+"' are matching as expected", "PASS");
 		   			break;
 		   	}
 		   	
 			if(icheckNextPageButton > 0)
 			{
 				blnStatus = common.SafeAction(Common.o.getObject("eleFirstPage"), "", "eleFirstPage");
 			}
    	}
    	catch (Exception e) 
 	   	{
    		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+readStr+" should available in forms Table",""+readStr+" is not available in forms Table", "FAIL");
    		e.printStackTrace();
    	}
 		return blnStatus;
 		
 	}
 	
   	/**
      * @function This function use to Select the data from the table and click the element accordingly
      * @param obj,readCol,actionCol,strReadString,actionObjetName
      * @return status
      * @throws Exception
      */
      public static int ActionOnTableForms(By obj, int readCol, int actionCol, String strReadString, String strGetString) throws Exception
      {      
        
        boolean SearchString=false;
        boolean getString=false;
        int trueCount=0;
        WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);       
        List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
        for(int i = 0; i <= allrows.size()-1; i++)
        {
               List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
               String readText = Cells.get(readCol).getText();
               if (readText.contains(strReadString))
               {
                      SearchString = true;
                      trueCount +=1;
                      logger.info("Search String is available in the table. '" + strReadString + "'");
                      String getText = Cells.get(actionCol).getText();
                      if(getText.equals(strGetString))
                      {
                     	 getString = true;
                     	 trueCount +=1;
                     	 logger.info("Search String is available in the table. '" + strGetString + "'");
                     	 break;
                      }
               }
           
             if(getString || SearchString)
             {
                    break;
             }
        }
        if (SearchString && !getString)
        {
            logger.info("Search String not available in the table. '" + strGetString + "'");  
          //  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Form '" + strReadString+"' and its description '"+strGetString + "' should be available in forms table","Form '" + strReadString+"' is available. But description'"+strGetString+"' is not availalbe", "FAIL");
          
        }
       
     	return trueCount;
      }
 	
	/**
	 * @function This function used to check the non availability of the Element in the Table
	 * @param obj
	 * @param readTextCol
	 * @param strReadString
	 * @return String
	 * @throws Exception
	 */
	public  static Boolean verifyTextFromTableFalse(String obj, int readTextCol, String strReadString, String strTableName) throws Exception
	{	
		  boolean SearchString = false;
		  String readText = null;
		  Common common = CommonManager.getInstance().getCommon();
		  if (common.ElementSize(Common.o.getObject("ele"+obj))<=0)
		   {
			  SearchString=false;
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Search string '" + strReadString + "' should not avilable in the "+strTableName+" table",
					  "Search string '" + strReadString + "' is not avilable in the "+strTableName+" table", "PASS");
		  }
		  else
		  {
			  WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(obj));
			  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
			  for(int i = 0; i <= allrows.size()-1; i++)
			  {
				  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
				  readText = Cells.get(readTextCol).getText(); 
				  if(readText.contains(strReadString))
				  {
					  SearchString = true;
					  break;
				  }
			   }
		  }
		  
		  if(!SearchString)
		  {
			  	//logger.info("Search String not available in the table. '" + strReadString + "'");  
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Search string '" + strReadString + "' should not avilable in the "+strTableName+" table",
					  "Search string '" + strReadString + "' is not avilable in the "+strTableName+" table", "PASS");
		  }
		  else
		  {
			  	//logger.info("Search String available in the table. '" + strReadString + "'");  
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Search string '" + strReadString + "' should avilable in the "+strTableName+" table",
					  "Search string '" + strReadString + "' is  avilable in the "+strTableName+" table", "FAIL");
				
		  }
		  return SearchString;
	}
	
	
	/**
	 * @function This function use to get the text from the table according to the column
	 * @param obj
	 * @param readTextCol
	 * @param strReadString
	 * @return String
	 * @throws Exception
	 */
	public  static Boolean verifyTextFromTable(String obj, int readTextCol, int actionTextCol, String strReadString, String strActionString, String strTableName) throws Exception
	{	
		  boolean SearchString = false;
		  boolean ActionString = false;
		  String readText = null;
		  String actionText = null;
		  WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(obj));
		  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		  for(int i = 0; i <= allrows.size()-1; i++)
		  {
			  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			  readText = Cells.get(readTextCol).getText(); 
			  if(readText.contains(strReadString))
			  {
				  SearchString = true;
				  actionText = Cells.get(actionTextCol).getText();
				  if(actionText.contains(strActionString))
				  {
					  ActionString = true;
				  }
				  break;
			  }
		   }
		  if(SearchString)
		  {
			  	//logger.info("Search String available in the table. '" + strReadString + "'");  
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Read String '" + strReadString + "' should avilable in the "+strTableName+" table",
					  "Read String '" + strReadString + "' is avilable in the "+strTableName+" table", "PASS");
			  if(ActionString)
			  {
				  	//logger.info("Search String available in the table. '" + strReadString + "'");  
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
						  "Action Search string '" + strActionString + "' should avilable in the "+strTableName+" table",
						  "Action Search string '" + strActionString + "' is avilable in the "+strTableName+" table", "PASS");
			  }
			  else
			  {
				  	//logger.info("Search String not available in the table. '" + strReadString + "'");  
				  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
						  "Read String '" + strActionString + "' should avilable in the "+strTableName+" table",
						  "Read String '" + strActionString + "' is not avilable in the "+strTableName+" table", "FAIL");
				  ActionString = false;
			  }
		  }
		  else
		  {
			  	//logger.info("Search String not available in the table. '" + strReadString + "'");  
			  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), 
					  "Search string '" + strReadString + "' should avilable in the "+strTableName+" table",
					  "Search string '" + strReadString + "' is not avilable in the "+strTableName+" table", "FAIL");
				SearchString = false;
		  }
		  return SearchString;
	}
	
	/**
	 * function perform the actions on action menu with scroll activity handled
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean selectActionItems(String sFuncValue) throws Exception
	{
			Boolean blnStatus = false;
			logger.info("Entering into the selectActionItems function");
			Common common = CommonManager.getInstance().getCommon();
			String[] arrActivtyName=sFuncValue.split("&&");
			int Counter = 0;
			List<WebElement> sGetList = null;
			outer:
			for(String strEachActivity : arrActivtyName)
			{
				blnStatus = false;
				inner:
				for(int i=0 ; i<50; i++)
	 			{
					if(Counter == 2)
					{
						sGetList = ManagerDriver.getInstance().getWebDriver().findElements(By.xpath(".//*[@class='x-menu-item-link x-menu-item-indent-no-separator']"));
					}else if(Counter == 0 || Counter ==1)
					{
						sGetList = ManagerDriver.getInstance().getWebDriver().findElements(By.xpath(".//*[@class='x-menu-item-link x-menu-item-indent-no-separator x-menu-item-indent-right-arrow']"));
					}
//	 				List<WebElement> sGetList = ManagerDriver.getInstance().getWebDriver().findElements(By.className("x-menu-item-text"));
	 				 for(WebElement element: sGetList)
	 	 	         {
	 	 		       	  String objName = element.getText();
	 	 		       	  System.out.println(objName);
	 			       	  if(objName.equals(strEachActivity))
	 			   	  		{
	 			       		  	logger.info(""+strEachActivity+" Activity found");
	 			       		  	String readAttriID1 = element.getAttribute("id");
	 			       		  	blnStatus = common.SafeAction(By.id(readAttriID1), "", "ele"+strEachActivity+"");
	 			       		  	Thread.sleep(500);
	 			       		  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should click the "+strEachActivity+" Activity","System clicked "+strEachActivity+" Activity", "PASS");
	 			       		  	blnStatus = true;
	 			       		  	Counter = Counter + 1;
	 			       			break inner;
	 			           	}
	 	 	         }
	 				 if(blnStatus == false)
			       	 {
	 					 int eleActvityScrollBottomCount = common.ElementSize(Common.o.getObject("eleActvityScrollBottom"));
	 					 if(eleActvityScrollBottomCount > 0)
	 					 {
	 						for(int j =0;j<=10;j++)
	 	 					 {
	 		 					 common.SafeAction(Common.o.getObject("eleActvityScrollBottom"), "eleActvityScrollBottom", "eleActvityScrollBottom");
	 	 					 }
	 					 }else{
	 						if(blnStatus == false)
	 						 {
	 							HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strEachActivity+"Activity should present in the activity list of "+arrActivtyName[1]+"",""+strEachActivity+" Activity is not present in the activity list of "+arrActivtyName[1]+"", "FAIL");
	 							break outer;
	 						 }
	 					 }
			       	 }
	 			} 	
			}
			return blnStatus;
	}
	
	/**
	 * function perform the actions on action menu with scroll activity handled
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean selectAdminApplicationLinks(String sFuncValue) throws Exception
	{
			Boolean blnStatus = false;
			logger.info("Entering into the selectActionItems function");
			Common common = CommonManager.getInstance().getCommon();
			String[] arrActivtyName=sFuncValue.split("&&");
			int Counter = 0;
			List<WebElement> sGetList = null;
			outer:
			for(String strEachActivity : arrActivtyName)
			{
				blnStatus = false;
				inner:
				for(int i=0 ; i<50; i++)
	 			{
					if(Counter == 1)
					{
						sGetList = ManagerDriver.getInstance().getWebDriver().findElements(By.xpath(".//*[@class='x-menu-item-link x-menu-item-indent-no-separator']"));
					}else if(Counter == 0)
					{
						sGetList = ManagerDriver.getInstance().getWebDriver().findElements(By.xpath(".//*[@class='x-menu-item-link x-menu-item-indent-no-separator x-menu-item-indent-right-arrow']"));
					}
//	 				List<WebElement> sGetList = ManagerDriver.getInstance().getWebDriver().findElements(By.className("x-menu-item-text"));
	 				 for(WebElement element: sGetList)
	 	 	         {
	 	 		       	  String objName = element.getText();
	 	 		       	  System.out.println(objName);
	 			       	  if(objName.equals(strEachActivity))
	 			   	  		{
	 			       		  	logger.info(""+strEachActivity+" Activity found");
	 			       		  	String readAttriID1 = element.getAttribute("id");
	 			       		  	blnStatus = common.SafeAction(By.id(readAttriID1), "", "ele"+strEachActivity+"");
	 			       		  	Thread.sleep(500);
	 			       		  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should click the "+strEachActivity+" Activity","System clicked "+strEachActivity+" Activity", "PASS");
	 			       		  	blnStatus = true;
	 			       		  	Counter = Counter + 1;
	 			       			break inner;
	 			           	}
	 	 	         }
	 				 if(blnStatus == false)
			       	 {
 						HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strEachActivity+"Activity should present in the activity list of "+arrActivtyName[1]+"",""+strEachActivity+" Activity is not present in the activity list of "+arrActivtyName[1]+"", "FAIL");
 						break outer;
			       	 }
	 			} 	
			}
			return blnStatus;
	}
	
	/**
     * function perform the actions on action menu with scroll activity handled
     * @param sFuncValue
     * @return true/false
     * @throws Exception
     */
	/**
     * function perform the actions on action menu with scroll activity handled
     * @param sFuncValue
     * @return true/false
     * @throws Exception
     */
     public static boolean selectActionApplicationLink(String sFuncValue) throws Exception
            {
                   Boolean blnStatus = false;
                   logger.info("Entering into the selectActionItems function");
                   Common common = CommonManager.getInstance().getCommon();
                   String[] arrActivtyName=sFuncValue.split("&&");
                   Integer intsize=0;
                   outer:
                   for(String strEachLink : arrActivtyName)
                   {
                         blnStatus = false;
                         inner:
                                
                         
                         for(int i=0 ; i<=50; i++)
                                
                         {
                                
                                List<WebElement> sGetList = ManagerDriver.getInstance().getWebDriver().findElements(By.className("x-menu-item-text"));
                                
                                for(WebElement element: sGetList)
                           {
                                        
                                       String readAttriID1 = element.getAttribute("id");
                                      int size=common.ElementSize(By.id(readAttriID1));
                                      if(size>0)
                                      {
                                              String objName = element.getText();
                                                if(objName.equals(strEachLink))
                                                     {
                                                            logger.info(""+strEachLink+" MenuItem found");
                                                            String readAttriID2 = element.getAttribute("id");
                                                            if(readAttriID2.contains(":UWRef_ExtMenuItemSet:") && strEachLink.equals(arrActivtyName[1]) )
                                                            {
                                                                   blnStatus = common.SafeAction(By.id(readAttriID2), "", "ele"+strEachLink+"");
                                                                   Thread.sleep(500);
                                                                     HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should click the "+strEachLink+" Link","System clicked "+strEachLink+" Link", "PASS");
                                                                   blnStatus = true;
                                                                   break inner;
                                                            }
                                                            else if(! readAttriID2.contains(":UWRef_ExtMenuItemSet:") && strEachLink.equals(arrActivtyName[0]) )
                                                            {
                                                                   blnStatus = common.SafeAction(By.id(readAttriID2), "", "ele"+strEachLink+"");
                                                                   Thread.sleep(500);
                                                                     HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should click the "+strEachLink+" Link","System clicked "+strEachLink+" Link", "PASS");
                                                                   blnStatus = true;
                                                                   break inner;
                                                            }
                                                                   
                                                             
                                                            //break inner;
                                              }
                                      }
                                      
                            }
                               
                         }
                         if(blnStatus == false)
                         {
                                HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+strEachLink+"Link should present in the list",""+strEachLink+" Link is not present in the list", "FAIL");
                                break outer;
                         }     
                   }
                   return blnStatus;
    }

	
	/**
     * @function This function use to Select the data from the table and click the element accordingly
     * @param obj,readCol,actionCol,strReadString,actionObjetName
     * @return status
     * @throws Exception
     */
     public static Boolean ActionOnTableCheckBox(By obj, int readCol, int actionCol, String strReadString, String sTagName, String strElementName) throws Exception
     {      
       boolean Status=false;
       boolean SearchString=false;
       boolean ActionObject=false;
       Common common = CommonManager.getInstance().getCommon();
       JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();      
       WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);       
       List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
       for(int i = 0; i <= allrows.size()-1; i++)
       {
              List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
              String readText = Cells.get(readCol).getText();
              if (readText.contains(strReadString))
              {
                     SearchString = true;
                     List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
		               for(WebElement element: CellElements)
		               {
	                         ActionObject = true;
	                         element.click();
	                         Status = SCRCommon.JavaScript(js);
	                         Status = common.SafeAction(Common.o.getObject(strElementName), "", strElementName);
	                         break;		
		               }
          }
            if(ActionObject == true)
            {
                   break;
            }
       }
       if(SearchString)
       {
                   logger.info("Search String available in the table. '" + strReadString + "'");  
                   HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + strReadString + "'", "PASS");
                   if(ActionObject)
                     {
                                logger.info("Search and click on object in the table cell and object name is '" + strReadString + "'");  
                                HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strReadString + "'","System searched object in the table and clicked on object. object name is '" + strReadString + "'", "PASS");
                                Status = true;                                    
                     }
                    else
                     {
                                logger.info("Search and click on object in the table cell and object name is '" + strReadString + "'");  
                                HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search object in the table cell and click on object. Object name is '" + strReadString + "'","System searched object in the table and clicked on object. object name is '" + strReadString + "'", "FAIL");
                                Status = false;
                     }
       }
       else
       {
                   logger.info("Search String not available in the table. '" + strReadString + "'");  
                   HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + strReadString + "'", "FAIL");
                   Status = false;
       }
       return Status;
     }
     
	 /**
     * @function This will use to take the current date
     * @return Current Effective Date
     */
     public static String returnDate(int datereduction) 
     {
            String targetDate = null;
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, datereduction);
            targetDate =  (date.get(Calendar.MONTH)+1)+"/"+(date.get(Calendar.DATE)+"/"+(date.get(Calendar.YEAR)));
        /*    System.out.println(cal.getTime());
            // Output "Wed Sep 26 14:23:28 EST 2012"

            String formatted = format1.format(cal.getTime());
            System.out.println(formatted);
            // Output "2012-09-26"

            System.out.println(format1.parse(formatted));*/
            return targetDate;
     } 
     
     public static Boolean ActionOnTable(By obj, int readCol, int actionCol, String strReadString, String sTagName) throws Exception
     {      
       boolean Status=false;
       boolean ActionObject=false;
       JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();      
       WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);       
       List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
       for(int i = 0; i <= allrows.size()-1; i++)
       {
              List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
              String readText = Cells.get(readCol).getText();
              if (readText.contains(strReadString))
              {
                     List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
		               for(WebElement element: CellElements)
		               {
	                         ActionObject = true;
	                         element.click();
	                         WebDriverWait wait = new WebDriverWait(ManagerDriver.getInstance().getWebDriver(),Integer.parseInt(HTML.properties.getProperty("VERYLONGWAIT")));
	                         wait.until(ExpectedConditions.stalenessOf(element));
	                         Status = SCRCommon.JavaScript(js);
	                         Status = true;
	                         break;		
		               }
          }
            if(ActionObject == true)
            {
                   break;
            }
       }
 	  return Status;
 	}
     
    public static boolean ActionOnTable(By obj, int readCol, int readCol1, int actionCol, String strReadString, String strReadString1, String actionObjetName, String sTagName) throws Exception
 	{	
 	  boolean Status=false;
 	  boolean ActionObject=false;
 	  JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();      
 	  WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
 	  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
 	  for(int i = 0; i <= allrows.size()-1; i++)
 	  {
 		  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
 		  String readText = Cells.get(readCol).getText();
 		  if(readText.contains(strReadString))
 		  {
 			  String readText1 = Cells.get(readCol1).getText();
 			  if (readText1.contains(strReadString1))
 	 		  {
 				  List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
 	 	          for(WebElement element: CellElements)
 	 	          {
 	 	        	  String objName = element.getText();
 	 	        	  if(objName.contains(actionObjetName))
 	         	  		{
 	 	        		  	ActionObject = true;
 	             			element.click();
 	             			Status = SCRCommon.JavaScript(js);
 	             			break;
 	 	            	}
 	 	          }
 	 		  }
 	     }
 		 if(ActionObject == true)
 		 {
 			 break;
 		 }
 	  }
 	  return Status;
 	} 
 	
    public static boolean ActionOnTable(By obj, int readCol, int readCol1, int readCol2, int actionCol, String strReadString, String strReadString1,String strReadString2, String actionObjetName, String sTagName) throws Exception
 	{	
 	  boolean Status=false;
 	  boolean ActionObject=false;
 	  JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();      
 	  WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
 	  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
 	  for(int i = 0; i <= allrows.size()-1; i++)
 	  {
 		  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
 		  String readText = Cells.get(readCol).getText();
 		  if(readText.contains(strReadString))
 		  {
 			  String readText1 = Cells.get(readCol1).getText();
 			  if (readText1.contains(strReadString1))
 	 		  {
 				 String readText2 = Cells.get(readCol2).getText();
 	 			  if (readText2.contains(strReadString2))
 	 			  {
 	 				  List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
 	 				  for(WebElement element: CellElements)
 	 				  {
 	 					  String objName = element.getText();
 	 					  if(objName.contains(actionObjetName))
 	 					  {
 	 						  ActionObject = true;
 	 						  element.click();
 	 						  Status = SCRCommon.JavaScript(js);
 	 						  break;
 	 					  }
 	 				  }
 	 			  }
 	 		  }
 	     }
 		 if(ActionObject == true)
 		 {
 			 break;
 		 }
 	  }
 	  return Status;
 	} 
 	
	/**
	 * @function This function use to Select the data from the table and click the element accordingly
	 * @param obj,readCol,actionCol,strReadString,actionObjetName
	 * @return status
	 * @throws Exception
	 */
	public static Boolean ActionOnTable(By obj, int readCol, int actionCol, String strReadString, String actionObjetName, String sTagName) throws Exception
	{	
	  boolean Status=false;
	  boolean SearchString=false;
	  boolean ActionObject=false;
	  JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();      
	  WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
	  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
	  for(int i = 0; i <= allrows.size()-1; i++)
	  {
		  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
		  String readText = Cells.get(readCol).getText();
		  if (readText.contains(strReadString))
		  {
			  SearchString = true;
			  List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
	          for(WebElement element: CellElements)
	          {
	        	  String objName = element.getText();
	        	  if(objName.contains(actionObjetName))
        	  		{
//	        		  	Status = true;
	        		  	ActionObject = true;
            			element.click();
            			Status = SCRCommon.JavaScript(js);
            			break;
	            	}
	          }
	     }
		 if(ActionObject == true)
		 {
			 break;
		 }
	  }
	  return Status;
	}
    
 	/**
 	 * this function is to convert string to int 
 	 * @param strFuncValue
 	 * @return true/false
 	 */
 	public static String getIntFromString(String strFuncValue)
	{
 		String str = strFuncValue;
	    StringBuilder myNumbers = new StringBuilder();
	    for (int i = 0; i < str.length(); i++) {
	        if (Character.isDigit(str.charAt(i))) {
	            myNumbers.append(str.charAt(i));
//		            System.out.println(str.charAt(i) + " is a digit.");
	        } else {
//		            System.out.println(str.charAt(i) + " not a digit.");
	        }
	    }
	    return myNumbers.toString();
	}
 	
 	/**
 	 * function - unDiscardChanges exist
 	 * @return true/false
 	 */
 	public static Boolean unDisCardChanges()
 	{
 		Boolean blnStatus = false;
 		Common common = CommonManager.getInstance().getCommon();
 		try
 		{
 	 		int iUnDiscardChanges = common.ElementSize(Common.o.getObject("eleUnDiscardChanges"));
 	 		if(iUnDiscardChanges > 0)
 	 		{
 	 			logger.info("UnSaved Changes found");
 	 			blnStatus = common.SafeAction(Common.o.getObject("eleUnDiscardChanges"), "", "eleUnDiscardChanges");
 	 			int iEditTransaction = common.ElementSize(Common.o.getObject("eljPolicyInfo_EditTransaction"));
 	 			if(iEditTransaction >0)
 	 			{
 	 				blnStatus = common.SafeAction(Common.o.getObject("eljPolicyInfo_EditTransaction"), "", "eljPolicyInfo_EditTransaction");
 	 	 			blnStatus = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "eleAlertPopUp");
 	 			}
 	 		}else
 	 		{
 	 			logger.info("UnSaved Changes not found");
 	 			blnStatus = true;
 	 		}
 		}catch(Exception e){
 			
 			e.printStackTrace();
 		}
 		return blnStatus;
 	}
 	
 	public static Boolean unDisCardChangesCommon()
 	{
 		Boolean blnStatus = false;
 		Common common = CommonManager.getInstance().getCommon();
 		try
 		{
 	 		int iUnDiscardChanges = common.ElementSize(Common.o.getObject("eleUnDiscardChanges"));
 	 		if(iUnDiscardChanges > 0)
 	 		{
 	 			logger.info("UnSaved Changes found");
 	 			blnStatus = common.SafeAction(Common.o.getObject("eleUnDiscardChanges"), "", "eleUnDiscardChanges");
 	 		}else
 	 		{
 	 			logger.info("UnSaved Changes not found");
 	 			blnStatus = true;
 	 		}
 		}catch(Exception e){
 			
 			e.printStackTrace();
 		}
 		return blnStatus;
 	}
 	
 	
	/*public static boolean activityValidation_sheetref(String sheetName)
	{
		logger.info("*****Entering into Activity Validation function*****");
		Boolean status = false;
		Boolean tcAvailable = false;
		String TCID = null;
		String TCIDAdd = null;
		String strColumnValue = null;
		String strColumnName = null;
		String sIteration = null;
		String strTargetDays = null;
		//String strEscDate = null;
		String strTargetStartPoint = null;
		String strTargetIncludeDays = null;
		String strExpected = null;
		String strUserType=null;
		//String strTargteDate=null;
		String recDate = null;
		String targetDaysHrs = null;
		String strVal = null;
		String [] strArray = null;
		//String policyPeriod = null;
		int rowcount;
		int addDays, addHrs;
		XlsxReader sXL = XlsxReader.getInstance();
		Common common = CommonManager.getInstance().getCommon();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		DateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
		Date baseDate = new Date();
		
		try
		{
		  rowcount = sXL.getRowCount(sheetName);
		  for(int i = 2; i <= rowcount; i++)
		  {
			  TCID = PCThreadCache.getInstance().getProperty("TCID");
			  TCIDAdd = sXL.getCellData(sheetName, "ID", i);
			  sIteration = sXL.getCellData(sheetName, "Iteration", i);
			  if(TCIDAdd.equals(TCID) && sIteration.equalsIgnoreCase(PCThreadCache.getInstance().getProperty(PCConstants.Iteration)))
			  {
				  tcAvailable = true;
				  int colcount = sXL.getColumnCount(sheetName);
					for(int j = 2; j <= colcount; j++)
					{
						strColumnValue = sXL.getCellData(sheetName, j, i);
						strColumnName = sXL.getCellData(sheetName, j, 1);
							if (!strColumnName.isEmpty() && !strColumnValue.isEmpty())
			  				{
								strUserType=sXL.getCellData(sheetName, colcount-1, i);
								switch(strColumnName.toUpperCase())
								{
									case "SUBJECT":
										status = SCRCommon.verifyResults("Activity_ActivitySubject", strColumnValue, "VERIFYELEMENTTEXT", "","Activity Subject validation");
										break;
									case "DESCRIPTION":
										status = SCRCommon.verifyResults("Activity_ActivityDesc", strColumnValue, "VERIFYELEMENTTEXT", "","Activity Description validation");
										break;
									case "ASSIGNTO":
										if(strColumnValue.contains("LOGGEDUSERNAME"))
										{
											String expUserName=PCThreadCache.getInstance().getProperty("LOGGED_USERNAME");
											//strColumnValue=strColumnValue.replace("LOGGEDUSERNAME", expUserName);                
											status = SCRCommon.verifyResults("Activity_AssignTo", expUserName, "VERIFYELEMENTTEXT", "","Activity Assing-To validation");
										}
										else
										{
											status = SCRCommon.verifyResults("Activity_AssignTo", strColumnValue, "VERIFYELEMENTTEXT", "","Activity Assing-To validation");
											
										}
										break;
									*//**case "TARGETDAYS11":
										strTargetDays = strColumnValue;
										String targetDaysHrs = null;
										addDays = Integer.parseInt(strTargetDays);
										addHrs = Integer.parseInt(sXL.getCellData(sheetName, j+1, i));
										strTargetStartPoint = sXL.getCellData(sheetName, j+2, i);
										strTargetIncludeDays = sXL.getCellData(sheetName, j+3, i); 
										
										recDate = common.ReadElement(Common.o.getObject("eleOrigReceivedDate"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
										Date d = (Date)formatter.parse(recDate);
										System.out.println(d);
										Calendar cal = Calendar.getInstance();
										cal.setTime(d);
										if(strTargetStartPoint.toUpperCase().contains("ACTIVITYCREATION") || strTargetStartPoint.toUpperCase().contains("POLICYEXPIRDATE"))
										{
											if(strTargetIncludeDays.toUpperCase().contains("BUSINESS"))
											{
												strExpected = SCRCommon.addDaysBusiness(addDays);
												strTargteDate=SCRCommon.addDaysBusinessNew(cal,addDays);
												d = (Date)formatter.parse(strTargteDate);
												cal.setTime(d);
												targetDaysHrs = getTaskEndTime(cal,addHrs);
												PCThreadCache.getInstance().setProperty(PCConstants.TARGETDAYSNDHRS, targetDaysHrs);
													
											}
											else if(strTargetIncludeDays.toUpperCase().contains("CALENDAR"))
											{
												strExpected = SCRCommon.ReturnDate(addDays);
												strExpected = SCRCommon.ReturnDate(cal, addDays);
												//strTargteDate=SCRCommon.addDaysBusiness(addDays);
											}
										}
										if(strUserType.toUpperCase().contains("LOGGEDUSERNAME"))
										{
											status = SCRCommon.verifyResults("Activity_TargetDate", strExpected, "VERIFYEDITBOXTEXT", "","Activity Target Day validation");
										}
										else
										{
											status = SCRCommon.verifyResults("Activity_TargetDate", strExpected, "VERIFYELEMENTTEXT", "","Activity Target Day validation");
										}
											
										j+=2;
										break;
									case "ESCALATIONDAYS11":
										String strEscalationDays = strColumnValue;
										addDays = Integer.parseInt(strEscalationDays);
										addHrs = Integer.parseInt(sXL.getCellData(sheetName, j+1, i));
										String strEscalationStartPoint = sXL.getCellData(sheetName, j+2, i);
										String strEscalationIncludeDays = sXL.getCellData(sheetName, j+3, i);
										
										recDate = common.ReadElement(Common.o.getObject("eleOrigReceivedDate"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
										Date d1 = (Date)formatter.parse(recDate);
										Calendar cal1 = Calendar.getInstance();
										System.out.println(d1);
										cal1.setTime(d1);
																			
										if(strEscalationStartPoint.toUpperCase().contains("ACTIVITYCREATION"))
										{
											if(strEscalationIncludeDays.toUpperCase().contains("BUSINESS"))
											{
												strExpected = SCRCommon.addDaysBusiness(addDays);
												strEscDate=SCRCommon.addDaysBusinessNew(cal1,addDays);
												d1 = (Date)formatter.parse(strEscDate);
												cal1.setTime(d1);
												targetDaysHrs = getTaskEndTime(cal1,addHrs);
												PCThreadCache.getInstance().setProperty(PCConstants.ESCLATIONDAYSNDHRS,targetDaysHrs);
											}
											else if(strEscalationIncludeDays.toUpperCase().contains("CALENDAR"))
											{
												strExpected = SCRCommon.ReturnDate(addDays);
											}
										}
										
										if(strUserType.toUpperCase().contains("LOGGEDUSERNAME"))
										{
											status = SCRCommon.verifyResults("Activity_EscalationDate", strExpected, "VERIFYEDITBOXTEXT", "","Activity Escalation Day validation");
										}
										else
										{
											status = SCRCommon.verifyResults("Activity_EscalationDate", strExpected, "VERIFYELEMENTTEXT", "","Activity Escalation Day validation");
										}
										j+=2;
										break;**//*
									case "PRIORITY":
										
										if(strUserType.toUpperCase().contains("LOGGEDUSERNAME"))
										{
											status = SCRCommon.verifyResults("Activity_Priority", strColumnValue, "VERIFYEDITBOXTEXT", "","Activity Priority validation");
										}
										else
										{
											status = SCRCommon.verifyResults("Activity_Priority", strColumnValue, "VERIFYELEMENTTEXT", "","Activity Priority validation");
										}
										
										break;	
									case "TARGETDAYS":
										strTargetDays = strColumnValue;
										//String targetDaysHrs = null;
										addDays = Integer.parseInt(strTargetDays);
										addHrs = Integer.parseInt(sXL.getCellData(sheetName, j+1, i));
										strTargetStartPoint = sXL.getCellData(sheetName, j+2, i);
										strTargetIncludeDays = sXL.getCellData(sheetName, j+3, i); 
										Calendar cal2 = Calendar.getInstance();
										
										recDate = common.ReadElement(Common.o.getObject("eleOrigReceivedDate"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
										//Date baseDate = (Date)formatter.parse(recDate);

										if(strTargetStartPoint.toUpperCase().contains("ACTIVITYCREATION"))
										{
											baseDate = (Date)formatter.parse(recDate);
											System.out.println(baseDate);
											
											cal2.setTime(baseDate);
										}
										else if(strTargetStartPoint.toUpperCase().contains("POLICYEXPIRDATE"))
										{
											//strVal = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
											strVal = SCRCommon.getPolicyPeriod();
											strArray =  strVal.split("-");
											String expDate = strArray[1].trim();
											baseDate = (Date)dateOnly.parse(expDate);
											cal2.setTime(baseDate);
										}
										else if(strTargetStartPoint.toUpperCase().contains("POLICYEFFDATE"))
										{
											//strVal = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
											strVal = SCRCommon.getPolicyPeriod();
											strArray =  strVal.split("-");
											String effDate = strArray[0].trim();
											baseDate = (Date)dateOnly.parse(effDate);
											cal2.setTime(baseDate);
										}
										
										if(strTargetIncludeDays.toUpperCase().contains("BUSINESS"))
										{
											strExpected = SCRCommon.addDaysBusinessNew(cal2,addDays);
											Date d = (Date)formatter.parse(strExpected);
											cal2.setTime(d);
											targetDaysHrs = getTaskEndTime(cal2,addHrs);
											PCThreadCache.getInstance().setProperty(PCConstants.TARGETDAYSNDHRS, targetDaysHrs);
											strArray = strExpected.split(" ");
											strExpected = strArray[0].trim();
													
										}
										else if(strTargetIncludeDays.toUpperCase().contains("CALENDAR"))
										{
											//strExpected = SCRCommon.ReturnDate(addDays);
											strExpected = SCRCommon.ReturnDate(cal2, addDays);
											strArray = strExpected.split(" ");
											strExpected = strArray[0].trim();
										}
									
										if(strUserType.toUpperCase().contains("LOGGEDUSERNAME"))
										{
											status = SCRCommon.verifyResults("Activity_TargetDate", strExpected, "VERIFYEDITBOXTEXT", "","Activity Target Day validation");
										}
										else
										{
											status = SCRCommon.verifyResults("Activity_TargetDate", strExpected, "VERIFYELEMENTTEXT", "","Activity Target Day validation");
										}
											
										j+=2;
										break;
									case "ESCALATIONDAYS":
										String escDayandHrs = null;
										String strEscalationDays = strColumnValue;
										addDays = Integer.parseInt(strEscalationDays);
										addHrs = Integer.parseInt(sXL.getCellData(sheetName, j+1, i));
										String strEscalationStartPoint = sXL.getCellData(sheetName, j+2, i);
										String strEscalationIncludeDays = sXL.getCellData(sheetName, j+3, i);
										
										recDate = common.ReadElement(Common.o.getObject("eleOrigReceivedDate"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
										
										Calendar cal1 = Calendar.getInstance();
										
										if(strTargetStartPoint.toUpperCase().contains("ACTIVITYCREATION"))
										{
											baseDate = (Date)formatter.parse(recDate);
											System.out.println(baseDate);
											
											cal1.setTime(baseDate);
										}
										else if(strEscalationStartPoint.toUpperCase().contains("POLICYEXPIRDATE"))
										{
											//strVal = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
											strVal = SCRCommon.getPolicyPeriod();
											strArray =  strVal.split("-");
											String expDate = strArray[1].trim();
											baseDate = (Date)dateOnly.parse(expDate);
											cal1.setTime(baseDate);
										}
										else if(strEscalationStartPoint.toUpperCase().contains("POLICYEFFDATE"))
										{
											//strVal = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
											strVal = SCRCommon.getPolicyPeriod();
											strArray =  strVal.split("-");
											String effDate = strArray[0].trim();
											baseDate = (Date)dateOnly.parse(effDate);
											cal1.setTime(baseDate);
										}
										if(strEscalationIncludeDays.toUpperCase().contains("BUSINESS"))
										{
											strExpected = SCRCommon.addDaysBusinessNew(cal1,addDays);
											Date d = (Date)formatter.parse(strExpected);
											cal1.setTime(d);
											escDayandHrs = getTaskEndTime(cal1,addHrs);
											PCThreadCache.getInstance().setProperty(PCConstants.TARGETDAYSNDHRS, escDayandHrs);
											strArray = strExpected.split(" ");
											strExpected = strArray[0].trim();
													
										}
										else if(strTargetIncludeDays.toUpperCase().contains("CALENDAR"))
										{
											strExpected = SCRCommon.ReturnDate(cal1, addDays);
											strArray = strExpected.split(" ");
											strExpected = strArray[0].trim();
										}
									
										
										if(strUserType.toUpperCase().contains("LOGGEDUSERNAME"))
										{
											status = SCRCommon.verifyResults("Activity_EscalationDate", strExpected, "VERIFYEDITBOXTEXT", "","Activity Escalation Day validation");
										}
										else
										{
											status = SCRCommon.verifyResults("Activity_EscalationDate", strExpected, "VERIFYELEMENTTEXT", "","Activity Escalation Day validation");
										}
										j+=2;
										break;
								}
								
							}
						}
			  }
		
		  }
			if(!tcAvailable)
			{
			  logger.info(""+TCID+" is not available in the '"+sheetName+"' Sheet");
			}
		}
		  catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			TCID = null;
    		TCIDAdd = null;
		}
		return status;
	}*/
 	
 	public static boolean activityValidation_sheetref(String sheetName)
    {
           logger.info("*****Entering into Activity Validation function*****");
           Boolean status = false;
           Boolean tcAvailable = false;
           String TCID = null;
           String TCIDAdd = null;
           String strColumnValue = null;
           String strColumnName = null;
           String sIteration = null;
           String strTargetDays = null;
           String strTargetStartPoint = null;
           String strTargetIncludeDays = null;
           String strExpected = null;
           String recDate = null;
           String arnDueDate = null;
           String targetDaysHrs = null;
           String strVal = null;
           String [] strArray = null;
           boolean blnLoggeduser = false;
           int rowcount;
           int addDays, addHrs;
           XlsxReader sXL = XlsxReader.getInstance();
           Common common = CommonManager.getInstance().getCommon();
           DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
           DateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
           Date baseDate = new Date();
           try
           {
             rowcount = sXL.getRowCount(sheetName);
             for(int i = 2; i <= rowcount; i++)
             {
                    TCID = PCThreadCache.getInstance().getProperty("TCID");
                    TCIDAdd = sXL.getCellData(sheetName, "ID", i);
                    sIteration = sXL.getCellData(sheetName, "Iteration", i);
                    if(TCIDAdd.equals(TCID) && sIteration.equalsIgnoreCase(PCThreadCache.getInstance().getProperty(PCConstants.Iteration)))
                    {
                          tcAvailable = true;
                          int colcount = sXL.getColumnCount(sheetName);
                               for(int j = 2; j <= colcount; j++)
                               {
                                      strColumnValue = sXL.getCellData(sheetName, j, i);
                                      strColumnName = sXL.getCellData(sheetName, j, 1);
                                             if (!strColumnName.isEmpty() && !strColumnValue.isEmpty())
                                             {
//                                                    strUserType=sXL.getCellData(sheetName, colcount-1, i);
                                                    switch(strColumnName.toUpperCase())
                                                    {
                                                    	   case "VERIFYLOGGEDUSERNAME":
                                                    		   if(strColumnValue.equalsIgnoreCase("YES"))
                                                    		   {
                                                    			   String expUserName = PCThreadCache.getInstance().getProperty("LOGGED_USERNAME");
                                                    			   String strAssignTo = common.ReadElement(Common.o.getObject("Activity_AssignTo"), 10);
                                                    			   if(expUserName.equalsIgnoreCase(strAssignTo.trim()))
                                                    			   {
                                                    				   blnLoggeduser = true;
                                                    			   }
                                                    		   }  break;
                                                           case "SUBJECT":
                                                                  status = SCRCommon.verifyResults("Activity_ActivitySubject", strColumnValue, "VERIFYELEMENTTEXT", "","Activity Subject validation");
                                                                  break;
                                                           case "DESCRIPTION":
                                                                  status = SCRCommon.verifyResults("Activity_ActivityDesc", strColumnValue, "VERIFYELEMENTTEXT", "","Activity Description validation");
                                                                  break;
                                                           case "ASSIGNTO":
                                                        	   	  String[] strLogedUserName = strColumnValue.split(":::"); 
                                                                  if(strLogedUserName[0].equalsIgnoreCase("USERROLE"))
                                                                  {
//                                                                        String expUserName=PCThreadCache.getInstance().getProperty("LOGGED_USERNAME");
                                                                         //strColumnValue=strColumnValue.replace("LOGGEDUSERNAME", expUserName);                
//                                                                        status = SCRCommon.verifyResults("Activity_AssignTo", expUserName, "VERIFYELEMENTTEXT", "","Activity Assing-To validation");
                                                                	  	status = verifyAssigntoRole(strLogedUserName[1]);	
                                                                  }
                                                                  else
                                                                  {
                                                                        status = SCRCommon.verifyResults("Activity_AssignTo", strColumnValue, "VERIFYELEMENTTEXT", "","Activity Assing-To validation");
                                                                        
                                                                  }
                                                                  break;
                                                           case "PRIORITY":
                                                                  if(blnLoggeduser)
                                                                  {
                                                                	  status = SCRCommon.verifyResults("Activity_Priority", strColumnValue, "VERIFYEDITBOXTEXT", "","Activity Priority validation");
                                                                  }
                                                                  else
                                                                  {
                                                                	  status = SCRCommon.verifyResults("Activity_Priority", strColumnValue, "VERIFYELEMENTTEXT", "","Activity Priority validation");
                                                                  }
                                                                  break; 
                                                           case "TARGETDAYS":
                                                                  strTargetDays = strColumnValue;
                                                                  //String targetDaysHrs = null;
                                                                  addDays = Integer.parseInt(strTargetDays);
                                                                  addHrs = Integer.parseInt(sXL.getCellData(sheetName, j+1, i));
                                                                  strTargetStartPoint = sXL.getCellData(sheetName, j+2, i);
                                                                  strTargetIncludeDays = sXL.getCellData(sheetName, j+3, i); 
                                                                  Calendar cal2 = Calendar.getInstance();
                                                                  
                                                                  recDate = common.ReadElement(Common.o.getObject("eleOrigReceivedDate"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
                                                                  //Date baseDate = (Date)formatter.parse(recDate);

                                                                  if(strTargetStartPoint.toUpperCase().contains("ARNDUEDATE"))
                                                                  {
                                                                	  arnDueDate = common.ReadElement(Common.o.getObject("ARNDueDate"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
                                                                	  baseDate = (Date)dateOnly.parse(arnDueDate);
                                                                      System.out.println(baseDate);
                                                                      
                                                                      cal2.setTime(baseDate);
                                                                  }
                                                                  else if(strTargetStartPoint.toUpperCase().contains("ACTIVITYCREATION"))
                                                                  {
                                                                        baseDate = (Date)formatter.parse(recDate);
                                                                        System.out.println(baseDate);
                                                                        
                                                                        cal2.setTime(baseDate);
                                                                  }
                                                                  else if(strTargetStartPoint.toUpperCase().contains("POLICYEXPIRDATE"))
                                                                  {
                                                                        //strVal = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
                                                                        strVal = SCRCommon.getPolicyPeriod();
                                                                        strArray =  strVal.split("-");
                                                                        String expDate = strArray[1].trim();
                                                                        baseDate = (Date)dateOnly.parse(expDate);
                                                                        cal2.setTime(baseDate);
                                                                  }
                                                                  else if(strTargetStartPoint.toUpperCase().contains("POLICYEFFDATE"))
                                                                  {
                                                                        //strVal = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
                                                                        strVal = SCRCommon.getPolicyPeriod();
                                                                        strArray =  strVal.split("-");
                                                                        String effDate = strArray[0].trim();
                                                                        baseDate = (Date)dateOnly.parse(effDate);
                                                                        cal2.setTime(baseDate);
                                                                  }
                                                                  
                                                                  if(strTargetIncludeDays.toUpperCase().contains("BUSINESS"))
                                                                  {
                                                                         strExpected = SCRCommon.addDaysBusinessNew(cal2,addDays);
                                                                        Date d = (Date)formatter.parse(strExpected);
                                                                        cal2.setTime(d);
                                                                        targetDaysHrs = getTaskEndTime(cal2,addHrs);
                                                                         PCThreadCache.getInstance().setProperty(PCConstants.TARGETDAYSNDHRS, targetDaysHrs);
                                                                        strArray = strExpected.split(" ");
                                                                        strExpected = strArray[0].trim();
                                                                                      
                                                                  }
                                                                  else if(strTargetIncludeDays.toUpperCase().contains("CALENDAR"))
                                                                 {
                                                                        //strExpected = SCRCommon.ReturnDate(addDays);
                                                                        strExpected = SCRCommon.ReturnDate(cal2, addDays);
                                                                        strArray = strExpected.split(" ");
                                                                        strExpected = strArray[0].trim();
                                                                  }
                                                           
                                                                  if(blnLoggeduser)
                                                                  {
                                                                        status = SCRCommon.verifyResults("Activity_TargetDate", strExpected, "VERIFYEDITBOXTEXT", "","Activity Target Day validation");
                                                                  }
                                                                  else
                                                                  {
                                                                        status = SCRCommon.verifyResults("Activity_TargetDate", strExpected, "VERIFYELEMENTTEXT", "","Activity Target Day validation");
                                                                  }
                                                                  j+=2;
                                                                  break;
                                                           case "ESCALATIONDAYS":
                                                                  String escDayandHrs = null;
                                                                  String strEscalationDays = strColumnValue;
                                                                  addDays = Integer.parseInt(strEscalationDays);
                                                                  addHrs = Integer.parseInt(sXL.getCellData(sheetName, j+1, i));
                                                                  String strEscalationStartPoint = sXL.getCellData(sheetName, j+2, i);
                                                                  String strEscalationIncludeDays = sXL.getCellData(sheetName, j+3, i);
                                                                  
                                                                  recDate = common.ReadElement(Common.o.getObject("eleOrigReceivedDate"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
                                                                  
                                                                  Calendar cal1 = Calendar.getInstance();
                                                                  
                                                                  if(strEscalationStartPoint.toUpperCase().contains("ARNDUEDATE"))
                                                                  {
                                                                	  arnDueDate = common.ReadElement(Common.o.getObject("ARNDueDate"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
                                                                	  baseDate = (Date)dateOnly.parse(arnDueDate);
                                                                      System.out.println(baseDate);
                                                                      
                                                                      cal1.setTime(baseDate);
                                                                  }
                                                                  else if(strTargetStartPoint.toUpperCase().contains("ACTIVITYCREATION"))
                                                                  {
                                                                        baseDate = (Date)formatter.parse(recDate);
                                                                        System.out.println(baseDate);
                                                                        
                                                                        cal1.setTime(baseDate);
                                                                  }
                                                                  else if(strEscalationStartPoint.toUpperCase().contains("POLICYEXPIRDATE"))
                                                                  {
                                                                        //strVal = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
                                                                        strVal = SCRCommon.getPolicyPeriod();
                                                                        strArray =  strVal.split("-");
                                                                        String expDate = strArray[1].trim();
                                                                        baseDate = (Date)dateOnly.parse(expDate);
                                                                        cal1.setTime(baseDate);
                                                                  }
                                                                  else if(strEscalationStartPoint.toUpperCase().contains("POLICYEFFDATE"))
                                                                  {
                                                                        //strVal = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
                                                                        strVal = SCRCommon.getPolicyPeriod();
                                                                        strArray =  strVal.split("-");
                                                                        String effDate = strArray[0].trim();
                                                                        baseDate = (Date)dateOnly.parse(effDate);
                                                                        cal1.setTime(baseDate);
                                                                  }
                                                                  if(strEscalationIncludeDays.toUpperCase().contains("BUSINESS"))
                                                                  {
                                                                        strExpected = SCRCommon.addDaysBusinessNew(cal1,addDays);
                                                                        Date d = (Date)formatter.parse(strExpected);
                                                                        cal1.setTime(d);
                                                                        escDayandHrs = getTaskEndTime(cal1,addHrs);
                                                                         PCThreadCache.getInstance().setProperty(PCConstants.TARGETDAYSNDHRS, escDayandHrs);
                                                                        strArray = strExpected.split(" ");
                                                                        strExpected = strArray[0].trim();
                                                                                      
                                                                  }
                                                                  else if(strEscalationIncludeDays.toUpperCase().contains("CALENDAR"))
                                                                  {
                                                                        strExpected = SCRCommon.ReturnDate(cal1, addDays);
                                                                        strArray = strExpected.split(" ");
                                                                        strExpected = strArray[0].trim();
                                                                  }       
                                                                  if(blnLoggeduser)
                                                                  {
                                                                        status = SCRCommon.verifyResults("Activity_EscalationDate", strExpected, "VERIFYEDITBOXTEXT", "","Activity Escalation Day validation");
                                                                  }
                                                                  else
                                                                  {
                                                                        status = SCRCommon.verifyResults("Activity_EscalationDate", strExpected, "VERIFYELEMENTTEXT", "","Activity Escalation Day validation");
                                                                  }
                                                                  j+=2;
                                                                  break;
                                                    }
                                                    
                                             }
                                      }
                    }
           
             }
                  if(!tcAvailable)
                  {
                    logger.info(""+TCID+" is not available in the '"+sheetName+"' Sheet");
                  }
           }
             catch(Exception e)
           {
                  e.printStackTrace();
           }finally{
                  TCID = null;
           TCIDAdd = null;
           }
           return status;
    }
 	
 	/*public static Boolean verifyAssigntoRole(String strFuncValue)
	{
		Boolean blnStatus = false;
		try {
			Common common = CommonManager.getInstance().getCommon();
			String strAssignTo = common.ReadElement(Common.o.getObject("Activity_AssignTo"), 10);
			if(!(strAssignTo == null))
			{
				blnStatus = common.SafeAction(Common.o.getObject("eleSubmissionParticipants"), "", "eleSubmissionParticipants");
				blnStatus = SCRCommon.verifyTextFromTable("eleParticipantTable", 1, 2, strAssignTo, strFuncValue, "Participant Table");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blnStatus;
	}*/
 	
 	public static Boolean verifyAssigntoRole(String strFuncValue)
    {
           Boolean blnStatus = false;
           try {
                  Common common = CommonManager.getInstance().getCommon();
                  String strAssignTo = common.ReadElement(Common.o.getObject("Activity_AssignTo"), 10);
                  if(!(strAssignTo == null))
                  {
                        blnStatus = common.SafeAction(Common.o.getObject("eleSubmissionParticipants"), "", "eleSubmissionParticipants");
                        if (common.ElementDisplayed(Common.o.getObject("eleParticipantTransTab")))
                        {
                               blnStatus = common.SafeAction(Common.o.getObject("eleParticipantTransTab"), "eleParticipantTransTab", "eleParticipantTransTab");
                        }
                        
                        blnStatus = SCRCommon.verifyTextFromTable("eleParticipantTable", 1, 2, strAssignTo, strFuncValue, "Participant Table");
                  }
           } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
           }
           return blnStatus;
    }

	


	/**
	 * fucntion use to calcualte the date excluding the holidays and weekends
	 * @param addNoOfDays
	 * @return String(Date)
	 */
	 public static String addDaysBusiness(int addNoOfDays) {
		 	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	        Calendar cal = new GregorianCalendar();
	        String strReturnDate = null;
	        // cal now contains current date
	        System.out.println(cal.getTime());
	        // add the working days
	        int workingDaysToAdd = addNoOfDays;
	        for (int i=1; i<=workingDaysToAdd; i++)
	            do 
	            {
	              cal.add(Calendar.DAY_OF_MONTH, 1);
//	                cal.add(Calendar.DATE, 1);
//	                System.out.println(cal.getTime());
	            } 
	            while ( ! isWorkingDay(cal));
	        System.out.println(cal.getTime());
	        strReturnDate = sdf.format(cal.getTime());
	        return strReturnDate;
	    }
	    
	 /**
		 * fucntion use to calcualte the date excluding the holidays and weekends
		 * @param addNoOfDays
		 * @return String(Date)
		 */
		 public static String addDaysBusinessNew(Calendar cal,int addNoOfDays) 
		 {
			 	Boolean blnPositiveDate = true;
			 	int workingDaysToAdd;
			    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		   		String sDate = sdf.format(cal.getTime());
		        String strReturnDate = null;
		        if(addNoOfDays < 0)
		        {
		        	blnPositiveDate = false;
		        	addNoOfDays = Math.abs(addNoOfDays);
		        }
		        workingDaysToAdd = addNoOfDays;
		        for (int i=1; i<=workingDaysToAdd; i++)
		            do 
		            {
		            	if(blnPositiveDate)
		            	{
		            		cal.add(Calendar.DAY_OF_MONTH, 1);
//			                cal.add(Calendar.DATE, 1);
//			                System.out.println(cal.getTime());
		            	}else{
		            		cal.add(Calendar.DAY_OF_MONTH, -1);
		            	}
		            } 
		            while ( ! isWorkingDay(cal));
		        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		        System.out.println(formatter.format(cal.getTime()));
		        System.out.println(cal.getTime());
		        strReturnDate = formatter.format(cal.getTime());
		        return strReturnDate;
		    }
	 	/**
	 	 * function use to check whether the given date contains holidays and weekend
	 	 * @param cal
	 	 * @return boolean
	 	 */
	    private static boolean isWorkingDay(Calendar cal) {
	        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	   		String sDate = sdf.format(cal.getTime());
	   		System.out.println(sDate);
	   		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
//	        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY || sDate1.equals(sDate))
	   		if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY || !isHoliday(sDate))
	        {
	            return false;
	        }
	        // tests for other holidays here
	        // ...
	        return true;
	    }
	    
	    public static String getTaskEndTime(Calendar cal, int hours){
	    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	   		String sDate = sdf.format(cal.getTime());
	   		System.out.println(sDate);
	        while (hours > 0){
	            int step = 0;
	            if(hours > 24) step = 24;
	            else step = hours;          
	            hours -= step;          
	            cal.add(Calendar.HOUR_OF_DAY, step);          
	            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	            /*if(dayOfWeek == Calendar.SATURDAY) hours += 24;
	            System.out.println(hours);
	            if(dayOfWeek == Calendar.SUNDAY) hours += 24;
	            System.out.println(hours);*/
	            if(isAddingHrsFallinginWeekend(cal) == 1) hours +=24;
//	            if(!isHoliday(sDate)) hours +=24;
	        }
	        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	        System.out.println(formatter.format(cal.getTime()));
	        return formatter.format(cal.getTime());
	    }
	    /**
	 	 * function use to add hours to a given date
	 	 * @param cal
	 	 * @return String
	 	 */
	    
	    public static String addDaysHrsToCalendarDay(Calendar cal, int hours){
	    	//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	   		cal.add(Calendar.HOUR_OF_DAY, hours);          
	        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	        System.out.println(formatter.format(cal.getTime()));
	        return formatter.format(cal.getTime());
	    }
	    
	    private static int isAddingHrsFallinginWeekend(Calendar cal) {
	    	int hours = 0;
	        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	   		String sDate = sdf.format(cal.getTime());
	   		//System.out.println(sDate);
	   		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
//	        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY || sDate1.equals(sDate))
	   		if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY || !isHoliday(sDate))
	        {
	   			hours = 1;
	        }
	        // tests for other holidays here
	        // ...
	        return hours;
	    }
	    
	    
	    //Holiday List
	    public static String[] holidaysList = {"10/25/2017","01/01/2017"};
	    
	    /**
	     * function to check the date is contains holidays
	     * @param cal
	     * @return boolean
	     */
		private static boolean isHoliday(String cal)
	    {	
	    	for(String abcd : holidaysList)
	    		if(cal.equals(abcd))
	    			return false;
	    	return true;
	    }
	  /**
	   * function to return the current month
	   * @return integer
	   */
	  public static int currentMonth()
	    {
	        int month;
	        GregorianCalendar date = new GregorianCalendar();      
	        month = date.get(Calendar.MONTH) + 1;
//		        month = month+1;
	        return month;
	    }
	  
	  public static boolean odsOuputValue(String sheetName)
		{
		  	Boolean status = true;
			if(HTML.properties.getProperty("EXECUTIONAPP").contains("ODS"))
		   	{
				logger.info("Entering into ODS value");
				Boolean tcAvailable = false;
				String TCID = null;
				String TCIDAdd = null;
				String strColumnName = null;
				String sIteration = null;
				String strColumnValue = null;
				String[] strColumnValues = null;
				String methodname = null;
				String componentName = null;
				int rowcount;
				XlsxReader sXL = XlsxReader.getInstance();
				try
				{
				  rowcount = sXL.getRowCount("ODSOutputValues");
				  Outer:
				  for(int i = 2; i <= rowcount; i++)
				  {
					  TCID = PCThreadCache.getInstance().getProperty("TCID");
					  TCIDAdd = sXL.getCellData("ODSOutputValues", "ID", i);
					  sIteration = sXL.getCellData("ODSOutputValues", "Iteration", i);
					  componentName = sXL.getCellData("ODSOutputValues", "Component", i);
					  if(TCIDAdd.equals(TCID) && sIteration.equalsIgnoreCase(PCThreadCache.getInstance().getProperty(PCConstants.Iteration)) && sheetName.equalsIgnoreCase(componentName))
					  {
						  tcAvailable = true;
						  int colcount = sXL.getColumnCount("ODSOutputValues");
						  for(int j = 2; j <= colcount; j++)
							{
								strColumnValue = sXL.getCellData("ODSOutputValues", j, i);
								strColumnName = sXL.getCellData("ODSOutputValues", j, 1);
								if (!strColumnName.isEmpty() && !strColumnValue.isEmpty())
				  				{
									methodname = strColumnName.substring(3);
									strColumnValues = strColumnValue.split(":::");
									switch(strColumnName)
									{
										case "getODSText":
											status = getODSText(strColumnValues[0], strColumnValues[1]);
											break;
										case "getValuefromTableForODS":
											status = getValuefromTableForODS(strColumnValues[1], strColumnValues[5], strColumnValues[0], strColumnValues[2], strColumnValues[3], strColumnValues[4]);
											break;
										case "TestData":
											status = SCRCommon.testData(strColumnValue);
											break;
										case "getODSEditText":
											status = SCRCommon.getODSEditText(strColumnValues[0], strColumnValues[1]);
											break;
									}
				  				}
								if(!status){
									logger.info("Problem in the method '"+methodname+"'");
									break Outer;
								}
							}
						  logger.info("Completed the ods Output value");
					  }
				  }
					if(!tcAvailable)
					{
					  logger.info(""+TCID+" is not available in the '"+sheetName+"' Sheet");
					}
				}catch(Exception e)
				{
					logger.info("Problem in the ods Output value");
					e.printStackTrace();
				}finally{
					TCID = null;
		    		TCIDAdd = null;
				}
		   	}
			return status;
	  }
	  	  
	  /**
	     * @function This function use to fill the dependent test data in corresponding component
	     * @param sValue
	     * @return
	     * @throws NumberFormatException 
	     * @throws Exception
	     */
	     public static boolean getODSText(String sElement, String strOdsFFName) throws NumberFormatException, Exception
	     {
          	   boolean Status = true;
	           Common common = CommonManager.getInstance().getCommon();
	           SCRCommon scrCommon = new SCRCommon();
	           FlatFile sReadWrite= FlatFile.getInstance();
	           String textValue = common.ReadElement(Common.o.getObject(sElement), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
	           String sFileName = scrCommon.FlatFileName();
	           Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), strOdsFFName, textValue, "OUTPUT", sFileName);
	           if(!Status || textValue.equals("") || textValue == null)
	           {
	        	   logger.info("'"+sElement+"' is not found in the system");
	           }
	           return Status;
	      }
	     
	     /**
		     * @function This function use to fill the dependent test data in corresponding component
		     * @param sValue
		     * @return
		     * @throws NumberFormatException 
		     * @throws Exception
		     */
		     public static boolean getODSEditText(String sElement, String strOdsFFName) throws NumberFormatException, Exception
		     {
	          	   boolean Status = true;
	          	   SCRCommon scrCommon = new SCRCommon();
		           Common common = CommonManager.getInstance().getCommon();
		           FlatFile sReadWrite= FlatFile.getInstance();
		           String textValue = common.ReadElementGetAttribute(Common.o.getObject(sElement), "value", Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
		           String sFileName = scrCommon.FlatFileName();
		           Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), strOdsFFName, textValue, "OUTPUT", sFileName);
		           if(!Status || textValue.equals("") || textValue == null)
		           {
		        	   logger.info("'"+sElement+"' is not found in the system");
		           }
		           return Status;
		      }
		     
	     
	     /**
	      * @function use to get the ODS data values
	      * @param obj,readCol,actionCol,strReadString,actionObjetName
	      * @return status
	      * @throws Exception
	      */
	      public static Boolean getValuefromTableForODS(String strTblObject, String strReadString, String strTableHeaderObject, String strReadHeaderName, String strGetHeaderName, String strOdsFFName) throws Exception
	      {      
	        boolean Status=false;
	        boolean SearchString=false;
	        FlatFile sReadWrite= FlatFile.getInstance();
	        SCRCommon scrCommon = new SCRCommon();
	        String sFileName = scrCommon.FlatFileName();
	        int readCol = getTableColumn(strReadHeaderName, strTableHeaderObject);
	        int actionCol = getTableColumn(strGetHeaderName, strTableHeaderObject);
	        WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(Common.o.getObject(strTblObject));       
	        List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
	        for(int i = 0; i <= allrows.size()-1; i++)
	        {
	               List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
	               String readText = Cells.get(readCol).getText();
	               if (readText.contains(strReadString))
	               {
	                      SearchString = true;
	                      String getText = Cells.get(actionCol).getText();
	                      if(strOdsFFName.equals("PolicyNumber"))
	                      {
	                    	  getText = "%"+getText.substring(getText.length() -6);
		                      Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), strOdsFFName, getText, "OUTPUT", sFileName);
	                      }else
	                      {
	                    	  Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), strOdsFFName, getText, "OUTPUT", sFileName);
	                      }
	               }
	             if(SearchString)
	             {
	                    break;
	             }
	        }
	        if(SearchString)
	        {
                    logger.info("Search String available in the table. '" + strReadString + "'");  
                    HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + strReadString + "'", "PASS");
	        }
	        else
	        {
                    logger.info("Search String not available in the table. '" + strReadString + "'");  
                    HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + strReadString + "'", "FAIL");
                    Status = false;
	        }

	        return Status;
	      }
	      
	      /**
	  	 * @function use the column of the table header
	  	 * @param CoverageName
	  	 * @param sFieldName
	  	 * @param sFieldValue
	  	 * @return true/false
	  	 * @throws IOException
	  	 */
	  	public static int getTableColumn(String sHeaderName, String strObjectName) throws IOException
	  	{
	  		Common common = CommonManager.getInstance().getCommon();
	          boolean blnSearchString= false;
	          String strTableHeader = null;
	          WebElement  mytable = common.returnObject(Common.o.getObject(strObjectName));
	          List<WebElement> rows_table = mytable.findElements(By.tagName("span"));
	          int intHeaderColumn = 0;
	          int intEmptyColumn = 0;
	          for(int i=0;i<rows_table.size();i++)
	          {
	          	strTableHeader = rows_table.get(i).getText();
//	          	System.out.println(strTableHeader);
	          	if(strTableHeader.equalsIgnoreCase(sHeaderName)){
	          		logger.info("Table Header "+sHeaderName+" found");
//	          		intHeaderColumn = i - intEmptyColumn ;
	          		intHeaderColumn = i;
	          		blnSearchString = true;
	          		break;
	          	}else if(strTableHeader.equals("")){
	          		intEmptyColumn = intEmptyColumn + 1;
	          	}

	          }
	          if(!blnSearchString){
	          	logger.info("Table Header "+sHeaderName+" is not found in the Add Class Table");
	          }
	  		return intHeaderColumn;
	  	}
	  	
	  	
	  	public static Boolean choosePolicyTransaction(String strFuncValue)
	  	{
	  		Common common = CommonManager.getInstance().getCommon();
	  		Boolean blnStatus = false;
	  		try {
				blnStatus = common.ActionOnTable(Common.o.getObject("PolicyTransactionTable"), 7, 4, strFuncValue, "a");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  		return blnStatus;
	  	}
	  	/**
	  	 * 
	  	 * @return
	  	 */
	  	public static Boolean funGetUserLoggedName()
        {
               Common common = CommonManager.getInstance().getCommon();
               Boolean blnStatus = false;
               try {
                     
                     blnStatus = common.SafeAction(Common.o.getObject("elePreferences"), "elePreferences","elePreferences");
                     String strUserName = common.ReadElement(Common.o.getObject("eleLogOut"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
                     strUserName=strUserName.replace("Log Out", "");
                     PCThreadCache.getInstance().setProperty(PCConstants.LOGGED_USERNAME, strUserName.trim());
                     logger.info("Logged in UserName is :" + PCThreadCache.getInstance().getProperty("LOGGED_USERNAME"));                  
                     HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Get the Logged in UserName","Logged in UserName : " + PCThreadCache.getInstance().getProperty("LOGGED_USERNAME") + "'", "PASS");
//                     blnStatus = common.SafeAction(Common.o.getObject("eleDeskTopAction"), "ele","eleDeskTopAction");
                     blnStatus = common.SafeAction(Common.o.getObject("elePreferences"), "elePreferences","elePreferences");
                   } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
               }
               return blnStatus;
        }
	  	 
	  	/**
	      * @function used to shift browser
	      **/
	  		  	
	  	public Boolean NewWindow(String funValue) throws Exception
    	{
	    		boolean status = false;
	    		//String[] sPair = funValue.split(":::");
	    		String sPrimaryWindow = driver.getWindowHandle();
	    		java.util.Set<String> allWindowHandles = driver.getWindowHandles();
	    		for(String handle : allWindowHandles)
	    		{
	    			if (driver.switchTo().window(handle).getTitle().contains(funValue))
	    			{
	    				//driver.switchTo().window(handle);
	    				logger.info("New window is displayed and swithced to that window");
	    				status = true;
	    				break;
	    			}
	    		}
	    		if(status)
	    		{
	    			//close the child window
	    			driver.close();
	    			//Switch to the parent window
	    			driver.switchTo().window(sPrimaryWindow);
	    		} 
	    		else
	    		{
	    			status =false;
	    			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Switch to New Window","Could not switch to New Window", "FAIL");
	    		}
	    		return status;
    	}
	  	 public static String getPolicyPeriod()
	  	 {
	  		 String period= null;
	  		 boolean status = false;
	  		 Common common = CommonManager.getInstance().getCommon();
	   		 try
	  		 {
	   			 int iPolicyPeriodSize = common.ElementSize(Common.o.getObject("elePolicyPeriod"));
	  			 if(iPolicyPeriodSize == 1)
	  			 {
	  				 
	  				period = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
	  			 }
	  			 else
	  			 {
  				 	status = common.SafeAction(Common.o.getObject("eleQuoteLHS"), "eleQuoteLHS","ele");
  				 	period = common.ReadElement(Common.o.getObject("elePolicyPeriod"), Integer.parseInt(HTML.properties.getProperty("LONGWAIT")));
	  			 }

	  		 }
	  		 catch(Exception e)
	  		 {
	  			 e.printStackTrace();
	  		 }
	  		return period;
	  	 }

	 	/**
		 	Description: Get the account number from search table
	 	 */
	  	public static Boolean getAccountNumber(String strFuncValue)
        {
               Common common = CommonManager.getInstance().getCommon();
               HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
              HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
               XlsxReader sXL = XlsxReader.getInstance();
               String strAccountNumber = null;
               Boolean blnStatus = false;
               try {
                      blnStatus = common.ElementExist(Common.o.getObject("AccountSearchResultsTbl"));
                      if(blnStatus)
                      {
                            strAccountNumber = common.GetTextFromTable(Common.o.getObject("AccountSearchResultsTbl"), 2, 1, strFuncValue);
                            PCThreadCache.getInstance().setProperty(PCConstants.AccountNumber, strAccountNumber);
                            whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
                            updateColumnNameValues.put("edtaccountNumber", strAccountNumber);
                            blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT, updateColumnNameValues, whereConstraint);
                            
                             updateColumnNameValues.clear();
                            updateColumnNameValues.put(PCConstants.AccountNumber, strAccountNumber);
                            blnStatus = sXL.executeUpdateQuery(PCConstants.SHEET_ICONValidation, updateColumnNameValues, whereConstraint);
                            if(HTML.properties.getProperty("EXECUTIONAPP").contains("ODS"))
            				{
            					FlatFile sReadWrite = FlatFile.getInstance();
            					SCRCommon scrCommon = new SCRCommon();
            					String FlatFileName = scrCommon.FlatFileName();
            					blnStatus = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "AccountNumber", strAccountNumber, "OUTPUT", FlatFileName);
            				}
                      }else
                      {
                            logger.info("Account Name "+strFuncValue+" is not exist in PC");
                      }
               } catch (Exception e1) {
                      // TODO Auto-generated catch block
                      e1.printStackTrace();
               }
               return blnStatus;
        }
	  	/**
		 * Add total cost in Quote transaction page table
		 * @param sFunctionValue
		 * @return
		 * @throws Throwable
		 */
		 public static boolean selectTotalCost(String sFunctionValue) throws Throwable
		    {
		           boolean status = false;
		           Common common = CommonManager.getInstance().getCommon();
		           String[] sValue = sFunctionValue.split(":::");
		           int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleCLAQTTable")); 
		           switch (sValue[0].toUpperCase())
		           {
		          
			           case "TOTALCOST":
		               try
		               {
		               
		                 status=SCRCommon.DataWebTable(Common.o.getObject("eleCLAQTTable"), sRowCount, 6,"", "double","div");
		                 //status=SCRCommon.FillCellValue(Common.o.getObject("eleQuoteTransactionstbl"), sRowCount, 6,"", "single", sValue[1]);
//		                      driver.findElement(Common.o.getObject("lstTotalAnnualCost")).sendKeys(sValue[1]);
		                
		                
		                status =common.SafeAction(Common.o.getObject("lstTotalAnnualCost"), sValue[1], "lstTotalAnnualCost"); 
		               }
		               catch(Exception e)
		               {
		                      e.printStackTrace(); 
		               }
		               break;
		           }
		           
		           return status;
		    }


	  	
	  	 /**
	      * @function This function use to Select the data from the table and click the element accordingly
	      * @param obj,readCol,actionCol,strReadString,actionObjetName
	      * @return status
	      * @throws Exception
	      */
	      public static Boolean ActionOnTableTrueFalse(By obj, int readCol, int actionCol, String strReadString, String strActionString, String sTagName) throws Exception
	      {      
		        boolean Status=false;
//		        boolean SearchString=false;
//		        boolean ActionString = false;
		        WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);       
		        List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		        for(int i = 0; i <= allrows.size()-1; i++)
		        {
		               List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
		               String readText = Cells.get(readCol).getText();
		               if (readText.equalsIgnoreCase(strReadString))
		               {
//		                  SearchString = true;
		                  String getText = Cells.get(actionCol).getText();
			                  if(getText.equalsIgnoreCase(strActionString))
			                  {
			                	  Status = true;
			                	  break;
			                  }
		               }
		        }
		        return Status;
	      }
	      
  /**
	 * @function This function use to Select the data from the table only with the last six digit policy number
	 * @param obj,readCol,actionCol,strReadString
	 * @return status
	 * @throws Exception
	 */
	public static Boolean ActionOnTableForICON(By obj, int readCol, int actionCol, String strReadString, String sTagName) throws Exception
	{	
		  boolean Status=false;
		  boolean SearchString=false;
		  JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver();      
		  WebElement mytable = ManagerDriver.getInstance().getWebDriver().findElement(obj);
		  List<WebElement> allrows = mytable.findElements(By.tagName("tr"));
		  Outer:
		  for(int i = 0; i <= allrows.size()-1; i++)
		  {
			  List<WebElement> Cells = allrows.get(i).findElements(By.tagName("td"));
			  String strPolicyNumber = Cells.get(readCol).getText();
			  if(!strPolicyNumber.equals("") && !(strPolicyNumber == null))
			  {
//						  if (strPolicyNumber.contains(strReadString))
				  if(strPolicyNumber.substring(strPolicyNumber.length()-6).contains(strReadString = strReadString.substring(strReadString.length()-6)))
					  {
						  SearchString = true;
						  List<WebElement> CellElements = Cells.get(actionCol).findElements(By.tagName(sTagName));
				          for(WebElement element: CellElements)
				          {
				        	  element.click();
				        	  Status = true;
			        		  Status = SCRCommon.JavaScript(js);
			        		  PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, strPolicyNumber);
			        		  PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH, strPolicyNumber.substring(strPolicyNumber.length()-6));
			        		  if(HTML.properties.getProperty("EXECUTIONAPP").contains("ODS"))
			        			{
			        				FlatFile sReadWrite = FlatFile.getInstance();
			        				SCRCommon SCRCommon = new SCRCommon();
			        				String FlatFileName = SCRCommon.FlatFileName();
			        				String policyNumebr = "%"+strPolicyNumber.substring(strPolicyNumber.length()-6);
			        				Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "PolicyNumber", policyNumebr, "OUTPUT", FlatFileName);
			        				Status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "FPolicyNumber", strPolicyNumber, "OUTPUT", FlatFileName);
		//		        			status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "PolicyNumber", policyNumber, "OUTPUT", FlatFileName);
			        				if(HTML.properties.getProperty("E2E").contains("YES"))
			        				{
				        				Status = retrieveTransKeyValueforODS("ZipCode:::City:::State");
			        				}
			        			}
			        		  break Outer;
			        	  }
				     }
			  }
		  }
		  if(SearchString)
		  {
			  	logger.info("Search String available in the table. '" + strReadString + "'");  
			  	HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + strReadString + "'", "PASS");
		  }
		  else
		  {
			  	logger.info("Search String not available in the table. '" + strReadString + "'");  
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should search string in table and Search string is '" + strReadString + "'","System searched string in table and srarch string is  '" + strReadString + "'", "FAIL");
				Status = false;
		  }
		  return Status;
	}
			
	public static Boolean retrieveTransKeyValueforODS(String keyName) 
	{	
		Boolean blnStatus = false;
		Connection ucaConn;
		Statement st = null;
		ResultSet rs = null;
		String transKeyValue = "";
		String query = "";
		String e2eTestCaseId = E2ETestCaseUtil.E2E_TestCaseID;
	//				String e2eTestCaseId = "E2E_ICON_TC1";
		ucaConn = DBConnectionManager.getInstance().getConnectionFromPool();
		String keyNames[] = keyName.split(":::");
		try {
			st = ucaConn.createStatement();
			for(String eachTransNames : keyNames)
			{
				blnStatus = false;
				query = "SELECT trans_key_value FROM E2E_TRANS	WHERE trans_id =(SELECT MAX (trans_id) FROM E2E_TRANS WHERE  E2E_TEST_CASE_ID ='"
						+ e2eTestCaseId
						+ "' AND UPPER (TRANS_KEY_NM) =	UPPER ('"
						+ eachTransNames + "'))";
				System.out.println("retrieveTransKeyValueforODS():::Query::" + query);
				rs = st.executeQuery(query);
				if (rs.next()) {
	//							blnStatus = true;
					transKeyValue = rs.getString(1);
					logger.info("Trans Key details -> " + eachTransNames + " : " + transKeyValue);
					FlatFile sReadWrite = FlatFile.getInstance();
					SCRCommon SCRCommon = new SCRCommon();
					String FlatFileName = SCRCommon.FlatFileName();
					blnStatus = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), eachTransNames, transKeyValue, "OUTPUT", FlatFileName);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionManager.getInstance().closeResultSet(rs);
			DBConnectionManager.getInstance().closeStatement(st);
			DBConnectionManager.getInstance().returnConnectionToPool(ucaConn);
		}
		return blnStatus;
	}
		
	public Boolean captureAllFields(String KeyNames)
	{
		Boolean blnStatus = true;
		String sheetName = "PageValues";
		String[] strKeyNames = KeyNames.split(":::");
		try 
		{
			for(String eachFieldTypes : strKeyNames)
			{
				switch(eachFieldTypes.toUpperCase())
				{
					case "LABEL":
						blnStatus = findTextEditValues(sheetName);
					case "CHECKBOX":
						blnStatus = checkBoxValues(sheetName, "CHECKBOX");
						break;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blnStatus;
	}
	
	public static Boolean findTextEditValues(String sheetName)
	{
		Boolean blnStatus = true;
		String getFieldValue = null;
		String getTagName = null;
		String[] getFieldID = null;
		int counter;
		Common common = CommonManager.getInstance().getCommon();
		XlsxReader sXL = XlsxReader.getInstance();
		try{
			List<WebElement> elementLabels = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("labelClassName"));
			for(WebElement getElement : elementLabels)
			{
				String getFieldName = getElement.getText();
				counter = 0;
				if(!getFieldName.equals(""))
				{
					String getElementID = getElement.getAttribute("id");
					switch(getFieldName.toUpperCase())
					{
						case "BILLING NUMBER":
							getFieldID[0] = "PolicyFile_Summary:Policy_SummaryScreen:BillingInformationDV:BillingInformation_ExtInputSet:BillingNumber_Ext-btnInnerEl";
							getTagName = "span";
							break;
						default:
							getFieldID = getElementID.split("-");
							getFieldID[0] = getFieldID[0].concat("-inputEl");
							int checkFieldExist = common.ElementSize(By.id(getFieldID[0]));
							if(checkFieldExist==1)
							{
								counter = 1;
								getTagName = common.getTagName(By.id(getFieldID[0]));
							}
							break;
					}
					if(counter == 1)
					{
						switch(getTagName)
						{
							case "div":
							case "a":
							case "span":
								getFieldValue = common.ReadElementforODS(By.id(getFieldID[0]), 5);
								break;
							case "input":
								getFieldValue = common.ReadElementGetAttribute(By.id(getFieldID[0]), "value", 5);
								break;
						}
						if(!getFieldValue.equals("") || !(getFieldValue == null))
						{
							int rowcount = sXL.getRowCount(sheetName);
							sXL.setCellData(sheetName, PCThreadCache.getInstance().getProperty("TCID"), rowcount, 0);
							sXL.setCellData(sheetName, getFieldName, rowcount, 1);
							sXL.setCellData(sheetName, getFieldValue, rowcount, 2);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			blnStatus = true;
		}
		return blnStatus;
	}
	
	public static Boolean checkBoxValues(String sheetName, String checkBoxValue)
	{
		Boolean blnStatus = true;
		String getCovergeName = null;
		XlsxReader sXL = XlsxReader.getInstance();
		try
		{
			switch(checkBoxValue.toUpperCase())
			{
				case "EDITCHECKBOX":

					break;
				case "CHECKBOX":
					List<WebElement> elementLabels = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("eleCoverages"));
					for(WebElement getElement : elementLabels)
					{
						getCovergeName = getElement.getText();	
					}
					if(!(getCovergeName == null))
					{
						int rowcount = sXL.getRowCount(sheetName);
						sXL.setCellData(sheetName, PCThreadCache.getInstance().getProperty("TCID"), rowcount, 0);
						sXL.setCellData(sheetName, getCovergeName, rowcount, 3);
					}
					break;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			blnStatus = true;
		}
		return blnStatus;
	}
	
	public static Boolean getValuefromPCUpdateintoE2E(String strFuncValue)
	{
		Boolean blnStatus = true;
		String getFieldValue = null;
		String getTagName = null;
		String[] getFieldID = null;
		String[] fieldNames = strFuncValue.split(":::");
		Common common = CommonManager.getInstance().getCommon();
		try{
			for(String fieldName : fieldNames)
			{
				List<WebElement> elementLabels = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("labelClassName"));
				getFieldLoop:
				for(WebElement getElement : elementLabels)
				{
					String getFieldName = getElement.getText();
						if(getFieldName.toUpperCase().equals(fieldName.toUpperCase()))
						{
									String getElementID = getElement.getAttribute("id");
									/*getFieldID = getElementID.split("-");
									getFieldID[0] = getFieldID[0].concat("-inputEl");
									getTagName = common.getTagName(By.id(getFieldID[0]));*/
									switch(getFieldName.toUpperCase())
									{
										case "BILLING NUMBER":
											getFieldID = getElementID.split("-");
											getFieldID[0] = "PolicyFile_Summary:Policy_SummaryScreen:BillingInformationDV:BillingInformation_ExtInputSet:BillingNumber_Ext-btnInnerEl";
											getTagName = "span";
											break;
										default:
											getFieldID = getElementID.split("-");
											getFieldID[0] = getFieldID[0].concat("-inputEl");
											getTagName = common.getTagName(By.id(getFieldID[0]));
											/*int checkFieldExist = common.ElementSize(By.id(getFieldID[0]));
											if(checkFieldExist==1)
											{
												counter = 1;
												getTagName = common.getTagName(By.id(getFieldID[0]));
											}*/
											break;
									}
									switch(getTagName)
									{
											case "div":
											case "a":
											case "span":
											case "textarea":
												getFieldValue = common.ReadElementforODS(By.id(getFieldID[0]), 5);
												break;
											case "input":
												getFieldValue = common.ReadElementGetAttribute(By.id(getFieldID[0]), "value", 5);
												break;
									}
									switch(getFieldName)
									{
										case "Number":
										case "Policy Number":
											getFieldName = "PolicyNumber";
											break;
										case "Name":
										case "Primary Named Insured":
										case "Account Name":
											getFieldName = "AccountName";
											break;
										case "Address 1":
											getFieldName = "Address";
											break;
										case "Zip Code":
											getFieldName = "ZipCode";
											break;
										case "Total Premium":
										case "Total Annualized Cost":
											getFieldName = "TotalPremium";
											break;
										case "Effective Date":
											getFieldName = "EffectiveDate";
											break;
										case "Expiration Date":
											getFieldName = "ExpirationDate";
											break;
										case "Producer of Record":	
										case "Producer Code":
											getFieldName = "ProducerCode";
											break;
										case "Installment Plan":
											getFieldName = "InstallmentPlan";
											break;
										case "Product Type":
											getFieldName = "ProductType";
											break;
										case "Term Type":
											getFieldName = "TermType";
											break;
										case "BOR Control Start Date":
											getFieldName = "BORStartDate";
											break;
										case "BOR Control End Date":
											getFieldName = "BOREndDate";
											break;
										case "Risk Score":
											getFieldName = "RiskScore";
											break;
										case "Anniversary Date":
											getFieldName = "AnniversaryDate";
											break;
										case "Limit - per Accident / per Employee Disease":
											getFieldName = "LimitperAccidentperEmployeeDisease";
											break;
										case "Policy Limit - Disease":
											getFieldName = "PolicyLimitDisease";
											break;
										case "Underwriting Companies":
											getFieldName = "UnderwritingCompanies";
											break; 
										case "Safety Incentive Program Credit":
											getFieldName = "SafetyIncentiveProgramCredit";
											break;
										case "Return To Work Program Credit":
											getFieldName = "ReturnToWorkProgramCredit";
											break;
										case "PPO Credit":
											getFieldName = "PPOCredit";
											break;
										case "Merit Rating Mod":
											getFieldName = "MeritRatingMod";
											break;
										case "Experience Mod Status":
											getFieldName = "ExperienceModStatus";
											break;
										case "Interstate Experience Rating Modification":
											getFieldName = "Interstate Experience Rating Modification";
											break;
										case "Drug & Alcohol Prevention ProgramCredit(WSLPIP)":
											getFieldName = "DrugAlcoholPreventionProgramCreditWSLPIP";
											break;
										case "Billing Method":
											getFieldName = "BillingMethod";
											break;
										case "Agency Name":
											getFieldName = "AgencyName";
											break;
										case "First name":
											getFieldName = "FirstName";
											break;
										case "Last name":
											getFieldName = "LastName";
											break;
										case "Total Cost":
											getFieldName = "TotalCost";
											break;
										case "Business Grouping":
											getFieldName = "BusinessGrouping";
											break;
										case "Trans Eff Date":
											getFieldName = "TransactionEffectiveDate";
											break;
										case "Account No":
											getFieldName = "AccountNumber";
											break;
										case "Rate as of Date":
											getFieldName = "RateAsOfDate";
											break;
										case "Billing Number":
											getFieldName = "BillingNumber";
											break;
									}
									if(!getFieldValue.equals(""))
									{
										updateE2ETrans(getFieldName, getFieldValue);
										break getFieldLoop;
									}
						}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			blnStatus = true;
		}
		return blnStatus;
	}
	
	public static Boolean getValuefromPCUpdateintoE2EExcel(String strFuncValue)
	{
		Boolean blnStatus = true;
		String getFieldValue = null;
		Common common = CommonManager.getInstance().getCommon();
		String[] fields = strFuncValue.split("###");
		for(String fieldValues : fields)
		{
			String[] e2eFields = fieldValues.split(":::");
			getFieldValue = common.ReadElementforODS(Common.o.getObject(e2eFields[0]), 5);
			if(!getFieldValue.equals(""))
			{
				updateE2ETrans(e2eFields[1], getFieldValue);
			}
		}
		return blnStatus;
	}
	
	public static boolean updateE2ETrans(String keyName, String keyValue) {
		boolean status = false;
		Connection ucaConn = null;
		Statement st = null;
		ResultSet rs = null;
		String e2eTestCaseId = E2ETestCaseUtil.keyMap.get(PCThreadCache.getInstance().getProperty("TCID")); 
//		String e2eTestCaseId = "E2E_OPT_PC_1";
		if(!(e2eTestCaseId== null))
		{
			try {
					ucaConn = DBConnectionManager.getInstance().getConnectionFromPool();
					st = ucaConn.createStatement();
					String query = "INSERT into E2E_TRANS(TRANS_KEY_NM, TRANS_KEY_VALUE,E2E_TEST_CASE_ID,LAST_UPDT_TMSP,LAST_UPDT_APPL_NM) VALUES('"
							+ keyName + "','" + keyValue + "','" + e2eTestCaseId + "',SYSDATE ,'PC')";
					logger.info("executing query = " + query);
					logger.info("Update the values in the e2e table for transname" + keyName + " and transValue is" + keyValue);
					st.executeUpdate(query);
					status = true;
	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBConnectionManager.getInstance().closeResultSet(rs);
				DBConnectionManager.getInstance().closeStatement(st);
				DBConnectionManager.getInstance().returnConnectionToPool(ucaConn);
			}
		}else{
			status = true;
		}
		return status;
	}
	
	public static Boolean splitE2EValues(String strFuncValue) throws Exception
	{
		Boolean blnStatus = true;
		String getFieldValue = null;
		String getTagName = null;
		String[] getFieldID = null;
		String ZipCode = null;
		String Address =null;
		Common common = CommonManager.getInstance().getCommon();
		List<WebElement> elementLabels = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject("labelClassName"));
		for(WebElement getElement : elementLabels)
		{
			String getFieldName = getElement.getText();
				if(getFieldName.equals(strFuncValue))
				{
						String getElementID = getElement.getAttribute("id");
						getFieldID = getElementID.split("-");
						getFieldID[0] = getFieldID[0].concat("-inputEl");
						getTagName = common.getTagName(By.id(getFieldID[0]));
							switch(getTagName)
							{
									case "div":
									case "a":
									case "span":
									case "textarea":
										getFieldValue = common.ReadElementforODS(By.id(getFieldID[0]), 5);
										break;
									case "input":
										getFieldValue = common.ReadElementGetAttribute(By.id(getFieldID[0]), "value", 5);
										break;
							}
							String[] getZipcode = getFieldValue.split(", ");
							Address = getZipcode[0].trim();
							ZipCode = getZipcode[1].substring(2).trim();
							blnStatus = updateE2ETrans("Address", Address);
							blnStatus = updateE2ETrans("ZipCode", ZipCode);
							break;
				}
		}
		return blnStatus;
	}
	
	
	public static boolean updateE2EValues(String strFuncValue) {
		boolean status = true;
		Connection ucaConn = null;
		Statement st = null;
		ResultSet rs = null;
//		String e2eTestCaseId = E2ETestCaseUtil.E2E_TestCaseID;
		String e2eTestCaseId = E2ETestCaseUtil.keyMap.get(PCThreadCache.getInstance().getProperty("TCID"));
//		String e2eTestCaseId = "E2E_PC_CLA_SC1";
		String[] DBValues = strFuncValue.split("###");
		if(!(e2eTestCaseId== null))
		{
			try 
			{
				for(String DB : DBValues)
				{
					String[] KeyNameValue = DB.split(":::");
						ucaConn = DBConnectionManager.getInstance().getConnectionFromPool();
						st = ucaConn.createStatement();
						String query = "INSERT into E2E_TRANS(TRANS_KEY_NM, TRANS_KEY_VALUE,E2E_TEST_CASE_ID,LAST_UPDT_TMSP,LAST_UPDT_APPL_NM) VALUES('"
								+ KeyNameValue[0] + "','" + KeyNameValue[1] + "','" + e2eTestCaseId + "',SYSDATE ,'PC')";
						logger.info("executing query = " + query);
						st.executeUpdate(query);
						status = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBConnectionManager.getInstance().closeResultSet(rs);
				DBConnectionManager.getInstance().closeStatement(st);
				DBConnectionManager.getInstance().returnConnectionToPool(ucaConn);
			}
		}else{
			status = true;
		}
		return status;
	}
	
	public static Boolean skillAndTrack(String strSheetName)
	{
		Boolean blnStatus = true;
		Boolean blnSegment = true;
		Boolean blnTrack = true;
		int segmentCount  = 0;
		int trackCount  = 0;
		Common common = CommonManager.getInstance().getCommon();
		XlsxReader xls = XlsxReader.getInstance();
        String tcID = PCThreadCache.getInstance().getProperty("TCID");
        HashMap<String,Object> whereConstraint = new HashMap<String,Object>();    
        ArrayList<HashMap<String,Object>> resultListRows= new ArrayList<HashMap<String,Object>>();                   
        whereConstraint.clear();
        whereConstraint.put(PCConstants.ID, tcID);
        whereConstraint.put(PCConstants.Iteration, PCThreadCache.getInstance().getProperty("Iteration"));
        resultListRows = xls.executeSelectQuery(strSheetName, whereConstraint);
        try{
        	 if(resultListRows.size()>0)
             {
	           	  	for(HashMap<String,Object> processRow: resultListRows)
	     	        {
	           	  		String segment = (String)processRow.get("Segment");
	           	  		segment = segment.trim();
	           	  		String skill = (String)processRow.get("Skill");
	           	  		skill = skill.trim();
	           	  		String track = (String)processRow.get("Track");
	           	  		track = track.trim();
	           	  		segmentCount = 0;
	           	  		trackCount  = 0;
//	           	  		blnSegment = ActionOnTableTrueFalse(Common.o.getObject("businessSegementSkill"), 1, 1, segment,segment, "div");
//		           	  		if(!blnSegment)
//		           	  		{
//		           	  			segmentCount = 1;
//		           	  			blnStatus = common.SafeAction(Common.o.getObject("eleAddSegement"), "eleAddSegement", "eleAddSegement");
//		           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementSkill"), 1, 1, "<none>", "<none>", "div");
//		           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_BusinessSegment"), segment, "lstSkill_BusinessSegment");
//		           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementSkill"), 2, 2, "<none>", "<none>", "div");
//		           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_Skill"), skill, "lstSkill_Skill");
//		           	  			logger.info("Skill '"+skill+"' and segment '"+ segment+"' both are added");
//		           	  		}
//		           	  		if(segmentCount == 0)
//		           	  		{
		           	  			blnSegment = ActionOnTableTrueFalse(Common.o.getObject("businessSegementSkill"), 1, 2, segment, skill, "div");
		           	  			if(!blnSegment)
		           	  			{
			           	  			segmentCount = 1;
				           	  		blnStatus = common.SafeAction(Common.o.getObject("eleAddSegement"), "eleAddSegement", "eleAddSegement");
			           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementSkill"), 1, 1, "<none>", "<none>", "div");
			           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_BusinessSegment"), segment, "lstSkill_BusinessSegment");
			           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementSkill"), 2, 2, "<none>", "<none>", "div");
			           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_Skill"), skill, "lstSkill_Skill");
			           	  			logger.info("Skill '"+skill+"' and segment '"+ segment+"' are added");
			           	  		
		           	  			}
		           	  			else
		           	  			{
		           	  				logger.info("Skill '"+skill+"' and segment '"+ segment+"' combination already available");
		           	  				blnStatus = true;
		           	  			}
		           	  		//}
//		           	  	blnTrack = ActionOnTableTrueFalse(Common.o.getObject("businessSegementTrack"), 1, 1, segment,segment, "div");
//		           	  		if(!blnTrack)
//		           	  		{
//		           	  			trackCount = 1;
//			           	  		blnStatus = common.SafeAction(Common.o.getObject("eleAddSkill"), "eleAddSkill", "eleAddSkill");
//		           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementTrack"), 1, 1, "<none>", "<none>", "div");
//		           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_BusinessSegment"), segment, "lstSkill_BusinessSegment");
//		           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementTrack"), 2, 2, "<none>", "<none>", "div");
//		           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_Track"), track, "lstSkill_Track");
//		           	  			logger.info("Track '"+track+"' and segment '"+ segment+"' are added");
//		           	  		}
//		           	  		if(trackCount == 0)
//		           	  		{
		           	  			blnTrack = ActionOnTableTrueFalse(Common.o.getObject("businessSegementTrack"), 1, 2, segment, track, "div");
		           	  			if(!blnTrack)
		           	  			{
		           	  				trackCount = 1;
				           	  		blnStatus = common.SafeAction(Common.o.getObject("eleAddSkill"), "eleAddSkill", "eleAddSkill");
			           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementTrack"), 1, 1, "<none>", "<none>", "div");
			           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_BusinessSegment"), segment, "lstSkill_BusinessSegment");
			           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementTrack"), 2, 2, "<none>", "<none>", "div");
			           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_Track"), track, "lstSkill_Track");
			           	  			logger.info("Track '"+track+"' and segment '"+ segment+"' are added");
		           	  			}
		           	  			else
		           	  			{
		           	  			logger.info("Track '"+track+"' and segment '"+ segment+"' combination already available");
		           	  			blnStatus = true;
		           	  			}
		           	  		//}
	     	        }
             }else{
            	 logger.info("TestCaseID "+tcID+" or Iteration "+PCThreadCache.getInstance().getProperty("Iteration")+" is not available in the "+strSheetName+" Sheet");
            	 blnStatus = true;
             }
        }catch(Exception e){
        	e.printStackTrace();
        }
		return blnStatus;
	}
	
	public static Boolean skillAndTrackNew(String strSheetName)
	{
		Boolean blnStatus = true;
		Boolean blnSegment = true;
		Boolean blnTrack = true;
		int segmentCount  = 0;
		int trackCount  = 0;
		Common common = CommonManager.getInstance().getCommon();
		XlsxReader xls = XlsxReader.getInstance();
        String tcID = PCThreadCache.getInstance().getProperty("TCID");
        HashMap<String,Object> whereConstraint = new HashMap<String,Object>();    
        ArrayList<HashMap<String,Object>> resultListRows= new ArrayList<HashMap<String,Object>>();                   
        whereConstraint.clear();
        whereConstraint.put(PCConstants.ID, tcID);
        whereConstraint.put(PCConstants.Iteration, PCThreadCache.getInstance().getProperty("Iteration"));
        resultListRows = xls.executeSelectQuery(strSheetName, whereConstraint);
        try{
        	 if(resultListRows.size()>0)
             {
	           	  	for(HashMap<String,Object> processRow: resultListRows)
	     	        {
	           	  		String segment = (String)processRow.get("Segment");
	           	  		segment = segment.trim();
	           	  		String skill = (String)processRow.get("Skill");
	           	  		skill = skill.trim();
	           	  		String track = (String)processRow.get("Track");
	           	  		track = track.trim();
	           	  		segmentCount = 0;
	           	  		trackCount  = 0;
           	  			blnSegment = ActionOnTableTrueFalse(Common.o.getObject("businessSegementSkill"), 1, 2, segment, skill, "div");
           	  			if(!blnSegment)
           	  			{
		           	  		int icheckNextPageButton = common.ElementSize(Common.o.getObject("eleNextPage"));
				   			if(icheckNextPageButton > 0)
				   			{
				   				String strPageCount = common.ReadElement(Common.o.getObject("eleCommonPageNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
				   				strPageCount = SCRCommon.getIntFromString(strPageCount);
				   				for(int i =0; i <Integer.parseInt(strPageCount); i++)
				   				{
				   					blnSegment = ActionOnTableTrueFalse(Common.o.getObject("businessSegementSkill"), 1, 2, segment, skill, "div");
				   					if(!blnSegment)
			           	  			{
				           	  			segmentCount = 1;
					           	  		blnStatus = common.SafeAction(Common.o.getObject("eleAddSegement"), "eleAddSegement", "eleAddSegement");
				           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementSkill"), 1, 1, "<none>", "<none>", "div");
				           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_BusinessSegment"), segment, "lstSkill_BusinessSegment");
				           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementSkill"), 2, 2, "<none>", "<none>", "div");
				           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_Skill"), skill, "lstSkill_Skill");
				           	  			logger.info("Skill '"+skill+"' and segment '"+ segment+"' are added");
				           	  		
			           	  			}
			           	  			else
			           	  			{
			           	  				logger.info("Skill '"+skill+"' and segment '"+ segment+"' combination already available");
			           	  				blnStatus = true;
			           	  			}
				   					blnTrack = ActionOnTableTrueFalse(Common.o.getObject("businessSegementTrack"), 1, 2, segment, track, "div");
			           	  			if(!blnTrack)
			           	  			{
			           	  				trackCount = 1;
					           	  		blnStatus = common.SafeAction(Common.o.getObject("eleAddSkill"), "eleAddSkill", "eleAddSkill");
				           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementTrack"), 1, 1, "<none>", "<none>", "div");
				           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_BusinessSegment"), segment, "lstSkill_BusinessSegment");
				           	  			blnStatus = common.ActionOnTable(Common.o.getObject("businessSegementTrack"), 2, 2, "<none>", "<none>", "div");
				           	  			blnStatus = common.SafeAction(Common.o.getObject("lstSkill_Track"), track, "lstSkill_Track");
				           	  			logger.info("Track '"+track+"' and segment '"+ segment+"' are added");
			           	  			}
			           	  			else
			           	  			{
			           	  				logger.info("Track '"+track+"' and segment '"+ segment+"' combination already available");
			           	  				blnStatus = true;
			           	  			}
				   				}
				   				if(icheckNextPageButton > 0)
				   	 			{
				   	 				blnStatus = common.SafeAction(Common.o.getObject("eleFirstPage"), "", "eleFirstPage");
				   	 			}
				   			}
				   			else
				   			{
				   				logger.info("TestCaseID "+tcID+" or Iteration "+PCThreadCache.getInstance().getProperty("Iteration")+" is not available in the "+strSheetName+" Sheet");
				   			}
           	  			}
	     	        }
             }else{
            	 logger.info("TestCaseID "+tcID+" or Iteration "+PCThreadCache.getInstance().getProperty("Iteration")+" is not available in the "+strSheetName+" Sheet");
            	 blnStatus = true;
             }
        }catch(Exception e){
        	e.printStackTrace();
        }
		return blnStatus;
	}
	
	public static String getMachineName()
	{
		String hostname = null;
		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		    System.out.println(hostname);
		}
		catch (UnknownHostException ex)
		{
		    System.out.println("Hostname can not be resolved");
		}
		return hostname;
	}
}