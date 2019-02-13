package com.pc.screen;

import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class AccountContact {
	
	public static String sheetname = "AccountContact";
	Common common = CommonManager.getInstance().getCommon();
	static Logger logger =Logger.getLogger(sheetname);
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	
	public Boolean SCRAccountContact() throws Exception
	{
			Boolean status = true;
			status = common.ClassComponent(sheetname,Common.o);

			if(!status)
			{
				status = false;
			}
			return status;
	}
	
	public boolean DataFill() throws Exception
	{
		HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
		HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
		String accountNumber = PCThreadCache.getInstance().getProperty(PCConstants.CACHE_ACCOUNT_NUMBER);
		String tcID = PCThreadCache.getInstance().getProperty("TCID");
		boolean status = false;
		XlsxReader sXL = XlsxReader.getInstance();
		int rowcount = sXL.getRowCount(sheetname);
		for(int i=1;i<rowcount;i++)
		{
			String value = sXL.getCellData(sheetname, 0, i);
			if(!value.isEmpty())
			{
				if(PCThreadCache.getInstance().getProperty("TCID").equals(value))
				{
					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.AccountNumber, accountNumber);
					whereConstraint.put(PCConstants.ID, tcID);
					status = sXL.executeUpdateQuery(PCConstants.SHEET_ACCOUNTCONTACT,  updateColumnNameValues,  whereConstraint );
					
					
					updateColumnNameValues.clear();
					whereConstraint.clear();
					updateColumnNameValues.put(PCConstants.edtaccountNumber, accountNumber);
					whereConstraint.put(PCConstants.ID, tcID);
					status = sXL.executeUpdateQuery(PCConstants.SHEET_SEARCHACCOUNT,  updateColumnNameValues,  whereConstraint );
					
					//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update AccountContact Set AccountNumber = '"+CreateAccount.AccountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
					//status = common.UpdateQuery(HTML.properties.getProperty("DataSheetName"), "Update SearchAccount Set edtaccountNumber = '"+CreateAccount.AccountNumber+"' where ID='"+PCThreadCache.getInstance().getProperty("TCID")+"'");
					status = true;
					break;
				}
			}
		}	
		return status;
	}
	
	public boolean VerifyResults(String funValue) throws Exception
	{
		boolean status = true;
		String[] sValue = funValue.split(":::");
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		switch (sValue[0].toUpperCase())
		{
			case "VERIFTYACCOUNTDETAILS":
			logger.info("Validating the '"+sValue[0]+"'");
					AccountFileTable(sValue[1]);
					common.SafeAction(Common.o.getObject("eleAccountDetailTab"), "value", "eleAccountDetailTab");
			    	String accNameinContactDetail = common.ReadElement(Common.o.getObject("txtAccNameinContactDetail"), x);
			    	String addressinContactDetail = common.ReadElement(Common.o.getObject("txtAddressinContactDetail"), x);
			    	String addressTypeinContactDetail = common.ReadElement(Common.o.getObject("txtMailinginContactDetail"), x);
			    	String FEINinContactDetail = common.ReadElement(Common.o.getObject("txtFEINinContactDetail"), x);
			    	common.CompareStringResult("Account Name", sValue[1], accNameinContactDetail);
			    	common.CompareStringResult("Address", sValue[2], addressinContactDetail);
			    	common.CompareStringResult("Address Type", sValue[3], addressTypeinContactDetail);
			    	common.CompareStringResult("FEIN Number", sValue[4], FEINinContactDetail);			    	
		    break;
		    
			case "VERIFYALLTHEROLES":
				logger.info("Validating the '"+sValue[0]+"'");
				common.SafeAction(Common.o.getObject("eleRoleTab"), "", "ele");
				AccountFileTable(sValue[1]);
				AccountFileRoleTable(sValue[2]);
			break;
			
			case "ADDINGNEWCONTACT":
				logger.info("Validating the '"+sValue[0]+"'");
//				Common.MouseHoverAction(Common.o.getObject("eleBillingContactforCompany"), Common.o.getObject("eleBillingContactforCompany1"));
			break;
			
			case "UPDATEVALUEVERIFY":
				logger.info("Validating the '"+sValue[0]+"'");
				UpdateStatusforAccountFileTable(sValue[1], sValue[2]);
			break;
			
			case "REMOVEROLE":
				logger.info("Validating the '"+sValue[0]+"'");
				RemoveRole(sValue[2]);
			break;	
			
			case "CHOOSETHEPRIMARYMAILING":
				logger.info("Validating the '"+sValue[0]+"'");
			break;	
				
		}
		return status;
	}
	
	public boolean AccountFileRoleTable(String accRole) throws Exception
	{
		boolean status = true;
		String wait = HTML.properties.getProperty("SHORTWAIT");
		Integer x = Integer.valueOf(wait);
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for(int i=1;i<=rows_count1;i++)
		{
			String accountRoles = common.ReadElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactCV:ContactRolesDV:ContactRolesLV-body']/div/table/tbody/tr["+i+"]/td[2]"), x);
			if(accountRoles.equals(accRole))
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Role '"+accRole+"' should match", "Account Role '"+accountRoles+"' is matching", "PASS");
				status = true;
				break;
			}
			else
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Role '"+accRole+"' should match", "Account Role '"+accountRoles+"' is not matching", "FAIL");
			}
		}
		return status;
	}
	
	/*public boolean AccountFileEditTable(String saccName) throws Exception
	{
		boolean status = true;
		String[] sFunValue = saccName.split(":::");
		Common.SafeAction(Common.o.getObject("accFileContactRoleTable"), "", "ele");
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for(int i=1;i<=rows_count1; i++)
		{
			String accName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).getText();
			if(accName.equals(sFunValue[0]))
			{
				driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Name '"+sFunValue[0]+"' should match", "Account Name '"+accName+"' is matching", "PASS");
				status = true;
			}
			else
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Name '"+sFunValue[0]+"' should match", "Account Name '"+accName+"' is not matching", "FAIL");
			}
			if(sFunValue[1].equals("EDIT"))
			{
				driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]/div/a")).click();
			}
			break;
		}			
		return status;
	}*/
	
    public boolean AccountFileEditTable(String saccName) throws Exception
    {
    boolean status = true;
    String[] sFunValue = saccName.split(":::");
    common.SafeAction(Common.o.getObject("accFileContactRoleTable"), "", "ele");
    WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
    List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
    int rows_count1 = rows_table1.size();
    for(int i=1;i<=rows_count1; i++)
    {
           String accName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).getText();
           if(accName.equals(sFunValue[0]))
           {
                  driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).click();
                 HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Name '"+sFunValue[0]+"' should match", "Account Name '"+accName+"' is matching", "PASS");
                 status = true;
                 if(sFunValue[1].equals("EDIT"))
                 {
                        driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]/div/a")).click();
                        break;
                 }
                 else
                 {
                        break;
                 }
           }                    
    }                    
    return status;
    }

	public boolean UpdateStatusforAccountFileTable(String sAccName, String sContactRole) throws Exception
	{
		boolean status = false;
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for(int i=1;i<=rows_count1;i++)
		{
			String accName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).getText();
			if(accName.equals(sAccName))
			{
				driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Name '"+sAccName+"' should match", "Account Name '"+accName+"' is matching", "PASS");
				String sNewRole = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[4]")).getText();
				if(sNewRole.contains(sContactRole))
				{
					HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account File Contact Role '"+sContactRole+"' should match", "Account File Contact Role '"+sNewRole+"' is matching", "PASS");
					status = true;
				}
			}
		}			
		if(status == false)
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account File Contact Role should matched", "Account File Contact Role not matches", "FAIL");
		}
		return status;
	}
	
	public boolean RemoveRole(String sContactRole) throws Exception
	{
		boolean status = false;
		WebElement tableRowXpathAccountFileTableRole = driver.findElement(Common.o.getObject("eleAccountFileRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathAccountFileTableRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for(int i=1;i<=rows_count1;i++)
		{
			String sRoleName = driver.findElement(By.xpath(".//*[@id='EditAccountContactPopup:ContactDetailScreen:AccountContactCV:ContactRolesDV:ContactRolesLV-body']/div/table/tbody/tr["+i+"]/td[2]/div")).getText();
			if(sRoleName.equals(sContactRole))
			{
				driver.findElement(By.xpath(".//*[@id='EditAccountContactPopup:ContactDetailScreen:AccountContactCV:ContactRolesDV:ContactRolesLV-body']/div/table/tbody/tr["+i+"]/td[1]/div/img")).click();
				common.SafeAction(Common.o.getObject("eleRemoveRole"), "value", "eleRemoveRole");
				common.SafeAction(Common.o.getObject("eleAccountFileContactUpdate"), "value", "eleAccountFileContactUpdate");
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Contact Role '"+sContactRole+"' should remove", "Account Contact Role '"+sContactRole+"' is Removed","PASS");
				status = true;
				break;
			}
		}
		if(status == false)
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Contact Role '"+sContactRole+"' should remove", "Account Contact Role '"+sContactRole+"' is not Removed","FAIL");
		}
		return status;
	}
	
	public boolean AccountFileTable(String saccName) throws Exception
	{
		boolean status = true;
		String[] sFunValue = saccName.split(":::");
		common.SafeAction(Common.o.getObject("accFileContactRoleTable"), "", "ele");
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("accFileContactRoleTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for(int i=1;i<=rows_count1;i++)
		{
			String accName = driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).getText();
			if(accName.equals(sFunValue[0]))
			{
				driver.findElement(By.xpath(".//*[@id='AccountFile_Contacts:AccountFile_ContactsScreen:AccountContactsLV-body']/div/table/tbody/tr["+i+"]/td[3]")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Name '"+sFunValue[0]+"' should match", "Account Name '"+accName+"' is matching", "PASS");
				status = true;
			}
			else
			{
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Account Name '"+sFunValue[0]+"' should match", "Account Name '"+accName+"' is not matching", "FAIL");
			}
		}			
		return status;
	}
	
	public boolean SetContactAsPrimary(String sNewAddress) throws Exception
	{
		boolean status = false;
		WebElement tableRowXpathRole = driver.findElement(Common.o.getObject("eleNewAddressTable"));
		List<WebElement> rows_table1 = tableRowXpathRole.findElements(By.tagName("tr"));
		int rows_count1 = rows_table1.size();
		for(int i=1;i<=rows_count1;i++)
		{
			String sAcutalAddress = driver.findElement(By.xpath(".//*[@id='EditAccountContactPopup:ContactDetailScreen:AccountContactCV:AddressesPanelSet:AddressesLV-body']/div/table/tbody/tr["+i+"]/td[4]")).getText();
			if(sAcutalAddress.contains(sNewAddress))
			{
				driver.findElement(By.xpath(".//*[@id='EditAccountContactPopup:ContactDetailScreen:AccountContactCV:AddressesPanelSet:AddressesLV-body']/div/table/tbody/tr["+i+"]/td[2]")).click();
				HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "New Address '"+sNewAddress+"' should mark as Primary", "New Address '"+sAcutalAddress+"' is Marked as Primary", "PASS");
				status = true;
			}
		}
		if(status==false)
		{
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "New Address '"+sNewAddress+"' should mark as Priamry", "New Address '"+sNewAddress+"' is not Marked as Priamry", "FAIL");
			status = true;
		}
		return status;
	}
	
	public boolean AddAddressEnable() throws Exception, Exception
	{
		boolean status = true;
		common.WaitUntilClickable(Common.o.getObject("eleAddOK"),  Integer.valueOf(HTML.properties.getProperty("SHORTWAIT")));
		return status;
	}
}