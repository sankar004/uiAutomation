/**
 * @ClassPurpose This Class used for the CustomerCompletion letter in SOR Policy
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

import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.ManagerDriver;
import com.pc.utilities.PCThreadCache;

public class QuoteProposalBuilder {
	
	public static String sheetname = "QuoteProposalBuilder";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	private WebDriver driver = ManagerDriver.getInstance().getWebDriver();
	
	/**
	 * @function Use to perform all the action in the QuoteProposalBuilder sheet
	 * @return true/false
	 * @throws Exception
	 */
	public Boolean SCRQuoteProposalBuilder() throws Exception
	{

		Boolean status = true;
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			return false;
		}
		
	return status;
	}
	

	 public boolean SelectLaunchbtn(String sReadString) throws Exception
    {
           boolean status = false;
           status = common.ActionOnTable(Common.o.getObject("eleQuoteProposalBuilderLaunch"), 2, 2, sReadString, "div");
           return status;
    }

	
	}
