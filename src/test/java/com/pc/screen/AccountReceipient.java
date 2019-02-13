package com.pc.screen;

import org.apache.log4j.Logger;

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;

public class AccountReceipient {
	
	public static String sheetname = "AccountReceipient";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	
	public Boolean SCRAccountReceipient() throws Exception
	{
			Boolean status = true;
			status = common.ClassComponent(sheetname,Common.o);
			if(!status)
			{
				status = false;
			}
			return status;
	}
	
	public boolean VerifyResults(String funValue) throws Exception
	{
		boolean status = true;
		String[] sValue = funValue.split(":::");
		String Value= null;
		logger.info("Verfying the Results");
		switch (sValue[0].toUpperCase())
		{
			case "EVIDENCETYPEVALUE":
				logger.info(sValue[0]);
				Value = common.ReadElementGetAttribute(Common.o.getObject("txtAREvidenceType"), "value", Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				break;
			case"ACCOUNTRECEIPIENTDATA":
				logger.info(sValue[0]);
				status = SelectAccountReceipientName(sValue[1]);
				status = common.WaitForElementExist(Common.o.getObject("txtARName1"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				Value = common.ReadElement(Common.o.getObject("txtARName1"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				Value = common.ReadElement(Common.o.getObject("txtARName2"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[2], Value);
				Value = common.ReadElement(Common.o.getObject("txtAREmail"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[3], Value);
				Value = common.ReadElement(Common.o.getObject("txtARAddress"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[4], Value);
				Value = common.ReadElement(Common.o.getObject("txtARCertType"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[5], Value);
				break;
			case "CANCELCHECK":
				logger.info(sValue[0]);
				status = common.ElementExistOrNotTrue(sValue[0], "Cancel Button Should Work", "Cancel Button is working ",Common.o.getObject("eleEdtButton"));
				break;
			case "SELECTACCOUNTRECEIPIENT":
				logger.info(sValue[0]);
				status = SelectAccountReceipientName(sValue[1]);
				break;
			case "UPDATECANCELCHECK":
				logger.info(sValue[0]);
				status = common.ElementExistOrNotTrue(sValue[0], "Update Cancel Button Should Work", "Update Cancel Button is working ",Common.o.getObject("eleAccountReceipientLabel"));
				break;
			case"UPDATEACCOUNTRECEIPIENTDATA":
				logger.info(sValue[0]);
				status = common.WaitForElementExist(Common.o.getObject("txtARName1"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				Value = common.ReadElement(Common.o.getObject("txtARName1"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[1], Value);
				Value = common.ReadElement(Common.o.getObject("txtARName2"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[2], Value);
				Value = common.ReadElement(Common.o.getObject("txtAREmail"), Integer.valueOf(HTML.properties.getProperty("VERYLONGWAIT")));
				status = common.CompareStringResult(sValue[0], sValue[3], Value);
				break;
		}
		if(!status)
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), ""+sValue[0]+" case should PASS",""+sValue[0]+" case should not fail", "FAIL");
			status = true;
		}
		return status;
	}	
	
	public boolean SelectAccountReceipientName(String sReadValue) throws Exception
	{
		boolean status = false;
		status = common.ActionOnTable(Common.o.getObject("eleARAccountRecipientsTable"), 2, 2, sReadValue, sReadValue, "a");
		return status;
	}
}