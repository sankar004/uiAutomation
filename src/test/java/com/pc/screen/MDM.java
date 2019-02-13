package com.pc.screen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.xpath.regex.ParseException;

import com.codoid.products.fillo.Recordset;
import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;

public class MDM {
	
	public static String sheetname = "MDM";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	
	public Boolean SCRMDM() throws Exception{

		Boolean status = true;
		com.pc.screen.SCRCommon.MDMWait();
		status = common.ClassComponent(sheetname, Common.o);
		if(!status)
		{
			return false;
		}					
		return status;
	}
	
	public boolean DBValidation() throws Exception
    {
		Boolean Status = false;
		Status = GetDataFromDB();
		return Status;
    }
    
	public boolean GetDataFromDB() throws Exception
    { 
				boolean sStatus = true;
				boolean Status = true;
				String Schema = null;
				String URL = null;
				String UserName = null;
				String Password = null;
				
						switch (HTML.properties.getProperty("Region").toUpperCase())
						{			
							case "QA":
								Schema = HTML.properties.getProperty("QAMDMSchema");
								URL = HTML.properties.getProperty("QAURL");
								
								UserName = HTML.properties.getProperty("QAMDMODSUserID");
								Password = HTML.properties.getProperty("QAMDMODSPassword");
								break;
							case "INT":
								Schema = HTML.properties.getProperty("INTMDMSchema");
								URL = HTML.properties.getProperty("INTURL");
								
								UserName = HTML.properties.getProperty("INTMDMODSUserID");
								Password = HTML.properties.getProperty("INTMDMODSPassword");
								break;
							case "LTQ":
								Schema = HTML.properties.getProperty("LTQMDMSchema");
								URL = HTML.properties.getProperty("INTURL");
								
								UserName = HTML.properties.getProperty("INTMDMODSUserID");
								Password = HTML.properties.getProperty("INTMDMODSPassword");
								break;
							case "LTI":
								Schema = HTML.properties.getProperty("LTIMDMSchema");
								URL = HTML.properties.getProperty("INTURL");
								
								UserName = HTML.properties.getProperty("INTMDMODSUserID");
								Password = HTML.properties.getProperty("INTMDMODSPassword");
								break;
							case "LTA":
								Schema = HTML.properties.getProperty("LTAMDMSchema");
								URL = HTML.properties.getProperty("INTURL");
								
								UserName = HTML.properties.getProperty("INTMDMODSUserID");
								Password = HTML.properties.getProperty("INTMDMODSPassword");
								break;
						}
					
					ResultSet rs2 = null;
					XlsxReader sXL = XlsxReader.getInstance();
					HashMap<String,Object> whereConstraint = new HashMap<String,Object>();
					HashMap<String,Object> updateColumnNameValues = new HashMap<String,Object>();
					ArrayList<HashMap<String,Object>> resultListRows= new ArrayList<HashMap<String,Object>>();
					
					String strSQLStatement = null;
					String strInPut = null;
					String strDBfieldValue = null;
					String strAccountNumber = null;
					String strAffinityGrpName=null;
					String strPolicyNumber = null;
					String strSQLStatement1 = null;
					String strSQLStatement2 = null;
					String strCheckQuery = "";
					String strFileName = HTML.properties.getProperty("suitepath") + "\\Data\\ODSData.xlsm";
					
					whereConstraint.put(PCConstants.Environment, "MDM");
					whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
					resultListRows = sXL.executeSelectQuery(PCConstants.SHEET_VALIDATION, whereConstraint);
					
					//String strExcelQuery = "Select * from Validation where Execution ='YES' AND Environment='MDM' AND ID ='" + PCThreadCache.getInstance().getProperty("TCID") + "'";
					//Recordset rs1 = common.GetDataFromExcel(strFileName,strExcelQuery);
					if(!resultListRows.isEmpty())
					{
						for(HashMap<String,Object> processRow: resultListRows)
						{
							strSQLStatement = (String)processRow.get("SQL");
							strInPut = (String)processRow.get("InPut");
							String strDBfieldName = (String)processRow.get("DBFieldName");
							String strDBfieldName1 = strDBfieldName.toUpperCase();
							String strExcelPCFieldValue = (String)processRow.get("PCFieldValue");
							String strTranslationLogic = (String)processRow.get("TranslationLogic");
							Boolean strExecuteQuery = false;
							switch (strInPut)
							{
								case "AccountNumber":
									{
										strAccountNumber = (String)processRow.get("AccountNumber");
										strSQLStatement1 = strSQLStatement.replace("$AccountNumber$", strAccountNumber);
										strSQLStatement2 = strSQLStatement1.replace("$SchemaName$", Schema);
										break;
									}
								case "PolicyNumber":
									{
										strPolicyNumber = (String)processRow.get("PolicyNumber");	
										strSQLStatement1 = strSQLStatement.replace("$PolicyNumber$", strPolicyNumber);
										strSQLStatement2 = strSQLStatement1.replace("$SchemaName$", Schema);
										break;
									}
								case "AffinityGroup_AffinityName":
									{
										strAffinityGrpName = (String)processRow.get("AffinityGroup_AffinityName");	
										strSQLStatement1 = strSQLStatement.replace("$AffinityGroup_AffinityName$", strAffinityGrpName);
										strSQLStatement2 = strSQLStatement1.replace("$SchemaName$", Schema);
										break;
									}
									
							}
							
							 if (!strCheckQuery.equals(strSQLStatement2))
							 {
								 rs2 = ConnectDB(strSQLStatement2,URL,UserName,Password);
								 while(rs2.next())
								 {     
									 strExecuteQuery = true;
									 ResultSetMetaData rsmd = rs2.getMetaData();
									 int columnCount = rsmd.getColumnCount();
									  for (int i=1;i<=columnCount;i++)
									  {
										  String ColumnName = rsmd.getColumnName(i);
										  strDBfieldValue = rs2.getString(i);
										  if(ColumnName.equals(strDBfieldName1))
										  {
											  updateColumnNameValues.clear();
											  whereConstraint.clear();
											  updateColumnNameValues.put(PCConstants.DBFieldValue, strDBfieldValue);
											  whereConstraint.put(PCConstants.DBFieldName, strDBfieldName);
											  whereConstraint.put(PCConstants.Environment, "MDM");
											  whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
											  sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
											  //common.UpdateDataInExcel(strFileName, "Update Validation Set DBFieldValue ='" + strDBfieldValue + "' where DBFieldName ='" +strDBfieldName+ "' and Environment='MDM' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
											break;
										  }
									  }
									  break;
								 }
							 }
							 else
							 {
								 	 strExecuteQuery = true;
									 ResultSetMetaData rsmd = rs2.getMetaData();
									 int columnCount = rsmd.getColumnCount();
									  for (int i=1;i<=columnCount;i++)
									  {
										  String ColumnName = rsmd.getColumnName(i);
										  strDBfieldValue = rs2.getString(i);
										  if(ColumnName.equals(strDBfieldName1))
										  {
											  updateColumnNameValues.clear();
											  whereConstraint.clear();
											  updateColumnNameValues.put(PCConstants.DBFieldValue, strDBfieldValue);
											  whereConstraint.put(PCConstants.DBFieldName, strDBfieldName);
											  whereConstraint.put(PCConstants.Environment, "MDM");
											  whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
											  sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
											  //common.UpdateDataInExcel(strFileName, "Update Validation Set DBFieldValue ='" + strDBfieldValue + "' where DBFieldName ='" +strDBfieldName+ "' AND Environment='MDM' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
											break;
										  }
									  }
								 }
							 if(strExecuteQuery)
							 {
								 Status = fnCompareDBandPC(strDBfieldValue,strExcelPCFieldValue,strTranslationLogic);
								 if(Status){
									  updateColumnNameValues.clear();
									  whereConstraint.clear();
									  updateColumnNameValues.put(PCConstants.Status, "PASS");
									  whereConstraint.put(PCConstants.DBFieldName, strDBfieldName);
									  whereConstraint.put(PCConstants.Environment, "MDM");
									  whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
									  sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
									  //common.UpdateStatusInExcel(strFileName, "Update Validation Set Status ='PASS' where DBFieldName ='" +strDBfieldName+ "' AND Environment='MDM' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
									  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "PC Value - '" + strExcelPCFieldValue + "' should match with ODS Value - '" + strDBfieldValue + "'","PC Value - '" + strExcelPCFieldValue + "' matched with ODS Value - '" + strDBfieldValue + "'", "PASS");
									}
									else
									{
										  updateColumnNameValues.clear();
										  whereConstraint.clear();
										  updateColumnNameValues.put(PCConstants.Status, "FAIL");
										  whereConstraint.put(PCConstants.DBFieldName, strDBfieldName);
										  whereConstraint.put(PCConstants.Environment, "MDM");
										  whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
										  sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
										//common.UpdateStatusInExcel(strFileName, "Update Validation Set Status ='FAIL' where DBFieldName ='" +strDBfieldName+ "' AND Environment='MDM' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
										HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "PC Value - '" + strExcelPCFieldValue + "' should match with ODS Value - '" + strDBfieldValue + "'","PC Value - '" + strExcelPCFieldValue + "' is not matched with ODS Value - '" + strDBfieldValue + "'", "FAIL");
									}
								 strCheckQuery = strSQLStatement2;
							 }
							 else
							 {
								  updateColumnNameValues.clear();
								  whereConstraint.clear();
								  updateColumnNameValues.put(PCConstants.Status, "FAIL");
								  whereConstraint.put(PCConstants.DBFieldName, strDBfieldName);
								  whereConstraint.put(PCConstants.Environment, "MDM");
								  whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
								  sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
								  //common.UpdateStatusInExcel(strFileName, "Update Validation Set Status ='FAIL' where DBFieldName ='" +strDBfieldName+ "' AND Environment='MDM' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
								  HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Execute Query for the field '" + strDBfieldName1 + "'","Query did not return any Record", "FAIL");
								  sStatus = false;
							 }
							 
					  }
    				}
           return sStatus;
    }
	
	public boolean fnCompareDBandPC(String strDBfieldValue,String strExcelPCFieldValue, String strTranslationLogic) throws Exception
	{
		boolean Status = false;
		String strDBfieldValue1=null;
		if(strTranslationLogic == "")
		{
			if(strDBfieldValue.equals(strExcelPCFieldValue) || strExcelPCFieldValue.equals(strDBfieldValue) )
				Status = true;
		}
		else
		{
			switch (strTranslationLogic)
			{
			case "Text":
				{
					if(strDBfieldValue == null && strExcelPCFieldValue == null)
					{
						Status = true;
					}
					else if(strDBfieldValue.equals(strExcelPCFieldValue) || strExcelPCFieldValue.equals(strDBfieldValue) )
					{
						Status = true;
					}
					break;
				}				
			case "Date":
				{	
					if(strDBfieldValue == null && strExcelPCFieldValue == null)
					{
						Status = true;
					}
					else
					{
						try{
							SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Date date=defFormat.parse(strDBfieldValue);
							SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
							strDBfieldValue1 = sdfDestination.format(date);
							
							if (strDBfieldValue1.equals(strExcelPCFieldValue) || strExcelPCFieldValue.equals(strDBfieldValue1) )
							{
								Status = true;
							}
						}catch (ParseException exp){
							exp.printStackTrace();
						}
					}
					break;
				}
			case "State":
				{
					if(strDBfieldValue == null && strExcelPCFieldValue == null)
					{
						Status = true;
					}
					switch(strDBfieldValue)
					{
						case "CT":
							strDBfieldValue1 = "Connecticut";
							break;
					}
					
					if(strDBfieldValue1.equals(strExcelPCFieldValue) || strExcelPCFieldValue.equals(strDBfieldValue1) )
					{
						Status = true;
					}
					break;
				}			
			}
		}
		return Status;
	}
	
	public ResultSet ConnectDB(String strSQLStatement,String strURL,String strUserName,String strPassword) throws ClassNotFoundException, SQLException
    {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(strURL,strUserName,strPassword);
		Statement stment = conn.createStatement();
		ResultSet rs = stment.executeQuery(strSQLStatement);
		return rs;
    }
}
