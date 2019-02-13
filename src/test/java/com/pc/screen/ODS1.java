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
import com.pc.constants.PCConstants;
import com.pc.utilities.Common;
import com.pc.utilities.CommonManager;
import com.pc.utilities.HTML;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.XlsxReader;


public class ODS1 {
	
	public static String sheetname = "ODS";
	static Logger logger =Logger.getLogger(sheetname);
	Common common = CommonManager.getInstance().getCommon();
	
	public Boolean SCRODS() throws Exception{

		Boolean status = true;
	//	com.pc.screen.SCRCommon.ODSWait();
		status = common.ClassComponent(sheetname, Common.o);
		
		if(!status)
		{
			logger.info("ODS Component was not Successfully Executed");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Execute ODS Component","ODS Component was not Successfully Executed", "FAIL");
			return false;
		}
		
		else
		{
			logger.info("ODS Component was Successfully executed");
			HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Execute ODS Component","ODS Component was Successfully Executed", "PASS");
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
								Schema = HTML.properties.getProperty("QAODSSchema");
								URL = HTML.properties.getProperty("QAURL");
								
								UserName = HTML.properties.getProperty("QAMDMODSUserID");
								Password = HTML.properties.getProperty("QAMDMODSPassword");
								break;
							case "INT":
								Schema = HTML.properties.getProperty("INTODSSchema");
								URL = HTML.properties.getProperty("INTURL");
								
								UserName = HTML.properties.getProperty("INTMDMODSUserID");
								Password = HTML.properties.getProperty("INTMDMODSPassword");
								break;
							case "LTQ":
								Schema = HTML.properties.getProperty("LTQODSSchema");
								URL = HTML.properties.getProperty("INTURL");
								
								UserName = HTML.properties.getProperty("INTMDMODSUserID");
								Password = HTML.properties.getProperty("INTMDMODSPassword");
								break;
							case "LTI":
								Schema = HTML.properties.getProperty("LTIODSSchema");
								URL = HTML.properties.getProperty("INTURL");
								
								UserName = HTML.properties.getProperty("INTMDMODSUserID");
								Password = HTML.properties.getProperty("INTMDMODSPassword");
								break;
							case "LTA":
								Schema = HTML.properties.getProperty("LTAODSSchema");
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
					String strUserName = null;
					int ODSWaitRepeatCount = 0;
					boolean SameQuery = false;
					String strCheckQuery = "";
					String strFileName = HTML.properties.getProperty("suitepath") + "\\Data\\ODSData.xlsm";
					
					whereConstraint.put(PCConstants.Environment, "ODS");
					whereConstraint.put(PCConstants.Execution, "YES");
					whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
					resultListRows = sXL.executeSelectQuery(PCConstants.SHEET_VALIDATION, whereConstraint);
					
					//String strExcelQuery = "Select * from Validation where Execution ='YES' AND Environment = 'ODS' AND ID ='" + PCThreadCache.getInstance().getProperty("TCID") + "'";
					//Recordset rs1 = common.GetDataFromExcel(strFileName,strExcelQuery);
					if(!resultListRows.isEmpty())
					{
						for(HashMap<String,Object> processRow: resultListRows)
						{
							strSQLStatement = (String)processRow.get("SQL");
							strInPut = (String)processRow.get("InPut");
							String strDBfieldName = (String)processRow.get("DBFieldName");
							String strDBfieldName1 = strDBfieldName.toUpperCase();
							String strExcelPCFieldName = (String)processRow.get("PCFieldName");
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
								case "AffinityGroup_CPID":
									{
										strAffinityGrpName = (String)processRow.get("AffinityGroup_CPID");	
										strSQLStatement1 = strSQLStatement.replace("$AffinityGroup_CPID$", strAffinityGrpName);
										strSQLStatement2 = strSQLStatement1.replace("$SchemaName$", Schema);
										break;
									}
								case "UserName":
									{
										strUserName = (String)processRow.get("User_UserName");	
										strSQLStatement1 = strSQLStatement.replace("$UserName$", strUserName);
										strSQLStatement2 = strSQLStatement1.replace("$SchemaName$", Schema);
										break;
									}
									
							}
							
							 if (!strCheckQuery.equals(strSQLStatement2))
								 
							 {
								 int odsWait = Integer.valueOf(HTML.properties.getProperty("ODSWAIT"));
								 int odswait1 = odsWait/6;
								 int Counter =0;
								 for(int k=1;k<=odsWait/10000;k++)
								 {
									 rs2 = ConnectDB(strSQLStatement2,URL,UserName,Password);
									 while(rs2.next())
									 {   
										 Counter=Counter+1;  
										 strExecuteQuery = true;
										 SameQuery = true;
										 ResultSetMetaData rsmd = rs2.getMetaData();
										 int columnCount = rsmd.getColumnCount();
										  for (int i=1;i<=columnCount;i++)
										  {
											  String ColumnName = rsmd.getColumnName(i);
											  strDBfieldValue = rs2.getString(i);
											 
											  if(ColumnName.equals(strDBfieldName1))
											  {
												  if (strDBfieldValue== null)
												  {
													  strDBfieldValue = "";
												  }
												  updateColumnNameValues.clear();
												  whereConstraint.clear();
												  updateColumnNameValues.put(PCConstants.DBFieldValue, strDBfieldValue);
												  whereConstraint.put(PCConstants.DBFieldName, strDBfieldName);
												  whereConstraint.put(PCConstants.Environment, "ODS");
												  whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
												  sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
												  
												  //common.UpdateDataInExcel(strFileName, "Update Validation Set DBFieldValue ='" + strDBfieldValue + "' where DBFieldName ='" +strDBfieldName+ "' and Environment='ODS' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
												break;
											  }
										  }
										  break;
								 }
								 if(!(Counter==0))
									 {
									 break;
									 }
								 else
									 {
										  if(!(ODSWaitRepeatCount==0) && (ODSWaitRepeatCount==6))
										  {
											  break;
										  }
										  else
										  {
											 SCRCommon.ODSWait(odswait1);
											 ODSWaitRepeatCount = ODSWaitRepeatCount+1;
										  }
									 }
								}
								 
							 }
							 else
							 {
								 	if(!SameQuery == false )
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
												  if (strDBfieldValue== null)
												  {
													  strDBfieldValue = "";
												  }
												  updateColumnNameValues.clear();
												  whereConstraint.clear();
												  updateColumnNameValues.put(PCConstants.DBFieldValue, strDBfieldValue);
												  whereConstraint.put(PCConstants.DBFieldName, strDBfieldName);
												  whereConstraint.put(PCConstants.Environment, "ODS");
												  whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
												  sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
												  //common.UpdateDataInExcel(strFileName, "Update Validation Set DBFieldValue ='" + strDBfieldValue + "' where DBFieldName ='" +strDBfieldName+ "' and Environment = 'ODS' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
												break;
											  }
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
									 	whereConstraint.put(PCConstants.Environment, "ODS");
									 	whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
									 	sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
									    //common.UpdateStatusInExcel(strFileName, "Update Validation Set Status ='PASS' where DBFieldName ='" +strDBfieldName+ "' and Environment = 'ODS' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
										logger.info("Verify Field -'"+strExcelPCFieldName+ "'.PC Value - '" + strExcelPCFieldValue + "' is matched with ODS Value - '" + strDBfieldValue + "'");
										HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify Field -'"+strExcelPCFieldName+ "'.PC Value - '" + strExcelPCFieldValue + "' should match with ODS Value - '" + strDBfieldValue + "'","Verified Field -'"+strExcelPCFieldName+ "'.PC Value - '" + strExcelPCFieldValue + "' matched with ODS Value - '" + strDBfieldValue + "'", "PASS");
									}
									else
									{
										updateColumnNameValues.clear();
									 	whereConstraint.clear();
									 	updateColumnNameValues.put(PCConstants.Status, "FAIL");
									 	whereConstraint.put(PCConstants.DBFieldName, strDBfieldName);
									 	whereConstraint.put(PCConstants.Environment, "ODS");
									 	whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
									 	sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
										//common.UpdateStatusInExcel(strFileName, "Update Validation Set Status ='FAIL' where DBFieldName ='" +strDBfieldName+ "' and Environment = 'ODS' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
										logger.info("Verify Field -'"+strExcelPCFieldName+ "'.PC Value - '" + strExcelPCFieldValue + "' is not matched with ODS Value - '" + strDBfieldValue + "'");
										HTML.fnInsertResult(PCThreadCache.getInstance().getProperty("testcasename"), PCThreadCache.getInstance().getProperty("methodName"), "Verify Field -'"+strExcelPCFieldName+ "'.PC Value - '" + strExcelPCFieldValue + "' should match with ODS Value - '" + strDBfieldValue + "'","Verified Field -'"+strExcelPCFieldName+ "'.PC Value - '" + strExcelPCFieldValue + "' is not matched with ODS Value - '" + strDBfieldValue + "'", "FAIL");
									}
								 strCheckQuery = strSQLStatement2;
							 }
							 else
							 {		
								 	updateColumnNameValues.clear();
							 		whereConstraint.clear();
							 		updateColumnNameValues.put(PCConstants.Status, "FAIL");
							 		whereConstraint.put(PCConstants.DBFieldName, strDBfieldName);
							 		whereConstraint.put(PCConstants.Environment, "ODS");
							 		whereConstraint.put(PCConstants.ID, PCThreadCache.getInstance().getProperty("TCID"));
							 		sXL.executeUpdateQuery(PCConstants.SHEET_VALIDATION,  updateColumnNameValues,  whereConstraint );
								 	//common.UpdateStatusInExcel(strFileName, "Update Validation Set Status ='FAIL' where DBFieldName ='" +strDBfieldName+ "' and Environment = 'ODS' and ID = '" + PCThreadCache.getInstance().getProperty("TCID") + "'");
								 	logger.info("Query did not return any Record");
								 	SameQuery = false;
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
		String strExcelPCFieldValue1=null;
		String strExcelPCFieldValue2=null;
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
					if (!(strDBfieldValue == null) || !(strExcelPCFieldValue == null))
					{
						if(strExcelPCFieldValue.toString().toUpperCase().equals(strDBfieldValue.toString().toUpperCase()))
						{
							Status = true;
						}	
					}
					
					break;
				}	
			case "Segment":
			{	
				switch(strDBfieldValue)
				{
					case "SC":
						strDBfieldValue1 = "Small Commercial";
						break;
				}
			
				if (!(strDBfieldValue == "") && !(strExcelPCFieldValue == null))
				{
					if(strExcelPCFieldValue.toString().toUpperCase().equals(strDBfieldValue1.toString().toUpperCase()))
					{
						Status = true;
					}	
				}
				else if ((strDBfieldValue == "") && (strExcelPCFieldValue == null))
				{
					Status = true;
				}
				else if (!(strDBfieldValue == "") || !(strExcelPCFieldValue == null))
				{
					Status = false;
				}
				break;
			}	
			case "SpaceContains":
			{	
				strExcelPCFieldValue1= strExcelPCFieldValue.replaceAll(" ","");
			
				if (!(strDBfieldValue == null) || !(strExcelPCFieldValue == null))
				{
					if(strExcelPCFieldValue1.toString().toUpperCase().contains(strDBfieldValue.toString().toUpperCase()) || strDBfieldValue.toString().toUpperCase().contains(strExcelPCFieldValue1.toString().toUpperCase()))
					{
						Status = true;
					}	
				}
				
				break;
			}
			case "Datetime":
				{	
					
						try{
							SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Date date=defFormat.parse(strDBfieldValue);
							SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
							strDBfieldValue1 = sdfDestination.format(date);
							
							SimpleDateFormat defFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
							Date date1=defFormat1.parse(strExcelPCFieldValue);
							SimpleDateFormat sdfDestination1 = new SimpleDateFormat("MM/dd/yyyy");
							strExcelPCFieldValue1 = sdfDestination1.format(date1);
							
							if (strDBfieldValue1.equals(strExcelPCFieldValue1) || strExcelPCFieldValue.equals(strDBfieldValue1) )
							{
								Status = true;
							}
						}catch (ParseException exp){
							exp.printStackTrace();
						}
					
					break;
				}
			case "Date":
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
				
				break;
			}
			case "DatetimeAMPM":
			{	
				
					try{
						SimpleDateFormat defFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Date date=defFormat.parse(strDBfieldValue);
						SimpleDateFormat sdfDestination = new SimpleDateFormat("MM/dd/yyyy");
						strDBfieldValue1 = sdfDestination.format(date);
						
						SimpleDateFormat defFormat1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
						Date date1=defFormat1.parse(strExcelPCFieldValue);
						SimpleDateFormat sdfDestination1 = new SimpleDateFormat("MM/dd/yyyy");
						strExcelPCFieldValue1 = sdfDestination1.format(date1);
						
						if (strDBfieldValue1.equals(strExcelPCFieldValue1) || strExcelPCFieldValue.equals(strDBfieldValue1) )
						{
							Status = true;
						}
					}catch (ParseException exp){
						exp.printStackTrace();
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
						case "OH":
							strDBfieldValue1 = "Ohio";
							break;
						case "US":
							strDBfieldValue1 = "United States";
							break;
						case "CA":
							strDBfieldValue1 = "California";
							break;
						case "TX":
							strDBfieldValue1 = "Texas";
							break;
						case "AR":
							strDBfieldValue1 = "Arkansas";
							break;
					}
					
					if(strDBfieldValue1.equals(strExcelPCFieldValue) || strExcelPCFieldValue.equals(strDBfieldValue1) )
					{
						Status = true;
					}
					break;
				}
			case "Hyphen":
			{	
				 strExcelPCFieldValue1= strExcelPCFieldValue.replaceAll("-","");
				String strDBfieldValue2= strDBfieldValue.replaceAll("-","");
				
			
				if (!(strDBfieldValue == null) || !(strExcelPCFieldValue == null))
				{
					if(strExcelPCFieldValue1.toString().toUpperCase().contains(strDBfieldValue2.toString().toUpperCase()))
					{
						Status = true;
					}	
				}
				
				break;
			}
			case "MapPCODS":
			{
				if(strDBfieldValue == null && strExcelPCFieldValue == null)
				{
					Status = true;
				}
				switch(strDBfieldValue)
				{
					case "WC7WorkersComp":
						strDBfieldValue1 = "Workers Compensation";
						break;
					case "HIGCOMPROP":
						strDBfieldValue1 = "Commercial Property";
						break;
					case "relNonRenewal":
						strDBfieldValue1 = "Release Non Renewal";
						break;
					case "PSRenewal_Ext":
						strDBfieldValue1 = "Renewal";
						break;
					case "custCareTeam":
						strDBfieldValue1 = "Customer Care Team";
						break;
				}
				
				if(strDBfieldValue1.equals(strExcelPCFieldValue) || strExcelPCFieldValue.equals(strDBfieldValue1) )
				{
					Status = true;
				}
				break;
			}
			
			case "Contains":
			{	
			
				if (!(strDBfieldValue == null) || !(strExcelPCFieldValue == null))
				{
					if(strExcelPCFieldValue.toString().toUpperCase().contains(strDBfieldValue.toString().toUpperCase()) || (strDBfieldValue.toString().toUpperCase().contains(strExcelPCFieldValue.toString().toUpperCase())))
					{
						Status = true;
					}	
				}
				
				break;
			}

			case "YesNo":
			{
				if(strExcelPCFieldValue.equalsIgnoreCase("Yes"))
					strExcelPCFieldValue1 = "Y";
				else if(strExcelPCFieldValue.equalsIgnoreCase("No"))
					strExcelPCFieldValue1 = "N";				
			
				if (!(strDBfieldValue == null) || !(strExcelPCFieldValue == null))
				{
					if(strExcelPCFieldValue1.equalsIgnoreCase(strDBfieldValue.toString()))
					{
						Status = true;
					}	
				}
				
				break;
			}
			case "SpaceHyphen":
				{	
					strExcelPCFieldValue1= strExcelPCFieldValue.replaceAll(" ","");
					strExcelPCFieldValue2= strExcelPCFieldValue1.replaceAll("-","");
				
					if (!(strDBfieldValue == null) || !(strExcelPCFieldValue == null))
					{
						if(strExcelPCFieldValue2.toString().toUpperCase().contains(strDBfieldValue.toString().toUpperCase()))
						{
							Status = true;
						}	
					}
					
					break;
				}
			case "PCContainsDB":
			{	
//				strExcelPCFieldValue1= strExcelPCFieldValue.replaceAll(".","");
			
				if (!(strDBfieldValue == null) || !(strExcelPCFieldValue == null))
				{
					if(strDBfieldValue.toString().toUpperCase().contains(strExcelPCFieldValue1.toString().toUpperCase()))
					{
						Status = true;
					}	
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