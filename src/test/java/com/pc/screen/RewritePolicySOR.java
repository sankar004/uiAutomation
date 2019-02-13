/**
 * @ClassPurpose This Class used for the Rewrite SOR Policy
 * @Scriptor Raj
 * @ReviewedBy
 * @ModifiedBy
 * @LastDateModified 3/23/2017
 */
package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.FlatFile;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class RewritePolicySOR {
	
	public static String sheetname = "RewritePolicySOR";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	
	/**
	 * @function Use to perform all the action in the RewritePolicySOR sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRRewritePolicySOR() throws Exception
	{

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			return false;
		}
		
	return status;
	}
	
	/**
	 * @function - This method used to return Effective Date
	 * @return True/False
	 * @throws Exception
	**/
	public boolean AddEffectiveDate(String strValue) throws Exception
	{
		boolean status = false;
		switch (strValue)
		{
			case "Date Received":
			{
				status = common.SafeAction(Common.o.getObject("edtCan_DateReceived"), SCRCommon.ReturnCurrentDate(), "lst");
			}
		}
		return status;
	}
	
	/**
	 * @function use to select the questions according to the input
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean CancelationQuestions(String sFuncValue) throws Exception
	{
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		status = ActionOnTable(Common.o.getObject("eleCan_ODENQues"), 0, 1, sValue[0], sValue[1], "input");
		return status;
	}
	
	/**
	 * @function use to select the questions according to the given input
	 * @param obj
	 * @param readCol
	 * @param actionCol
	 * @param strReadString
	 * @param actionObjetName(true/false)
	 * @param sTagName
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean ActionOnTable(By obj, int readCol, int actionCol, String strReadString, String actionObjetName, String sTagName) throws Exception
	{	
	  boolean Status=false;
	  boolean SearchString=false;
	  boolean ActionObject=false;
	  WebElement mytable = driver.findElement(obj);
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
	
	public boolean PolicyNumber() throws Exception
	{
		boolean status = false;
		Payment payment = new Payment();
		status = common.WaitForElementExist(Common.o.getObject("eleQuotePolicyNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		if(status)
		{
			payment.policyNumber = common.ReadElement(Common.o.getObject("eleQuotePolicyNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
			PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, payment.policyNumber);
			payment.policyNumberSearch = payment.policyNumber.substring(payment.policyNumber.length()-6);
			PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH, payment.policyNumberSearch);
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Number shold be generated","Policy Number generated: Policy Number is '" + payment.policyNumber + "'", "PASS");
			if(HTML.properties.getProperty("EXECUTIONAPP").contains("ODS"))
			{
				FlatFile sReadWrite = FlatFile.getInstance();
				SCRCommon SCRCommon = new SCRCommon();
				String FlatFileName = SCRCommon.FlatFileName();
				String policyNumebrSearch = "%"+payment.policyNumberSearch;
				status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "PolicyNumber", policyNumebrSearch, "OUTPUT", FlatFileName);
			}
		}
		return status;
	}
}
