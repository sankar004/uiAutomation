package com.pc.screen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class PolicyInfo {
	
		public static String sheetname = "PolicyInfo";
		static Logger logger =Logger.getLogger(sheetname);
		Common common = CommonManager.getInstance().getCommon();
		
		public Boolean SCRPolicyInfo() throws Exception{
			
			Boolean status = true;
			status = common.ClassComponent(sheetname, Common.o);
			if(!status)
			{
				status = false;
			}
			return status;
	}
		
		public boolean VerifyResults(String sFuncValue) throws Exception
		{
			boolean status = false;
			String[] sValue = sFuncValue.split(":::");
			logger.info("Verifying the Results");
			String Value = null;
				switch (sValue[0].toUpperCase())
				{				
						case "VERIFYPOLICYINFO":
					    logger.info(sValue[0]);
						Value = common.ReadElementGetAttribute(Common.o.getObject("edtIndusCode"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						status = common.CompareStringResult(sValue[0], sValue[1], Value);
						Value = common.ReadElementGetAttribute(Common.o.getObject("edtNAICS"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						status = common.CompareStringResult(sValue[0], sValue[2], Value);
						Value = common.ReadElementGetAttribute(Common.o.getObject("edtMSI"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						status = common.CompareStringResult(sValue[0], sValue[3], Value);
						Value = common.ReadElementGetAttribute(Common.o.getObject("edtEBS"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						status = common.CompareStringResult(sValue[0], sValue[4], Value);
						Value = common.ReadElementGetAttribute(Common.o.getObject("lstOrganisationType"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						status = common.CompareStringResult(sValue[0], sValue[5], Value);
						Value = common.ReadElementGetAttribute(Common.o.getObject("edtFEIN"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						status = common.CompareStringResult(sValue[0], sValue[6], Value);
						Value = common.ReadElementGetAttribute(Common.o.getObject("lstProductType"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						status = common.CompareStringResult(sValue[0], sValue[7], Value);
						Value = common.ReadElementGetAttribute(Common.o.getObject("lstSubmissionSource"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						status = common.CompareStringResult(sValue[0], sValue[8], Value);
						Value = common.ReadElementGetAttribute(Common.o.getObject("lstSalesAgreementCode"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
						status = common.CompareStringResult(sValue[0], sValue[9], Value);
						break;
						case "VERIFYPOLICYINFOSCREEN":
							Value = common.ReadElement(Common.o.getObject("elePolicyInfoAssert"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
							status = common.CompareStringResult(sValue[0], sValue[1], Value);
							break;
						case "VERIFYBORSTARTDATE":
			                    logger.info(sValue[0]);
			                                  
			                                  Value = common.ReadElement(Common.o.getObject("elePolBORStartDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			                                  status = common.CompareStringResult("PolicyBORStartDate", SCRCommon.ReturnCurrentDate(), Value);
			                               break;
			                               
			                           case "VERIFYBORENDDATE":
			                                  logger.info(sValue[0]);
			                                  Value = common.ReadElementGetAttribute(Common.o.getObject("elePolBOREndDate"),"value", Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			                                status = common.CompareStringResult("PolicyBOREnd", sValue[1], Value);
			                                break;
			                                
			                           case "BORDATE":
			                                  logger.info(sValue[0]);
			                                  
			                                  Value = common.ReadElement(Common.o.getObject("elePolBORStartDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			                                  status = common.CompareStringResult("PolicyBORStartDate", SCRCommon.ReturnCurrentDate(), Value);
			                                  Value = common.ReadElement(Common.o.getObject("elePolBOREndDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			                                  status = common.CompareStringResult("PolicyBOREndDate", SCRCommon.ReturnSixtyDaysFromDate(), Value);
			                                  break;
			                           case "VERIFYSORBORNOTSET":
			                               logger.info(sValue[0]);
			                               Value = common.ReadElement(Common.o.getObject("elePolBORStartDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			                               if(Value.equals(SCRCommon.ReturnCurrentDate()))
			                               {
			                                      status = false;
			                                      HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Current Date should not match","Current Date is matching", "FAIL");
			                               }else{
			                                      status = true;
			                                      HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Current Date should not match","Current Date is not matching", "PASS");
			                               }
			                               
			                               Value = common.ReadElement(Common.o.getObject("elePolBOREndDate"), Integer.valueOf(HTML.properties.getProperty("NORMALWAIT")));
			                               if(Value.equals(SCRCommon.ReturnSixtyDaysFromDate()))
			                               {
			                                      status = false;
			                                      HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Sixty Days from Current Date should not match","Sixty Days from Current Date is matching", "FAIL");
			                               }else{
			                                      status = true;
			                                      HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Sixty Days from Current Date should not match","Sixty Days from Current Date is not matching", "PASS");
			                               }
			                               
			                                break; 
		      	}
				if(!status)
				{
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sValue[0]+" case should PASS",""+sValue[0]+" case should not fail", "FAIL");
					status = true;
				}
			return status;
		}
		
		/**
         * @function use to select the check box of the particular Named Insured in Additional Named Insured table
         * @param  sReadString
         * @return true/false
         * @throws Exception
         */
         public boolean SelectNamedInsured(String sReadString) throws Exception
     	{
     		boolean status = false;
     		JavascriptExecutor js = (JavascriptExecutor) ManagerDriver.getInstance().getWebDriver(); 
 			status=SCRCommon.ActionOnTableCheckBox(Common.o.getObject("eleAddInsDBAAddedTable"), 2, 4, sReadString, "img", "elePolicyInfoAssert");
 			status = SCRCommon.JavaScript(js);
     		return status;
     	}
             
         public void verifyRadio(String eleName)
         {
        	
        	 List<WebElement> radio_options = ManagerDriver.getInstance().getWebDriver().findElements(Common.o.getObject(eleName));
        	 for(WebElement option:radio_options)
        	        {
        	            if(option.isSelected())
        	            {
        	                System.out.println(option.getAttribute("innerHTML"));
        	                break;
        	            }
        	        }
         }
         
         public boolean submissionOriginalDate(String strFuncValue) throws Exception
     	{
     		boolean status = false;
     		String[] strSplitValue = strFuncValue.split(":::");
     		String strDate = null;
     		switch(strSplitValue[0].toUpperCase())
     		{
     			case"CURRENTDATE":
     				strDate = SCRCommon.ReturnCurrentDate();
     				status = common.SafeAction(Common.o.getObject("edtSubmissionOrginalDate"), strDate, "edt");
     				break;
     			case"INCREASEDECREASEDATE":
     				strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
     				status = common.SafeAction(Common.o.getObject("edtSubmissionOrginalDate"), strDate, "edt");
     				break;
     		}
     		return status;
     	}
         
     	public boolean mMUserNBReceivedDate(String strFuncValue) throws Exception
     	{
     		boolean status = false;
     		String[] strSplitValue = strFuncValue.split(":::");
     		String strDate = null;
     		switch(strSplitValue[0].toUpperCase())
     		{
     			case"CURRENTDATE":
     				strDate = SCRCommon.ReturnCurrentDate();
     				status = common.SafeAction(Common.o.getObject("edtMMUserReceivedDate"), strDate, "edt");
     				break;
     			case"INCREASEDECREASEDATE":
     				strDate = SCRCommon.returnDate(Integer.parseInt(strSplitValue[1]));
     				status = common.SafeAction(Common.o.getObject("edtMMUserReceivedDate"), strDate, "edt");
     				break;
     		}
     		return status;
     	}
         
}
