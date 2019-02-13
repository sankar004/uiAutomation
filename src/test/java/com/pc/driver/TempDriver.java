package com.pc.driver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import com.codoid.products.fillo.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;

import com.codoid.products.fillo.Fillo;
import com.pc.constants.PCConstants;
import com.pc.dao.E2EUtilityDAO;
import com.pc.utilities.CommonManager;

import java.sql.DriverManager;

import com.pc.utilities.HTML;
import com.pc.utilities.LocalDriverFactory;
import com.pc.utilities.PCThreadCache;
import com.pc.utilities.RemoteDriverFactory;
import com.pc.utilities.Common;
import com.pc.utilities.ReportUtil;
import com.pc.utilities.XlsxReader;

public class TempDriver
{	
	
	
	
	   private String baseUrls;
	   private static ConcurrentLinkedQueue<String> testCaseNames = new ConcurrentLinkedQueue<String>();
	   
	   @BeforeSuite
	   public void loadConfig() throws Exception {
		
		    PropertyConfigurator.configure("log4j.properties");
			try {
				HTML.fnSummaryInitialization("Execution Summary Report");
				XlsxReader.getInstance().addTestCasesFromDataSheetName(testCaseNames);
				System.out.println( "Inside BeforeSuite"); 
				//ReportUtil.loadTestCases();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	   }
	
	
	
	
	
	@Parameters({ "DataSheetName","Region" })
	@Test(priority=1,enabled=true)
	public void testParallel3(String DataSheetName, String Region) throws Exception
	{
		
		//Starting test code
		/* String testCaseName =  "SearchAccount_01|E2E_1|2";
		 String e2eTestCaseId = null;
		 String e2eMasterSheetId = null;
		 StringTokenizer st = new StringTokenizer(testCaseName,"|");
		 if(st.hasMoreTokens()){
			 testCaseName = st.nextToken();
		 }
		 if(st.hasMoreTokens()){
			 e2eTestCaseId = st.nextToken();
		 }
		 if(st.hasMoreTokens()){
			 e2eMasterSheetId = st.nextToken();
		 }
		
		ArrayList<HashMap<String,Object>> e2eMappingRows= new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> whereClause = new HashMap<String, Object>();
		whereClause.put("TestCaseName", testCaseName);
		e2eMappingRows = XlsxReader.getInstance().executeSelectQuery("E2EDBREAD", whereClause);
		
		E2EUtilityDAO e2eUtilDao = new E2EUtilityDAO();
		String e2eAccouuntNumber = e2eUtilDao.getAccountNumber(e2eTestCaseId);
		String e2ePolicyNumber = e2eUtilDao.getPolicyNumber(e2eTestCaseId);
		e2eUtilDao.closeConnections();
		e2eUtilDao = null; //Mark for Garbage collection
		if(!e2eMappingRows.isEmpty()){
			for(HashMap<String,Object> processRow: e2eMappingRows){
				String sheetName = (String)processRow.get("SheetName");
				String columnName = (String)processRow.get("ColumnName");
				String valueToUpdate = (String)processRow.get("ValueToUpdate");
				String testCaseID = (String)processRow.get("ID");
				log.info("sheetName = " +sheetName);
				log.info("columnName = " +columnName);
				log.info("valueToUpdate = " +valueToUpdate);
				log.info("testCaseID = " +testCaseID);
				if(sheetName != null && sheetName.length() > 0 &&
					columnName != null && columnName.length() > 0 &&
					valueToUpdate != null && valueToUpdate.length() > 0){
						
						HashMap<String,Object> updateWhereClause = new HashMap<String, Object>();
						HashMap<String,Object> updateColumnValue = new HashMap<String, Object>();
						String value = null;
						if(valueToUpdate != null && valueToUpdate.equalsIgnoreCase("accountnumber")){
							value = e2eAccouuntNumber;
						} else if(valueToUpdate != null && valueToUpdate.equalsIgnoreCase("policynumber")){
							value = e2ePolicyNumber;
						}
						updateColumnValue.put(columnName, value);
						
						updateWhereClause.put("ID", testCaseID);
						
						XlsxReader.getInstance().executeUpdateQuery(sheetName, updateColumnValue, updateWhereClause);
				}
				
			}
		}
		
		E2EUtilityDAO e2eUtilityDAO = new E2EUtilityDAO();
		e2eUtilityDAO.updateE2EMasterStatus(e2eMasterSheetId, "pass");
		e2eUtilityDAO.closeConnections();
		e2eUtilityDAO = null; *///Mark for Garbage collection
		 Connection ucaConn;
		Statement 	st;
		ResultSet rs;
		//Ending test code
		// Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		 String dir = null;
			File directory = new File (".");
			dir = directory.getCanonicalPath();
			
	         
		// String url = "jdbc:ucanaccess:////ad1.prod/hig/commercial/ComPAS/temp/E2E/Regression/EXECUTIONREPORT.accdb"; 
	//	 ucaConn = DriverManager.getConnection(url);
	
		// ucaConn.setAutoCommit(true);
		// ucaConn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		// st = ucaConn.createStatement();
		
		boolean exitLoop = false;
		String testCaseName = null;
		try{
        	testCaseName = testCaseNames.remove();
        } catch(NoSuchElementException e){
        	exitLoop = true;
        }
		if (testCaseName==null){
			exitLoop = true;
		}
		//while(!exitLoop){
			
			String execMode = HTML.properties.getProperty("EXECUTIONMODE");
			  //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			WebDriver driver = null;
			//System.out.println("TestParallel3  Started = " + Thread.currentThread().getId());
			//System.out.println( " Mode =" + execMode ); 
			if(execMode.equalsIgnoreCase(PCConstants.executionModeLocal)){
				driver = LocalDriverFactory.getInstance().createNewDriver();
			}else {
				driver = RemoteDriverFactory.getInstance().createNewDriver();
			}
			
			//System.out.println("TestParallel3  Started = " + Thread.currentThread().getId());
			//System.out.println("Test PArallel3 driver = " + driver  ); 
			Common common = new Common();
			CommonManager.getInstance().setCommon(common);
	         com.pc.utilities.ManagerDriver.getInstance().setWebDriver(driver);
			
	      //  System.out.println("Thread ID = " + Thread.currentThread().getId() + " common = "+ common);
	        common.RunTest("RunModeNo","TC26","","");
	        
	         
//	       String strColumnName = "ID";
//			String  strCondition = testCaseName;
//			Integer TCRow;  
//	        HTML.properties.setProperty("DataSheetName",DataSheetName);
//		      PCThreadCache.getInstance().setProperty("DataSheetName",DataSheetName);
//		      
//		      XlsxReader sXL = XlsxReader.getInstance(); //new XlsxReader(DataSheetName);
//			  String sheetname = "TestCase";
//			  //System.out.println("Execution Step 0....strCondition="+strCondition+" ;");
//			  int rowcount = sXL.getRowCount(sheetname);
//			  for(int i = 2; i <= rowcount; i++)
//			  {		
//				  if(sXL.getCellData(sheetname, strColumnName, i).equalsIgnoreCase(strCondition))
//				  {
//					  
//					  boolean ScriptLevelStatus = true;
//					  
//					  String TCID  = sXL.getCellData(sheetname, "ID", i);
//					  String TestCaseID  = sXL.getCellData(sheetname, "TestCaseID", i);
//					  String TestSetID  = sXL.getCellData(sheetname, "TestSetID", i);
//					  String testCaseType = sXL.getCellData(sheetname, "TestCaseType", i);
//					  
//					  
//					  String testcasename = sXL.getCellData(sheetname, "TestCaseName", i);
//					  
//					 
//			  			
//					   int colcount = sXL.getColumnCount(sheetname);
//			  			for(int j = 2; j <= colcount; j++)
//			  			{
//			  				try
//							  {
//			  						String ColName = sXL.getCellData(sheetname, j, 1);
//							  		if(ColName.contains("Component"))
//									  {
//							  				TCRow = i;
//							  				String methodName = sXL.getCellData(sheetname, j, i);
//							  				//HTML.properties.setProperty("methodName",methodName);
//							  				PCThreadCache.getInstance().setProperty("methodName",methodName);
//							  				////log.info("methodName ======"+methodName + Thread.currentThread().getId());
//							  				
//							  				if (!methodName.isEmpty())
//							  				{
//												int count = sXL.totalNumberOfSteps(methodName,TCID);
//												String url = "C:\\Selenium\\workspace\\PC\\Data\\Data - Copy.xlsm";
//												Fillo fillo=new Fillo();
//										         Connection connection=fillo.getConnection(url);
//												String query = "insert into ComponentSummary(ID, Component,NoSteps) VALUES('"+TCID+"','"+methodName+"','"+count+"')";
//												System.out.println("executing query = "+query);
//												connection.executeUpdate(query);
//												connection.close();
//												//st.execute(query);
//							  				}
//						  			}
//							  }
//							  catch(Exception e)
//							  {
//								    e.printStackTrace();
//								  	ScriptLevelStatus = false;
//									break; 
//							  }
//			  			}
//						
//				  }
//			  }
//	        
//	        
//	        try{
//	        	testCaseName = testCaseNames.remove();
//	        } catch(java.util.NoSuchElementException e){
//	        	exitLoop = true;
//	        }
//	        if (testCaseName==null){
//				exitLoop = true;
//			}
	//}
	
	}
	
	
	
	   @AfterSuite
	   public void exitConfig() {
		
		    	try {
		    		XlsxReader.getInstance().closeConnections();
					HTML.fnSummaryCloseHtml(HTML.properties.getProperty("Release"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println( "Inside AfterSuite"); 
		}
	
}
