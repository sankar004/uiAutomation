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

public class Payment {
	
	public static String sheetname = "Payment";
	static Logger logger =Logger.getLogger(sheetname);
	public  String policyNumber;
	public  String policyNumberSearch;
	public  String submissionNumber;
	Common common = CommonManager.getInstance().getCommon();
	
	public Boolean SCRPayment() throws Exception
	{

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			status = false;
		}
		return status;
	 }
		
	public boolean PolicyNumber() throws Exception
	{
		boolean status = false;
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		common.WaitForElementExist(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		policyNumber = common.ReadElement(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER, policyNumber);
		policyNumberSearch = policyNumber.substring(policyNumber.length()-6);
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_POLICY_NUMBER_SEARCH, policyNumberSearch);
		logger.info("Policy Created::Policy Number '" + policyNumber + "'");
		logger.info("Policy Created::Last Six Digit Policy Number '" + policyNumberSearch + "'");
		updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		status = sXL.executeUpdateQuery(sheetname,  updateColumnNameValues,  whereConstraint );
		if(HTML.properties.getProperty("EXECUTIONAPP").contains("ODS"))
		{
			FlatFile sReadWrite = FlatFile.getInstance();
			SCRCommon SCRCommon = new SCRCommon();
			String FlatFileName = SCRCommon.FlatFileName();
			String policyNumebrSearch = "%"+policyNumberSearch;
			status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "PolicyNumber", policyNumebrSearch, "OUTPUT", FlatFileName);
			status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "FPolicyNumber", policyNumber, "OUTPUT", FlatFileName);
//			status = sReadWrite.write(PCThreadCache.getInstance().getProperty("TCID"),PCThreadCache.getInstance().getProperty("methodName"), "PolicyNumber", policyNumber, "OUTPUT", FlatFileName);
		}
		//Marking objects for Garbage collection
		whereConstraint = null;
		updateColumnNameValues = null;
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Number shold be generated","Policy Number generated: Policy Number is '" + policyNumber + "'", "PASS");
		return status;
	}
		
	public boolean PolicyNumber1() throws Exception
	{
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		String wait = HTML.properties.getProperty("LONGWAIT");
		Integer x = Integer.valueOf(wait);
		common.WaitForElementExist(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), x);
		String sPolicyNumber = common.ReadElement(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), x);
		logger.info("Policy Created::Policy Number '" + sPolicyNumber + "'");
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		sXL.executeUpdateQuery(sheetname,  updateColumnNameValues,  whereConstraint );
		//Marking objects for Garbage collection
		whereConstraint = null;
		updateColumnNameValues = null;
		//common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update Payment Set PolicyNumber = '"+ sPolicyNumber +"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Number shold be generated","Policy Number generated: Policy Number is '" + sPolicyNumber + "'", "PASS");
		return true;
	}
	
	public boolean SubmissionNumber() throws Exception
	{
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
		XlsxReader sXL = XlsxReader.getInstance();
		common.WaitForElementExist(Common.o.getObject("elePolicyNumbetInPolicySummaryPage"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		submissionNumber = common.ReadElement(Common.o.getObject("eleSubmissionNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		logger.info("Policy Created::Submission Number '" +submissionNumber+ "'");
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_SUBMISSION_NUMBER, submissionNumber);
		updateColumnNameValues.put(PCConstants.SubmissionNumber, submissionNumber);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		sXL.executeUpdateQuery(PCConstants.SHEET_PAYMENT,  updateColumnNameValues,  whereConstraint );
		//Marking objects for Garbage collection
		whereConstraint = null;
		updateColumnNameValues = null;
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Submission Number shold be generated","Submission Number generated and the number is '" + submissionNumber + "'", "PASS");
		return true;
	}
	
	public boolean RetrivePolicyNumber() throws Exception
	{
		boolean status = false;
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
		XlsxReader sXL = XlsxReader.getInstance();
//		sXL = new XlsxReader(HTML.properties.getProperty("DataSheetName"));
		common.WaitForElementExist(Common.o.getObject("eleShell_PolicyNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		policyNumber = common.ReadElement(Common.o.getObject("eleShell_PolicyNumber"), Integer.valueOf(HTML.properties.getProperty("LONGWAIT")));
		policyNumberSearch = policyNumber.substring(policyNumber.length()-6);
		logger.info("Policy Created::Policy Number '" + policyNumber + "'");
		logger.info("Policy Created::Last Six Digit Policy Number '" + policyNumberSearch + "'");
//		status = Common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update "+sheetname+" Set PolicyNumber = '"+PolicyNumber+"' where ID='"+HTML.properties.getProperty("TCID")+"'");
		PCThreadCache.getInstance().setProperty(PCConstants.CACHE_SUBMISSION_NUMBER, policyNumber);
		updateColumnNameValues.put(PCConstants.PolicyNumber, policyNumber);
		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
		sXL.executeUpdateQuery(PCConstants.SHEET_PAYMENT,  updateColumnNameValues,  whereConstraint );
		//Marking objects for Garbage collection
		whereConstraint = null;
		updateColumnNameValues = null;
		HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Policy Number shold be generated","Policy Number generated: Policy Number is '" + policyNumber + "'", "PASS");
		return status;
	}
}
