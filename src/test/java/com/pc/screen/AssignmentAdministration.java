/**
 * @ClassPurpose This Class used for the AssignmentAdministration usecase
 * @Scriptor Kumar
 * @ReviewedBy
 * @ModifiedBy Sojan David
 * @LastDateModified 3/24/2017
 */
package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class AssignmentAdministration {
	
	public static String sheetname = "AssignmentAdministration";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	
	/**
	 * @function Use to perform all the objects/elements/functions from the AssignmentAdministration excel sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRAssignmentAdministration() throws Exception
	{
			Boolean status = true;
			status = common.ClassComponent(sheetname,Common.o);
			if(!status)
			{
				status = false;
			}
			return status;
	}
	
	/**
	 * @function use to put the assignment adminsitration table in the table
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean AssignmentAdministrationTable(String sFuncValue) throws Exception
	{
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleAssignmentAdministrationTable"));    	
		try
		{
			switch (sValue[0].toUpperCase())
			{
					case "NEW":
			    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 1, "", "single", "div");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchIcon"), "ele", "ele");
			    		status = common.SafeAction(Common.o.getObject("edtAAAgencyName"), sValue[1], "edt");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
			    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 3, "<none>", "single", "div");
			    		status = common.SafeAction(Common.o.getObject("lstAssigneeRole"), sValue[2],"lst");
			    		status = common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"), "ele", "ele");
			    		status = common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[3], "edt");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
			    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 5, "", "single", "div");
			    		status = common.SafeAction(Common.o.getObject("lstAASegment"), sValue[4], "lst");
			    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 8, "", "single", "div");
			    		status = common.SafeAction(Common.o.getObject("lstEffectiveDate"), SCRCommon.ReturnCurrentDate(), "lst");
		    		break;
					case "CONCAT":
						status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 1, "", "single", "div");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchIcon"), "ele", "ele");
			    		status = common.SafeAction(Common.o.getObject("edtAAAgencyName"), sValue[1], "edt");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
			    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 3, "", "single", "div");
			    		status = common.SafeAction(Common.o.getObject("lstAssigneeRole"), sValue[2],"lst");
			    		status = common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"), "ele", "ele");
			    		status = common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[3], "edt");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
			    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
			    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 5, "", "single", "div");
			    		status = common.SafeAction(Common.o.getObject("lstAASegment"), sValue[4], "lst");
//			    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 5, "", "single");
			    		status = common.SafeAction(Common.o.getObject("lstSIC"), sValue[5], "lst");
//			    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 6, "", "single");
			    //		status = common.SafeAction(Common.o.getObject("lstRenewalReviewType"), sValue[6], "lst");
       		    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 7, "", "single","div");
			    		status = common.SafeAction(Common.o.getObject("lstEffectiveDate"), SCRCommon.ReturnCurrentDate(), "lst");
			    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 13, "", "single", "div");
			    		status = common.SafeAction(Common.o.getObject("lstJobType"), sValue[7], "lst");
						break;
			}
    	}
		catch(Throwable e)
		{
			e.printStackTrace();
			if(status == false)
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class","System not completed performing the add class", "FAIL");
			}
		}
		return status;	
	}
	
	/**
	 * @function edit the assignment administration table
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean EditAssignmentAdministrationTable(String sFuncValue) throws Exception
	{
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleAssignmentAdministrationTable"));    	
		try
		{
//    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 1, "", "single");
//    		status = Common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchIcon"), "ele", "ele");
//    		status = Common.SafeAction(Common.o.getObject("edtAAAgencyName"), sValue[0], "edt");
//    		status = Common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
//    		status = Common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 3, sValue[0], "single", "div");
    		status = common.SafeAction(Common.o.getObject("lstAssigneeRole"), sValue[1],"lst");
//    		status = Common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"), "ele", "ele");
//    		status = Common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[2], "edt");
//    		status = Common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
//    		status = Common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
//    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 4, sValue[2], "single");
//    		status = Common.SafeAction(Common.o.getObject("lstAASegment"), sValue[3], "lst");
//    		status = SCRCommon.DataWebTable(Common.o.getObject("eleAssignmentAdministrationTable"), sRowCount, 7, "", "single");
//    		status = Common.SafeAction(Common.o.getObject("lstEffectiveDate"), SCRCommon.ReturnCurrentDate(), "lst");
    	}
		catch(Throwable e)
		{
			e.printStackTrace();
			if(status == false)
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class","System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}
	
	/**
	 * @function use to select the assignment
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean SelectAssignment(String sFuncValue) throws Exception
	{
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleAssignmentAdministrationTable"), 1, 0, sFuncValue, "", "div");
		return status;
	}
	
	/**
	 * 
	 * @param sFuncValue
	 * @return
	 * @throws Exception
	 */
	public boolean SBACUWAssignmentTable(String sFuncValue) throws Exception
	{
		boolean status = false;
		String sValue[] = sFuncValue.split(":::");
		int sRowCount = SCRCommon.TableRowCount(Common.o.getObject("eleSBACUWAssignmentTable"));    	
		try
		{
			switch (sValue[0].toUpperCase())
			{	
				case "NEW":
		    		status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 1, "", "single", "div");
		    		status = common.SafeAction(Common.o.getObject("edtSBACUWAgentMail"), sValue[1], "edt");
		    		status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 3, "", "single", "div");
		    		status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentFirstName"), sValue[2], "edt");
		    		status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 4, "", "single", "div");
		    		status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentLastName"), sValue[3], "edt");
		    		status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 5, "", "single", "div");
		    		status = common.SafeAction(Common.o.getObject("eleAAAssignedUserIcon"), "ele", "ele");
		    		status = common.SafeAction(Common.o.getObject("edtAAUserName"), sValue[4], "edt");
		    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSearchButton"), "eleAAProducerCodeSearchButton", "ele");
		    		status = common.SafeAction(Common.o.getObject("eleAAProducerCodeSelect"), "eleAAProducerCodeSelect", "ele");
		    		break;
				case "UPDATE":
					status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 1, "", "single", "div");
		    		status = common.SafeAction(Common.o.getObject("edtSBACUWAgentMail"), sValue[0], "edt");
		    		status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 3, "", "single", "div");
		    		status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentFirstName"), sValue[1], "edt");
		    		status = SCRCommon.DataWebTable(Common.o.getObject("eleSBACUWAssignmentTable"), sRowCount, 4, "", "single", "div");
		    		status = common.SafeAction(Common.o.getObject("edtSBACUWAgentAgentLastName"), sValue[2], "edt");
					break;
			}
    	}
		catch(Throwable e)
		{
			e.printStackTrace();
			if(status == false)
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "System should complete performing the add class","System not completed performing the add class", "FAIL");
			}
		}
		return status;
	}
	
	/**
	 * @function Verifying the results of the cases
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean VerifyResults(String sFuncValue) throws Exception
	{
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		logger.info("Verifying the Results");
		String Value = null;
			switch (sValue[0].toUpperCase())
			{	
				case "VERIFYCREATEDASSIGNMENT":
					logger.info(sValue[0]);
					Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 1, sValue[1]);
					status = common.CompareStringResult(sValue[0], sValue[1], Value);
					Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 3, sValue[2]);
					status = common.CompareStringResult(sValue[0], sValue[2], Value);
					Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 4, sValue[3]);
					status = common.CompareStringResult(sValue[0], sValue[3], Value);
					Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 5, sValue[4]);
					status = common.CompareStringResult(sValue[0], sValue[4], Value);
				break;
				case "VERIFYEDITEDASSIGNMENT":
					logger.info(sValue[0]);
					Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 3, sValue[1]);
					status = common.CompareStringResult(sValue[0], sValue[1], Value);
//					Value = Common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 4, sValue[2]);
//					status = Common.CompareStringResult(sValue[0], sValue[2], Value);
				break;
				case "VERIFYDELETERESULTS":
					logger.info(sValue[0]);
					Value = common.ReadElement(Common.o.getObject("eleZeroResultsErrorMessage"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
					status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
				case "VERIFYNEWASSIGNEE":
					logger.info(sValue[0]);
					Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 4, sValue[1]);
					status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
				/*case "VERIFYSBACUWCREATEDASSIGNMENT":
					Value = Common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 1, sValue[1]);
					status = Common.CompareStringResult(sValue[0], sValue[1], Value);
					Value = Common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 3, sValue[2]);
					status = Common.CompareStringResult(sValue[0], sValue[2], Value);
					Value = Common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 4, sValue[3]);
					status = Common.CompareStringResult(sValue[0], sValue[3], Value);
					Value = Common.GetTextFromTable(Common.o.getObject("eleSBACUWAssignmentTable"), 5, sValue[4]);
					status = Common.CompareStringResult(sValue[0], sValue[4], Value);
				break;*/
				case "VERIFYCONCATRESULTS":
					logger.info(sValue[0]);
					Value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 10, sValue[1]);
					status = common.CompareStringResult(sValue[0], sValue[1], Value);
					break;
				case "VERIFYASSIGNEDUSER":
					logger.info(sValue[0]);
					Value = common.GetTextFromTable(Common.o.getObject("eleAASelectUser"), 1, sValue[1]);
					status = common.CompareStringResult(sValue[0], sValue[1], Value);
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
	 * @function use to delete the assignment adminstration prequest
	 * @param sFuncValue
	 * @return true/false
	 * @throws Exception
	 */
	public boolean preRequestCheck(String sFuncValue) throws Exception
	{
		boolean status = false;
		String[] sValue = sFuncValue.split(":::");
		String value = null;
		int elementSize = 0;
		switch (sValue[0].toUpperCase())
		{
			case "ASSIGNMENTADMINISTRATION":
				elementSize = common.ElementSize(Common.o.getObject("eleZeroResultMsg"));
				if(elementSize == 0)
				{
					logger.info("Assignment Administration User is available hence deleteing it");
					status = common.SafeAction(Common.o.getObject("eleAAEdit"), "", "eleAAEdit");
					value = common.GetTextFromTable(Common.o.getObject("eleAssignmentAdministrationTable"), 4, sValue[1]);
					if(value.equalsIgnoreCase(sValue[1]))
					{
						status = common.ActionOnTable(Common.o.getObject("eleAssignmentAdministrationTable"), 4, 0, sValue[1], "", "div");
						status = common.SafeAction(Common.o.getObject("eleAADelete"), "", "eleAADelete");
						status = common.SafeAction(Common.o.getObject("eleAlertPopUp"), "", "eleAlertPopUp");
					}
				}else
				{
					status = true;
					logger.info("Assignment Administration PreUser is not available");
				}
				break;
		}
			
		return status;
	}

}